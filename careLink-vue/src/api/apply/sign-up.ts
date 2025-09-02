// api/incidentApi.ts
import axios, {post} from 'axios';
import { number, string } from 'vue-types';

//查询全部
interface FetchActivitiesParams {
  key?: string;
  pageNum?: number;
  pageSize?: number;
}
export const activitylist = async ({key = '', pageNum = 1, pageSize = 10}: FetchActivitiesParams) => {
  try {
    const response = await axios.get('http://localhost/VolunteerController/showlist', {
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
    return response.data;
  } catch (error) {
    console.error("Error fetching data:", error);
    throw error; // 或者根据需要处理错误
  }
};

// 添加新的事件记录
export const addVolunteer = async (data: {
  id: number;
  name: string;
  applyDate: string;
  activityId: string;
  status: string;
  employeeId: number;
  auditDate: string;
  rejectReason: string;


}) => {
  try {
    console.log(data)
    const response = await axios({
      url: 'http://localhost/VolunteerController/save',
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
    throw new Error(`Failed to add volunteer: ${error.message}`);
  }
};

//修改方法
export const modifyVolunteer = async (data: {
  id: number;
  name: string;
  applyDate: string;
  activityId: string;
  status: string;
  employeeId: number;
  auditDate: string;
  rejectReason: string;
}) => {
  try {
    console.log(data);
    const response = await axios({
      url: 'http://localhost/VolunteerController/update',
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
    throw new Error(`Failed to modify volunteer: ${error.message}`);
  }
};

//删除方法
export const deletevolunteer = async (id) => {
  try {
    console.log(id);
    const response = await axios({
      url: 'http://localhost/VolunteerController/remove',
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
    throw new Error(`Failed to remove volunteer: ${error.message}`);
  }
};
