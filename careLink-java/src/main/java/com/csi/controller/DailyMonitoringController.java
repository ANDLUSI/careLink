package com.csi.controller;

import com.csi.domain.DailyMonitoring;
import com.csi.domain.Result;
import com.csi.service.DailyMonitoringService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/DailyMonitoring")
public class DailyMonitoringController {
    @Autowired
    private DailyMonitoringService dailyMonitoringService;
    /**
     * 查询老人近七天检测数据
     * @return
     */
    @GetMapping("/findById")
    public Object findById(@RequestParam("id") Integer id){
        List<DailyMonitoring> list = dailyMonitoringService.findById(id);
        Result<List<DailyMonitoring>> result =new Result<>();
        if (list!=null){
            result.setCode(2001);
            result.setMessage("查询成功");
            result.setData(list);
        }else {
            result.setCode(4001);
            result.setMessage("查询失败");
        }
        return  result;
    }

    /**
     *  查询每日健康监测记录
     * 根据老人Id查询所有记录/ 根据日期查询当天所有老人记录 / 两个条件一起查询某天老人记录
     * @param residentId
     * @param monitoringDate
     * @return
     */
    @GetMapping("/findByDate")
    public Object findByDate(@RequestParam(value = "id",required = false)Integer residentId ,@RequestParam(value = "monitoringDate")Date monitoringDate){
        List<DailyMonitoring> list = dailyMonitoringService.findByDate(residentId, monitoringDate);
        Result<List<DailyMonitoring>> result =new Result<>();
        if (list!=null){
            result.setCode(2001);
            result.setMessage("查询成功");
            result.setData(list);
        }else {
            result.setCode(4001);
            result.setMessage("查询失败");
        }
        return  result;
    }


    /**
     * 新增监测记录信息
     * @param dailyMonitoring
     * @return
     */
    @PostMapping("/add")
    public Object add(@RequestBody DailyMonitoring dailyMonitoring){
        int add = dailyMonitoringService.add(dailyMonitoring);
        Result result =new Result<>();
        if (add==1){
            result.setCode(2001);
            result.setMessage("新增成功");
        }else {
            result.setCode(4001);
            result.setMessage("新增失败");
        }
        return result;
    }

    /**
     * 分页查询全部|模糊匹配
     * @param name
     * @param monitoringDate
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public  Object list(@RequestParam(value = "name",required = false) String name,
                        @RequestParam(value = "monitoringDate",required = false )String monitoringDate,
                        @RequestParam(value = "pageNo",required = false)int pageNo,
                        @RequestParam(value = "pageSize",required = false) int pageSize){


        System.out.println("======================================"+monitoringDate);

        List<DailyMonitoring> list = dailyMonitoringService.list(name,monitoringDate , pageNo, pageSize);
        Result<PageInfo<DailyMonitoring>> result =new Result<>();
        if (list!=null){
            PageInfo<DailyMonitoring> pageInfo =new PageInfo<>(list);
            result.setCode(2001);
            result.setMessage("查询成功");
            result.setData(pageInfo);
        }else {
            result.setCode(4001);
            result.setMessage("查询失败");
        }

     return result;
    }

    /**
     * 修改监测信息
     * @param dailyMonitoring
     * @return
     */
    @PostMapping("/modify")
    public Object modify(@RequestBody DailyMonitoring dailyMonitoring){
        int modify = dailyMonitoringService.modify(dailyMonitoring);
        Result result= new Result();
        if (modify==1){
            result.setCode(2001);
            result.setMessage("修改成功");
        }else {
            result.setCode(4001);
            result.setMessage("修改失败");
        }
        return  result;
    }


    /**
     * 根据id删除
     * @param id
     * @return
     */
    @GetMapping("/deleteById")
    public  Object deleteById(@RequestParam("id")int id ){
       int i = dailyMonitoringService.deleteById(id);
       Result result =new Result();
       if (i==1){
           result.setCode(2001);
           result.setMessage("删除成功");
       }else {
           result.setCode(4001);
           result.setMessage("删除失败");
       }
       return result;
    }


    /**
     *批量删除
     * @param deleteList
     * @return
     */
    @PostMapping("/batchDelete")
    public Object batchDelete(@RequestBody List<Integer> deleteList){
        int i = dailyMonitoringService.batchDelete(deleteList);
        Result<Object> result=new Result<>();
        if (i==deleteList.size()){
            result.setCode(2001);
            result.setMessage("全部删除成功");
        }else {
            result.setCode(4001);
            result.setMessage("未全部删除/删除失败");
        }
        return result;
    }
}
