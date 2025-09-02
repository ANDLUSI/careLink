package com.csi.mapper;

import com.csi.domain.Nurse;
import com.csi.domain.NurseDTO;

import java.util.List;

public interface NurseMapper {

    int save(Nurse nurse);

    int remove(Integer id);

    int update(NurseDTO nurse);

    //查询单条
    Nurse findById(Integer employeeId);

    //模糊查询+查询全部
    List<Nurse> list(String key);
}
