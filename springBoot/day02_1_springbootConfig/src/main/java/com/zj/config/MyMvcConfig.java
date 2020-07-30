package com.zj.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.security.auth.message.config.RegistrationListener;
import java.util.Arrays;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    //只要访问这些路径，就会跳转到index.html页面
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index");
        registry.addViewController("/index.html").setViewName("/index");
        registry.addViewController("/index").setViewName("/index");
        registry.addViewController("/main.html").setViewName("/dashboard");
    }
 //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyLoginInterceptor()).addPathPatterns("/**").excludePathPatterns(
                "/index.html", "/", "/user/login", "/css/**", "/js/**", "/img/**");
    }
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new MyServletComponent(), "/java103/servletDemo");
        return servletRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new MyFilterComponent());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/java103/servletDemo"));
        return filterRegistrationBean;
    }
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new MyListenerComponent());
        return servletListenerRegistrationBean;
    }
}
