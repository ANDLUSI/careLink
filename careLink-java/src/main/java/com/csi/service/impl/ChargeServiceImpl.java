package com.csi.service.impl;

import com.csi.domain.Charge;
import com.csi.mapper.ChargeMapper;
import com.csi.service.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChargeServiceImpl implements ChargeService {

    @Autowired
    ChargeMapper chargeMapper;
    @Override
    public int save(Charge charge) {
        return chargeMapper.save(charge);
    }

    @Override
    public int remove(Integer id) {
        return chargeMapper.remove(id);
    }

    @Override
    public int update(Charge charge) {
        return chargeMapper.update(charge);
    }

    @Override
    public Charge findById(Integer id) {
        return chargeMapper.findById(id);
    }

    @Override
    public List<Charge> list(String key) {
        return chargeMapper.list(key);
    }

    @Override
    public List<Charge> listByType(String type) {
        return chargeMapper.listByType(type);
    }
}
