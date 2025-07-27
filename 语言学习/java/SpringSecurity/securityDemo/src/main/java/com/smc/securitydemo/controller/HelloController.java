package com.smc.securitydemo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @Date 2022/6/26
 * @Author smc
 * @Description:
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping("sayHello")
    @Secured({"ROLE_normal","Role_admin"})
    @PreAuthorize("hasAnyAuthority('admins')")
    @PostFilter("filterObject.username == 'admin1'")
    public ArrayList<UserInfo> sayHello(){
        ArrayList<UserInfo> list = new ArrayList<UserInfo>();
        list.add(new UserInfo(1l,"admin1","20"),"33");
        list.add(new UserInfo(2l,"admin2","20"),"44");
        return list;
    }
}
