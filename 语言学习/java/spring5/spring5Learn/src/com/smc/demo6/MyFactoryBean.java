package com.smc.demo6;

import com.smc.demo4.Course;
import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<Course> {

    //定义返回bean
    @Override
    public Course getObject() throws Exception {
        Course course = new Course();
        course.setName("CPP");
        return course;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
