package com.zj.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new MyServletComponent(), "/servletDemo");
        return servletRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new MyFilterComponent());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/servletDemo"));
        return filterRegistrationBean;
    }
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean<MyListenerComponent> servletListenerRegistrationBean = new ServletListenerRegistrationBean(new MyListenerComponent());
        return servletListenerRegistrationBean;
    }
}
