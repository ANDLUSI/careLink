package com.csi.service;

import com.csi.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int save(Activity activity);

    int remove(Integer id);

    int update(Activity activity);

    //查询单条
    Activity findById(Integer id);



    //查询全部+模糊查询+分页
    List<Activity> list(String key,Integer activeStatus);
}
