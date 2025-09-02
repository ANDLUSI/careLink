// api/incidentApi.ts
import axios, {post} from 'axios';

interface FetchActivitiesParams {
  key?: string;
  pageNum?: number;
  pageSize?: number;
}

//查询全部
export const showList1 = async ({key = '', pageNum = 1, pageSize = 10}: FetchActivitiesParams) => {
  try {
    const response = await axios.get('http://localhost/ActivityController/list', {
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
export const addActivity = async (data: {
  name: string;
  applicationStart: string;
  activeStart: string;
  activeEnd: string;
  location: string;
  description: string;
  activeStatus: number;
}) => {
  try {
    console.log(data)
    const response = await axios({
      url: 'http://localhost/ActivityController/save',
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
    throw new Error(`Failed to add activity: ${error.message}`);
  }
};

//修改方法
export const modifyActivity = async (data: {
  name: string;
  applicationStart: string;
  activeStart: string;
  activeEnd: string;
  location: string;
  description: string;
  activeStatus: number;
}) => {
  try {
    console.log(data);
    const response = await axios({
      url: 'http://localhost/ActivityController/update',
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
    throw new Error(`Failed to modify activity: ${error.message}`);
  }
};

//删除方法
export const deleteActivity = async (id) => {
  try {
    console.log(id);
    const response = await axios({
      url: 'http://localhost/ActivityController/remove',
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
    throw new Error(`Failed to remove activity: ${error.message}`);
  }
};
