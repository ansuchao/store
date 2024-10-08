package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
@Configuration
/*处理器拦截器的注册*/
public class LoginInterceptorConfigurer implements WebMvcConfigurer {
    //创建拦截器对象
    HandlerInterceptor interceptor = new LoginInterceptor();


    /*配置拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置白名单:存放一个List集合
        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/districts/**");
        patterns.add("/products/**");
    //
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }

}
