package com.example.web_05;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");//设置服务器字符集为utf-8
        //通过响应头，设置浏览器也使用utf-8字符集
        response.setHeader("content-type","text/html;charset=UTF-8");
//        response.setContentType("text/html;charset=UTF-8");
        // Hello
        PrintWriter out = response.getWriter();
        out.write("你很好");
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}