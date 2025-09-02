package com.csi.config;

import com.csi.controller.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@EnableWebMvc
@ComponentScan("com.csi.controller")
public class SpringMvcCoreConfig implements WebMvcConfigurer {



    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/jsp/", ".jsp");
    }

    @Bean
    public HandlerMapping handlerMapping(){
        return new RequestMappingHandlerMapping();
    }

    @Bean
    public HandlerAdapter handlerAdapter(){
        return new RequestMappingHandlerAdapter();
    }


    @Autowired
    private MyInterceptor myInterceptor;
    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 将拦截器添加到Springmvc环境，默认拦截所有Springmvc分发的请求
        registry.addInterceptor(myInterceptor)
                .excludePathPatterns(
                        "/RoleController/login",
                        "/UserController/loginP",
                        "/UserController/loginC",
                        "/UserController/sendCode",
                        "/UserController/register",
                        "/UserController/message",
                        "/ChartController/ageState",
                        "/ChartController/nurseNum",
                        "/ChartController/recordNum",
                        "/ChartController/residentNum",
                        "/ChartController/applyNum",
                        "/ChartController/getIncidentCount",
                        "/ChartController/userNum"
                );
    }

    //静态资源访问
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //跨域访问
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("*") ;
    }
}
