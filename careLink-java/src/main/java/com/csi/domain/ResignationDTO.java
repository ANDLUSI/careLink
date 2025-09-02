package com.csi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class ResignationDTO {
    //用于修改表单的字段
    //自增主键ID
    private int id;
    //入住人ID
    private int residentId;
    //申请日期
    //申请日期
    //申请日期
    @DateTimeFormat(pattern = "yyyy-MM-dd mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Date applyDate;
    //退宿原因
    private String reason;
    //状态（0待审核 1已通过 2已拒绝）
    private int status;
}
