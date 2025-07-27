package com.example.down;

import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@WebServlet(name = "DownloadServlet", value = "/DownloadServlet")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取下载文件名
        String downloadFileName = "srcxmut.jpg";
        //2、读取要下载的文件内容（通过ServletContext对象可以读取）
        ServletContext servletContext = getServletContext();
        //获取要下载的文件类型
        String mimeType = servletContext.getMimeType("/file/" + downloadFileName);
        System.out.println(mimeType);
        //3、在回传前通过响应头告诉客户端返回的数据类型
        response.setContentType(mimeType);
        //4、还要告诉客户端收到的数据是用于下载使用（还是使用响应头）
        //content-Disposition响应头，表示收到的数据怎么处理
        //attachement表示附件，表示下载使用
        //filename = 指定下载的文件名
        response.setHeader("Content-Disposition","attchement;filename = "+ URLEncoder.encode("厦门理工.jpg","UTF-8"));
        InputStream resourceAsStream = servletContext.getResourceAsStream("/file/" + downloadFileName);
        //获取响应的输出流
        OutputStream outputStream = response.getOutputStream();
        //读取输入流中的所有数据传递给输出流
        IOUtils.copy(resourceAsStream,outputStream);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
