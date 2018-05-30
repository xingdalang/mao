package com.dl.mao.controller;

import com.dl.mao.common.ResultBean;
import com.dl.mao.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xingguanghui
 * @create 2018-05-29 18:31
 **/
@Controller
public class LoginController extends BaseController{

    @RequestMapping(value = "/login",method = RequestMethod.POST,consumes="application/json")
    @ResponseBody
    public ResultBean login(@RequestBody User user){

        return null;
    }
}
