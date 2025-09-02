package com.csi.service.impl;

import com.csi.domain.Employee;
import com.csi.mapper.EmployeeMapper;
import com.csi.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Transactional(readOnly = true)
    @Override
    public List<Employee> list(String name,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Employee> list = employeeMapper.list(name);
        return list;

    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> findLikeName(String name) {
        return employeeMapper.findLikeName(name);
    }

    @Override
    public int add(Employee employee) {
        return employeeMapper.add(employee);
    }

    @Override
    public int deleteById(String employeeId) {
        return employeeMapper.deleteById(employeeId);
    }

    @Override
    public int batchDelete(List<Integer> deleteList) {
        return employeeMapper.batchDelete(deleteList);
    }

    @Override
    public int modify(Employee employee) {
        return employeeMapper.modify(employee);
    }
}
