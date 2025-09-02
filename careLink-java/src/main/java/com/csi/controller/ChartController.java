package com.csi.controller;

import com.csi.domain.Result;
import com.csi.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping(value = "ChartController")
@RestController
public class ChartController {
//    //查询老人年龄分布
//    List<String> ageState();

    @Autowired
    ChartService chartService;

    /**
     * 查询护理工数量
     * @return
     */
    @RequestMapping(value = "nurseNum",method = RequestMethod.GET)
    public Object nurseNum(){
        Integer nurseNum = chartService.nurseNum();


        if (nurseNum!=0){
            Result result = new Result<>("查询成功",2001);
            result.setData(nurseNum);
            return result;
        }
        Result result = new Result<>("查询失败",4001);
        return result;
    }

    /**
     * 查询突发事件数量
     * @return
     */
    @RequestMapping(value = "recordNum",method = RequestMethod.GET)
    public Object recordNum(){
        Integer nurseNum = chartService.recordNum();


        if (nurseNum!=0){
            Result result = new Result<>("查询成功",2001);
            result.setData(nurseNum);
            return result;
        }
        Result result = new Result<>("查询失败",4001);
        return result;
    }

    /**
     * 查询入住人数量
     * @return
     */
    @RequestMapping(value = "residentNum",method = RequestMethod.GET)
    public Object residentNum(){
        Integer nurseNum = chartService.residentNum();


        if (nurseNum!=0){
            Result result = new Result<>("查询成功",2001);
            result.setData(nurseNum);
            return result;
        }
        Result result = new Result<>("查询失败",4001);
        return result;
    }

    /**
     * 查询注册用户数量
     *
     * @return
     */
    @GetMapping("/userNum")
    public  Object userNum(){
      int num = chartService.getUserName();
      Result result= new Result();
      result.setCode(2001);
      result.setMessage("擦护心成功");
      result.setData(num);
      return result;
    }
    /**
     * 查询待处理的申请
     * @return
     */
    @RequestMapping(value = "applyNum",method = RequestMethod.GET)
    public Object applyNum(){
        Integer nurseNum = chartService.applyNum();


        if (nurseNum!=0){
            Result result = new Result<>("查询成功",2001);
            result.setData(nurseNum);
            return result;
        }
        Result result = new Result<>("查询失败",4001);
        return result;
    }

    /**
     * 查询老人年龄分布
     * @return
     */
    @RequestMapping(value = "ageState",method = RequestMethod.GET)
    public Object ageState() {
        // 出生日期集合
        List<Date> list = chartService.ageState();

        // 年龄集合
        List<Integer> ageList = new ArrayList<>();

        // 当前日期
        LocalDate today = LocalDate.now();

        // 根据出生日期计算出年龄并且放到年龄集合中
        for (Date date : list) {
            // 将 Date 转换为 LocalDate
            LocalDate birthDate = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // 计算年龄
            int age = Period.between(birthDate, today).getYears();
            ageList.add(age);
        }

        // 返回年龄统计
        Map<String, Long> ageDistribution = ageList.stream()
                .collect(Collectors.groupingBy(
                        age -> {
                            if (age >= 60 && age <= 69) return "60-69岁";
                            if (age >= 70 && age <= 79) return "70-79岁";
                            if (age >= 80 && age <= 89) return "80-89岁";
                            if (age >= 90) return "90岁以上";
                            return "低于60岁"; // 错误数据或不在范围内
                        },
                        Collectors.counting()
                ));

        Result result = new Result("处理成功",2001);
        result.setData(ageDistribution);
        return result;
    }


    @RequestMapping(value = "getIncidentCount")
    public List<Map<String, Object>>  getIncidentCount() {
        // 获取当前日期
        String currentDate = LocalDate.now().toString();
        List<Map<String, Object>> incidentCountByWeek = chartService.getIncidentCountForLast7Days(currentDate);
        return incidentCountByWeek;
    }

    @RequestMapping(value = "getIncidentCount15")
    public List<Map<String, Object>>  getIncidentCount15() {
        // 获取当前日期
        String currentDate = LocalDate.now().toString();
        List<Map<String, Object>> incidentCountByWeek = chartService.getIncidentCountForLast15Days(currentDate);
        return incidentCountByWeek;
    }


}
