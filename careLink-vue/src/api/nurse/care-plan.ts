// api/incidentApi.ts
import axios, {post} from 'axios';

interface FetchIncidentsParams {
    key?: string;
    oldStatus?:number;
    planStatus?:number
    pageNum?: number;
    pageSize?: number;
}
//查询全部
export const showList = async ({ key = '', pageNum = 1, pageSize = 10,oldStatus,planStatus }: FetchIncidentsParams) => {
    try {
        const response = await axios.get('http://localhost/CarePlanController/list', {
            params: {
                key,
                oldStatus,
                planStatus,
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
export const addPlan = async (data: {
  residentId: string;
  employeeId: string;
  planDate: string;
  planDetail: string;
  status: string;
}) => {
  try {
    console.log(data)
    const response = await axios({
      url:'http://localhost/CarePlanController/save',
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
    throw new Error(`Failed to add incident: ${error.message}`);
  }
};

//修改方法
export const modifyPlan = async (data: {
  id: string;
  residentId: string;
  employeeId: string;
  planDate: string;
  planDetail: string;
  status: string;
}) => {
  try {
    console.log(data);
    const response = await axios({
      url: 'http://localhost/CarePlanController/update',
      method: 'post',
      data: data,
      headers: {
        Tkoen:sessionStorage.getItem("token")
      }
    });
    if (response.data.code==6001){
      alert("无权访问！")
    }
    return response.data;
  } catch (error) {
    throw new Error(`Failed to modify incident: ${error.message}`);
  }
};

//删除方法
export const deletePlan = async (id) => {
  try {
    console.log(id);
    const response = await axios({
      url: 'http://localhost/CarePlanController/remove',
      method: 'get',
      params: {
        id:id
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
    throw new Error(`Failed to remove incident: ${error.message}`);
  }
};

//根据状态查询老人方法
export const findByStatus = async (status) => {
  try {
    const response = await axios({
      url: 'http://localhost/CarePlanController/findStatus',
      method: 'get',
      params: {
        oldStatus:status
      },
      headers: {
        Token:sessionStorage.getItem("token")
      }
    });
    if (response.data.code==6001){
      alert("无权访问！")
    }
    console.log(response.data.data)
    return response.data.data;
  } catch (error) {
    throw new Error(`Failed to remove incident: ${error.message}`);
  }
};
