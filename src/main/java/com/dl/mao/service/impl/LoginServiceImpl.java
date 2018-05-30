package com.dl.mao.service.impl;

import com.dl.mao.common.ResultBean;
import com.dl.mao.dao.UserMapper;
import com.dl.mao.model.User;
import com.dl.mao.service.ILoginService;
import com.dl.mao.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xingguanghui
 * @create 2018-05-30 10:13
 **/
@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    UserMapper userMapper;

    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public ResultBean login(User u) {
        ResultBean re = new ResultBean();
        User user = userMapper.selectByNameAndPass(u.getUserName(), MD5Util.generate(u.getPass()));
        if(user != null){
            re.setCode(0);
            re.setMsg("登录成功!");
        }else {
            re.setCode(1);
            re.setMsg("用户名密码错误!");
        }
        return re;
    }

    @Override
    public ResultBean signUp(User user) {
        ResultBean re = new ResultBean();
        User u = userMapper.selectByNameAndPass(user.getUserName(), null);
        if(u != null){
            re.setCode(1);
            re.setMsg("用户名重复!");
            return re;
        }
        user.setPass(MD5Util.generate(user.getPass()));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        try {
            int insert = userMapper.insert(user);
            if(insert == 1){
                re.setCode(0);
                re.setMsg("注册成功!");
            }else {
                re.setCode(1);
                re.setMsg("系统错误!");
            }
        }catch (Exception e){
            re.setCode(1);
            re.setMsg("系统错误!");
            logger.error(e.getMessage());
        }
        return re;
    }
}
