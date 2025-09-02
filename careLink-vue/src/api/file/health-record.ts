import axios from "axios";

//上传病历
export const uploadMedicalRecord = async (medicalRecord : File)=> {
  try{
    const formData = new FormData();
    formData.append("medicalRecord", medicalRecord);
    const response = await axios.post('http://localhost/HealthRecordController/uploadM', formData, {
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

//上传报告
export const uploadReport = async (report : File)=> {
  try{
    const formData = new FormData();
    formData.append("report", report);
    const response = await axios.post('http://localhost/HealthRecordController/uploadR', formData, {
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

export const modifyMe = async (data:{
  id:string;
  medicalRecord:string;
})=>{
  try{
    const response = await axios({
      method: 'POST',
      url: 'http://localhost/HealthRecordController/modifyM',
      data: data,
      headers:{
        Token:sessionStorage.getItem("token")
      }
    });
    if(response.data.code==6001){
      alert('无权访问！')
    }
    return response.data;
  }catch (error){
    throw new Error(`Error modifying contract:", ${error.message}`);
  }
}

export const modifyRe = async (data:{
  id:string;
  medicalRecord:string;
})=>{
  try{
    const response = await axios({
      method: 'POST',
      url: 'http://localhost/HealthRecordController/modifyR',
      data: data,
      headers:{
        Token:sessionStorage.getItem("token")
      }
    });
    if(response.data.code==6001){
      alert('无权访问！')
    }
    return response.data;
  }catch (error){
    throw new Error(`Error modifying contract:", ${error.message}`);
  }
}

export const deleteHealthRecord = async (id) => {
  try {
    console.log(id);
    const response = await axios({
      url: 'http://localhost/HealthRecordController/delete',
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
