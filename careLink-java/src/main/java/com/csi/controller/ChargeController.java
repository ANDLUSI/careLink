package com.csi.controller;

import com.csi.domain.Charge;
import com.csi.domain.Result;
import com.csi.service.ChargeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "ChargeController")
@RestController
public class ChargeController {

    @Autowired
    private ChargeService chargeService;

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
        List<Charge> list = chargeService.list(key);
        System.out.println("========================="+list.size());

        //生成分页对象
        PageInfo<Charge> page = new  PageInfo<>(list);

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
     * @param charge
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Object save(@RequestBody Charge charge){

        int ret = chargeService.save(charge);

        if (ret == 0) {
            return new Result<>("处理失败", 4001);
        }

        return new Result<>("处理成功", 1001);

    }


    /**
     * 修改方法
     * @param charge
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Object update(Charge charge){

        int ret = chargeService.update(charge);

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
        int ret = chargeService.remove(id);

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
        Charge charge = chargeService.findById(id);

        if (charge==null){
            Result result = new Result<>("处理失败",4001);
        }
        Result result = new Result<>("处理成功",1001);
        result.setData(charge);

        return result;
    }

    /**
     * 匹配房间方法
     * @param type
     * @return
     */
    @RequestMapping(value = "findByType",method = RequestMethod.GET)
    public Object listByType(@RequestParam(value = "type") String type){
        System.out.println("==========================================="+type);
        List<Charge> charges = chargeService.listByType(type);

        if (charges.isEmpty()){
            Result result = new Result<>("处理失败",4001);
            return result;
        }
        Result result = new Result<>("处理成功",1001);
        result.setData(charges);

        return result;
    }
}
