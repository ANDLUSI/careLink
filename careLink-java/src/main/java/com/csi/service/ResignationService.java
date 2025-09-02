package com.csi.service;

import com.csi.domain.Resignation;
import com.csi.domain.ResignationDTO;

import java.util.List;
import java.util.Map;

public interface ResignationService {
    int save(Resignation resignation);

    int remove(Integer id);

    int update(ResignationDTO resignation);

    //查询单条
    Resignation findById(Integer id);

    //查询全部+模糊查询
    List<Resignation> list(Integer residentId,Integer status);







}
