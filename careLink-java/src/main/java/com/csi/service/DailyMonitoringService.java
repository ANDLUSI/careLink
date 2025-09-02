package com.csi.service;

import com.csi.domain.DailyMonitoring;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DailyMonitoringService {
    List<DailyMonitoring> findById(Integer id);

    int add(DailyMonitoring dailyMonitoring);

    //查询某天全部或某人监测记录
    List<DailyMonitoring> findByDate( Integer residentId ,Date monitoringDate);

    List<DailyMonitoring> list(String name,String monitoringDate,int pageNo,int pageSize);

    int modify(DailyMonitoring dailyMonitoring);

    int deleteById(int id);

    int batchDelete( List<Integer> deleteList);

}
