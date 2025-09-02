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
        const response = await axios.get('http://localhost/NurseController/list', {
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


// 上传图片的方法
export const uploadImage = async (file: File) => {
  try {
    // 创建 FormData 对象
    const formData = new FormData();
    formData.append('file', file);

    // 发起 POST 请求
    const response = await axios.post('http://localhost/NurseController/certificate', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        Token:sessionStorage.getItem("token")
      }
    });

    if (response.data.code==6001){
      alert("无权访问！")
    }
    console.log('Image uploaded successfully:', response.data);
    return response.data;
  } catch (error) {
    throw new Error(`Error uploading image: ${error.message}`);
  }
};


// 添加新的事件记录
export const addNurse = async (data: {
  employeeId: string;
  name: string;
  experience: string;
  certificate: string;
  rank: string;
}) => {
  try {
    console.log(data)
    const response = await axios({
      url:'http://localhost/NurseController/save',
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
export const modifyNurse = async (data: {
  id: string;
  employeeId: string;
  name: string;
  experience: string;
  certificate: string;
  rank: string;
}) => {
  try {
    console.log(data);
    const response = await axios({
      url: 'http://localhost/NurseController/update',
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
export const deleteNurse = async (id) => {
  try {
    console.log(id);
    const response = await axios({
      url: 'http://localhost/NurseController/remove',
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
