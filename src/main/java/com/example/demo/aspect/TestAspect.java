package com.example.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class TestAspect {


    @Pointcut("execution(* com.example.demo.controller.*.*(..))")
    public void executionGet(){

    }

    @Around(value = "executionGet()")
    public Object around (ProceedingJoinPoint point) throws Throwable {


        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        for (String s:
             paramNames) {
            System.out.println(s);
        }
       Object object =  point.proceed();

        return "woaini";
    }



}
