package com.csi.controller;


import com.csi.domain.*;
import com.csi.service.ActivityService;
import com.csi.service.UserService;
import com.csi.service.VolunteerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping(value = "VolunteerController")
@RestController
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;
    //自动注入
    private ServletContext servletContext;

    //新增方法

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(@RequestBody Volunteer volunteer) {
        System.out.println(volunteer.getApplyDate());
        Date date = new Date();
        volunteer.setApplyDate(date);
        volunteer.setAuditDate(date);
        int save = volunteerService.save(volunteer);
        if (save == 0) {
            Result result = new Result<>("新增失败", 4001);
            return result;
        }
        Result result = new Result<>("新增成功", 1001);

        return result;


    }

    //更新方法
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Object update(@RequestBody Volunteer volunteer) {
        System.out.println("========================"+volunteer);
        volunteer.setAuditDate(new Date());
        int update = volunteerService.update(volunteer);
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
        int remove = volunteerService.remove(id);
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
        Volunteer volunteer = volunteerService.findById(id);
        if (volunteer == null) {

            Result result = new Result<>("查找失败", 4040);
            return result;
        }
        Result result = new Result<>("查找成功", 200);
        result.setData(volunteer);
        return result;


    }


    /**
     * 分页查询+模糊查询+查询全部
     *
     * @return
     */
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public Object list(@RequestParam(value = "userId", required = false) Integer userId,
//            @RequestParam(value = "status", required = false) Integer status) {
//
//        System.out.println(userId);
//        System.out.println(status);
//
//        //查询集合
//        List<Volunteer> list = volunteerService.list(userId, status);
//
//
//        if (list.isEmpty()) {
//            Result result = new Result<>("处理失败", 4001);
//            return result;
//        }
//        Result result = new Result<>("处理成功", 1001);
//           result.setData(list);
//        return result;
//    }

    /**
     * 分页查询+模糊查询+查询全部
     * 一个志愿者申请了哪些活动
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@RequestParam(value = "userId", required = false) Integer userId,
                       @RequestParam(value = "status", required = false) Integer status,
                       @RequestParam(value = "pageNo", required = false,defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false,defaultValue = "100") Integer pageSize) {

        System.out.println(userId);
        System.out.println(status);
        System.out.println("=============================" + pageNum);
        System.out.println("=============================" + pageSize);

        //查询集合
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);


        }
        List<Volunteer> list = volunteerService.list(userId, status);
        PageInfo<Volunteer> pageInfo = new PageInfo<>(list);
        System.out.println("++++++++++++++++++++++++" + pageInfo);
        if (list.isEmpty()) {
            Result result = new Result<>("处理失败", 4001);
            return result;
        }
        Result result = new Result<>("处理成功", 1001);
        result.setData(pageInfo);
        return result;
    }


    /*
     * 一个活动下有哪些志愿者申请
     * */
    @RequestMapping(value = "/activitylist", method = RequestMethod.GET)
    public Object activitylist(@RequestParam(value = "activityId", required = false) Integer activityId,
                               @RequestParam(value = "status", required = false) Integer status,
                               @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "999") Integer pageSize) {

        System.out.println(activityId);
        System.out.println(status);
        System.out.println("=============================" + pageNum);
        System.out.println("=============================" + pageSize);

        //查询集合
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);


        }
        //只查到了一个活动下的活动信息
        Volunteer  volunteer= volunteerService.activitylist(activityId, status);
        if (volunteer ==null) {
            Result result = new Result<>("处理失败", 4001);
            return result;
        }

        List<User> users = volunteerService.selectUser(activityId, status);
        volunteer.setUsers(users);
       // 还需要查到活动下的志愿者信息

//        PageInfo<Volunteer> pageInfo = new PageInfo<>(volunteer);
//        System.out.println("++++++++++++++++++++++++" + pageInfo);

        Result result = new Result<>("处理成功", 1001);
//        result.setData(pageInfo);
        result.setData(volunteer);
        return result;
    }

    @RequestMapping(value = "/showlist", method = RequestMethod.GET)
    public Object showlist(@RequestParam(value = "key", required = false) String key,
                               @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "999") Integer pageSize) {


        System.out.println(key);
        System.out.println("=============================" + pageNum);
        System.out.println("=============================" + pageSize);

        //查询集合
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);


        }
        //只查到了一个活动下的活动信息
        List<Volunteer> showlist = volunteerService.showlist(key);
        if (showlist ==null) {
            Result result = new Result<>("处理失败", 4001);
            return result;
        }

//        List<User> users = volunteerService.selectUser(userId, status);
//        volunteer.setUsers(users);
        // 还需要查到活动下的志愿者信息

        PageInfo<Volunteer> pageInfo = new PageInfo<>(showlist);
//        System.out.println("++++++++++++++++++++++++" + pageInfo);

        Result result = new Result<>("处理成功", 1001);
//        result.setData(pageInfo);
        result.setData(pageInfo);
        return result;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add( Volunteer volunteer) {
        System.out.println(volunteer.getApplyDate());
        Date date = new Date();
        volunteer.setApplyDate(date);
        volunteer.setAuditDate(date);
        int save = volunteerService.save(volunteer);
        if (save == 0) {
            Result result = new Result<>("新增失败", 4001);
            return result;
        }
        Result result = new Result<>("新增成功", 1001);

        return result;


    }



}
