package com.csi.controller;

import com.csi.domain.Resignation;
import com.csi.domain.Result;
import com.csi.domain.Room;
import com.csi.domain.RoomDTO;
import com.csi.service.RoomService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "RoomController")
@RestController
public class RoomController {
    @Autowired
    private RoomService roomService;
    //自动注入
    public ServletContext servletContext;


    //新增方法
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(@RequestBody Room room) {
        System.out.println(room.getTypeId());
        int save = roomService.save(room);
        if (save == 0) {
            Result result = new Result<>("新增失败", 4001);
            return result;
        }
        Result result = new Result<>("新增成功", 1001);

        return result;
    }


    //修改方法
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Object update(@RequestBody RoomDTO room) {
        System.out.println(room.getTypeId());
        int update = roomService.update(room);
        if (update == 0) {
            Result result = new Result<>("修改失败", 4040);
            return result;
        }
        Result result = new Result<>("修改成功", 200);
        return result;
    }



    //删除方法
    @RequestMapping(value = "remove",method = RequestMethod.GET)
  public Object remove(@RequestParam(value = "id") Integer id) {
      System.out.println("=============================" + id);
      int remove = roomService.remove(id);
      if (remove == 0) {
          Result result = new Result<>("删除失败", 4040);
          return result;
      }
      Result result = new Result<>("删除成功", 200);
      return result;
  }

    //查找方法
    @RequestMapping(value = "findById", method = RequestMethod.GET)
    public  Object findById(@RequestParam(value = "id") Integer id) {
        System.out.println("=============================" + id);
        Room room = roomService.findById(id);
        if (room == null) {

            Result result = new Result<>("查找失败", 4040);
            return result;
        }
        Result result = new Result<>("查找成功", 200);
        result.setData(room);
        return result;



    }
    //查找全部+模糊查询+分页查询
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object findAll(@RequestParam(value = "key", required = false) String key,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        System.out.println(key);
        System.out.println(pageNum);
        System.out.println(pageSize);
        //引入分页插件
        PageHelper.startPage(pageNum, pageSize);
        //查询集合
        List<Room> list = roomService.list(key);
        //生成分页对象
        PageInfo<Room> page = new PageInfo<>(list);


        if (list.isEmpty()) {
            Result result = new Result<>("查找失败", 4040);
            return result;
        }
        Result result = new Result<>();
        result.setCode(1001);
        result.setMessage("查找成功");
        result.setData(page);
        return result;
    }

    /**
     * 查询全部空余房间
     * @return
     */
    @GetMapping("/spareRoom")
    public Object spareRoom(){
        List<Room> rooms = roomService.spareRoom();
        Result<List<Room>> result=new Result<>();
        if (rooms!=null){
            result.setCode(2001);
            result.setMessage("查询成功");
            result.setData(rooms);
        }else {
            result.setCode(4001);
            result.setMessage("查询失败");
        }
        return result;
    }

}
