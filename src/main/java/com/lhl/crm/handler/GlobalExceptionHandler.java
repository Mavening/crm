package com.lhl.crm.handler;

import com.alibaba.fastjson.JSON;
import com.lhl.crm.base.ResultInfo;
import com.lhl.crm.exceptions.NoLoginException;
import com.lhl.crm.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常处理
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    /**
     * 方法的返回值
     *      1.返回视图
     *      2.返回数据
     * 判断方法返回值：通过方法上是否声明@ResponseBody注解
     * @param request
     * @param response
     * @param handler 方法对象
     * @param e
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

        /**
         * 非法请求拦截，判断是否抛出未登录异常，如果抛出该异常，则要求用户登录，会重定向到登录页面
         */
        if(e instanceof NoLoginException){
            //重定向到登录页面
            ModelAndView mv = new ModelAndView("redirect:index");
            return mv;
        }

        //默认的异常情况(返回视图)
        ModelAndView modelAndView = new ModelAndView("error");
        //设置异常信息
        modelAndView.addObject("code",500);
        modelAndView.addObject("msg","系统异常，请重试");
        //判断HandlerMethod
        if(handler instanceof HandlerMethod){
            //类型转换
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            //获取方法上声明的@ResponseBody注解对象
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            //判断responseBody对象是否为空，如果为空则表示返回的是视图，如果不为空则表示返回的是数据
            if(null==responseBody){
                //返回视图
                //判断异常类型
                if(e instanceof ParamsException){
                    //设置异常信息
                    ParamsException p =  (ParamsException) e;
                    modelAndView.addObject("code",p.getCode());
                    modelAndView.addObject("msg",p.getMsg());
                }
                return modelAndView;
            }else{
                //返回数据
                //设置默认的异常处理
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(500);;
                resultInfo.setMsg("服务器异常，请重试");
                //判断是否是自定义异常
                if(e instanceof ParamsException){
                    resultInfo.setCode(((ParamsException) e).getCode());
                    resultInfo.setMsg(((ParamsException) e).getMsg());
                }
                //设置响应类型及编码格式
                response.setContentType("application/json;charset=UTF-8");
                //字符输出流
                PrintWriter writer = null;
                try {
                    writer = response.getWriter();
                    //将需要返回的对象转换为json格式的字符串
                    String json = JSON.toJSONString(resultInfo);
                    writer.write(json);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } finally {
                    if(null!=writer) writer.close();
                }
                return null;
            }
        }
        return modelAndView;
    }
}
