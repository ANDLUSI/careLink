package com.csi.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CarePlanDTO {
    //护理计划ID
    private Integer id;
    //入住人ID
    private Integer residentId;
    //发布人任务ID
    private Integer employeeId;
    //计划开始日期
    private String planDate;
    //计划详情
    private String planDetail;
    //状态: 1继续执行/2暂不执行
    private Integer status;
}
