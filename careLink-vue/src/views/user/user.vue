<template xmlns:el-table-column="http://www.w3.org/1999/html">
  <div id="big_back">
    <div id="page-wrapper">
      <div class="main-page">
        <div class="grids widget-shadow">
          <h3>用户管理</h3>
          <div class="search-container">
            <el-input v-model="userName" placeholder="请输入搜索关键词" class="search-input"></el-input>
            <el-button type="primary"  class="search-button" @click="curChange(1)">搜索</el-button>
          </div>
          <div>
            <el-table  :data="page.list"  style="width: 100%">

              <el-table-column  property="id" label="用户ID " width="150" align="center" />
              <el-table-column property="name" label="姓名" width="150" align="center" />
              <el-table-column
                property="contact"
                label="联系方式"
                width="150"
                align="center"
                show-overflow-tooltip
              />
              <el-table-column label="性别" width="150" align="center">
                <template v-slot="scope">
                  <span>{{ scope.row.gender === 1 ? '男' : '女' }}</span>
                </template>
              </el-table-column>
              <el-table-column  label="状态" width="100" align="center" >
                <template v-slot="scope">
                  <span>
                   {{ scope.row.status === 1 ? '正常' :
                    (scope.row.status === 0 ? '失信' :
                      (scope.row.status === -1 ? '拉黑' : '')) }}
                  </span>
                </template>
              </el-table-column>
              <el-table-column  fixed="right" label="操作" min-width="10" align="center">
                <template v-slot="scope">

                  <el-button type="warning"  @click="changeStatus(scope.row.id,0,page.pageNum)">失信</el-button>
                  <el-button type="danger" @click="changeStatus(scope.row.id,-1,page.pageNum)">拉黑</el-button>
                  <el-button type="primary" @click="changeStatus(scope.row.id,1,page.pageNum)">恢复</el-button>
                </template>
              </el-table-column>
              <el-table-column:slot >

              </el-table-column:slot>
            </el-table>
          </div>
          <div class="page" >
            <el-pagination
              class="custom-pagination"
              @size-change="changePageSize"
              @current-change="curChange"
              :current-page="page.pageNum"
              :page-sizes="[4,8,16]"
              :page-size="page.pageSize"
              :layout="'total, sizes, prev, pager, next, jumper'"
              :pager-count=3
              background
              prev-text="上一页"
              next-text="下一页"
              :total="page.total"
             >
            </el-pagination>

          </div>
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
import {modifyStatus, showList} from "@/api/user/user-manage";
import { ref, onMounted, computed } from 'vue';

const userId=ref(1)


const  userName=ref('')

const gender=ref()
const page=ref('');
const pageSize=ref(4)

//生命周期函数，在组件被挂载之时执行
onMounted(()=>{
  curChange(1)
})

/**
 * 切换页面
 * 模糊查询方法
 */
const curChange = async (curpage:number) => {
  const res =await showList(userName.value,curpage,pageSize.value)
  console.log(res)
   page.value=res.data.data

}

/**
 * 修改每页显示条数
 */
const changePageSize = (ps:number) => {
  pageSize.value=ps
  //回到第一页
  curChange(1)
}

/**
 * 操作用户状态
 */
 const changeStatus =async (userId:number,status:number,curPage:number) => {
    const res =await modifyStatus(userId ,status)
    if (res.data.code==2001){
      curChange(curPage)
    }

}



</script>


<style scoped>

.custom-pagination .el-pager li.active {
  background-color: #409EFF; /* 更改当前页的背景色 */
  color: #fff; /* 更改当前页的文字颜色 */
}

.page{

  position: fixed; /* 将元素固定在视口内 */
  bottom: 10px; /* 将元素贴合到视口底部 */
  left: 80%; /* 将元素左边缘移动到视口水平中心 */
  transform: translateX(-50%); /* 通过水平位移，将元素中心对齐到视口中心 */
  width: 100%; /* 初始设置为100%，但随后会调整以实现居中效果 */
  text-align: center; /* 文本对齐方式设为居中，这对块级或内联块级元素内的内容有效 */
  /* 根据需要设置其他样式，例如背景色、内边距等 */
  padding: 10px 0; /* 示例内边距，可根据需要调整 */
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
.btn_style_lo{
  color: white;
  font-weight: bold;
  background-color: #66CC66;
  padding: 5px 10px;
  font-size: 12px;
  line-height: 1.5;
  border-radius: 3px;
}
.btn_style_mo{
  color: white;
  font-weight: bold;
  background-color: #ff9933;
  padding: 5px 10px;
  font-size: 12px;
  line-height: 1.5;
  border-radius: 3px;
}
.btn_style_del{
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
