package com.csi.controller;

import com.csi.domain.Bill;
import com.csi.domain.HealthRecord;
import com.csi.domain.Resident;
import com.csi.domain.Result;
import com.csi.service.BillService;
import com.csi.service.ContractService;
import com.csi.service.HealthRecordService;
import com.csi.service.ResidentService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Resident")
@ResponseBody
@CrossOrigin
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private HealthRecordService healthRecordService;

    @Autowired
    private BillService billService;

    @PostMapping("/findByNameAndIDCard")
    public Object findByNameAndIDCard(@RequestParam("searchName")String name,@RequestParam("searchIdNumber")String IDCard){

        Resident resident = residentService.findByNameAndIDCard(name, IDCard);
        Result<Resident> result =new Result<>();
        if (resident!=null){
            result.setCode(2001);
            result.setMessage("查询成功");
            result.setData(resident);
        }else {
            result.setCode(4001);
            result.setMessage("查询失败");
        }
        return result;
    }

    @PostMapping("/add")
    public Object add(@RequestBody Resident resident){

        System.out.println("+++++++++++++++++++++++++++++"+resident);

        if (resident.getEmployeeId()<=0 || resident.getRoomNo()<=0){
            Result result =new Result<>();
            result.setCode(4001);
            result.setMessage("新增失败");
            return  result;
        }

        /**
         * 生成账单
         */
        //获取护理费
     double price1=   billService.getPriceByEmpId(String.valueOf(resident.getEmployeeId()));
        //获取住宿费
      double price2 =  billService.getPriceRoomNo(resident.getRoomNo());
       //获取伙食费（默认）
       double price3 =  billService.getPriceByFood();

        System.out.println("===================================="+price1);

        Bill bill =new Bill();
        bill.setResidentId(resident.getId());
        bill.setBillDate(new Date());
        bill.setAmount(price1+price2+price3);
        bill.setStatus(0);
        bill.setDesc("护理费"+price1+"元,住宿费"+price2+"元，伙食费"+price3+"元");
        int ret = billService.saveBill(bill);
        int add =0;
        if (ret==1){
            add = residentService.add(resident);
            contractService.saveId(resident.getId());
            healthRecordService.saveId(resident.getId());
        }


        Result result =new Result<>();
        if (add==1){
            result.setCode(2001);
            result.setMessage("新增成功");
        }else {
            result.setCode(4001);
            result.setMessage("新增失败");
        }
        return  result;
    }

    @GetMapping("/deleteById")
    public Object deleteById(@RequestParam("id") Integer id){
        int i = residentService.deleteById(id);
        Result result =new Result<>();
        if (i==1){
            result.setCode(2001);
            result.setMessage("删除成功");
        }else {
            result.setCode(4001);
            result.setMessage("删除失败");
        }
        return  result;

    }

    @PostMapping("/batchDelete")
    public Object batchDelete(@RequestBody List<Integer> deleteList){
        int i = residentService.batchDelete(deleteList);
        Result result =new Result<>();
        if (i==deleteList.size()){
            result.setCode(2001);
            result.setMessage("全部删除成功");
        }else {
            result.setCode(4001);
            result.setMessage("未全部删除/删除失败");
        }
        return result;
    }

    @PostMapping("/modify")
    public  Object modify(@RequestBody Resident resident){
        System.out.println(resident);
        int i = residentService.modify(resident);
        Result result =new Result<>();
        if (i==1){
            result.setCode(2001);
            result.setMessage("修改成功");
        }else {
            result.setCode(4001);
            result.setMessage("修改失败");
        }
        return  result;
    }

    /**
     * 分页查询全部|分页模糊查询
     * @param name
     * @param
     * @return
     */
    @GetMapping("/list")
    public Object list(@RequestParam(value = "name",required = false) String name,
                       @RequestParam("pageNo") int pageNo,
                       @RequestParam("pageSize") int pageSize){
        List<Resident> list = residentService.list(name,pageNo,pageSize);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++"+list);
        PageInfo<Resident> pageInfo =new PageInfo<>(list);
        Result<PageInfo<Resident>> result =new Result<>();
        if (list!=null){
            result.setCode(1001);
            result.setMessage("查询成功");
            result.setData(pageInfo);
        }else {
            result.setCode(4001);
            result.setMessage("查询失败");
        }
        return result;
    }
}
