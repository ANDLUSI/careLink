package com.csi.service;

import com.csi.domain.Nurse;
import com.csi.domain.NurseDTO;

import java.util.List;

public interface NurseService {

    int save(Nurse nurse);

    int remove(Integer id);

    int update(NurseDTO nurse);

    //查询单条
    Nurse findById(Integer id);

    //模糊查询+查询全部
    List<Nurse> list(String key);
}
