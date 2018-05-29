package com.dl.mao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xingguanghui
 * @create 2018-05-29 18:31
 **/
@Controller
public class LoginController {
    @RequestMapping("")
    @ResponseBody
    public Object login(@RequestBody User user){
        return null;
    }
}
