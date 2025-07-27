package com.example.ajax;

import com.example.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AjaxServlet", value = "/admin/AjaxServlet")
public class AjaxServlet extends BaseServlet {
    public void javascriptAjaxRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("获取到了请求");
        response.getWriter().write("回应");
    }
}
