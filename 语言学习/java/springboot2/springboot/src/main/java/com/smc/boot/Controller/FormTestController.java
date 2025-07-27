package com.smc.boot.Controller;

import com.smc.boot.bean.User;
import com.smc.boot.exception.UserTooManyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/5/8
 * @Author smc
 * @Description:
 */
@Slf4j
@RestController
public class FormTestController {
    @GetMapping("/form_layout")
    public String form_layouts(){
        return "form/form_layout";
    }


    @GetMapping("/dynamic_table")
    public String dynamic_table(Model model){
        List<User> users = new ArrayList();
        if (users.size()>3){
            throw new UserTooManyException();
        }
        return "form/form_layout";
    }
    /**
     * 自动封装上传过来的文件
     * @param email
     * @param username
     * @param headerImg
     * @param photos
     * @return
     */
    @PostMapping("/upload")
    public String upload(String email, String username,
                         @RequestPart("headImg") MultipartFile headerImg,
                         @RequestPart("photos") MultipartFile[] photos) throws IOException {
        log.info("上传的信息：email={},username={},headerImg={},photos={}",
                email,username,headerImg.getSize(),photos.length);
        if (!headerImg.isEmpty()){
            //保存到文件服务器，OSS服务器
            String originalFilename = headerImg.getOriginalFilename();
            headerImg.transferTo(new File("/static/"+originalFilename));
        }
        if (photos.length>0){
            for (MultipartFile photo : photos) {
                String originalFilename = photo.getOriginalFilename();
                photo.transferTo(new File("/static/photos/"+originalFilename));
            }
        }
        return "main";
    }
}
