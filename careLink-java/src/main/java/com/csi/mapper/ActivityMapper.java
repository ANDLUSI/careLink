package com.csi.mapper;

import com.csi.domain.Activity;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    int save(Activity activity);

    int remove(Integer id);

    int update(Activity activity);

    //查询单条
    Activity findById(Integer id);


    //查询全部+模糊查询+分页
    List<Activity> list (@Param("key") String key, @Param("activeStatus") Integer activeStatus);
}
