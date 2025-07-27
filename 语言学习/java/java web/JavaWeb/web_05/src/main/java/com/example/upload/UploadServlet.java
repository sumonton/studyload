package com.example.upload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UploadServlet", value = "/UploadServlet")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 处理文件上传
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("文件上传过来了");
        //先判断上传的数据是否是多段数据（只有多端数据才是文件上传）
        if (ServletFileUpload.isMultipartContent(request)){
            //创建FileItemFactory工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            //创建用于解析上传数据的工具类ServlerFileUpload类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            //解析上传的数据，得到每一个表单项FileItem
            try {
                List<FileItem> list = servletFileUpload.parseRequest(request);
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()){
                        //普通表单项
                        System.out.println("表单项name属性值："+fileItem.getFieldName());
                        System.out.println("表单项value属性值值："+fileItem.getString("UTF-8"));
                    }else {
                        System.out.println("表单项的name属性值："+fileItem.getFieldName());
                        System.out.println("上传文件名："+fileItem.getName());
                        fileItem.write(new File("/Users/smc/Desktop/smc/语言学习/java/java web/JavaWeb/src/"+fileItem.getName()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
