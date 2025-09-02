package com.csi.service.impl;

import com.csi.mapper.ChartMapper;
import com.csi.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChartServiceImpl implements ChartService {

    @Autowired
    ChartMapper chartMapper;

    @Override
    public Integer nurseNum() {
        return chartMapper.nurseNum();
    }

    @Override
    public Integer recordNum() {
        return chartMapper.recordNum();
    }

    @Override
    public Integer residentNum() {
        return chartMapper.residentNum();
    }

    @Override
    public Integer applyNum() {
        return chartMapper.applyNum();
    }

    @Override
    public List<Date> ageState() {
        return chartMapper.ageState();
    }

    @Override
    public List<Map<String, Object>> getIncidentCountForLast7Days(String currentDate) {
        return chartMapper.getIncidentCountForLast7Days(currentDate);
    }

    @Override
    public List<Map<String, Object>> getIncidentCountForLast15Days(String currentDate) {
        return chartMapper.getIncidentCountForLast7Days(currentDate);
    }

    @Override
    public int getUserName() {
        return chartMapper.getUserNum();
    }


}
