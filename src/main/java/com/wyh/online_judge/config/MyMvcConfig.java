package com.wyh.online_judge.config;
import com.wyh.online_judge.controller.LoginHandlerInterception;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/register.html").setViewName("register");
        registry.addViewController("/main.html").setViewName("dashboard");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterception())
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html","/register.html","/",
                        "/user/login","/user/toRegister","/user/register",
                        "/user/userExist",
                        "/css/**","/js/**","/img/**");
    }
}
