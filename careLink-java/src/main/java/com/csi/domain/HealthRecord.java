package com.csi.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthRecord {
    //健康档案id
    private Integer id;

    //入住人id
    private int residentId;

    //病历（文档路径）
    private String medicalRecord;

    //详细身体情况报告（文档路径）
    private String report;

    //入住人
    private Resident resident;
}
