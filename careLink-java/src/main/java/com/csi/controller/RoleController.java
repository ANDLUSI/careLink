package com.csi.controller;

import com.csi.domain.EmpLoginDTO;
import com.csi.domain.Employee;
import com.csi.domain.Result;
import com.csi.domain.Role;
import com.csi.service.RoleService;
import com.csi.utils.JwtUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "RoleController")
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;


    /**
     * 分页查询+查询全部
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(@RequestParam(value = "key",required = false) String key,
                       @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        System.out.println(pageNum);
        System.out.println(pageSize);

        //引入分页插件
        PageHelper.startPage(pageNum,pageSize);

        //查询集合
        List<Role> list = roleService.list(key);

        //生成分页对象
        PageInfo<Role> page = new  PageInfo<>(list);

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
     * @param role
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Object save(@RequestBody Role role){

        int ret = roleService.save(role);

        if (ret == 0) {
            return new Result<>("处理失败", 4001);
        }

        return new Result<>("处理成功", 1001);
    }


    /**
     * 修改方法
     * @param role
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Object update(Role role){

        int ret = roleService.update(role);

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
        int ret = roleService.remove(id);

        if (ret==0){
            Result result = new Result<>("处理失败",4001);
        }
        Result result = new Result<>("处理成功",1001);

        return result;
    }





    /**
     * 员工登录并且返回带着员工id的Token
     * @param empLoginDTO
     *
     * @return
     */
    @RequestMapping(value = "login")
    public Object login(@RequestBody EmpLoginDTO empLoginDTO){

        Employee employee = roleService.login(empLoginDTO.getPhone(),empLoginDTO.getPassword());

        if (employee == null) {
            Result result = new Result<>("登录失败", 4001);
            return result;
        }
        Result result = new Result<>("登录成功", 2001);
        result.setData(employee);
        String token = JwtUtil.getToken(employee.getEmployeeId());
        result.setToken(token);
        return result;
    }


}
