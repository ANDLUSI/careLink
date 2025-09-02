package com.csi.service.impl;

import com.csi.domain.DailyMonitoring;
import com.csi.mapper.DailyMonitoringMapper;
import com.csi.service.DailyMonitoringService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DailyMonitoringServiceImpl implements DailyMonitoringService {
    @Autowired
    private DailyMonitoringMapper dailyMonitoringMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    private final String keyName = "dailyMonitoring:";


    @Transactional(readOnly = true)
    @Override
    public List<DailyMonitoring> findById(Integer id) {
//        ValueOperations<String,Object> opsForValue = redisTemplate.opsForValue();
//        List<DailyMonitoring> list = (List<DailyMonitoring>) opsForValue.get(keyName+id);
//         if (list==null){
//             System.out.println("从数据库查询！==============");
             List<DailyMonitoring> list = dailyMonitoringMapper.findById(id);
//              opsForValue.set(keyName+id,list);
//         }

       return list;
    }

    @Override
    public int add(DailyMonitoring dailyMonitoring) {
        return dailyMonitoringMapper.add(dailyMonitoring);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DailyMonitoring> findByDate(Integer residentId, Date monitoringDate) {
        return dailyMonitoringMapper.findByDate(residentId,monitoringDate);
    }

    @Override
    public List<DailyMonitoring> list(String name, String monitoringDate,int pageNo,int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        return dailyMonitoringMapper.list(name,monitoringDate);

    }

    @Override
    public int modify(DailyMonitoring dailyMonitoring) {
        return dailyMonitoringMapper.modify(dailyMonitoring);
    }

    @Override
    public int deleteById(int id) {
        return dailyMonitoringMapper.deleteById(id);
    }

    @Override
    public int batchDelete(List<Integer> deleteList) {
        return dailyMonitoringMapper.batchDelete(deleteList);
    }


}
