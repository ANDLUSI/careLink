import resident from "@/views/user/resident.vue";
import axios from "axios";

export const getStatus = (status: number) => {
  return status === 1 ? '已支付' : '未支付';
};

export const addBill = async (data:{
  residentId: number;
  billDate:string;
  amount:number;
  status:number;
  desc:string;
})=> {
  try{
    const response = await axios({
      url: 'http://localhost/BillController/save',
      method: 'post',
      data: data,
      headers:{
        Token:sessionStorage.getItem("token")
      }
    });
    if (response.data.code==6001){
      alert('无权访问！')
    }
  }catch(error){
    throw new Error(`Failed to add bill: ${error.message}`);
  }
}

export const deleteBill = async (id: number) => {
  try {
    const response = await axios({
      method: 'get',
      url: 'http://localhost/BillController/delete',
      params:{
        id: id
      },
      headers:{
        Token:sessionStorage.getItem("token")
      }
    });
    if(response.data.code==6001){
      alert('无权访问！')
    }
    return response.data;
  }catch(error) {
    throw new Error(`Failed to remove incident: ${error.message}`);
  }
}

export const modifyBill = async (id: number, status: number)=>{
  try {
    const response = await axios({
      url: 'http://localhost/BillController/modify',
      method: 'post',
      data: {id:id,status:status},
      headers: {
        Token:sessionStorage.getItem("token")
      }
    });
    if(response.data.code==6001){
      alert('无权访问！')
    }
    console.log(id+'----------------------'+status)
    return response.data;
  } catch (error) {
    throw new Error(`Failed to modify incident: ${error.message}`);
  }
}
