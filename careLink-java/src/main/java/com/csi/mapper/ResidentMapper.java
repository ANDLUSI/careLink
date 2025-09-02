package com.csi.mapper;

import com.csi.domain.Resident;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 入住老人管理
 */
public interface ResidentMapper {

  //更具老人姓名+身份证查询
  Resident findByNameAndIDCard(@Param("name") String name, @Param("idCard") String idCard);

  int add(Resident resident);

  int deleteById(@Param("id") Integer id);

  //根据id批量删除
  int batchDelete(@Param("deleteList") List<Integer> deleteList);

  int modify(Resident resident);

  //通过名字模糊查询
  List<Resident> list(@Param("name")String name);
}
