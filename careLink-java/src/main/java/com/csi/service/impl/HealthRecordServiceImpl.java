package com.csi.service.impl;

import com.csi.domain.HealthRecord;
import com.csi.mapper.HealthRecordMapper;
import com.csi.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HealthRecordServiceImpl implements HealthRecordService {

    @Autowired
    HealthRecordMapper healthRecordMapper;

    @Override
    public int saveId(int residentId) {
        return healthRecordMapper.saveId(residentId);
    }

    @Override
    public int addHealthRecord(HealthRecord healthRecord) {
        return healthRecordMapper.addHealthRecord(healthRecord);
    }

    @Override
    public int deleteHRById(int id) {
        return healthRecordMapper.deleteHRById(id);
    }

    @Override
    public int modifyMedicalRecord(HealthRecord healthRecord) {
        return healthRecordMapper.modifyMedicalRecord(healthRecord);
    }

    @Override
    public int modifyReport(HealthRecord healthRecord) {
        return healthRecordMapper.modifyReport(healthRecord);
    }

    @Override
    public HealthRecord findById(int id) {
        return healthRecordMapper.findById(id);
    }

    @Override
    public List<HealthRecord> findAllHR(String key) {
        return healthRecordMapper.findAllHR(key);
    }
}
