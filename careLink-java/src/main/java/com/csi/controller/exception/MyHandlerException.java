package com.csi.controller.exception;

import com.csi.domain.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyHandlerException {

    @ExceptionHandler(NullPointerException.class)
    public Result handlerNullException(NullPointerException e){
        System.out.println("发生了空指针异常："+e.getMessage());
        Result result = new Result<>("发生了空指针异常",5001);
        return result;
    }

    @ExceptionHandler(ArithmeticException.class)
    public Result ArithmeticException(ArithmeticException e){
        System.out.println("发生了数学异常："+e.getMessage());
        Result result = new Result<>("发生了数学异常",5001);
        return result;
    }

//    @ExceptionHandler(Exception.class)
    public Result Exception(Exception e){
        System.out.println("发生了异常："+e.getMessage());
        Result result = new Result<>("发生了异常",5001);
        return result;
    }
}
