package com.csi.service;

import com.csi.domain.Charge;

import java.util.List;

public interface ChargeService {


    int save(Charge charge);

    int remove(Integer id);

    int update(Charge charge);

    //查询单条
    Charge findById(Integer id);

    //模糊查询+查询全部
    List<Charge> list(String key);

    //类型匹配
    List<Charge> listByType(String type);
}
