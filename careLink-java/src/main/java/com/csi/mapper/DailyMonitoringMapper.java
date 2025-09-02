package com.csi.mapper;

import com.csi.domain.DailyMonitoring;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 每日监测
 */
public interface DailyMonitoringMapper {

    //查询老人近七日健康监测记录
    List<DailyMonitoring> findById(Integer id);


    //新增每日的监测记录
    int add(DailyMonitoring dailyMonitoring);


    //查询某天全部或某人监测记录
    List<DailyMonitoring> findByDate(@Param("residentId") Integer residentId , @Param("monitoringDate")Date monitoringDate);

    List<DailyMonitoring> list(@Param("name")String name,@Param("monitoringDate") String monitoringDate);

    int modify(DailyMonitoring dailyMonitoring);

    int deleteById(int id);

    int batchDelete(@Param("deleteList") List<Integer> deleteList);
}
