package com.csi.mapper;

import com.csi.domain.IncidentRecord;

import java.util.List;

public interface IncidentRecordMapper {

    int save(IncidentRecord incidentRecord);

    int remove(Integer id);

    int update(IncidentRecord incidentRecord);

    //查询单条
    IncidentRecord findById(Integer id);

    //模糊查询+查询全部
    List<IncidentRecord> list(String key);
}
