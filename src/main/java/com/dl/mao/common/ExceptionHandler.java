package com.dl.mao.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xingguanghui
 * @create 2018年5月30日 10:55:12
 **/
@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public Object defaultHandler(HttpServletRequest req,Exception e){
        ResultBean re = new ResultBean();
        re.setCode(500);
        re.setMsg("system error!");
        return re;
    }
}