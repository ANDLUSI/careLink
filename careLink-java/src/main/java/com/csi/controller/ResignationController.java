package com.csi.controller;

import com.csi.domain.Activity;
import com.csi.domain.Resignation;
import com.csi.domain.ResignationDTO;
import com.csi.domain.Result;
import com.csi.service.ResignationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "ResignationController")
@RestController
public class ResignationController {
    @Autowired
    private ResignationService resignationService;

    //自动注入
    private ServletContext servletContext;

    //新增方法
    @RequestMapping(value = "save", method = RequestMethod.POST)

    public Object save(@RequestBody Resignation resignation) {
        System.out.println("=============="+resignation.getResidentId());
        int save = resignationService.save(resignation);
        if (save == 0) {
            Result result = new Result<>("新增失败", 4001);
            return result;
        }
        Result result = new Result<>("新增成功", 1001);

        return result;

    }

    //修改方法
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Object update(@RequestBody ResignationDTO resignation) {
        System.out.println(resignation.getId());
        System.out.println(resignation.getStatus());
        int update = resignationService.update(resignation);
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
        int remove = resignationService.remove(id);
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
        Resignation resignation = resignationService.findById(id);
        if (resignation == null) {
            Result result = new Result<>("查找失败", 4040);
            return result;
        }
        Result result = new Result<>("查找成功", 200);
        result.setData(resignation);
        return result;
    }

    //查找全部+模糊查询+分页查询
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object findAll(@RequestParam(value = "key", required = false) Integer residentId,
                          @RequestParam(value = "status", required = false) Integer status,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        System.out.println(residentId);
        System.out.println(status);
        System.out.println(pageNum);
        System.out.println(pageSize);
        //引入分页插件
        PageHelper.startPage(pageNum, pageSize);
        //查询集合
        List<Resignation> list = resignationService.list(residentId, status);
        //生成分页对象
        PageInfo<Resignation> page = new PageInfo<>(list);


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
