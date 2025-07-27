package com.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter(filterName = "AdminFilter",urlPatterns = "/admin/*",
//        initParams = {@WebInitParam(name = "username",value="abc"),@WebInitParam(name = "url",value="abc")})
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println(config.getFilterName());
        System.out.println(config.getInitParameter("username"));
        System.out.println(config.getInitParameter("url"));
        System.out.println(config.getServletContext());

    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        if (user == null){
            request.getRequestDispatcher("/index.jsp").forward(request,response);
            return;
        }else {
            //让用户继续往下执行请求目标资源
            chain.doFilter(request,response);
        }
    }
}
