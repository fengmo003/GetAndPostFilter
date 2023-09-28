package com.dekang.filter;

import com.dekang.common.ParameterRequestWrapper;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter( urlPatterns = "/*")
public class QueryInterceptor implements Filter  {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("first拦截器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String method = req.getMethod();
        System.out.println("请求方式是什么" + method);
        //修改get请求
        if("GET".equals(method)) {
            System.out.println("first拦截器获取Attribute" + req.getAttribute("name"));//无

//        req.setAttribute("name","第一次设置的name");//作用范围为：一次请求域，即下次Filter也能取的到
            HttpServletResponse res = (HttpServletResponse) servletResponse;
//        res.setHeader("firstHeader","firstHeader");//设置请求头，会一直层层传递到控制层的响应头
            Map<String, Object> map = new HashMap<>();
            map.put("name", "王五");
            map.put("sex", "1000");

            ParameterRequestWrapper parameterRequestWrapper = new ParameterRequestWrapper(req, map);

            filterChain.doFilter(parameterRequestWrapper, res);//按Order优先顺序调用下一个Filter，直到Controller层
        }else{
            filterChain.doFilter(servletRequest, servletResponse);//按Order优先顺序调用下一个Filter，直到Controller层
        }
        System.out.println("firstFilterEnd");//doFilter内部执行完毕之后，才回来执行这个，类似二叉树的前序遍历

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        System.out.println("first拦截器销毁");
    }
}
