// api/incidentApi.ts
import axios, {post} from 'axios';

interface FetchIncidentsParams {
    key?: string;
    pageNum?: number;
    pageSize?: number;
}
//查询全部
export const showCharge = async ({ key = '', pageNum = 1, pageSize = 10 }: FetchIncidentsParams) => {
    try {
        const response = await axios.get('http://localhost/ChargeController/list', {
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
export const addCharge = async (data: {
  item: string;
  price: string;
  description: string;
}) => {
  try {
    console.log(data)
    const response = await axios({
      url:'http://localhost/ChargeController/save',
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
export const modifyCharge = async (data: {
  id: string;
  item: string;
  price: string;
  description: string;
}) => {
  try {
    console.log(data);
    const response = await axios({
      url: 'http://localhost/ChargeController/update',
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
    throw new Error(`Failed to modify incident: ${error.message}`);
  }
};

//删除方法
export const deleteCharge = async (id) => {
  try {
    console.log(id);
    const response = await axios({
      url: 'http://localhost/ChargeController/remove',
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
