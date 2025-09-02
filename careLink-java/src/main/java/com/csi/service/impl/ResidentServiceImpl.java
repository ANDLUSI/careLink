package com.csi.service.impl;

import com.csi.domain.Resident;
import com.csi.mapper.ResidentMapper;
import com.csi.service.ResidentService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResidentServiceImpl implements ResidentService {
   @Autowired
   private ResidentMapper residentMapper;

    @Transactional(readOnly = true)
    @Override
    public Resident findByNameAndIDCard(String name, String IDCard) {
        return residentMapper.findByNameAndIDCard(name,IDCard);
    }

    @Override
    public int add(Resident resident) {
        return residentMapper.add(resident);
    }

    @Override
    public int deleteById(Integer id) {
        return residentMapper.deleteById(id);
    }

    @Override
    public int batchDelete(List<Integer> deleteList) {
        return residentMapper.batchDelete(deleteList);
    }

    @Override
    public int modify(Resident resident) {
        return residentMapper.modify(resident);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Resident> list(String name,int pageNo,int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        return residentMapper.list(name);
    }
}
