// api/incidentApi.ts
import axios, {post} from 'axios';

interface FetchOutParams {
  key?: string;
  pageNum?: number;
  pageSize?: number;
}

//查询全部
export const showList2 = async ({key = '', pageNum = 1, pageSize = 10}: FetchOutParams) => {
  try {
    const response = await axios.get('http://localhost/ResignationController/list', {
      params: {
        key,
        pageNum,
        pageSize
      },
      headers: {
        Token:sessionStorage.getItem("token")
      }
    });
    if (response.data.code==6001){
      alert("无权访问！")
    }
    return {
      list: response.data.data.list,
      pages: response.data.data.pages,
      pageNum: response.data.data.pageNum
    };
  } catch (error) {
    console.error("Error fetching data:", error);
    throw error; // 或者根据需要处理错误
  }
};


// 添加新的事件记录
export const addout = async (data: {
  residentId: string;
  name: string;
  applyDate: string;
  reason: string;
  status: string;

}) => {
  try {
    console.log(data)
    const response = await axios({
      url: 'http://localhost/ResignationController/save',
      method: 'post',
      data: data,
      headers: {
        Token:sessionStorage.getItem("token")
      }
    });
    if (response.data.code==6001){
      alert("无权访问！")
    }
    return response.data;
  } catch (error) {
    throw new Error(`Failed to add out: ${error.message}`);
  }
};

//修改方法
export const modifyOut = async (data: {
  id:number;
  residentId: string;
  name: string;
  applyDate: string;
  reason: string;
  status: string;
}) => {
  try {
    console.log(data);
    const response = await axios({
      url: 'http://localhost/ResignationController/update',
      method: 'post',
      data: data,
      headers: {
        Token:sessionStorage.getItem("token")
      }
    });
    if (response.data.code==6001){
      alert("无权访问！")
    }
    return response.data;
  } catch (error) {
    throw new Error(`Failed to modify out: ${error.message}`);
  }
};

//删除方法
export const deleteOut = async (id) => {
  try {
    console.log(id);
    const response = await axios({
      url: 'http://localhost/ResignationController/remove',
      method: 'get',
      params: {
        id: id
      },
      headers: {
        Token:sessionStorage.getItem("token")
      }
    });
    if (response.data.code==6001){
      alert("无权访问！")
    }
    return response.data;
  } catch (error) {
    throw new Error(`Failed to remove out: ${error.message}`);
  }
};


