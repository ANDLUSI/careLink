package com.csi.service.impl;

import com.csi.domain.Resident;
import com.csi.domain.User;
import com.csi.mapper.UserMapper;
import com.csi.service.UserService;
import com.github.pagehelper.PageHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int cancelOldPeople(int userId, int residentId) {
        return userMapper.cancelOldPeople(userId,residentId);
    }

    @Override
    public int addOldPeople(int userId, int residentId) {
        return userMapper.addOldPeople(userId,residentId);
    }

    @Override
    public User loginP(String contact, String password) {
        return userMapper.loginP(contact, password);
    }

    @Override
    public User loginC(String contact) {
        return userMapper.loginC(contact);
    }

    @Override
    public int register(User user) {
        int count = userMapper.findContact(user.getContact());
        if (count > 0) {
            return 0;
        }
        return userMapper.register(user);
    }

    @Override
    public List<Resident> findResidentsById(Integer userId) {
        return userMapper.findResidentsById(userId);
    }

    @Override
    public User findById(Integer userId) {
        return userMapper.findById(userId);
    }





    @Override
    public int modifyUser(User user) {
        return userMapper.modifyUser(user);
    }

    @Override
    public int modifyPassword(String password, Integer userId) {
        return userMapper.modifyPassword(password,userId);
    }

    @Override
    public List<User> list(String userName, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        return userMapper.list(userName);
    }

    @Override
    public int modifyStatus(int userStatus ,Integer userId) {
        return userMapper.modifyStatus(userStatus,userId);
    }
}
