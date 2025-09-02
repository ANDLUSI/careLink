package com.csi.mapper;


import com.csi.domain.Resignation;
import com.csi.domain.ResignationDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ResignationMapper {
    int save(Resignation resignation);

    int remove(Integer id);

    int update(ResignationDTO resignation);

    //查询单条
    Resignation findById(Integer id);

    //模糊查询
    List<Resignation> findByLike(Map<String, Object> map);

    //查询全部
    List<Resignation> list(@Param("residentId") Integer residentId,@Param("status") Integer status);


}
