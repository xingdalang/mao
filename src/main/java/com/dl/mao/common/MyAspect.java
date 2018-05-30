package com.dl.mao.common;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志切面
 *
 * @author xingguanghui
 * @create 2018-03-15 12:45
 **/
@Aspect
@Component
public class MyAspect {
    private Logger logger = LoggerFactory.getLogger(MyAspect.class);

    @Pointcut("execution(public * com.dl.mao.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod() + " IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + JSON.toJSONString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut="webLog()", returning="rvt")
    public void doAfter(JoinPoint joinPoint, Object rvt) {
        logger.info(joinPoint.getSignature().getName()+" RETURN : " + JSON.toJSONString(rvt));
    }
}
