package com.csi.service;

import com.csi.domain.Resident;
import com.csi.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    //注销已添加的老人
    int cancelOldPeople( int userId,int residentId);

    //绑定老人
    int addOldPeople( int userId, int residentId);


    //手机号+密码登录
    User loginP(String contact, String password);

    //手机号+验证码登录
    User loginC(String contact);

    //注册
    int register(User user);

    //查询用户所关联的老人信息
    List<Resident> findResidentsById(@Param("useId") Integer userId);

    //查询用户基本信息
    User findById(Integer userId );



    //修改个人信息
    int modifyUser(User user);

    int modifyPassword(String password,Integer userId);


    List<User> list(String userName, int pageNo, int pageSize);

    int modifyStatus(int userStatus,Integer userId);
}
