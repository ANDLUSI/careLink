// api/incidentApi.ts
import axios, {post} from 'axios';

interface FetchIncidentsParams {
  requestId?: number;
  contractNumber?: string;
  pageNum?: number;
  pageSize?: number;
}
//查询全部
export const showList = async ({ residentId = 0,contractNumber = '', pageNum = 1, pageSize = 10 }: FetchIncidentsParams) => {
  try {
    const response = await axios.get('http://localhost/ContractController/findAll', {
      params: {
        residentId,
        contractNumber,
        pageNum,
        pageSize
      }
    });
    return {
      list: response.data.data.contracts,
      pages: response.data.data.pages,
      pageNum: response.data.data.pageNum
    };
  } catch (error) {
    console.error("Error fetching data:", error);
    throw error; // 或者根据需要处理错误
  }
};

//上传合同方法
export const uploadContract = async (contract : File)=> {
  try{
    const formData = new FormData();
    formData.append("contract", contract);
    const response = await axios.post('http://localhost/ContractController/upload', formData, {
      headers:{
        'Content-Type': 'multipart/form-data',
        Token:sessionStorage.getItem("token")
      }
    });
    if(response.data.code==6001){
      alert('无权访问！')
    }
    return response.data;
  }catch(error){
    throw new Error(`Error uploading contract:", ${error.message}`);
  }
}

// 添加新的事件记录
export const addContract = async (data: {
  residentId: string;
  contractNumber: string;
  startDate: string;
  endDate: string;
  filePath: string;
}) => {
  try {
    console.log(data)
    const response = await axios({
      url:'http://localhost/ContractController/addContract',
      method: 'post',
      data: data,
      headers: {
        Token:sessionStorage.getItem("token")
      }
    });
    if(response.data.code==6001){
      alert('无权访问！')
    }
    return response.data;
  } catch (error) {
    throw new Error(`Failed to add incident: ${error.message}`);
  }
};

//修改方法
export const modifyContract = async (data: {
  id: string;
  contractNumber: string;
  startDate: string;
  endDate: string;
  filePath: string;
}) => {
  try {
    console.log(data);
    const response = await axios({
      url: 'http://localhost/ContractController/modify',
      method: 'post',
      data: data,
      headers: {
        Token:sessionStorage.getItem("token")
      }
    });
    if(response.data.code==6001){
      alert('无权访问！')
    }
    return response.data;
  } catch (error) {
    throw new Error(`Failed to modify incident: ${error.message}`);
  }
};

//删除方法
export const deleteContract = async (id) => {
  try {
    console.log(id);
    const response = await axios({
      url: 'http://localhost/ContractController/delete',
      method: 'get',
      params: {
        id:id
      },
      headers: {
        Token:sessionStorage.getItem("token")
      }
    });
    if(response.data.code==6001){
      alert('无权访问！')
    }
    return response.data;
  } catch (error) {
    throw new Error(`Failed to remove incident: ${error.message}`);
  }
};
