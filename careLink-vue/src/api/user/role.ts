// api/incidentApi.ts
import axios, {post} from 'axios';

interface FetchIncidentsParams {
    key?: string;
    pageNum?: number;
    pageSize?: number;
}
//查询全部
export const showList = async ({ key = '', pageNum = 1, pageSize = 10 }: FetchIncidentsParams) => {
    try {
        const response = await axios.get('http://localhost/RoleController/list', {
            params: {
                key,
                pageNum,
                pageSize
            },
          headers: {
            token: sessionStorage.getItem("token")
          }
        });
      if (response.data.code==6001){
        alert("无权访问")
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
export const addRole = async (data: {
  id: string;
  name: string;
  description: string;
}) => {
  try {
    console.log(data)
    const response = await axios({
      url:'http://localhost/RoleController/save',
      method: 'post',
      data: data,
      headers: {
        token: sessionStorage.getItem("token")
      }
    });
    if (response.data.code==6001){
      alert("无权访问")
    }
    return response.data;
  } catch (error) {
    throw new Error(`Failed to add role: ${error.message}`);
  }
};

//修改方法
export const modifyRole = async (data: {
  id: string;
  name: string;
  description: string;
}) => {
  try {
    console.log(data);
    const response = await axios({
      url: 'http://localhost/RoleController/update',
      method: 'post',
      data: data,
      headers: {
        token: sessionStorage.getItem("token")
      }
    });
    if (response.data.code==6001){
      alert("无权访问")
    }
    return response.data;
  } catch (error) {
    throw new Error(`Failed to modify role: ${error.message}`);
  }
};

//删除方法
export const deleteRole = async (id) => {
  try {
    console.log(id);
    const response = await axios({
      url: 'http://localhost/RoleController/remove',
      method: 'get',
      params: {
        id:id
      },
      headers: {
        token: sessionStorage.getItem("token")
      }
    });
    if (response.data.code==6001){
      alert("无权访问")
    }
    return response.data;
  } catch (error) {
    throw new Error(`Failed to remove role: ${error.message}`);
  }
};
