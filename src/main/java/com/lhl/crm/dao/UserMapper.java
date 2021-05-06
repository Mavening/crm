package com.lhl.crm.dao;

import com.lhl.crm.base.BaseMapper;
import com.lhl.crm.vo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User,Integer> {

    /**
     * 通过用户查询用户记录，返回用户对象
     */
    User queryByName(String username);


    /**
     * 查询所有的销售人员
     * @return
     */
    List<Map<String,Object>> queryAllSales();
}