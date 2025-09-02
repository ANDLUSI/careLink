package com.csi.controller;

import com.csi.domain.CarePlan;
import com.csi.domain.CarePlanDTO;
import com.csi.domain.Resident;
import com.csi.domain.Result;
import com.csi.service.CarePlanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "CarePlanController")
@RestController
public class CarePlanController {

    @Autowired
    CarePlanService carePlanService;

    /**
     * 分页查询+模糊查询+查询全部
     * @param key
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(@RequestParam(value = "key",required = false) String key,
                       @RequestParam(value = "oldStatus",required = false) Integer oldStatus,
                       @RequestParam(value = "planStatus",required = false) Integer planStatus,
                       @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        System.out.println(key);
        System.out.println(pageNum);
        System.out.println(pageSize);

        //引入分页插件
        PageHelper.startPage(pageNum,pageSize);

        //查询集合
        List<CarePlan> list = carePlanService.list(key,oldStatus,planStatus);

        //生成分页对象
        PageInfo<CarePlan> page = new  PageInfo<>(list);

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
     * @param carePlanDTO
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Object save(@RequestBody List<CarePlanDTO> carePlanDTO ) throws ParseException {



        int ret = 0;
        if (carePlanDTO!=null){
            for (CarePlanDTO planDTO : carePlanDTO) {
                // 定义输入和输出的时间格式
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                // 将接收到的时间字符串转换为 Date 对象
                Date occurrenceTime = inputFormat.parse(planDTO.getPlanDate());

                // 将 Date 对象转换为符合数据库要求的字符串格式
                String formattedOccurrenceTime = outputFormat.format(occurrenceTime);

                planDTO.setPlanDate(formattedOccurrenceTime);
                carePlanService.save(planDTO);
                ret++;
            }
        }

        if (ret==0){
            Result result = new Result<>("处理失败",4001);
            return result;
        }
        Result result = new Result<>("处理成功",2001);

        return result;
    }


    /**
     * 修改方法
     * @param carePlanDTO
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Object update(@RequestBody CarePlanDTO carePlanDTO){

        int ret = carePlanService.update(carePlanDTO);

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
        int ret = carePlanService.remove(id);

        if (ret==0){
            Result result = new Result<>("处理失败",4001);
            return  result;
        }
        Result result = new Result<>("处理成功",2001);

        return result;
    }

    /**
     * 查找方法
     * @param id
     * @return
     */
    @RequestMapping(value = "findById",method = RequestMethod.GET)
    public Object findById(@RequestParam(value = "employeeId") Integer id){
        System.out.println("==========================================="+id);
        CarePlan carePlan = carePlanService.findById(id);

        if (carePlan==null){
            Result result = new Result<>("处理失败",4001);
            return result;
        }
        Result result = new Result<>("处理成功",2001);
        result.setData(carePlan);

        return result;
    }

    /**
     * 通过老人状态查询老人
     * @param oldStatus
     * @return
     */
    @RequestMapping(value = "findStatus",method = RequestMethod.GET)
    public Object findOldByStatus(@RequestParam(value = "oldStatus") Integer oldStatus){
        System.out.println("==========================================="+oldStatus);
        List<Resident> residents = carePlanService.oldList(oldStatus);

        if (residents.size()==0){
            Result result = new Result<>("处理失败",4001);
            return result;
        }
        Result result = new Result<>("处理成功",2001);
        result.setData(residents);

        return result;
    }

}
