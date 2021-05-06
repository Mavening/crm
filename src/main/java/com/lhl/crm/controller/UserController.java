package com.lhl.crm.controller;

import com.lhl.crm.base.BaseController;
import com.lhl.crm.base.ResultInfo;
import com.lhl.crm.exceptions.ParamsException;
import com.lhl.crm.model.UserModel;
import com.lhl.crm.service.UserService;
import com.lhl.crm.utils.CookieUtil;
import com.lhl.crm.utils.LoginUserUtil;
import com.lhl.crm.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userName
     * @param userPwd
     * @return
     */
    @ResponseBody
    @PostMapping("login")
    public ResultInfo userLogin(String userName, String userPwd){
        //通过try catch捕获service层的异常，如果service层抛出异常则表示登录失败，否则登录成功
        ResultInfo resultInfo = new  ResultInfo();
        try{
            UserModel userModel = userService.userLogin(userName, userPwd);
            resultInfo.setMsg("登录成功");
            resultInfo.setResult(userModel);
        }catch (ParamsException e){
            resultInfo.setCode(400);
            resultInfo.setMsg(e.getMsg());
            return resultInfo;
        }catch (Exception e){
            resultInfo.setCode(500);
            resultInfo.setMsg("登录失败");
        }
        return resultInfo;
    }

    /**
     * 修改密码
     * @param request
     * @param oldPwd
     * @param newPwd
     * @param repeatPwd
     * @return
     */
    @ResponseBody
    @PutMapping("updatePwd")
    public ResultInfo updateUserPassword(HttpServletRequest request,String oldPwd,String newPwd,String repeatPwd){
        try{
            //获取cookie中的用户id
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            userService.updatePassword(userId,oldPwd,newPwd,repeatPwd);
        } catch (ParamsException e){
            return new ResultInfo(400,e.getMsg(),null);
        } catch (Exception e){
            return new ResultInfo(401,e.getMessage(),null);
        }
            return new ResultInfo(200,"修改成功",null);
    }

    @ResponseBody
    @GetMapping("/queryAllSales")
    public List<Map<String, Object>> queryAllSales(){
        List<Map<String, Object>> list = userService.queryAllSales();
        list.forEach(System.out::println);
        return list;
        //return new ResultInfo(200,"操作成功",null);
    }

}
