package com.csi.service.impl;

import com.csi.domain.Resignation;
import com.csi.domain.ResignationDTO;
import com.csi.mapper.ResignationMapper;
import com.csi.service.ResignationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
@Transactional

public class ResignationServiceImpl implements ResignationService {
    @Autowired
    private ResignationMapper resignationMapper;
    @Override
    public int save(Resignation resignation) {
        return resignationMapper.save(resignation);
    }

    @Override
    public int remove(Integer id) {
        return resignationMapper.remove(id);
    }

    @Override
    public int update(ResignationDTO resignation) {
        return resignationMapper.update(resignation);
    }

    @Override
    public Resignation findById(Integer id) {
        return resignationMapper.findById(id);
    }



    @Override
    public List<Resignation> list(Integer residentId,Integer status) {
        return resignationMapper.list(residentId,status);
    }
}
