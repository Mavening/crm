package com.lhl.crm.interceptors;

import com.lhl.crm.dao.UserMapper;
import com.lhl.crm.exceptions.NoLoginException;
import com.lhl.crm.utils.CookieUtil;
import com.lhl.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 非法访问拦截
 */
@Component
public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserMapper userMapper;

    /**
     * 拦截用户是否登录状态
     * 返回true表示目标方法可以执行
     * 返回false表示目标方法不能执行
     * 判断用户是否是登录状态：
     *      1.判断cookie中是否存在用户信息(获取用户id)
     *      2.数据库中是否存在指定用户id值
     *
     * 如果用户是登录状态，允许目标方法执行
     * 如果用户是非登录状态，则抛出未登录异常(在全局异常中做判断，如果是未登录异常，则跳转到登录页面)
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取cookie中的用户id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //判断用户id是否为空，且数据库中存在该id的用户记录
        if(null==userId||userMapper.selectByPrimaryKey(userId)==null){
            //抛出未登录异常
            throw new NoLoginException();
        }
        return true;
    }
}
