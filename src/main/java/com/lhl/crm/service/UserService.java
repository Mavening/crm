package com.lhl.crm.service;


import com.lhl.crm.model.UserModel;
import com.lhl.crm.vo.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 用户登录
     * @param username
     * @param userPwd
     */
    UserModel userLogin(String username, String userPwd);

    /**
     * 通过id查询用户
     * @return
     */
    User selectByPrimaryKey(Integer userId);

    /**
     * 修改用户密码
     */
    @Transactional
    void updatePassword(Integer userId,String oldPwd,String newPwd,String repeatPwd);

    /**
     * 查询所有销售人员
     * @return
     */
    List<Map<String,Object>> queryAllSales();

}
