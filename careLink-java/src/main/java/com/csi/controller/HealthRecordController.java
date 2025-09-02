package com.csi.controller;

import com.csi.domain.Contract;
import com.csi.domain.HealthRecord;
import com.csi.domain.Result;
import com.csi.service.HealthRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("HealthRecordController")
@RestController
public class HealthRecordController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    HealthRecordService healthRecordService;

    @PostMapping("/saveId")
    public Object saveContract(@RequestParam("residentId") int residentId){
        Result result = new Result();
        int ret = healthRecordService.saveId(residentId);
        if(ret==1){
            result.setCode(2001);
            result.setMessage("成功");
        }else {
            result.setCode(4001);
            result.setMessage("失败");
        }
        return result;
    }

    @PostMapping("/uploadM")
    public Object uploadM(@RequestParam("medicalRecord") MultipartFile medicalRecord){
        String filename = medicalRecord.getOriginalFilename();

        String realPath = servletContext.getRealPath("/");
        File file = new File(realPath+"/upload/medicalRecords");
        if(!file.exists()){
            file.mkdirs();
        }
        String ext = filename.substring(filename.lastIndexOf("."));
        String newName = UUID.randomUUID().toString().replace("-","")+ext;
        String returnPath = "http://localhost/upload/medicalRecords/"+newName;
        Result<String> result = new Result<>();
        try{
            medicalRecord.transferTo(new File(file,newName));
            result.setCode(2001);
            result.setMessage("文件上传成功！");
            result.setData(returnPath);
        }catch (IOException e){
            result.setCode(4001);
            result.setMessage("文件上传失败！");
        }
        return result;
    }

    @PostMapping("/uploadR")
    public Object uploadR(@RequestParam("report") MultipartFile report){
        String filename = report.getOriginalFilename();

        String realPath = servletContext.getRealPath("/");
        File file = new File(realPath+"/upload/reports");
        if(!file.exists()){
            file.mkdirs();
        }
        String ext = filename.substring(filename.lastIndexOf("."));
        String newName = UUID.randomUUID().toString().replace("-","")+ext;
        String returnPath = "http://localhost/upload/reports/"+newName;
        Result<String> result = new Result<>();
        try{
            report.transferTo(new File(file,newName));
            result.setCode(2001);
            result.setMessage("文件上传成功！");
            result.setData(returnPath);
        }catch (IOException e){
            result.setCode(4001);
            result.setMessage("文件上传失败！");
        }
        return result;
    }

    @PostMapping("/addHealthRecord")
    public Object addHealthRecord(HealthRecord healthRecord){
        int ret = healthRecordService.addHealthRecord(healthRecord);
        if(ret==1){
            return new Result("成功",2001);
        }
        return new Result("失败",4001);
    }

    @GetMapping("/delete")
    public Object delete(@RequestParam("id") int id){
        int ret = healthRecordService.deleteHRById(id);
        if(ret==1){
            return new Result("成功",2001);
        }
        return new Result("失败",4001);
    }

    @PostMapping("/modifyM")
    public Object modifyM(@RequestBody HealthRecord healthRecord){
        int ret = healthRecordService.modifyMedicalRecord(healthRecord);
        if(ret==1){
            return new Result("成功",2001);
        }
        return new Result("失败",4001);
    }

    @PostMapping("/modifyR")
    public Object modifyR(@RequestBody HealthRecord healthRecord){
        int ret = healthRecordService.modifyReport(healthRecord);
        if(ret==1){
            return new Result("成功",2001);
        }
        return new Result("失败",4001);
    }

    @GetMapping("/findById")
    public Object findById(@RequestParam("id") int id){
        HealthRecord healthRecord = healthRecordService.findById(id);
        if(healthRecord!=null){
            Result<HealthRecord> result = new Result<>("成功",2001);
            result.setData(healthRecord);
            return result;
        }
        return new Result("暂无数据",4001);
    }

    @GetMapping("/findAll")
    public Object findAll(@RequestParam(value = "key",required = false) String key,
                          @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);

        List<HealthRecord> healthRecords = healthRecordService.findAllHR(key);

        PageInfo<HealthRecord> pageInfo = new PageInfo<>(healthRecords);

        if(healthRecords.isEmpty()){
            return new Result("暂无数据",4001);
        }
        Result<PageInfo<HealthRecord>> result = new Result<>("成功",2001);
        result.setData(pageInfo);
        return result;
    }

}
