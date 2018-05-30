package com.dl.mao.controller;

import com.dl.mao.common.ResultBean;
import com.dl.mao.model.User;
import com.dl.mao.service.ILoginService;
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

    @RequestMapping(value = "/login",method = RequestMethod.POST,consumes="application/json")
    @ResponseBody
    public ResultBean login(@RequestBody User user){

        ResultBean re = loginService.login(user);

        return re;
    }

    @RequestMapping(value = "/siup",method = RequestMethod.POST,consumes="application/json")
    @ResponseBody
    public ResultBean siUp(@RequestBody User user){

        ResultBean re = loginService.siUp(user);

        return re;
    }
}
