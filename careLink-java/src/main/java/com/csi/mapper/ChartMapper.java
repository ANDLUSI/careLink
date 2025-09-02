package com.csi.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ChartMapper {

    //查询护理工人数
    Integer nurseNum();

    //查询突发事件数量
    Integer recordNum();
    //查询入住人数量
    Integer residentNum();
    //查询待处理的申请
    Integer applyNum();
    //查询老人年龄分布
    List<Date> ageState();

    List<Map<String, Object>> getIncidentCountForLast7Days(String currentDate);

    List<Map<String, Object>> getIncidentCountForLast15Days(String currentDate);


    int getUserNum();
}
