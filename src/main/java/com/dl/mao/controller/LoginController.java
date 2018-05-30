package com.dl.mao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dl.mao.common.RequestBean;
import com.dl.mao.common.ResultBean;
import com.dl.mao.model.User;
import com.dl.mao.service.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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



    @Autowired
    ILoginService loginService;

    @ApiOperation(value = "登录接口", httpMethod = "POST", response = ResultBean.class)
//    @RequestMapping(value = "/login",method = RequestMethod.POST,consumes="application/json")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean login(@RequestBody RequestBean bean){
        String data = bean.getData();
        User user = JSONObject.parseObject(data, User.class);
        ResultBean re = loginService.login(user);

        return re;
    }

    @ApiOperation(value = "注册接口", httpMethod = "POST", response = ResultBean.class)
//    @RequestMapping(value = "/signUp",method = RequestMethod.POST,consumes="application/json")
    @RequestMapping(value = "/signUp",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean signUp(@RequestBody User user){

        ResultBean re = loginService.signUp(user);

        return re;
    }
}
