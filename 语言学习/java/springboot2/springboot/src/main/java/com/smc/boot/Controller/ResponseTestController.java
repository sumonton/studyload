package com.smc.boot.Controller;

import java.util.Date;

import com.smc.boot.bean.Pet;

import com.smc.boot.bean.Person;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date 2022/5/4
 * @Author smc
 * @Description:
 */
@Controller
public class ResponseTestController {
    @ResponseBody
    @GetMapping(value = "/test/person",produces = "application/x-smc")//设置数据返回类型
    public Person getPerson() {
        Person person = new Person();
        person.setUserName("张三");
        person.setAge(18);
        person.setBirth(new Date());
        person.setPet(new Pet());
        return person;
    }

}
