package com.csi.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
/**
 * 注册用户信息
 */
@Getter
@Setter
@ToString
public class User {
    //用户id
    private int id;
    //姓名
    private String name;
    //与老人关系
    private String relationship;
    //联系方式
    private String contact;
    //密码
    private String password;
    //性别
    private int gender;
    //头像路径
    private  String headPhoto;
    //老人信息
    private List<Resident> residents;
    //用户状态
    private int status;
}
