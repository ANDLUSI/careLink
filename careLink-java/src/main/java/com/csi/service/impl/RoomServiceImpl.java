package com.csi.service.impl;

import com.csi.domain.Room;
import com.csi.domain.RoomDTO;
import com.csi.mapper.RoomMapper;
import com.csi.service.RoomService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public int save(Room room) {
        return roomMapper.save(room);
    }

    @Override
    public int remove(Integer id) {
        return roomMapper.remove(id);
    }

    @Override
    public int update(RoomDTO room) {
        return roomMapper.update(room);
    }

    @Override
    public Room findById(Integer id) {
        return roomMapper.findById(id);
    }

    @Override
    public List<Room> findByLike(Map<String, Object> map) {
        return roomMapper.findByLike(map);
    }

    @Override
    public List<Room> list(String key) {
        return roomMapper.list(key);
    }

    @Override
    public List<Room> spareRoom() {
        return roomMapper.spareRoom();
    }


}
