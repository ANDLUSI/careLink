package com.csi.service.impl;

import com.csi.domain.User;
import com.csi.domain.Volunteer;
import com.csi.mapper.VolunteerMapper;
import com.csi.service.VolunteerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VolunteerServiceImpl implements VolunteerService {
    @Autowired
    private VolunteerMapper volunteerMapper;
    @Override
    public int save(Volunteer volunteer) {
        return volunteerMapper.save(volunteer);
    }

    @Override
    public int remove(Integer id) {
        return volunteerMapper.remove(id);
    }

    @Override
    public int update(Volunteer volunteer) {
        return volunteerMapper.update(volunteer);
    }

    @Override
    public Volunteer findById(Integer id) {
        return volunteerMapper.findById(id);
    }

    @Override
    public List<Volunteer> findByLike(Map<String, Object> map) {
        return volunteerMapper.findByLike(map);
    }

    @Override
    public List<Volunteer> list(Integer userId,  Integer status) {
        return volunteerMapper.list(userId, status);
    }

    @Override
    public   Volunteer activitylist(Integer activityId,Integer status) {
        return volunteerMapper.activitylist(activityId,status);
    }

    @Override
    public List<User> selectUser(Integer activityId, Integer status) {
        return volunteerMapper.selectUser(activityId,status);
    }

    @Override
    public List<Volunteer> showlist(String key) {
        return volunteerMapper.showlist(key);
    }


}
