package com.csi.service;

import com.csi.domain.User;
import com.csi.domain.Volunteer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface VolunteerService {
    int save(Volunteer volunteer);

    int remove(Integer id);

    int update(Volunteer volunteer);

    //查询单条
    Volunteer findById(Integer id);

    //模糊查询
    List<Volunteer> findByLike(Map<String, Object> map);

    //查询全部
    List<Volunteer> list(Integer userId,  Integer status);
    Volunteer activitylist(Integer activityId,Integer status);
    List <User> selectUser( Integer activityId,Integer status);
    List <Volunteer> showlist( String key);
}
