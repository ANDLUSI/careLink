package com.csi.service.impl;

import com.csi.domain.CarePlan;
import com.csi.domain.CarePlanDTO;
import com.csi.domain.Resident;
import com.csi.mapper.BillMapper;
import com.csi.mapper.CarePlanMapper;
import com.csi.service.CarePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarePlanServiceImpl implements CarePlanService {
    @Autowired
    CarePlanMapper carePlanMapper;

    @Override
    public int save(CarePlanDTO carePlanDTO) {
        return carePlanMapper.save(carePlanDTO);
    }

    @Override
    public int remove(Integer id) {
        return carePlanMapper.remove(id);
    }

    @Override
    public int update(CarePlanDTO carePlanDTO) {
        return carePlanMapper.update(carePlanDTO);
    }

    @Override
    public CarePlan findById(Integer id) {
        return carePlanMapper.findById(id);
    }

    @Override
    public List<CarePlan> list(String key, Integer oldStatus, Integer planStatus) {
        return carePlanMapper.list(key,oldStatus,planStatus);
    }

    @Override
    public List<Resident> oldList(Integer oldStatus) {
        return carePlanMapper.oldList(oldStatus);
    }
}
