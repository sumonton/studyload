package com.smc.boot.Controller;

import com.smc.boot.bean.Admin;
import com.smc.boot.bean.Person;
import com.smc.boot.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Period;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/5/1
 * @Author smc
 * @Description:
 */
@RestController
public class HelloController {
    @Autowired
    private Admin admin;

    @RequestMapping("returnAdmin")
    public Admin returnAdmin() {
        return admin;
    }

    @RequestMapping("/hello")
    public String handle01() {
        return "Hello,Spring Boot2";


    }

    @RequestMapping("/car/{path}")
    public Map matrixMethod(@MatrixVariable("low")
                                       Integer low, @MatrixVariable
                                       ("brand") String brand) {
        Map<String, Object> map = new HashMap<>();
        map.put("low",low);
        map.put("brand",brand);
        return map;
    }
    @PostMapping("/saveUser")
    public Person saveUser(Person person){
        return person;
    }
    @Value("${person.name:李四}")
    private String name;

    @GetMapping
    public String hello1(){
        return "Hello "+name;
    }
}
