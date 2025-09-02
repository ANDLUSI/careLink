package com.csi.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 员工
 */
@Getter
@Setter
@ToString
public class Employee {
    private String employeeId;
    private String name;
    private int gender;
    private int age;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "UTC")
    private Date bornDate;
    private String phone;
    private String password;
    private String address;
    private double salary;
    private int roleId;
    private String idCard;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "UTC")
    private Date hireDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "UTC")
    private Date updatedAt;

    private Integer createBy;
    private Integer updateBy;

}

