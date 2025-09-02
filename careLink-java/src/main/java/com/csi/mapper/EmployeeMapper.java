package com.csi.mapper;

import com.csi.domain.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {

    List<Employee> list(@Param("name") String name);

   List<Employee> findLikeName(@Param("name")String name);

   int  add(Employee employee);

   int deleteById(@Param("employeeId")String employeeId);

    //根据id批量删除
    int batchDelete(@Param("deleteList") List<Integer> deleteList);

   int  modify(Employee employee);

}
