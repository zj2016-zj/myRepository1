package com.zj.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
@Controller
public class LoginFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取Zuul上下文对象
        RequestContext context = RequestContext.getCurrentContext();
        //从上下文获取
        HttpServletRequest request = context.getRequest();
        //获取token信息
        String token = request.getParameter("token");
        //判断
        if(StringUtils.isBlank(token)){ //如果token为空
            //过滤该请求，不对其进行路由
            context.setSendZuulResponse(false);
            //设置响应状态码
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            //设置响应信息
            context.setResponseBody("{'status':'401','text':'request error!'}");
        }
        return null;
    }
}
