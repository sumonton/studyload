package com.example.web_05;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "HelloServlet2", value = "/HelloServlet2")
public class HelloServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ServletContext context = getServletContext();
//        String username = context.getInitParameter("username");
//        String path = context.getContextPath();
//        String realPath = context.getRealPath("/");
//        System.out.println(username);
//        System.out.println(path);
//        System.out.println(realPath);
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL());
        System.out.println(request.getRemoteHost());
        System.out.println(request.getHeader("user-Agent"));
        System.out.println(request.getMethod());
        System.out.println(request.getParameter("username"));
        System.out.println(Arrays.asList(request.getParameterNames()));
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/hello-servlet");
//        requestDispatcher.forward(request,response);
//        response.setStatus(302);
//        response.setHeader("Location","/web_05_war_exploded/hello-servlet");
        response.sendRedirect("/web_05_war_exploded/hello-servlet");


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
