// api/incidentApi.ts
import axios, {post} from 'axios';

interface FetchActivitiesParams {
  key?: string;
  pageNum?: number;
  pageSize?: number;
}

//查询全部
export const showList4 = async ({key = '', pageNum = 1, pageSize = 10}: FetchActivitiesParams) => {
  try {
    const response = await axios.get('http://localhost/RoomController/list', {
      params: {
        key,
        pageNum,
        pageSize,
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
export const addRoom = async (data: {
  id: number;
  typeId: number;
  intoNumber: number;
  maxNumber: number;
}) => {
  try {
    console.log(data)
    const response = await axios({
      url: 'http://localhost/RoomController/save',
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
    throw new Error(`Failed to add room: ${error.message}`);
  }
};

//修改方法
export const modifyRoom = async (data: {
  id: number;
  typeId: number;
  intoNumber: number;
  maxNumber: number;
}) => {
  try {
    console.log(data);
    const response = await axios({
      url: 'http://localhost/RoomController/update',
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
    throw new Error(`Failed to modify room: ${error.message}`);
  }
};

//删除方法
export const deleteRoom = async (id) => {
  try {
    console.log(id);
    const response = await axios({
      url: 'http://localhost/RoomController/remove',
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
