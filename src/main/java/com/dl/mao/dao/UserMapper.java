package com.dl.mao.dao;

import com.dl.mao.model.User;

public interface UserMapper{

//    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);


    User selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}