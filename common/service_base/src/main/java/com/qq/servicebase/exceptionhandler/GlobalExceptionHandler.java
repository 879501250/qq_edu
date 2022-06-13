package com.qq.servicebase.exceptionhandler;

import com.qq.commonutils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ==========@ControllerAdvice注解==========
 * 给Controller控制器添加统一的操作或处理，其抽象级别应该是用于对Controller进行切面环绕的
 * 而具体的业务织入方式则是通过结合其他的注解实现，如下面的@ExceptionHandler
 * 1.结合方法型注解@ExceptionHandler，用于捕获Controller中抛出的指定类型的异常，
 * 从而达到不同类型的异常区别处理的目的。
 * 2.结合方法型注解@InitBinder，用于request中自定义参数解析方式进行注册，从而达到自定义指定格式参数的目的。
 * 3.结合方法型注解@ModelAttribute，表示其注解的方法将会在目标Controller方法执行之前执行。
 * 从上面的讲解可以看出，@ControllerAdvice的用法基本是将其声明在某个bean上，
 * 然后在该bean的方法上使用其他的注解来指定不同的织入逻辑。不过这里@ControllerAdvice
 * 并不是使用AOP的方式来织入业务逻辑的，而是Spring内置对其各个逻辑的织入方式进行了内置支持。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) //当出现所有异常时执行
    @ResponseBody //使得返回得不是异常页面，而是我们自定义的json数据格式
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了全局的统一异常处理~");
    }

    @ExceptionHandler(ArithmeticException.class) //指定出现ArithmeticException异常时执行这个方法
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("执行了ArithmeticException异常处理~");
    }

    @ExceptionHandler(QqException.class) //指定出现自定义异常时执行这个方法
    @ResponseBody
    public Result error(QqException e){
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }
}
