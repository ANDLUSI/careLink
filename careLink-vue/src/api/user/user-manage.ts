// api/incidentApi.ts
import axios from 'axios';

interface UserParams {
    key?: string;
    pageNum?: number;
    pageSize?: number;
}

//分页模糊查询
export const showList = async (name:string,curPage:number,pageSize:number) => {

       const res =await axios({
         method:"get",
         url:"http://localhost/UserController/list",
         params:{
           userName:name,
           pageNo:curPage,
           pageSize:pageSize,
         },
         headers: {
           token: sessionStorage.getItem("token")
         }
       });
  if (res.data.code==6001){
    alert("无权访问")
  }
  return res;

}


//修改用户状态
export const modifyStatus = async (userId:number,status:number) => {
  try {

    const res = await axios({
      url: 'http://localhost/UserController/modifyStatus',
      method: 'post',
      params:{
         userId:userId,
        status:status
      },
      headers: {
        token: sessionStorage.getItem("token")
      }
    });
    if (res.data.code==6001){

    }
    return res;
  } catch (error) {
    throw new Error(`Failed to modify userStatus: ${error.message}`);
  }

};


