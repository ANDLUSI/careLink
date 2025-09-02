package com.csi.controller;

import com.csi.domain.Employee;
import com.csi.domain.Nurse;
import com.csi.domain.Result;
import com.csi.service.EmployeeService;
import com.csi.service.NurseService;
import com.github.pagehelper.PageInfo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService  employeeService;

    @Autowired
    private NurseService nurseService;

    @GetMapping("/list")
    public Object list(@RequestParam(value = "name",required = false) String name,
                       @RequestParam(value = "pageSize",required = false)int pageSize,
                       @RequestParam(value = "pageNo",required = false) int pageNum){


        List<Employee> list = employeeService.list(name,pageNum,pageSize);
        PageInfo<Employee> pageInfo =new PageInfo<>(list);
        System.out.println("======================="+list);

        Result<PageInfo<Employee>> result =new Result<>();
        if (list!=null){
            result.setCode(2001);
            result.setMessage("查询成功");
            result.setData(pageInfo);
        }else {
            result.setCode(4001);
            result.setMessage("查询失败");
        }

        return result;
    }



    @GetMapping("/findLikeName")
    public Object findLikeName(@RequestParam("name") String name){
        List<Employee> list = employeeService.findLikeName(name);
        Result<List<Employee>> result =new Result<>();
        result.setCode(2001);
        result.setMessage("查询成功");
        result.setData(list);
        return result;

    }


    @PostMapping("/save")
    public Object save(@RequestBody Employee employee){

        Date date =new Date();
        employee.setHireDate(date);

        SimpleDateFormat format =new SimpleDateFormat("yyMM");
        //ID前缀
        String prefix = format.format(date);
        //ID后缀
        String suffix =  employee.getPhone().substring(7);
        //生成员工Id
        employee.setEmployeeId(prefix+suffix);
        System.out.println("==================================================="+employee.getRoleId());
        int add = employeeService.add(employee);

        if (employee.getRoleId()==4){
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            Nurse nurse =new Nurse();
            nurse.setEmployeeId(employee.getEmployeeId());
            nurse.setName(employee.getName());
            nurseService.save(nurse);
        }
        Result<Object> result =new Result<>();
        if (add==1){
            result.setCode(2001);
            result.setMessage("新增成功");
        }else {
            result.setCode(4001);
            result.setMessage("新增失败");
        }
        return result;
    }


    @GetMapping("/deleteById")
    public Object deleteById(@RequestParam("id") String employeeId){
        int i = employeeService.deleteById(employeeId);
        Result<Object> result =new Result<>();
        if (i==1){
            result.setCode(2001);
            result.setMessage("删除成功");
        }else {
            result.setCode(4001);
            result.setMessage("删除失败");
        }
        return result;
    }


    @PostMapping("/batchDelete")
    public Object batchDelete(@RequestBody List<Integer> list){
        int i = employeeService.batchDelete(list);
        Result result =new Result<>();
        if (i==list.size()){
            result.setCode(2001);
            result.setMessage("全部删除成功");
        }else {
            result.setCode(4001);
            result.setMessage("未全部删除/删除失败");
        }
        return result;
    }

    @PostMapping("/modify")
    public Object modify(@RequestBody Employee employee){
        Date date =new Date();
        employee.setUpdatedAt(date);
        int modify = employeeService.modify(employee);
        Result<Object> result =new Result<>();
        if (modify==1){
            result.setCode(2001);
            result.setMessage("修改成功");
        }else {
            result.setCode(4001);
            result.setMessage("修改失败");
        }
        return result;
    }

}
