<template>
  <div id="big_back">
    <div id="page-wrapper">
      <div class="main-page">
        <div class="grids widget-shadow">
          <h3>护理人员管理</h3>
          <div class="search-container">
            <el-input v-model="searchKey" placeholder="请输入搜索关键词" class="search-input"></el-input>
            <el-button type="primary" class="search-button" @click="fetchData()">搜索</el-button>
            <el-button type="primary" class="search-button" @click="add()">新增</el-button>
          </div>
          <ul class="list-group">
            <li class="list-group-item d-flex" id="head_li">
              <div class="table_top">
                <strong>员工ID</strong>
                <strong>姓名</strong>
                <strong>工作经验</strong>
                <strong>技能证书</strong>
                <strong>护理等级</strong>
                <strong class="op">操作</strong>
              </div>
            </li>
            <!-- 通过 v-for 渲染所有的护理工 -->
            <li v-for="nurse in nurseList" :key="nurse.id" class="list-group-item d-flex">
              <div class="table_top">
                <!-- 渲染突发事件的具体数据 -->
                <div>{{ nurse.employeeId}}</div>
                <div>{{ nurse.name }}</div>
                <div>{{ nurse.experience }}</div>
                <div>
                  <a v-if="nurse.certificate" :href="nurse.certificate" target="_blank">
                    <img :src="nurse.certificate" alt="证书" style="max-width: 100px; max-height: 100px;">
                  </a>
                </div>
                <div>{{ nurse.rank === 1 ? '初级护理' : nurse.rank === 2 ? '中级护理' : nurse.rank === 3 ? '高级护理' : '未知' }}</div>
                <span class="op_btn">
                  <!-- 操作按钮，点击分别调用详情、修改、删除方法 -->
                  <a class="btn btn_style_lo btn-sm" @click="detail(nurse.id)">详情</a>
                  <a class="btn btn_style_mo btn-sm" @click="modify(nurse.id)">修改</a>
                  <a class="btn btn_style_del btn-sm" @click="remove(nurse.id)">删除</a>
                </span>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="page_div">
      <!-- 分页按钮 -->
      <div id="page_div">
        <!-- 上一页按钮 -->
        <a @click="prevPage">上一页</a>
        <!-- 动态生成分页按钮 -->
        <a v-for="page in pages" :key="page"  @click="gotoPage(page)" :class="{ active: page === currentPage }">{{ page }}</a>
        <!-- 下一页按钮 -->
        <a @click="nextPage">下一页</a>
      </div>
      <!-- 分页条数选择 -->
      <div class="every_page_div">
        <span class="test_every">每页显示条数：</span>
        <select class="every_tiem" v-model="pageSize" @change="fetchData()">
          <option value="5">5</option>
          <option value="10">10</option>
          <option value="15">15</option>
          <option value="20">20</option>
        </select>
      </div>
    </div>


    <!-- 新增表单 -->
    <el-dialog v-model="addFormVisible" title="新增护理工" width="50%">
      <el-form :model="addForm" label-width="120px">
        <el-form-item label="员工ID">
          <el-input v-model="addForm.employeeId"></el-input>
        </el-form-item>
        <el-form-item label="员工姓名">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="工作经验">
          <el-input v-model="addForm.experience"></el-input>
        </el-form-item>
        <el-form-item label="技能证书">
          <input type="file" @change="handleFileChange" />
<!--          <el-input type="file" v-model="selectedFile" @change="handleFileChange"></el-input>-->
          <el-button type="primary" class="search-button" @click="uploadImg">上传证书</el-button>
          <img v-if="addForm.certificate" :src="addForm.certificate" alt="上传的证书" style="max-width: 100px; max-height: 100px;">
          <el-input hidden v-model="addForm.certificate"></el-input>
        </el-form-item>
        <el-form-item label="护理等级">
          <el-select v-model="addForm.rank">
            <el-option label="初级护理" value="1"></el-option>
            <el-option label="中级护理" value="2"></el-option>
            <el-option label="高级护理" value="3"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addFormVisible = false">取消</el-button>
          <el-button type="primary" @click="submitAddForm">保存</el-button>
        </span>
      </template>
    </el-dialog>


    <!-- 详情表单表单 -->
    <el-dialog v-model="detailFormVisible" title="护理人员详情" width="50%">
      <el-form :model="detailForm" label-width="120px">
        <el-form-item label="员工ID">
          <el-input v-model="detailForm.employeeId"></el-input>
        </el-form-item>
        <el-form-item label="员工姓名">
          <el-input v-model="detailForm.name"></el-input>
        </el-form-item>
        <el-form-item label="工作经验">
          <el-input type="text" v-model="detailForm.experience" ></el-input>
        </el-form-item>
        <el-form-item label="技能证书">
<!--          <img v-if="detailForm.certificate" :src="detailForm.certificate" alt="上传的证书" style="max-width: 200px; max-height: 200px;">-->
          <div>
            <a v-if="detailForm.certificate" :href="detailForm.certificate" target="_blank">
              <img v-if="detailForm.certificate" :src="detailForm.certificate" alt="上传的证书" style="max-width: 100px; max-height: 100px;">
            </a>
          </div>
        </el-form-item>
        <el-form-item label="护理等级">
          <el-select v-model="modifyForm.rank">
            <el-option label="初级护理" value="1"></el-option>
            <el-option label="中级护理" value="2"></el-option>
            <el-option label="高级护理" value="3"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="detailFormVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 更新表单 -->
    <el-dialog v-model="modifyFormVisible" title="修改护理工信息" width="50%">
      <el-form :model="modifyForm" label-width="120px">
        <el-form-item label="员工ID">
          <el-input v-model="modifyForm.employeeId"></el-input>
        </el-form-item>
        <el-form-item label="员工姓名">
          <el-input v-model="modifyForm.name"></el-input>
        </el-form-item>
        <el-form-item label="工作经验">
          <el-input v-model="modifyForm.experience"></el-input>
        </el-form-item>
        <el-form-item label="技能证书">
          <input type="file" @change="handleFileChange" />
          <el-button type="primary" class="search-button" @click="uploadImg">上传证书</el-button>
          <div>
            <a v-if="modifyForm.certificate" :href="modifyForm.certificate" target="_blank">
              <img v-if="modifyForm.certificate" :src="modifyForm.certificate" alt="上传的证书" style="max-width: 100px; max-height: 100px;">
            </a>
          </div>
          <el-input hidden v-model="modifyForm.certificate"></el-input>
        </el-form-item>
        <el-form-item label="护理等级">
          <el-select v-model="modifyForm.rank">
            <el-option label="初级护理" value="1"></el-option>
            <el-option label="中级护理" value="2"></el-option>
            <el-option label="高级护理" value="3"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="modifyFormVisible = false">取消</el-button>
          <el-button type="primary" @click="submitModifyForm()">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import {addNurse, deleteNurse, modifyNurse, showList, uploadImage} from "@/api/nurse/nursem";
import {ElMessage} from "element-plus";


// 表单状态(默认是隐藏的)
const addFormVisible = ref(false);
const modifyFormVisible = ref(false);
const detailFormVisible = ref(false);

// 新增表单数据（整个表单是ref的响应式数据）
const addForm = ref({
  employeeId: '',
  name: '',
  experience: '',
  certificate: '',
  rank: '',
});

// 详情表单数据（整个表单是ref的响应式数据）
const detailForm = ref({
  employeeId: '',
  name: '',
  experience: '',
  certificate: '',
  rank: '',
});

// 修改表单数据（整个表单是ref的响应式数据）
const modifyForm = ref({
  id: 0,
  employeeId: '',
  name: '',
  experience: '',
  certificate: '',
  rank: '',
});
// 定义响应式数据
const nurseList = ref([]); // 储存事件记录数据
const currentPage = ref(1); // 当前页数
const pageSize = ref(10); // 每页显示的条数
const totalPages = ref(1); // 总页数
const searchKey = ref(''); //模糊匹配关键字

/**
 * 获取事件记录数据的函数，支持分页 方法：(模糊查询+分页查询+查询全部)
 * 调用封装的方法：在api文件夹中
 * 鼠标单击await后面的方法名之后，按住ctrl+alt+b可以定位到api文件夹中的方法
 */
const fetchData = async () => {
  const result = await showList({
    key: searchKey.value,
    pageNum: currentPage.value,
    pageSize: pageSize.value
  });
  // 将返回值里的东西取出来，因为是ref型的东西
  nurseList.value = result.list;
  totalPages.value = result.pages;
  currentPage.value = result.pageNum;

  // 清空搜索框
  searchKey.value = '';
};

// 上一页按钮的点击事件
const prevPage = () => {
  if (currentPage.value > 1) { // 如果当前页大于 1，可以翻到上一页
    currentPage.value--;
    //调用查询全部
    fetchData();
  }
};

// 下一页按钮的点击事件
const nextPage = () => {
  if (currentPage.value < totalPages.value) { // 如果当前页小于总页数，可以翻到下一页
    currentPage.value++;
    // 调用查询全部
    fetchData();
  }
};

// 跳转到指定页的函数
const gotoPage = (page: number) => {
  currentPage.value = page;
  fetchData(); // 跳转到指定的页数
};

// 新增按钮的点击事件
const add = () => {
  addForm.value = {
    residentId: '',
    incidentType: '',
    occurrenceTime: '',
    description: '',
    resolution: '',
    handlerId: ''
  };
  addFormVisible.value = true;
};

/**
 * 提交新增表单方法
 * 调用封装的方法：在api文件夹中
 * 鼠标单击await后面的方法名之后，按住ctrl+alt+b可以定位到api文件夹中的方法
 */
const submitAddForm = async () => {
  // 转换 Proxy 对象为普通对象
  const formData = JSON.parse(JSON.stringify(addForm.value));
  try {
    await addNurse(formData);
    console.log(addForm.value)
    fetchData();
    addFormVisible.value = false;
  } catch (error) {
    console.error('Error adding incident:', error);
  }
};

// 详情按钮的点击事件(响应式页面数据回显，无需再单独findByID查询单条记录)
const detail = (id: number) => {
  const nurse = nurseList.value.find(item => item.id === id);
  if (nurse) {
    detailForm.value = { ...nurse };
    detailFormVisible.value = true;
  }
};


// 修改按钮的点击事件(响应式页面数据回显，无需再单独findByID查询单条记录)
const modify = (id: number) => {
  const nurse = nurseList.value.find(item => item.id === id);
  if (nurse) {
    modifyForm.value = { ...nurse };
    modifyFormVisible.value = true;
  }
};

/**
 * 提交修改表单方法
 * 调用封装的方法：在api文件夹中
 * 鼠标单击await后面的方法名之后，按住ctrl+alt+b可以定位到api文件夹中的方法
 */
const submitModifyForm = async () => {
  try {
    // 创建 URLSearchParams 对象并填充表单数据(模拟表序列化提交)
    const formData = new URLSearchParams();
    for (const key in modifyForm.value) {
      if (modifyForm.value.hasOwnProperty(key)) {
        formData.append(key, modifyForm.value[key]);
      }
    }

    await modifyNurse(formData);
    fetchData();
    modifyFormVisible.value = false;
  } catch (error) {
    console.error('Error updating incident:', error);
  }
};


/**
 * 提交删除方法
 * 调用封装的方法：在api文件夹中
 * 鼠标单击await后面的方法名之后，按住ctrl+alt+b可以定位到api文件夹中的方法
 */
const remove = async (id) => {
  if (confirm("你确定要删除这条记录吗？")) {
    await deleteNurse(id);
    fetchData();
  }
};

// 在组件挂载时获取数据
onMounted(() => {
  fetchData();
});


/**
 * 上传图片的方法
 */
// 存储选中的文件
const selectedFile = ref<File | null>(null);

// 处理文件选择
const handleFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement;
  if (target && target.files && target.files.length > 0) {
    const file = target.files[0];
    selectedFile.value = file;
    console.log('Selected file:', file); // 输出文件对象
  } else {
    ElMessage.error('No file selected or invalid input element');
  }
};

// 上传图片的方法
const uploadImg = async () => {
  console.log('Selected file before upload:', selectedFile.value); // 输出文件对象
  if (!selectedFile.value) {
    ElMessage.error('没有选择图片！');
    return;
  }

  try {
    const response = await uploadImage(selectedFile.value);
    ElMessage.success('图片上传成功！');
    // 更新表单中的图片 URL
    addForm.value.certificate = response.data;
    modifyForm.value.certificate = response.data;
  } catch (error) {
    ElMessage.error('图片上传失败！');
    console.error('Error uploading image:', error);
  }
};



// 计算属性，用于生成分页按钮 方法：（分页按钮控制）
const pages = computed(() => {
  const maxPageNumbers = 5; // 最多显示 5 个页码按钮
  const halfMaxPageNumbers = Math.floor(maxPageNumbers / 2); // 一半的页码数，用来确定分页按钮的显示范围
  let startPage = Math.max(1, currentPage.value - halfMaxPageNumbers); // 起始页
  let endPage = Math.min(totalPages.value, currentPage.value + halfMaxPageNumbers); // 结束页

  // 如果分页按钮不足 5 个，调整起始和结束页码
  if ((endPage - startPage) < maxPageNumbers - 1) {
    if (startPage === 1) {
      endPage = Math.min(totalPages.value, maxPageNumbers); // 如果起始页是 1，结束页根据最大页数来调整
    } else {
      startPage = Math.max(1, totalPages.value - maxPageNumbers + 1); // 如果结束页是最大页数，起始页调整
    }
  }
  return Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index); // 返回一个页码数组
});
</script>


<style scoped>
/* 分页样式 */
.page_div{
  background-color: white;
  position: fixed;
  width: 100%;
  height: 80px;
  bottom: 0px;
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
  height: 54px;
  line-height: 36px;
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
  min-width: 110px;
  max-width: 160px;
  /* 设置每个单元格最大高度，超出部分隐藏 */
  max-height: 33px;
  overflow-y: hidden;
  /* 使每个单元格占满剩余空间 */
  flex: 1;
  box-sizing: border-box;
}

/* 按钮容器 */
.op_btn {
  display: flex;
  gap: 10px;
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
  .big_back {
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
