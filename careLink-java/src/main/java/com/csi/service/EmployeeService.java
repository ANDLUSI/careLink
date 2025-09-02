package com.csi.service;

import com.csi.domain.Employee;
import java.util.List;

public interface EmployeeService {

    List<Employee> list(String name,int pageNum,int pageSize);

    List<Employee> findLikeName(String name);

    int  add(Employee employee);

    int deleteById(String employeeId);

    //根据id批量删除
    int batchDelete(List<Integer> deleteList);

    int  modify(Employee employee);
}
