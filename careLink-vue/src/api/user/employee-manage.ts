// api/incidentApi.ts
import axios from 'axios';


/**
 * 分页查询全部|名字模糊查询
 * @param name
 * @param curPage
 * @param pageSize
 */
export const showList = async (name:string,curPage:number,pageSize:number) => {
       const res =await axios({
         method:"get",
         url:"http://localhost/employee/list",
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
 * 修改员工信息
 * @param resident
 */
export const modifyEmployee= async (employee:any) => {
  console.log(employee)
  try {
    const res = await axios({
      method: 'post',
      url: 'http://localhost/employee/modify',
      data:employee,
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
 * 删除员工
 * @param id
 */
export const delEmployee= async (id:number) => {
  try {
    const res = await axios({
      method: 'get',
      url: 'http://localhost/employee/deleteById',
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
 * @param employee
 */
export const saveEmployee= async (employee:any) => {
  console.log(employee)
  try {
    const res = await axios({
      url: 'http://localhost/employee/save',
      method: 'post',
      data:employee,
      headers: {
        token: sessionStorage.getItem("token")
      }
    });
    if (res.data.code==6001){

    }
    return res;
    return res;
  } catch (error) {
    console.log(error)
  }
};

/**
 * 批量删除
 */
export const employeeBatchDelete= async (deleteIds:[]) => {
  console.log(deleteIds)
  try {
    const res = await axios({
      url: 'http://localhost/employee/batchDelete',
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

/**
 * 获取角色信息
 */
export const findRoles = async () => {
  const res =await axios({
    method:"get",
    url:"http://localhost/RoleController/list",
    headers: {
    token: sessionStorage.getItem("token")
  }
});
  if (res.data.code==6001){

  }
  return res;
}
