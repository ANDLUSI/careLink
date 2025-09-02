package com.csi.controller.interceptor;

import com.csi.domain.Employee;
import com.csi.domain.Result;
import com.csi.service.RoleService;
import com.csi.service.UserService;
import com.csi.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.List;

//@Component
@Controller
public class MyInterceptor implements HandlerInterceptor {


    @Autowired
    private RoleService roleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器执行了preHandle");
        Result result = null;
        // 获取请求的 URI
        String requestURI = request.getRequestURI();
        System.out.println("当前请求路径: " + requestURI);

        // 通过Token获取当前登录人ID
        String token = request.getHeader("Token");
        // 通过Token获取当前登录人角色token
        String token2 = request.getHeader("Token2");

        if (token2 != null ){//用户权限
            //验证token是否有效的方法
            boolean tokenCheck = JwtUtil.checkToken(token2);
            if (!tokenCheck){
                result = new Result<>("无权访问！",6001);
                //写出提示需要
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                //需要进行JSON转换
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(result);
                out.print(json);
                return false;
            }
            //如果token有效则解析出其中的标志变量
            Claims claims = JwtUtil.getTokenBody(token2);
            String flag =  claims.get("empId", String.class);
            System.out.println("解析出的user================"+flag);
            if ("user".equals(flag)){
                return true;
            }

        }else if (token != null){ //管理权限
            //验证token是否有效的方法
            boolean tokenCheck = JwtUtil.checkToken(token);
            if (!tokenCheck){
                result = new Result<>("无权访问！",6001);
                //写出提示需要
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                //需要进行JSON转换
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(result);
                out.print(json);
                return false;
            }
            //如果token有效则解析出其中的empId
            Claims claims = JwtUtil.getTokenBody(token);
            String empId =  claims.get("empId", String.class);
            System.out.println("解析出的empID================"+empId);
            Employee employee = roleService.findById(empId);
            //是管理员则直接放行
            if (employee.getRoleId()==1){
                return true;
            }
            List<String> permissions = roleService.getPermission(Long.parseLong(empId));
            // 检查用户权限
            if (permissions != null) {
                for (String permission : permissions) {
                    System.out.println("路径====="+permission);
                    //使用contains方法匹配字符串
                    if (permission.contains(requestURI)) {
                        // 如果员工有访问权限，放行请求
                        return true;
                    }
                }
            }
        }else {
            result = new Result<>("无权访问！",6001);
            //写出提示需要
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            //需要进行JSON转换
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);
            out.print(json);
            return false;
        }

        // 如果没有权限，返回无权访问的响应
        result = new Result<>("无权访问！",6001);
        //写出提示需要
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        //需要进行JSON转换
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(result);
        out.print(json);
        return false;
//        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("拦截器执行了afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
