package com.dl.mao.service;

import com.dl.mao.common.ResultBean;
import com.dl.mao.dao.UserMapper;
import com.dl.mao.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface ILoginService {


    /**
     * 登录
     * @param user
     * @return
     */
    ResultBean login(User user);

    /**
     * 注册
     * @param user
     * @return
     */
    ResultBean siUp(User user);
}
