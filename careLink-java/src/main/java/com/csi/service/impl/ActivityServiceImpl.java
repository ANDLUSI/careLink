package com.csi.service.impl;

import com.csi.domain.Activity;
import com.csi.mapper.ActivityMapper;
import com.csi.service.ActivityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    @Override
    public int save(Activity activity) {
        return activityMapper.save(activity);
    }

    @Override
    public int remove(Integer id) {
        return activityMapper.remove(id);
    }

    @Override
    public int update(Activity activity) {
        return activityMapper.update(activity);
    }

    @Override
    public Activity findById(Integer id) {
        return activityMapper.findById(id);
    }


    @Override
    public List<Activity> list(String key,Integer activeStatus) {
        return activityMapper.list(key,activeStatus);
    }
}
