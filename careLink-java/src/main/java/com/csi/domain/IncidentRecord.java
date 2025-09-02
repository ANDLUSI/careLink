package com.csi.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncidentRecord {
    //自增id
    private Integer id;
    //入住人ID
    private Integer residentId;
    //事件类型 (如跌倒、突发疾病等)
    private String incidentType;
    //发生时间
    private String occurrenceTime;
    //详细描述
    private String description;
    //处理结果
    private String resolution;
    //处理人ID
    private Integer handlerId;
    //记录时间
    private String logTime;
}
