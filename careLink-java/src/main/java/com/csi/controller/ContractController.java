package com.csi.controller;

import com.csi.domain.Contract;
import com.csi.domain.Result;
import com.csi.service.ContractService;
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

@RequestMapping("ContractController")
@RestController
public class ContractController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    ContractService contractService;

    @PostMapping("/upload")
    public Object upload(@RequestParam("contract") MultipartFile contract){
        String fileName = contract.getOriginalFilename();

        String realPath = servletContext.getRealPath("/");
        File file = new File(realPath+"/upload/contracts");
        if (!file.exists()) {
            file.mkdirs();
        }

        String ext = fileName.substring(fileName.lastIndexOf("."));
        String newName = UUID.randomUUID().toString().replace("-", "") + ext;
        String returnPath = "http://localhost/upload/contracts/"+newName;
        System.out.println(returnPath);
        Result<String> result = new Result<>();
        try {
            contract.transferTo(new File(file,newName));
            result.setCode(2001);
            result.setMessage("文件上传成功！");
            result.setData(returnPath);
        } catch (IOException e) {
            result.setCode(4001);
            result.setMessage("文件上传失败！");
        }
        return result;
    }

    @PostMapping("/saveId")
    public Object saveContract(@RequestParam("residentId") int residentId){
        int ret = contractService.saveId(residentId);
        if(ret==1){
            return new Result("成功",2001);
        }
        return new Result("失败",4001);
    }

    @PostMapping("/addContract")
    public Object addContract(@RequestBody Contract contract){
        int ret = contractService.addContract(contract);
        if(ret==1){
            return new Result("成功",2001);
        }
        return new Result("失败",4001);
    }

    @GetMapping("/delete")
    public Object deleteById(@RequestParam("id") int id){
        int ret = contractService.deleteContractById(id);
        if(ret==1){
            return new Result("成功",2001);
        }
        return new Result("失败",4001);
    }

    @PostMapping("/modify")
    public Object modifyContract(@RequestBody Contract contract){
        int ret = contractService.modifyContract(contract);
        if(ret==1){
            return new Result("成功",2001);
        }
        return new Result("失败",4001);
    }

    @GetMapping("/findById")
    public Object findById(@RequestParam("id") int id){
        Contract contract = contractService.findContractById(id);
        if(contract!=null){
            Result<Contract> result = new Result<>("成功",2001);
            result.setData(contract);
        }
        return new Result("暂无数据",4001);
    }

    @GetMapping("/findAll")
    public Object findAll(@RequestParam(value = "key",required = false) String key,
                          @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){

        PageHelper.startPage(pageNum,pageSize);

        List<Contract> contracts = contractService.findAllContract(key);

        PageInfo<Contract> pageInfo = new PageInfo<>(contracts);

        if(contracts.isEmpty()){
            Result<Contract> result = new Result<>("暂无数据",4001);
            return result;
        }
        Result<PageInfo<Contract>> result = new Result<>("成功",2001);
        result.setData(pageInfo);
        return result;
    }
}
