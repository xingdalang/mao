package com.dl.mao.common;

import com.alibaba.fastjson.JSONObject;
import com.dl.mao.service.impl.LoginServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xingguanghui
 * @create 2018年5月30日 10:55:12
 **/
@RestControllerAdvice
public class ExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public Object defaultHandler(HttpServletRequest req,Exception e){
        logger.error(e.getMessage());
        ResultBean re = new ResultBean();
        re.setCode(500);
        re.setMsg("system error!");
        return re;
    }
}