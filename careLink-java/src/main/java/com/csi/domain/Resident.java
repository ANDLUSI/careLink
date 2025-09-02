package com.csi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.sql.DataSource;
import java.util.Date;

/**
 * 老人信息表
 */

@Getter
@Setter
@ToString
public class Resident {

    private Integer id;  //id
    private String name; //姓名
    private String idCard; //身份证
    private String contact;//电话
    private int gender; //性别
    private int age; //年龄
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bornDate;
    private int roomNo; //房间号
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date admissionDate; //入住日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date departureDate;  //离院日期
    private int  roomStatus;  //入住状态( 1 在住/0 已退)
    private String emergency; //紧急联系人
    private String emergencyContact; //紧急联系人电话
    private int healthyStatus; //健康状况
    private int employeeId; //护理工id

}
