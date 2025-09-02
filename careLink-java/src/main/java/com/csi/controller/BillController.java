package com.csi.controller;

import com.csi.domain.Bill;
import com.csi.domain.Contract;
import com.csi.domain.Result;
import com.csi.service.BillService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("BillController")
@RestController
public class BillController {

    @Autowired
    BillService billService;

    @PostMapping("/save")
    public Object save(@RequestBody Bill bill) {
        int ret = billService.saveBill(bill);
        if(ret==1){
            return new Result("成功",2001);
        }
        return new Result("失败",4001);
    }

    @GetMapping("/delete")
    public Object delete(@RequestParam("id") Integer id) {
        int ret = billService.deleteBillById(id);
        if(ret==1){
            return new Result("成功",2001);
        }
        return new Result("失败",4001);
    }

    @PostMapping("/modify")
    public Object modify(@RequestBody Bill bill) {
        System.out.println(bill.getId()+"---------------"+ bill.getStatus());
        int ret = billService.modifyBill(bill);
        if(ret==1){
            return new Result("成功",2001);
        }
        return new Result("失败",4001);
    }

    @GetMapping("/findById")
    public Object findById(@RequestParam("id") int id) {
        Bill bill = billService.findBillById(id);
        if(bill!=null){
            Result<Bill> result = new Result<>("成功",2001);
            result.setData(bill);
        }
        return new Result("暂无数据",4001);
    }

    @GetMapping("/findAll")
    public Object findAll(@RequestParam(value = "key",required = false) String key,
                          @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        List<Bill> bills = billService.findAllBill(key);

        PageInfo<Bill> pageInfo = new PageInfo<>(bills);

        if(bills.isEmpty()){
            Result<Bill> result = new Result<>("暂无数据",4001);
            return result;
        }
        Result<PageInfo<Bill>> result = new Result<>("成功",2001);
        result.setData(pageInfo);
        return result;
    }
}
