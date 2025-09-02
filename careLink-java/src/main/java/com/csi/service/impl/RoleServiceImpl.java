package com.csi.service.impl;

import com.csi.domain.Employee;
import com.csi.domain.Role;
import com.csi.mapper.RoleMapper;
import com.csi.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;


    @Override
    public int save(Role role) {
        return roleMapper.save(role);
    }

    @Override
    public int remove(Integer id) {
        return roleMapper.remove(id);
    }

    @Override
    public int update(Role role) {
        return roleMapper.update(role);
    }

    @Override
    public List<Role> list(String key) {
        return roleMapper.list(key);
    }

    @Override
    public Employee login(String phone, String password) {
        return roleMapper.login(phone,password);
    }

    @Override
    public List<String> getPermission(long empId) {
        return roleMapper.getPermission(empId);
    }

    @Override
    public Employee findById(String empId) {
        return roleMapper.findById(empId);
    }


}
