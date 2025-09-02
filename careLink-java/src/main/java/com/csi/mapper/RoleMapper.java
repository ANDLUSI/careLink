package com.csi.mapper;

import com.csi.domain.Employee;
import com.csi.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {


    int save(Role role);

    int remove(Integer id);

    int update(Role role);

    List<Role> list(String key);

    //员工登录方法
    Employee login(@Param("phone") String phone,@Param("password") String password);

    //查询当前员工的所有权限
    List<String> getPermission(@Param("empId") long empId);

    Employee findById(String empId);
}
