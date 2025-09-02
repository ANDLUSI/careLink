package com.csi;


import com.csi.config.ServiceJavaConfig;
import com.csi.domain.DailyMonitoring;
import com.csi.service.DailyMonitoringService;
import com.csi.service.ResidentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceJavaConfig.class)
public class Test1 {

    @Autowired
    private DailyMonitoringService dailyMonitoringService;
    @Autowired
    private ResidentService residentService;

    @Test
    public void test1(){
        List<DailyMonitoring> list = dailyMonitoringService.findById(7);
        System.out.println(list);
    }

    @Test
    public void test2(){
        List<Integer> list =new ArrayList<>();
        list.add(1);
        list.add(2);
        int i = residentService.batchDelete(list);
        System.out.println(i);
    }

    @Test
    public void test3(){

    }

}
