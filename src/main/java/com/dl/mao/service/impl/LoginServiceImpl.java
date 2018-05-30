package com.dl.mao.service.impl;

import com.dl.mao.common.ResultBean;
import com.dl.mao.dao.UserMapper;
import com.dl.mao.model.User;
import com.dl.mao.service.ILoginService;
import com.dl.mao.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xingguanghui
 * @create 2018-05-30 10:13
 **/
@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    UserMapper userMapper;

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


}
