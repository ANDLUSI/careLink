<template xmlns:el-table-column="http://www.w3.org/1999/html">
  <div id="big_back">
    <div id="page-wrapper">
      <div class="main-page">
        <div class="grids widget-shadow">
          <h3>每日健康检测管理</h3>
          <div class="search-container" style="margin:10px 0 10px 0">
            <el-input v-model="residentName" placeholder="请输入老人姓名搜索" class="search-input"></el-input>
            <el-date-picker v-model="monitoringDate" placeholder="选择日期"
                            format="YYYY/MM/DD"
                            value-format="YYYY-MM-DD"
                            >
            </el-date-picker>
            <el-button type="primary" class="search-button" @click="curChange(1)">搜索</el-button>
            <el-button type="success" class="save-button" @click="save">新增</el-button>
            <el-button type="danger" class="save-button" @click="batchDelete">批量删除</el-button>
          </div>

          <div>
            <el-table :data="page.list" @selection-change="getSelectedId" style="width: 100%;font-size: 16px">
              <el-table-column type="selection" width="35"/>
              <el-table-column property="residentName" label=老人姓名  align="center"/>
              <el-table-column property="bloodPressure" label="血压"  align="center"/>
              <el-table-column property="bloodSugar" label="血糖" align="center"/>
              <el-table-column property="heartRate" label="心率" align="center"/>
              <el-table-column property="temperature" label="体温" align="center"/>
              <el-table-column property="monitoringDate" label="日期" align="center"/>
              <el-table-column fixed="right" label="操作" min-width="200" align="center">
                <template v-slot="scope">
                  <el-button type="primary" @click="details(scope.row)">详情</el-button>
                  <el-button type="warning" @click="modify(scope.row,page.pageNum)">修改</el-button>
                  <el-button type="danger" @click="del(scope.row.id,page.pageNum)">删除</el-button>
                </template>
              </el-table-column>
              <el-table-column:slot>

              </el-table-column:slot>
            </el-table>
          </div>
          <div class="page">
            <el-pagination
              class="custom-pagination"
              @size-change="changePageSize"
              @current-change="curChange"

              :current-page="page.pageNum"
              :page-sizes="[4,8,16]"
              :page-size="page.pageSize"
              :layout="'total, sizes, prev, pager, next, jumper'"
              background
              prev-text="上一页"
              next-text="下一页"
              :total="page.total"

            >
            </el-pagination>

          </div>
          <!--           详情和修改页面-->
          <el-dialog title="员工信息" v-model="dialogFormVisible">
            <el-form
              ref="employeeInfo"
              style="max-width: 600px"
              :model="dailyMonitoring"
              :rules="rules"
              label-width="auto"
              class="demo-ruleForm"
              :size="formSize"
              status-icon
              :disabled="isFormDisabled"
            >
                  <el-form-item label="老人" prop="bloodSugar">
                    <el-input type="text" v-model="dailyMonitoring.residentName" />
                  </el-form-item>

                  <el-form-item label="血压" prop="bloodSugar">
                    <el-input type="text" v-model="dailyMonitoring.bloodPressure"/>
                  </el-form-item>

                  <el-form-item label="血糖" prop="bloodSugar">
                    <el-input type="text" v-model="dailyMonitoring.bloodSugar"/>
                  </el-form-item>

                  <el-form-item label="心率" prop="heartRate">
                    <el-input type="text" v-model="dailyMonitoring.heartRate" />
                  </el-form-item>


                    <el-form-item label="体温" prop="temperature">
                      <el-input type="text" v-model="dailyMonitoring.temperature" />
                    </el-form-item>

                  <el-form-item label="检测日期" prop="monitoringDate">
                    <el-input type="date" v-model="dailyMonitoring.monitoringDate" />
                  </el-form-item>

              <el-form-item label="备注" prop="notes">
                <el-input v-model="dailyMonitoring.notes" type="textarea" />
              </el-form-item>
            </el-form>
            <template #footer>
              <div :style="{ display: isFooterVisible ? 'block' : 'none' }" style="text-align: center">
                <el-button type="warning" @click="confirmModify">确认</el-button>
                <el-button type="primary" @click="exit">取消</el-button>
              </div>
            </template>
          </el-dialog>

          <el-dialog title="新增监测记录" v-model="dialogFormVisible2">
            <el-form
              style="max-width: 600px"
              :model="newDailyMonitoring"
              :rules="rules"
              label-width="auto"
              class="demo-ruleForm"
              :size="formSize"
              status-icon
            >
              <el-form-item label="老人" prop="residentId">
                <el-select
                  v-model="newDailyMonitoring.residentId"
                  filterable
                  remote
                  reserve-keyword
                  remote-show-suffix
                  placeholder="请输入老人姓名"
                  :remote-method="remoteMethod"
                  :loading="loading"
                  style="width: 240px"
                >
                  <el-option
                    v-for="item in residents"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>

              <el-form-item label="血压" prop="bloodSugar">
                <el-input type="text" v-model="newDailyMonitoring.bloodPressure"/>
              </el-form-item>

              <el-form-item label="血糖" prop="bloodSugar">
                <el-input type="text" v-model="newDailyMonitoring.bloodSugar"/>
              </el-form-item>

              <el-form-item label="心率" prop="heartRate">
                <el-input type="text" v-model="newDailyMonitoring.heartRate" />
              </el-form-item>


              <el-form-item label="体温" prop="temperature">
                <el-input type="text" v-model="newDailyMonitoring.temperature" />
              </el-form-item>

              <el-form-item label="检测日期" prop="monitoringDate">
                <el-input type="date" v-model="newDailyMonitoring.monitoringDate" />
              </el-form-item>

              <el-form-item label="备注" prop="notes">
                <el-input v-model="newDailyMonitoring.notes" type="textarea" />
              </el-form-item>
            </el-form>

            <template #footer>
              <div style="text-align: center">
                <el-button type="warning" @click="confirmSave">确认</el-button>
                <el-button type="primary" @click="exit">取消</el-button>
              </div>
            </template>
          </el-dialog>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {
  Check,
  Delete,
  Edit,
  Message,
  Search,
  Star,
} from '@element-plus/icons-vue'
import {
  dailyMonitoringBatchDelete,
  delDailyMonitoring, findResident,
  modifyDailyMonitoring, saveDailyMonitoring,
  showList
} from "@/api/file/dailyMonitoring-manage";
import {ref, onMounted, computed} from 'vue';

const residentName = ref()
const monitoringDate = ref()
const dailyMonitoring = ref({})
const newDailyMonitoring = ref({})
const  serchName=ref('')
//控制详情|修改影藏表单显示和影藏
const dialogFormVisible = ref(false)
//控制新增影藏表单显示和影藏
const dialogFormVisible2 = ref(false)

const dialogFormVisible3=ref(false)
//控制表单是否禁用
const isFormDisabled = ref(false)
//控制确认按钮显示与影藏
const isFooterVisible = ref(false)
const residents=ref([])
const page = ref('');
const pageSize = ref(4)
const curPage = ref(1)

//存储选中的id
const selectedId = ref([])
const loading=ref(false)
//生命周期函数，在组件被挂载之时执行
onMounted(() => {
  curChange(1)
})

/**
 * 切换页面
 * 模糊查询方法
 */
const curChange = async (curpage: number) => {
  console.log(typeof monitoringDate.value)
  const res = await showList(residentName.value,monitoringDate.value, curpage, pageSize.value)
  page.value = res.data.data

}


/**
 * 修改每页显示条数
 */
const changePageSize = (ps: number) => {
  pageSize.value = ps
  //回到第一页
  curChange(1)
}


/**
 * 查看详情
 */
const details = (rs) => {
  dialogFormVisible.value = true;
  dailyMonitoring.value = rs
  isFormDisabled.value = true
  isFooterVisible.value = false

}

/**
 * 显示修改页面
 * @param rs
 */
const modify = (rs: any, pageNum: number) => {
  dialogFormVisible.value = true;
  isFormDisabled.value = false
  isFooterVisible.value = true
  dailyMonitoring.value = rs
  curPage.value = pageNum

}


/**
 * 确认修改老人信息
 */
const confirmModify = async () => {
  if (!confirm("确认修改吗！")) {
    return;
  }
  console.log(dailyMonitoring.value)
  const res = await modifyDailyMonitoring(dailyMonitoring.value)
  if (res.data.code == 2001) {
     await curChange(curPage.value)
    exit()
  }
}

/**
 * 显示新增界面
 * @param id
 */
const save = () => {
  dialogFormVisible2.value = true
}

/**
 * 确认新增
 */
const confirmSave = async () => {
  console.log(newDailyMonitoring.value)
  const res = await saveDailyMonitoring(newDailyMonitoring.value)
  if (res.data.code == 2001) {
    alert("新增成功")
    dialogFormVisible2.value = false
    curChange(1)
  } else {
    alert("新增失败")
  }
}


/**
 * 删除一条检测记录
 * @param id
 * @param curPage 点击时当前页
 */
const del = async (id: number, curPage: number) => {
  if (!confirm("确认删除吗！")) {
    return;
  }
  const res = await delDailyMonitoring(id)
  if (res.data.code == 2001) {
    curChange(curPage)
    alert("删除成功！")
  }
}


/**
 * 获取选择的id
 * @param val
 */
const getSelectedId = (val) => {
  val.forEach(item => {
    selectedId.value.push(item.id)
  })
}

/**
 *
 * 批量删除
 */
const batchDelete = async () => {
  if (!confirm("确认删除吗！")) {
    return
  }
  const res = await dailyMonitoringBatchDelete(selectedId.value)
  console.log(selectedId.value)
    alert("成功删除" + (selectedId.value.length-1) + "条记录")
    curChange(1)
    selectedId.value=[]

}

/**
 * 查找老人
 * @param query
 */
const remoteMethod = async (query:string) => {
  if (query) {
    residents.value=[]
    loading.value = true
    setTimeout(async () => {
      loading.value = false
      const res = await findResident(query, 1, 9999)
      let list = res.data.data.list;
      list.forEach(item => {
        residents.value.push({id: item.id, name: item.name})
      })
    }, 200)

  }
}

/**
 * 退出页面
 */
const exit = () => {
  dialogFormVisible.value = false;
  dialogFormVisible2.value = false;

}


</script>


<style scoped>

.custom-pagination .el-pager li.active {
  background-color: #409EFF; /* 更改当前页的背景色 */
  color: #fff; /* 更改当前页的文字颜色 */
}

.page {
  bottom: 50px;
  position: fixed;
  left: 500px;
}


/* 父容器 */
#big_back {
  width: 100%; /* 宽度自适应，填满屏幕 */
  margin: 0;
  height: auto;
}

/* 页面内容区域 */
#page-wrapper {
  width: 100%;
}

/* 列表部分样式 */
.list-group {
  width: 100%;
  padding: 0;
  margin: 0;
}

.list-group li {
  width: 100%;
  background-color: white;
  margin-bottom: 1px; /* 增加项与项之间的间距 */
}

/* 表格行 */
.table_top {
  display: flex;
  flex-wrap: wrap; /* 允许内容换行 */
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

/* 正常网页样式调整 */
.table_top strong, .table_top div {
  text-align: center;
  padding: 5px;
  margin: 5px;
  margin-left: -50px;
  min-width: 120px;
  max-width: 160px;
  /* 设置每个单元格最大高度，超出部分隐藏 */
  max-height: 33px;
  overflow-y: hidden;
  flex: 1; /* 使每个单元格占满剩余空间 */
  box-sizing: border-box;
}

/* 按钮容器 */
.op_btn {
  display: flex;
  gap: 10px;
  margin-right: 50px;
}

/* 按钮样式 */
.btn_style_lo {
  color: white;
  font-weight: bold;
  background-color: #66CC66;
  padding: 5px 10px;
  font-size: 12px;
  line-height: 1.5;
  border-radius: 3px;
}

.btn_style_mo {
  color: white;
  font-weight: bold;
  background-color: #ff9933;
  padding: 5px 10px;
  font-size: 12px;
  line-height: 1.5;
  border-radius: 3px;
}

.btn_style_del {
  color: white;
  font-weight: bold;
  background-color: #FF6666;
  padding: 5px 10px;
  font-size: 12px;
  line-height: 1.5;
  border-radius: 3px;
}

/* 搜索按钮样式 */
.find_btn {
  margin: 20px 0;
  display: inline-block;
  width: 38px;
  height: 38px;
  background-image: url("../images/sousuo.png");
  background-repeat: no-repeat;
  background-size: 80% 80%;
  background-position: 5px 4px;
  border-radius: 8px;
  background-color: lightgray;
  cursor: pointer;
}

/* 分页样式 */

#page_div {
  text-align: center;
  margin-top: 30px;
  margin-left: -200px;
}

#page_div a {
  padding: 8px 15px;
  background-color: #CCCCFF;
  color: white;
  border-radius: 8px;
  margin: 0 5px;
  text-decoration: none;
}

.every_page_div {
  margin-left: 20px;
  margin-top: -15px;

}

.every_tiem {
  padding: 5px;
  border-radius: 5px;
  background-color: #CCCCFF;

}


/* 适配大屏幕 */
@media screen and (min-width: 1024px) {
  max-width: none; /* 去掉最大宽度限制 */
}

.table_top {
  flex-wrap: nowrap; /* 防止表格项换行 */
}

.table_top div, .table_top strong {
  min-width: 160px;
  max-width: 200px; /* 增加元素的最大宽度，避免过小 */
  text-align: center;
}

.btn {
  font-size: 14px;
}


/* 小屏设备 (如手机) */
@media screen and (max-width: 768px) {
  /* 列表内容避免溢出 */
  .table_top {
    flex-direction: column; /* 内容垂直堆叠 */
    align-items: center; /* 居中对齐 */
  }

  .table_top div, .table_top strong {
    max-width: 100%; /* 自适应宽度 */
    text-align: center;
  }

  /* 搜索按钮 */
  .find_btn {
    margin-left: 0;
    display: block;
    width: 50px;
    height: 50px;
    margin-bottom: 20px;
  }

  /* 分页栏 */
  #page_div {
    text-align: center;
    height: 80px;
    width: 100%;
    margin-left: 50px;
    font-size: 14px;
  }

  /* 调整分页按钮和下拉框布局 */
  .every_page_div {
    display: inline-block;
    margin-top: 10px;
  }

  /* 增加间距和自动换行 */
  .table_top .op_btn {
    flex-direction: column; /* 操作按钮纵向排列 */
    align-items: center;
    margin-left: 0;
  }

  .btn {
    font-size: 12px;
    margin-bottom: 10px;
  }
}

/*搜索框和搜索按钮*/
.search-container {
  display: flex;
  align-items: center;
}

.search-input {
  width: 300px; /* 设置输入框的宽度 */
  margin-right: 10px; /* 设置输入框和按钮之间的间距 */
}

.search-button {
  height: 30px; /* 设置按钮的高度 */
  padding: 0 20px; /* 设置按钮的内边距 */
  font-weight: bold;
}
</style>
