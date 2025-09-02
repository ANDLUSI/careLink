package com.csi.service.impl;

import com.csi.domain.Nurse;
import com.csi.domain.NurseDTO;
import com.csi.mapper.NurseMapper;
import com.csi.service.NurseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NurseServiceImpl implements NurseService {

    @Autowired
    private NurseMapper nurseMapper;

    @Override
    public int save(Nurse nurse) {
        return nurseMapper.save(nurse);
    }

    @Override
    public int remove(Integer id) {
        return nurseMapper.remove(id);
    }

    @Override
    public int update(NurseDTO nurse) {
        return nurseMapper.update(nurse);
    }

    @Override
    public Nurse findById(Integer employeeId) {
        return nurseMapper.findById(employeeId);
    }

    @Override
    public List<Nurse> list(String key) {
        return nurseMapper.list(key);
    }
}
