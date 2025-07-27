package com.smc.mybatis.dao;

import com.smc.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Select;

/**
 * @Date 2022/4/2
 * @Author smc
 * @Description:
 */
public interface EmployeeMapperAnnotation {
    @Select("select * from tb1_employee where id = ${id}")
    Employee findEmpById(Integer id);

}
