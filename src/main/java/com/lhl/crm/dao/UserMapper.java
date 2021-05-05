package com.lhl.crm.dao;

import com.lhl.crm.base.BaseMapper;
import com.lhl.crm.vo.User;

public interface UserMapper extends BaseMapper<User,Integer> {

    /**
     * 通过用户查询用户记录，返回用户对象
     */
    User queryByName(String username);
}