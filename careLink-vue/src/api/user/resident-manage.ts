// api/incidentApi.ts
import axios from 'axios';



//分页模糊查询
export const showList = async (name:string,curPage:number,pageSize:number) => {

       const res =await axios({
         method:"get",
         url:"http://localhost/Resident/list",
         params:{
           name:name,
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


/**
 * 修改老人信息
 * @param resident
 */
export const modifyResident= async (resident:any) => {
  console.log(resident)
  try {
    const res = await axios({
      method: 'post',
      url: 'http://localhost/Resident/modify',
      data:resident,
      headers: {
        token: sessionStorage.getItem("token")
      }
    });
    if (res.data.code==6001){

    }
    return res;
  } catch (error) {
    console.log(error)
  }

};

/**
 * 删除老人
 * @param id
 */
export const delResident= async (id:number) => {
  try {
    const res = await axios({
      method: 'get',
      url: 'http://localhost/Resident/deleteById',
      params:{
           id:id
      },
      headers: {
        token: sessionStorage.getItem("token")
      }
    });
    if (res.data.code==6001){

    }
    return res;
  } catch (error) {
    console.log(error)
  }

};

/**
 * 新增老人
 * @param resident
 */
export const saveResident= async (resident:any) => {
  try {
    const res = await axios({
      url: 'http://localhost/Resident/add',
      method: 'post',
      data:resident,
      headers: {
        token: sessionStorage.getItem("token")
      }
    });
    if (res.data.code==6001){

    }
    return res;
  } catch (error) {
    console.log(error)
  }

};

/**
 * 查询所有房间类型
 */
export const findRoomType= async () => {
  try {
    const res = await axios({
      url: 'http://localhost/ChargeController/findByType',
      method: 'get',
      params:{
        type:'房间'
      },
      headers: {
        token: sessionStorage.getItem("token")
      }
    });
    if (res.data.code==6001){

    }
    return res;
  } catch (error) {
    console.log(error)
  }
};




/**
 * 查询所有空余房间
 */
export const findSpareRoom= async () => {
  try {
    const res = await axios({
      url: 'http://localhost/RoomController/spareRoom',
      method: 'get',
      headers: {
        token: sessionStorage.getItem("token")
      }
    });
    if (res.data.code==6001){

    }
    return res;
  } catch (error) {
    console.log(error)
  }
};


/**
 * 批量删除
 */
export const residentBatchDelete= async (deleteIds:[]) => {
  console.log(deleteIds)
  try {
    const res = await axios({
      url: 'http://localhost/Resident/batchDelete',
      method: 'post',
      data:deleteIds,
      headers: {
        token: sessionStorage.getItem("token")
      }
    });
    if (res.data.code==6001){

    }
    return res;
  } catch (error) {
    console.log(error)
  }
};
