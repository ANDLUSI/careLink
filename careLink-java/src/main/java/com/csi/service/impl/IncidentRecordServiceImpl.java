package com.csi.service.impl;

import com.csi.domain.IncidentRecord;
import com.csi.mapper.IncidentRecordMapper;
import com.csi.service.IncidentRecordService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class IncidentRecordServiceImpl implements IncidentRecordService {

    @Autowired
    private IncidentRecordMapper incidentRecordMapper;

    @Override
    public int save(IncidentRecord incidentRecord) {
        return incidentRecordMapper.save(incidentRecord);
    }

    @Override
    public int remove(Integer id) {
        return incidentRecordMapper.remove(id);
    }

    @Override
    public int update(IncidentRecord incidentRecord) {
        return incidentRecordMapper.update(incidentRecord);
    }

    @Override
    public IncidentRecord findById(Integer id) {
        return incidentRecordMapper.findById(id);
    }

    @Override
    public List<IncidentRecord> list(String key) {
        return incidentRecordMapper.list(key);
    }
}
