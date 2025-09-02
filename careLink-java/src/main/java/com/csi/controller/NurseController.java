package com.csi.controller;

import com.csi.domain.Nurse;
import com.csi.domain.NurseDTO;
import com.csi.domain.Result;
import com.csi.service.NurseService;
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

@RequestMapping(value = "NurseController")
@RestController
public class NurseController {

    @Autowired
    private NurseService nurseService;

    //自动注入ServletContext
    @Autowired
    private ServletContext servletContext;

    @PostMapping(value = "/certificate")
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {

        //获取文件绝对路径
        String realPath = servletContext.getRealPath("/");
        //生成处理文件存放文件夹
        File folder = new File(realPath+"/upload/certificate");
        //不存在则创建
        if (!folder.exists()){
            folder.mkdirs();
        }

        //获取文件名
        String fileName = file.getOriginalFilename();
        //取出文件名后缀
        String ext = fileName.substring(fileName.lastIndexOf("."));
        //重命名图片
        String newName = UUID.randomUUID().toString().replace(".","")+ext;

        //写出文件(存储)
        file.transferTo(new File(folder,newName));

        //返回json
        //最终的图片路径
        String returnPath = "http://localhost/upload/certificate/"+newName;
        System.out.println(returnPath);
        Result<String> result = new Result<>("上传成功",2001);
        result.setData(returnPath);
        return result;

    }



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
        List<Nurse> list = nurseService.list(key);

        //生成分页对象
        PageInfo<Nurse> page = new  PageInfo<>(list);

        if (list.isEmpty()){
            Result result = new Result<>("处理失败",4001);
            return result;
        }
        Result result = new Result<>("处理成功",2001);
        result.setData(page);
        return result;
    }

    /**
     * 新增方法
     * @param nurse
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Object save(@RequestBody Nurse nurse ){
        System.out.println(nurse.getName());
        System.out.println(nurse.getRank());
        int ret = nurseService.save(nurse);

        if (ret==0){
            Result result = new Result<>("处理失败",4001);
            return result;
        }
        Result result = new Result<>("处理成功",2001);

        return result;
    }


    /**
     * 修改方法
     * @param nurse
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Object update(NurseDTO nurse){
        System.out.println(nurse.getName());
        System.out.println(nurse.getRank());
        int ret = nurseService.update(nurse);

        if (ret==0){
            Result result = new Result<>("处理失败",4001);
            return result;
        }
        Result result = new Result<>("处理成功",2001);

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
        int ret = nurseService.remove(id);

        if (ret==0){
            Result result = new Result<>("处理失败",4001);
            return  result;
        }
        Result result = new Result<>("处理成功",2001);

        return result;
    }

    /**
     * 查找方法
     * @param employeeId
     * @return
     */
    @RequestMapping(value = "findById",method = RequestMethod.GET)
    public Object findById(@RequestParam(value = "employeeId") Integer employeeId){
        System.out.println("==========================================="+employeeId);
        Nurse nurse = nurseService.findById(employeeId);
        if (nurse==null){
            Result result = new Result<>("处理失败",4001);
            return result;
        }
        Result result = new Result<>("处理成功",2001);
        result.setData(nurse);

        return result;
    }
}
