package com.csi.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 用于更新方法传参
 */
@Getter
@Setter
public class NurseDTO {
    //自增主键
    private Integer id;
    //员工ID
    private Integer employeeId;
    //姓名
    private String name;
    //工作经验
    private String experience;
    //技能证书路径
    private String certificate;
    //护理等级
    private String rank;
}
