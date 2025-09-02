$(function () {

  var userId = sessionStorage.getItem("userId")
  var token = sessionStorage.getItem("token2");


  $("#modify-info").click(function () {
    $("#mask-modify-info").show()
    $(".personal-data").show()
  })

  $(".add-oldPeople,.add-button").click(function () {
    $("#mask-SearchOldpeople").show()
    $(".SearchOldpeople").show()
  })



  $(".modify-pass").click(function () {
    $(".newPassword").show()
  })


  // 退出编辑资料页面
  $(".personal-data>.exit").click(function () {
    $(".mask").hide()
    $(".personal-data").hide()
  })

  //退出修改密码页面
  $(".newPassword>.exit").click(function () {
    $(".newPassword").hide();
  })

  // 退出搜索页面
  $(".SearchOldpeople .exit").click(function () {
    $(".mask").hide()
    $(".SearchOldpeople").hide()
  })


  // 触发文件上传

  $('。custom-file-upload').click(function () {
    $('#file-upload').click();
  });



  /**
   * 添加老人页面
   * 
   */

  //查询老人
  $("#searchResident").click(function () {
 
    var residentId;
    // 获取表单数据
    var data = $("#searchForm").serialize()

    // 在这里发送这些数据到后端进行处理
    $.ajax({
      type: "post",
      url: "http://localhost/Resident/findByNameAndIDCard",
      data: data,
      headers:{
        Token2:token
    },
      success: function (data) {
        const searchResultsDiv = $('#searchResults');
        searchResultsDiv.empty();
        if (data.code === 2001) {
          var resident = data.data;
          residentId = resident.id
          const resultElement = $('<div></div>').addClass('search-result');
          resultElement.append($('<p></p>').text('姓名: ' + resident.name));
          // 出于安全考虑，只显示部分身份证号
          resultElement.append($('<p></p>').text('身份证号: ' + resident.idCard.slice(0, 6) + '****' + resident.idCard.slice(-4)));
          resultElement.append($('<button><i class="iconfont icon-tianjia"></i></button>'));
          searchResultsDiv.append(resultElement);

        } else {
          const resultElement = $('<div></div>').addClass('search-result');
          resultElement.append($('<p  style="color: red;"></p>').text("没有找到该老人！！！"));
          searchResultsDiv.append(resultElement);
        }

        
      }

    })
    // 进行添加
    $("#searchResults").on('click', 'button', function () {
      $.ajax({
        type: "post",
        url: "http://localhost/UserController/addOldPeople",
        data: {
          userId: userId,
          residentId: residentId
        },
        headers:{
          Token2:token
      },
        success: function (data) {
          if (data.code == 1001) {
            init()
        
            alert(data.message)
          } else {
            alert(data.message)
          }
        }
      })
      $("#searchResults").empty()
      $("#searchName").val('')
      $("#searchIdNumber").val('')
    });
  });
  /**
      * 注销老人按钮
      */
  $('#logout-button').click(function () {
    var select_id = $("#elder-select").val()
    if (confirm("确认要注销吗！")) {
      $.ajax({
        type: "post",
        url: "http://localhost/UserController/cancelOldPeople",
        data: {
          userId: userId,
          residentId: select_id
        },
        headers:{
          Token2:token
      },
        success: function (data) {
          if (data.code == 1001) {
            $("#elder-select option").remove()
            $("#myResident > *").remove()
           
            init()
            var select_id = $("#elder-select").val()
            showMonitoringInfo(select_id)
             
          } else {
            alert(data.message)
          }
         
        }
      })

    }
  });

  /**
   * 我的申请导航栏切换
   */

  $(".already-passed").click(function () {
    $(".myApply .arlo_tm_portfolio_filter>li:first-child>div").show()
    $(".myApply .arlo_tm_portfolio_filter>li:not(:first-child)>div").hide()
    showAlreadyPassed(userId)

  })
  $(".pending-review").click(function () {
    $(".myApply .arlo_tm_portfolio_filter>li:eq(1)>div").show()
    $(".myApply .arlo_tm_portfolio_filter>li:not(:eq(1))>div").hide()
    showPendingReview(userId)
  })

  $(".rejected").click(function () {
    $(".myApply .arlo_tm_portfolio_filter>li:eq(2)>div").show()
    $(".myApply .arlo_tm_portfolio_filter>li:not(:eq(2))>div").hide()
    showRejected(userId)
  })

  /**
   * 我的申请分页
   */
  var nowPage = 1;
  var pageSize = 4;


  //上一页
  $("#prePage").click(function () {
    $("#already-passed ul li:not(:first)").remove();
    nowPage = nowPage - 1

    if (nowPage <= 1) {
      nowPage = 1
      $(this).addClass('disabled-button')
      $("#nextPage").removeClass('disabled-button')
    } else {
      $("#nextPage").removeClass('disabled-button');
    }
    showAlreadyPassed();
  })
  //下一页
  $("#nextPage").click(function () {
    $("#already-passed ul li:not(:first)").remove();
    nowPage = nowPage + 1;
    var lastPageNo = $("#page span a:last-child").html()
    if (nowPage >= lastPageNo) {
      nowPage = lastPageNo
      $(this).addClass('disabled-button')
      $("#prePage").removeClass('disabled-button');
    } else {
      $("#prePage").removeClass('disabled-button');
    }
    showAlreadyPassed();
  })
  //切换每页显示条数
  $("#pageSize").change(function () {
    $("#already-passed ul li:not(:first)").remove();
    pageSize = $(this).val()
    nowPage = 1
    showAlreadyPassed();
  })

  //点击页码跳转对应页
  $("#page span").on('click', 'a', function (event) {
    event.preventDefault()
    $("#already-passed ul li:not(:first)").remove();
    nowPage = $(this).html()
    showAlreadyPassed();

  })

  /**
   * 取消活动申请
   *  var applicationId = $("#elder-select").find('option:selected').data('nurse-id');
   */
  $("#pending-review").on('click', '.container .btn', function () {
    var applicationId = $(this).data('application-id');
    $.ajax({
      type: "get",
      url: "http://localhost/VolunteerController/remove",
      data: {
        id: applicationId
      },
      headers:{
        Token2:token
    },
      success: function (data) {
        if (data.code == 200) {
          alert("取消成功！")
          $("#pending-review>div").remove();
          showPendingReview()
        } else {
          alert("取消失败")
        }
      }

    })
  })







  /**
   * 我的老人导航栏切换
   */
  $(".eyeryDay").click(function () {
    $(".health-status .arlo_tm_portfolio_filter>li:first-child .eyeryDay-status").show()
    $(".health-status .arlo_tm_portfolio_filter>li:not(:first-child)>div").hide()
  })
  $(".tody").click(function () {
    $(".health-status .arlo_tm_portfolio_filter>li:eq(1) .tody-status").show()
    $(".health-status .arlo_tm_portfolio_filter>li:not(:eq(1))>div").hide()
  })

  /**
   * 显示护理工信息
   */
  $(".basic").click(function () {
    $(".health-status .arlo_tm_portfolio_filter>li:eq(1) .basic-info").show()
    $(".health-status .arlo_tm_portfolio_filter>li:not(:eq(1))>div").hide()
    var selectednurseId = $("#elder-select").find('option:selected').data('nurse-id');
    showNursingInfo(selectednurseId)

  })
  /**
   * 切换老人再次渲染对应的护理工信息
   */
  $("#elder-select").change(function () {
    var selectednurseId = $("#elder-select").find('option:selected').data('nurse-id');
  
    showNursingInfo(selectednurseId)
    var select_id = $("#elder-select").val()
    showMonitoringInfo(select_id)
  })

  /**
   * 后端数据渲染 
   * 
   * 
   * */

  $(document).ready(function () {
   
    init()
    showAlreadyPassed(userId)
  })

  



  // 血糖折线图
  var bloodSugar = {
    xAxis: {
      type: 'category',
      data: []
    },
    title: {
      text: '近七天血糖(mmol/L)',
      left: 'top'
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        formatter: '{value} '
      }

    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {},
    toolbox: {
      show: true,
      feature: {
        dataZoom: {
          yAxisIndex: 'none'
        },
        dataView: { readOnly: false },
        magicType: { type: ['line', 'bar'] },
        restore: {},
        saveAsImage: {}
      }
    },
    series: [
      {
        data: [],
        type: 'line'
      }
    ]
  };


  var chartDom_bloodSugar = document.getElementById('bloodSugar');
  var myChart_bloodSugar = echarts.init(chartDom_bloodSugar);






  // 血压折线图
  var bloodPressure = {
    xAxis: {
      type: 'category',
      data: []
    },
    title: {
      text: '近七天血压(mmHg)',
      left: 'top'
    },
    yAxis: {
      type: 'value'
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {},
    toolbox: {
      show: true,
      feature: {
        dataZoom: {
          yAxisIndex: 'none'
        },
        dataView: { readOnly: false },
        magicType: { type: ['line', 'bar'] },
        restore: {},
        saveAsImage: {}
      }
    },
    series: [
      {
        data: [],
        type: 'line',
        smooth: true
      }
    ]
  };

  var chartDom_bloodPressure = document.getElementById('bloodPressure');
  var myChart_bloodPressure = echarts.init(chartDom_bloodPressure);


  /**
   * 心率折线图
   */
  var heartRate = {
    xAxis: {
      type: 'category',
      data: []
    },
    title: {
      text: '近七日心率(次/分钟))',
      left: 'top'
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        formatter: '{value} '
      }

    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {},
    toolbox: {
      show: true,
      feature: {
        dataZoom: {
          yAxisIndex: 'none'
        },
        dataView: { readOnly: false },
        magicType: { type: ['line', 'bar'] },
        restore: {},
        saveAsImage: {}
      }
    },
    series: [
      {
        data: [],
        type: 'line'
      }
    ]
  };


  var chartDom_heartRate = document.getElementById('heartRate');
  var myChart_heartRate = echarts.init(chartDom_heartRate);



  /**
   * 体温图
   */
  var temperature = {
    title: {
      text: '近七日体温(℃)',
      left: '1%'
    },
    tooltip: {
      trigger: 'axis'
    },
    grid: {
      left: '5%',
      right: '15%',
      bottom: '10%'
    },
    xAxis: {
      data: []

    },
    yAxis: {},
    toolbox: {
      right: 10,
      feature: {
        dataZoom: {
          yAxisIndex: 'none'
        },
        restore: {},
        saveAsImage: {}
      }
    },
    dataZoom: [
      {
        startValue: '2014-06-01'
      },
      {
        type: 'inside'
      }
    ],
    visualMap: {
      top: 50,
      right: 10,
      pieces: [
        {
          gt: 32,
          lte: 35,
          color: 'rgb(0,0,139)'
        },
        {
          gt: 35,
          lte: 36,
          color: 'rgb(173, 216,230)'
        },
        {
          gt: 36,
          lte: 37,
          color: '#90EE90'
        },
        {
          gt: 37,
          lte: 39,
          color: '#FFFF00'
        },
        {
          gt: 39,
          lte: 40,
          color: '#FF0000'
        }
      ],
      outOfRange: {
        color: '#999'
      }
    },
    series: {
      name: '体温',
      type: 'line',
      data: [],
      markLine: {
        silent: true,
        lineStyle: {
          color: '#333'
        },
        data: [

        ]
      }
    }
  }
  var chartDom_temperature = document.getElementById('temperature');
  var myChart_temperature = echarts.init(chartDom_temperature);


  function showMonitoringInfo(id) {

    $.ajax({
      type: "get",
      url: "http://localhost/DailyMonitoring/findById",
      data: {
        id: id
      },
      headers:{
        Token2:token
    },
      success: function (data) {
        var list = data.data

        
        bloodPressure.series[0].data = [];
        bloodPressure.xAxis.data = [];
    
        bloodSugar.series[0].data = [];
        bloodSugar.xAxis.data = [];
    
        heartRate.series[0].data = [];
        heartRate.xAxis.data = [];
    
        temperature.series.data = [];
        temperature.xAxis.data = [];
      
        list.forEach(item => {
          // 获得截取后时间的数组
          var time = item.monitoringDate.split("-")
          bloodPressure.series[0].data.push(item.bloodPressure)
          bloodPressure.xAxis.data.push(time[1] + "-" + time[2])

          bloodSugar.series[0].data.push(item.bloodSugar)
          bloodSugar.xAxis.data.push(time[1] + "-" + time[2])

          heartRate.series[0].data.push(item.heartRate)
          heartRate.xAxis.data.push(time[1] + "-" + time[2])

          temperature.xAxis.data.push(time[1] + "-" + time[2])
          temperature.series.data.push(item.temperature)


        
        });
          myChart_bloodPressure.setOption(bloodPressure);
          myChart_bloodSugar.setOption(bloodSugar);
          myChart_heartRate.setOption(heartRate)
          myChart_temperature.setOption(temperature);

          
      }
          

      
    });


    
  }


























  /**
     * 修改用户个人前反向加载用户信息信息
     */
  $("#modify-info").click(function () {
    //获取用户当前密码
    var nowPassword;
   
      $.ajax({
        type:"get",
        url:"http://localhost/UserController/findResidentsById",
        data:{
          userId:userId
        },
        headers:{
          Token2:token
      },
        success:function(data){
          var user = data.data;
          nowPassword = user.password
          if(user.headPhoto!=null&&(user.headPhoto!="")){
            $(".personal-data form li label img").attr("src", user.headPhoto)
          }
          $((".personal-data form  input[name='id']")).val(user.id)
          $((".personal-data form li input[name='name']")).val(user.name)
          $(".personal-data form li input[value='" + user.gender + "']").prop("checked", true);
          $(".personal-data form li input[name='contact']").val(user.contact)
          $(".personal-data form li input[name='password']").val(user.password)
        }
      })
      

    })
    // 进入修改密码
    $("#confirmPassword").click(function (event) {
      event.preventDefault();
      var currentPassword = $("#currentPassword").val();
      var newPassword = $("#newPassword").val();
      var confirmNewPassword = $("#confirmNewPassword").val();
      console.log(currentPassword, newPassword);

      var errorDiv = $("#error");
      // 假设有一个方式验证 currentPassword 是否正确（这里简化处理）
      if (nowPassword == currentPassword) {
        if (newPassword !== confirmNewPassword) {
          errorDiv.html('新密码和确认新密码不匹配');
        } else if (newPassword.length < 6) {
          errorDiv.html('新密码长度必须至少为6个字符');
        } else {
          // 发送请求到服务器以更改密码         
          $.ajax({
            type: "post",
            url: 'http://localhost/UserController/modifyPassword',
            data: {
              newPassword: newPassword,
              userId: 1
            }, headers:{
              Token2:token
          },
            success: function (data) {
              console.log(data);
              if (data.code == 2001) {
                alert('密码修改成功！');
                $(".newPassword").hide();
                $(".mask").hide()
                $(".personal-data").hide()
                errorDiv.html('');
                //更改编辑个人资料页面密码值为当前修改值
                $(".personal-data form li input[name='password']").val(newPassword)
                
              } else {
                alert("信息修改失败")
              }
            }
          })
        }
      } else {
        errorDiv.html('当前密码错误');
      }
    })

   

    // 修改用户头像
    $(".modify-data .personal-data form #file-upload").change(function () {
      var formData = new FormData();
      var inputFile = $(this)[0];
      var newHeadPhoto = inputFile.files[0];
      if (newHeadPhoto) {
        formData.append("newHeadPhoto", newHeadPhoto)
        $.ajax({
          type: "post",
          url: 'http://localhost/UserController/changeHeadPhoto',
          data: formData,
          headers:{
            Token2:token
        },
          processData: false,
          contentType: false,
          success: function (data) {
            var path = data.data;
            $("#headPhoto").attr("src", "http://localhost/headPhoto/" + path)
            //给hidden的input 给上图片路径方便序列化
            $("input[name='headPhoto']").val("http://localhost/headPhoto/" + path)

          }
        })
      }
    })
   


    //修改用户基本信息
    $(".confirm").click(function () {
      var datas = $(".personal-data form").serialize();
      if (confirm("确认修改？")) {
      $.ajax({
        type: "post",
        url: 'http://localhost/UserController/modify',
        data: datas,
        headers:{
          Token2:token
      },
        success: function (data) {
          if (data.code == 101) {
            $(".mask").hide()
            $(".personal-data").hide()
            alert("信息修改成功")
            init()
          } else {
            alert("信息修改失败")
          }
        }
      })
    }

    })

 


  /**
        * 渲染个人信息
        */
  function init() {
   $.ajax({
    type:"get",
    url:"http://localhost/UserController/findResidentsById",
    data:{
      userId:userId
    },
    headers:{
      Token2:token
     },
     success:function(data){
      var user = data.data;
      $("#userInfo #name span label").text(user.name)
      var gender = "女"
      if (user.gender == 1) {
        gender = "男"
      }
      $("#userInfo #gender label").text(gender)
      $("#userInfo #phone label").text(user.contact)
      // var headSrc ="assets/img/headPhoto.png"
      if(user.headPhoto!=null&&(user.headPhoto!="")){
        $(".content.hero .inner_content .image_wrap img").attr('src', user.headPhoto); 
      }
    
      /**渲染用户关联的老人 */
      var list = user.residents
     
      var maResident = $("#myResident")
      /**下拉选框 */
      var elderSelect = $("#elder-select")
      maResident.empty()
      elderSelect.empty()
      if(list.length>1||list[0]!=null){
        showMonitoringInfo(list[0].id)
        $(".showNoResident").hide()
        $(".basic-info ul").show()
        $(".eyeryDay-status ul").show()
        $(list).each(function (i, o) {
          var tag_p = $("<p><label>" + o.name + "</label><p>")
          maResident.append(tag_p)
          var option_tag = $("<option value='" + o.id + "' data-nurse-id='" + o.employeeId + "'>" + o.name + "</option>");
          elderSelect.append(option_tag)
        })
      }else{
        $("#elder-select option").remove()
        $("#myResident > *").remove()
        $(".basic-info ul").hide()
        $(".eyeryDay-status ul").hide()
        $(".showNoResident").show()
      }
      
    
    } 
    
    })
  
    
  }


  /**
   * 渲染护理工基本信息
   */
  function showNursingInfo(nurseId) {
     
    $.ajax({
      type: "get",
      url: "http://localhost/NurseController/findById",
      data: {
        employeeId: nurseId
      },headers:{
        Token2:token
       },
      success: function (data) {
        if (data.code == 2001) {
          var nurse = data.data
          var gender = "男"
          if (nurse.employee.gender == 0) {
            gender = "女"
          }
         
          $(".basic-info ul #nurseName label").text(nurse.name)
          $(".basic-info ul #gender label").text(gender)
          $(".basic-info ul #age label").text(nurse.employee.age)
          $(".basic-info ul #phone label").text(nurse.employee.phone)
          $(".basic-info ul #experience label textarea").text(nurse.experience)
          $(".basic-info ul #rank label").text(nurse.rank)
          
          $(".basic-info ul #certificate label img").attr("src", nurse.certificate)


        }
      }
    })

  }



  /**
   * 
   * 渲染我的申请->已通过
   */
  function showAlreadyPassed(userId) {
    $.ajax({
      type: "get",
      url: "http://localhost/VolunteerController/list",
      data: {
        pageNo: nowPage,
        pageSize: pageSize,
        userId: userId,
        status: 1
      },headers:{
        Token2:token
       },
      success: function (data) {
        console.log(data.data);
        if (data.code == 1001) {
          var list = data.data.list
          var already_passed = $("#already-passed  ul")
       if(list.length>0){
           $("#noPart").hide()
           $("#already-passed ul li:not(:first)").remove();
          $(list).each(function (i, o) {
            var li_tag = $("<li></li>")
            var p_tag1 = $("<p data-long="+o.activity.name +">" + o.activity.name + "</p>")
            var p_tag2 = $("<p>" + o.applyDate.split("T")[0] + " " + o.applyDate.split("T")[1].substr(0, 5) + "</p>")
            var p_tag3 = $("<p>" + o.activity.activeStart.split("T")[0] + " " + o.activity.activeStart.split("T")[1].substr(0, 5) + "</p>");
            var p_tag4 = $("<p>" + o.activity.activeEnd.split("T")[0] + " " + o.activity.activeEnd.split("T")[1].substr(0, 5) + "</p>");
            var p_tag5 = $("<p>" + o.employeeName + "</p>")
            var p_tag6 = $("<p>已通过</p>")
            li_tag.append(p_tag1)
            li_tag.append(p_tag2)
            li_tag.append(p_tag3)
            li_tag.append(p_tag4)
            li_tag.append(p_tag5)
            li_tag.append(p_tag6)
            already_passed.append(li_tag)
          })
        }else{
          $("#noPart").show()
        }
          var pageNum = data.data.pages;

          var pageContainer = $("#page span");
          $("#page span a").remove()
          for (var i = 1; i <= pageNum; i++) {
            if (data.data.pageNum == i) {
              var a_tag = $("<a  style='color: #71B689;'  href=javascript:void(0);>" + i + "</a>")
            } else {
              var a_tag = $("<a  style='color: #333;' href=javascript:void(0);>" + i + "</a>")
            }

            pageContainer.append(a_tag)
          }


        }
      }
    })

  }

  /**
   * 
   * 渲染我的申请->待审核
   */
  function showPendingReview(userId) {
    $.ajax({
      type: "get",
      url: "http://localhost/VolunteerController/list",
      data: {
        userId: userId,
        status: 0
      },headers:{
        Token2:token
       },
      success: function (data) {
        if (data.code == 1001) {
          var list = data.data.list
          var pending_review = $("#pending-review")
          if(list.length>0){          
          $("#pending-review>div").remove();
          $("#noApplication").hide()
          $(list).each(function (i, o) {
            var div_tag1 = $("<div class='container'></div>")
            var div_tag2 = $("<div class='status'>你的申请<strong>正在审核中...&nbsp;<br>请耐心等待!</strong></div>")
            var div_tag3 = $("<div class='details'></div>")
            var p_tag1 = $("<p><strong>申请时间：" + o.applyDate.split("T")[0] + " " + o.applyDate.split("T")[1].substr(0, 5) + "</strong></p>")
            var p_tag2 = $("<p><strong>活动名称：" + o.activity.name + "</strong></p>")
            var a_tag = $("<a  data-application-id=" + o.id + "   class='btn'>取消申请</a>")
            div_tag3.append(p_tag1)
            div_tag3.append(p_tag2)
            div_tag1.append(div_tag2)
            div_tag1.append(div_tag3)
            div_tag1.append(a_tag)
            pending_review.append(div_tag1)
          })

        }else{
          $("#noApplication").show()
        }


        }
      }
    })

  }

  /**
     * 
     * 渲染我的申请->被驳回
     */
  function showRejected(userId) {
    $.ajax({
      type: "get",
      url: "http://localhost/VolunteerController/list",
      data: {
        userId: userId,
        status: -1
      },headers:{
        Token2:token
       },
      success: function (data) {
        if (data.code == 1001) {
          var list = data.data.list
          var rejected = $("#rejected ul")
          if(list.length>0){
            $("#rejected ul li:not(:first)").remove();
           $("#noRejection").hide()
            rejected.show()
          $(list).each(function (i, o) {
            var li_tag = $("<li></li>")
            var p_tag1 = $("<p>" + o.activity.name + "</p>")
            var p_tag2 = $("<p>" + o.applyDate.split("T")[0] + " " + o.applyDate.split("T")[1].substr(0, 5) + "</p>")
            var p_tag3 = $("<p  style=margin-left:70px ;>" + o.rejectReason + "</p>")

            var p_tag4 = $("<p>" + o.employeeName + "</p>")
            var p_tag5 = $("<p>已驳回</p>")
            li_tag.append(p_tag1)
            li_tag.append(p_tag2)
            li_tag.append(p_tag3)
            li_tag.append(p_tag4)
            li_tag.append(p_tag5)
            rejected.append(li_tag)
          })
        }else{
          $("#noRejection").show()
          $("#rejected ul").hide()
        }


        }
      }
    })

  }

})





