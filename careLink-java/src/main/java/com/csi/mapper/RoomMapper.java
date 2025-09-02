package com.csi.mapper;


import com.csi.domain.Room;
import com.csi.domain.RoomDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoomMapper {
    int save(Room room);

    int remove(Integer id);

    int update(RoomDTO room);

    //查询单条
    Room findById(Integer id);

    //模糊查询
    List<Room> findByLike(Map<String, Object> map);
    //查询全部
    List<Room> list(String key);

    List<Room> spareRoom();
}
