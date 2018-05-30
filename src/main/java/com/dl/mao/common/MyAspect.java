package com.dl.mao.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dl.mao.util.RSAUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SystemConfiguration systemConfiguration;

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


    @Around("webLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        String arg = JSON.toJSONString(args[0]);
        JSONObject object = JSON.parseObject(arg);
        // 解密
        String data = JSON.toJSONString(object.get("data"));
        String sign = JSON.toJSONString(object.get("sign"));

    /*    String decrypt = RSAUtils.decrypt(data, systemConfiguration.getRsaPrivateKey());
        boolean checkSign = RSAUtils.doCheck(data, sign, systemConfiguration.getRsaPrivateKey());
        */
        //
        JSONObject de = JSON.parseObject(data);

        RequestBean request = new RequestBean();
        request.setData(data);
        request.setSign(sign);

        args[0] = request;
        Object retVal = pjp.proceed(args);
        return retVal;
    }


    @AfterReturning(pointcut="webLog()", returning="rvt")
    public void doAfter(JoinPoint joinPoint, Object rvt) {
        logger.info(joinPoint.getSignature().getName()+" RETURN : " + JSON.toJSONString(rvt));
    }
}
