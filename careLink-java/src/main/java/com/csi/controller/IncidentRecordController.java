package com.csi.controller;

import com.csi.domain.IncidentRecord;
import com.csi.domain.Result;
import com.csi.service.IncidentRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/IncidentRecordController")
@RestController
public class IncidentRecordController {

    @Autowired
    private IncidentRecordService incidentRecordService;

    /**
     * 分页查询+模糊查询+查询全部
     * @param key
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(@RequestParam(value = "key",required = false) String key,
                       @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        System.out.println(key);
        System.out.println(pageNum);
        System.out.println(pageSize);

        //引入分页插件
        PageHelper.startPage(pageNum,pageSize);

        //查询集合
        List<IncidentRecord> list = incidentRecordService.list(key);

        //生成分页对象
        PageInfo<IncidentRecord> page = new  PageInfo<>(list);

        if (list.isEmpty()){
            Result result = new Result<>("处理失败",4001);
            return result;
        }
        Result result = new Result<>("处理成功",1001);
        result.setData(page);
        return result;
    }

    /**
     * 新增方法
     * @param incidentRecord
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Object save(@RequestBody IncidentRecord incidentRecord){
        try {
            // 定义输入和输出的时间格式
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // 将接收到的时间字符串转换为 Date 对象
            Date occurrenceTime = inputFormat.parse(incidentRecord.getOccurrenceTime());


            // 将 Date 对象转换为符合数据库要求的字符串格式
            String formattedOccurrenceTime = outputFormat.format(occurrenceTime);
            Date logTime = new Date();
            String logTimeS = outputFormat.format(logTime);

            incidentRecord.setOccurrenceTime(formattedOccurrenceTime);
            incidentRecord.setLogTime(logTimeS);

            int ret = incidentRecordService.save(incidentRecord);

            if (ret == 0) {
                return new Result<>("处理失败", 4001);
            }

            return new Result<>("处理成功", 1001);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Result<>("日期格式解析错误", 4002);
        }
    }


    /**
     * 修改方法
     * @param incidentRecord
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Object update(IncidentRecord incidentRecord){
        // 定义输入和输出的时间格式
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 将接收到的时间字符串转换为 Date 对象
        Date occurrenceTime = null;
        try {
            occurrenceTime = inputFormat.parse(incidentRecord.getOccurrenceTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // 将 Date 对象转换为符合数据库要求的字符串格式
        String formattedOccurrenceTime = outputFormat.format(occurrenceTime);
        Date logTime = new Date();
        String logTimeS = outputFormat.format(logTime);

        incidentRecord.setOccurrenceTime(formattedOccurrenceTime);
        incidentRecord.setLogTime(logTimeS);

        System.out.println(incidentRecord.getIncidentType());
        int ret = incidentRecordService.update(incidentRecord);

        if (ret==0){
            Result result = new Result<>("处理失败",4001);
        }
        Result result = new Result<>("处理成功",1001);

        return result;
    }

    /**
     * 删除方法
     * @param id
     * @return
     */
    @RequestMapping(value = "remove",method = RequestMethod.GET)
    public Object remove(@RequestParam(value = "id") Integer id){
        System.out.println("==========================================="+id);
        int ret = incidentRecordService.remove(id);

        if (ret==0){
            Result result = new Result<>("处理失败",4001);
        }
        Result result = new Result<>("处理成功",1001);

        return result;
    }

    /**
     * 查找方法
     * @param id
     * @return
     */
    @RequestMapping(value = "find",method = RequestMethod.GET)
    public Object findById(@RequestParam(value = "id") Integer id){
        System.out.println("==========================================="+id);
        IncidentRecord incidentRecord = incidentRecordService.findById(id);

        if (incidentRecord==null){
            Result result = new Result<>("处理失败",4001);
        }
        Result result = new Result<>("处理成功",1001);
        result.setData(incidentRecord);

        return result;
    }
}
