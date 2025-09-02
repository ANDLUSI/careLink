package com.csi.controller;

import com.csi.domain.Activity;
import com.csi.domain.Result;
import com.csi.service.ActivityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "ActivityController")
@RestController
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    //自动注入ServletContext
    @Autowired
    private ServletContext servletContext;

//    新增方法

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(@RequestBody Activity activity) {
        System.out.println("========================="+activity.getName());
        int save = activityService.save(activity);
        if (save == 0) {
            Result result = new Result("新增失败", 4040);
            return result;
        }
        Result result = new Result<>("新增成功", 200);
        return result;
    }

    //修改方法
    @PostMapping (value = "/update" ,consumes = "application/json")
    public Object update(@RequestBody Activity activity) {
        System.out.println(activity.getName());
        int update = activityService.update(activity);
        if (update == 0) {
            Result result = new Result<>("修改失败", 4040);
            return result;
        }
        Result result = new Result<>("修改成功", 200);
        return result;

    }

    //删除方法
    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public Object remove(@RequestParam(value = "id") Integer id) {
        System.out.println("=============================" + id);
        int remove = activityService.remove(id);
        if (remove == 0) {
            Result result = new Result<>("删除失败", 4040);
            return result;
        }
        Result result = new Result<>("删除成功", 200);
        return result;


    }

    //查找方法
    @RequestMapping(value = "findById", method = RequestMethod.GET)
    public Object findById(@RequestParam(value = "id") Integer id) {
        System.out.println("=============================" + id);
        Activity activity = activityService.findById(id);
        if (activity == null) {
            Result result = new Result<>("查找失败", 4040);
            return result;
        }
        Result result = new Result<>("查找成功", 200);
        result.setData(activity);
        return result;


    }

    //查询全部+分页查询+模糊查询
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object findAll(@RequestParam(value = "key",required = false) String key,
                          @RequestParam(value = "activeStatus",required = false) Integer activeStatus,
                          @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        System.out.println(key);
        System.out.println(pageNum);
        System.out.println(pageSize);
        //引入分页插件
        PageHelper.startPage(pageNum,pageSize);
        //查询集合
        List<Activity> list = activityService.list(key,activeStatus);
          //生成分页对象
        PageInfo<Activity> page = new PageInfo<>(list);


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


}
