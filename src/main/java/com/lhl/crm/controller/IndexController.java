package com.lhl.crm.controller;

import com.lhl.crm.base.BaseController;
import com.lhl.crm.service.UserService;
import com.lhl.crm.utils.LoginUserUtil;
import com.lhl.crm.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 系统登录页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }


    /**
     * 系统界面欢迎页
     *
     * @param
     * @return java.lang.String
     */
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    /**
     * 后端管理主页面
     *
     * @param
     * @return java.lang.String
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        //查询用户对象,设置session作用域
        User user = null;
        //获取cookie中用户id的值
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        user = userService.selectByPrimaryKey(userId);
        request.getSession().setAttribute("user",user);
        return "main";
    }

    /**
     * 跳转到修改密码界面
     * @return
     */
    @RequestMapping("password")
    public String toPassword(){
        return "/user/password";
    }

    @RequestMapping("settings")
    public String toSettings(){
        return "/user/add_update";
    }

   /* *//**
     * 用户退出
     *
     * @return
     *//*
    @RequestMapping("/signout")
    public String toSignout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:index";
    }*/
}
