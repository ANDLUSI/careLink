package com.csi.service;

import com.csi.domain.CarePlan;
import com.csi.domain.CarePlanDTO;
import com.csi.domain.Resident;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarePlanService {

    int save(CarePlanDTO carePlanDTO);

    int remove(Integer id);

    int update(CarePlanDTO carePlanDTO);

    //查询单条
    CarePlan findById(Integer id);

    //模糊查询+组合条件查询+查询全部
    List<CarePlan> list(@Param("key") String key,
                        @Param("oldStatus") Integer oldStatus,
                        @Param("planStatus") Integer planStatus);

    //根据健康状态查询老人
    List<Resident> oldList(@Param("oldStatus") Integer oldStatus );
}
