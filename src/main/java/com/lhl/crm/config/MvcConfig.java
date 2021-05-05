package com.lhl.crm.config;

import com.lhl.crm.interceptors.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    //将方法的返回值交给IOC维护
    @Bean
    public NoLoginInterceptor noLoginInterceptor1(){
        return new NoLoginInterceptor();
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //需要一个实现了拦截器功能的实例对象，这里使用的是noLoginInterceptor
        registry.addInterceptor(noLoginInterceptor1())
                .addPathPatterns("/**")  //默认拦截所有资源
                .excludePathPatterns("/css/**","/images/**","/js/**","/lib/**","/index","/user/login");  //放行资源


    }
}
