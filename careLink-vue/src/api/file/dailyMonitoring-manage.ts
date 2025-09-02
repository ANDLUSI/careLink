// api/incidentApi.ts
import axios from 'axios';


/**
 * 分页查询全部|名字模糊查询
 * @param name
 * @param monitoringDate
 * @param curPage
 * @param pageSize
 */
export const showList = async (name:string,monitoringDate:any,curPage:number,pageSize:number) => {
       const res =await axios({
         method:"get",
         url:"http://localhost/DailyMonitoring/list",
         params:{
           name:name,
           monitoringDate:monitoringDate,
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
 * 修改员工信息
 * @param resident
 */
export const modifyDailyMonitoring= async (dailyMonitoring:any) => {
  try {
    const res = await axios({
      method: 'post',
      url: 'http://localhost/DailyMonitoring/modify',
      data:dailyMonitoring,
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
 * 删除检查记录
 * @param id
 */
export const delDailyMonitoring= async (id:number) => {
  try {
    const res = await axios({
      method: 'get',
      url: 'http://localhost/DailyMonitoring/deleteById',
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
 * 新增员工
 * @param dailyMonitoring
 */
export const saveDailyMonitoring= async (dailyMonitoring:any) => {
  console.log(dailyMonitoring)
  try {
    const res = await axios({
      url: 'http://localhost/DailyMonitoring/add',
      method: 'post',
      data:dailyMonitoring,
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
export const dailyMonitoringBatchDelete= async (deleteIds:[]) => {
  console.log(deleteIds)
  try {
    const res = await axios({
      url: 'http://localhost/DailyMonitoring/batchDelete',
      method: 'post',
      data:deleteIds
      // headers: {
      // }
    });
    return res;
  } catch (error) {
    console.log(error)
  }
};

//分页模糊查询
export const findResident = async (name:string,curPage:number,pageSize:number) => {

  const res =await axios({
    method:"get",
    url:"http://localhost/Resident/list",
    params:{
      name:name,
      pageNo:curPage,
      pageSize:pageSize,
    }
  })
  return res;

}


