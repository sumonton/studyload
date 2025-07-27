package com.example;

import com.example.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SessionServlet", value = "/SessionServlet")
public class SessionServlet extends BaseServlet {
    public void  createOrGetSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建session会话
        HttpSession session = request.getSession();
        //判读当前session是否微信
        boolean aNew = session.isNew();
        //获取session唯一表示id
        String id  = session.getId();
        response.getWriter().write("得到的session，它的id是："+ id + "<br/>");
        response.getWriter().write("得到的session是否为新："+ aNew + "<br/>");

    }

    /**
     * session存值
     * @param request
     * @param response
     */
    public void  setAttribute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建session会话
        HttpSession session = request.getSession();
        //存值
        session.setAttribute("key1","value1");
        response.getWriter().write("已经在session中保存了值");

    }

    /**
     * session取值
     * @param request
     * @param response
     * @throws IOException
     */
    public void  getAttribute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建session会话
        HttpSession session = request.getSession();
        //取值
        String key1 = (String)session.getAttribute("key1");
        response.getWriter().write("key1值为："+ key1 + "<br/>");
    }

    /**
     * 获取session默认生命周期时长
     * @param request
     * @param response
     * @throws IOException
     */
    public void  getDefaultLife(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取session默认生命周期时长
        int maxInactiveInterval = request.getSession().getMaxInactiveInterval();
        response.getWriter().write("默认生命周期为："+ maxInactiveInterval + "<br/>");
    }

    /**
     * 销毁session
     * @param request
     * @param response
     * @throws IOException
     */
    public void  deleteSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取session默认生命周期时长
        request.getSession().invalidate();
        response.getWriter().write("session已销毁");
    }
}
