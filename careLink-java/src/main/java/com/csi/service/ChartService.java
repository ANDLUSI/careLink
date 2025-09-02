package com.csi.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ChartService {

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
    //按日期查询当日突发事件
    List<Map<String, Object>> getIncidentCountForLast7Days(String currentDate);
    //按日期查询当日突发事件
    List<Map<String, Object>> getIncidentCountForLast15Days(String currentDate);

    //查询用户数量
    int getUserName();
}
