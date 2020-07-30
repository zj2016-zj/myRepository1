package com.zj.config;

import javax.servlet.*;
import java.io.IOException;

public class MyFilterComponent implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("请求被拦截过滤");
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("filter销毁");
    }
}
