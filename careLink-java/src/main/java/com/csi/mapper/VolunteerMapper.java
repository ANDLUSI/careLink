package com.csi.mapper;


import com.csi.domain.User;
import com.csi.domain.Volunteer;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface VolunteerMapper {
    int save(Volunteer volunteer);

    int remove(Integer id);

    int update(Volunteer volunteer);

    //查询单条
    Volunteer findById(Integer id);

    //模糊查询
    List<Volunteer> findByLike(Map<String, Object> map);

    //查的是一个志愿者申请了多少活动
    List<Volunteer> list(@Param("userId") Integer userId,@Param("status") Integer status);
    //   查的是一个活动下申请了多少志愿者
    Volunteer activitylist(@Param("activityId") Integer activityId,@Param("status") Integer status);
    List <User> selectUser(@Param("activityId") Integer activityId,@Param("status") Integer status);
    List <Volunteer> showlist(String key);

}
