package com.csi.service;

import com.csi.domain.HealthRecord;

import java.util.List;

public interface HealthRecordService {

    //添加老人id
    int saveId(int residentId);

    //添加病历+身体情况报告
    int addHealthRecord(HealthRecord healthRecord);

    //删除信息
    int deleteHRById(int id);

    //修改病历
    int modifyMedicalRecord(HealthRecord healthRecord);

    //修改身体情况报告
    int modifyReport(HealthRecord healthRecord);

    //通过id查询
    HealthRecord findById(int id);

    //查询全部
    List<HealthRecord> findAllHR(String key);
}
