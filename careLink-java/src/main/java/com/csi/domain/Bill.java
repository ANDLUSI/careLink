package com.csi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class Bill {
    //账单id
    private Integer id;
    //入住人id
    private int residentId;
    //账单日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date billDate;
    //金额
    private double amount;
    //支付状态
    private int status;
    //订单描述
    private String desc;
    //入住人
    private Resident resident;
}
