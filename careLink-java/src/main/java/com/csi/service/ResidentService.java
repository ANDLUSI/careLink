package com.csi.service;

import com.csi.domain.Resident;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResidentService {
    //更具老人姓名+身份证查询
    Resident findByNameAndIDCard(String name,  String idCard);

    int add(Resident resident);

    int deleteById( Integer id);

    //根据id批量删除
    int batchDelete(List<Integer> deleteList);

    int modify(Resident resident);

    //分页查询全部|通过名字/身份证模糊查询
    List<Resident> list(String name,int pageNo,int pageSize);
}
