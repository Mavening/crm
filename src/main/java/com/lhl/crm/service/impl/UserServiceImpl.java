package com.lhl.crm.service.impl;

import com.lhl.crm.base.BaseService;
import com.lhl.crm.dao.UserMapper;
import com.lhl.crm.exceptions.ParamsException;
import com.lhl.crm.model.UserModel;
import com.lhl.crm.query.UserQuery;
import com.lhl.crm.service.UserService;
import com.lhl.crm.utils.AssertUtil;
import com.lhl.crm.utils.Md5Util;
import com.lhl.crm.utils.PhoneUtil;
import com.lhl.crm.utils.UserIDBase64;
import com.lhl.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseService<User,Integer> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserModel userLogin(String userName, String userPwd) {
        //1.参数判断，判断用户姓名，用户密码非空
        checkLoginParams(userName,userPwd);
        //2.查询数据库
        User user = userMapper.queryByName(userName);
        AssertUtil.isTrue(null==user,"用户不存在");
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(userPwd)),"密码不正确");
        //checkUserPwd(userPwd,user.getUserPwd());
        //返回构建用户对象
        return buildUserInfo(user);
    }

    /**
     * 需要返回给客户端的用户对象
     * @param user
     */
    private UserModel buildUserInfo(User user) {
        UserModel userModel = new UserModel();
        //设置加密的用户id
        userModel.setUserId(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    /**
     * 密码判断
     * @param userPwd
     * @param userPwd1
     */
    private void checkUserPwd(String userPwd, String userPwd1) {
        //将密码加密之后和数据库中密码比较
        String encode = Md5Util.encode(userPwd);
        AssertUtil.isTrue(!encode.equals(userPwd),"密码不正确");
    }

    /**
     * 用户名判断
     * @param userName
     * @param userPwd
     * @throws ParamsException
     */
    private void checkLoginParams(String userName, String userPwd)  {
        //验证用户名
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        //验证密码
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"密码不能为空");
    }

    @Override
    public User selectByPrimaryKey(Integer integer) throws DataAccessException {
        return userMapper.selectByPrimaryKey(integer);
    }

    @Override
    public void updatePassword(Integer userId, String oldPwd, String newPwd, String repeatPwd) {
        User user = userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(null==user,"用户不存在");
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd),"请输入旧密码");
        AssertUtil.isTrue(StringUtils.isBlank(newPwd),"请输入新密码");
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd),"请输入确认密码");
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPwd)),"原始密码不正确");
        AssertUtil.isTrue(!newPwd.equals(repeatPwd),"两次密码不一致");
        user.setUserPwd(Md5Util.encode(newPwd));
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"修改密码失败");
    }

    @Override
    public List<Map<String, Object>> queryAllSales() {
        return userMapper.queryAllSales();
    }

    @Override
    public List<User> queryByParamsForTable(UserQuery userQuery) {
        return userMapper.selectByParmas(userQuery);
    }

    @Override
    public void addUser(User user) {
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()),"用户名不能为空");
        AssertUtil.isTrue(null!=userMapper.queryByName(user.getUserName()),"用户名已存在");
        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()),"手机号不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()),"手机号格式不正确");
        AssertUtil.isTrue(StringUtils.isBlank(user.getEmail()),"邮箱不能为空");

        Date date = new Date();
        user.setIsValid(1);
        user.setCreateDate(date);
        user.setUpdateDate(date);
        //默认密码
        user.setUserPwd(Md5Util.encode("123456"));

        AssertUtil.isTrue(userMapper.insertSelective(user)==0,"用户添加失败");

    }


    @Override
    public void updateUser(User user,Integer userId) {
        //如果用户名存在，且与当前修改的记录不是同一个，则表示其他记录占用了该用户名
        AssertUtil.isTrue(null==user.getId(),"待更新记录不存在");
        AssertUtil.isTrue(null==userMapper.selectByPrimaryKey(user.getId()),"待更新记录不存在");
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()),"用户名不能为空");
        AssertUtil.isTrue(null!=userMapper.queryByName(user.getUserName())&&!user.getId().equals(userId),"用户名已存在");
        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()),"手机号不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()),"手机号格式不正确");
        AssertUtil.isTrue(StringUtils.isBlank(user.getEmail()),"邮箱不能为空");
        //设置默认值
        Date date = new Date();
        user.setUpdateDate(date);
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)==0,"更新用户操作失败");
    }
}
