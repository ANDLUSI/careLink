package com.csi.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Nurse {
    //自增主键
    private Integer id;
    //员工ID
    private String employeeId;
    //姓名
    private String name;
    //工作经验
    private String experience;
    //技能证书路径
    private String certificate;
    //护理等级
    private Integer rank;
    //员工对象
    private Employee employee;
}
