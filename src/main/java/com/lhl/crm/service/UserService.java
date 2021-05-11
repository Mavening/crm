package com.lhl.crm.service;


import com.lhl.crm.model.UserModel;
import com.lhl.crm.query.UserQuery;
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

    /**
     * 多条件查询
     * @param userQuery
     */
    List<User> queryByParamsForTable(UserQuery userQuery);

    /**
     * 添加用户
     * 1.参数校验
     *      用户名userName 非空，不能重复
     *      手机号码 非空，格式正确
     *      邮箱 非空
     * 2.设置参数默认值
     *      is_valid 1
     *      createDate 系统当前时间
     *      updateDate 系统当前时间
     *      默认密码  123456--->MD5加密
     * 3.执行添加操作，判断受影响的行数
     * @param user
     */
    @Transactional
    void addUser(User user);

    /**
     * 更新用户
     * 1.参数校验
     *      判断用户id是否为空，且数据存在
     *      用户名userName 非空，不能重复
     *      手机号码 非空，格式正确
     *      邮箱 非空
     * 2.设置参数的默认值
     *      updateDate  系统当前时间
     * 3.执行更新操作，判断受影响的行数即可
     * @param user
     */
    @Transactional
    void updateUser(User user,Integer userId);
}
