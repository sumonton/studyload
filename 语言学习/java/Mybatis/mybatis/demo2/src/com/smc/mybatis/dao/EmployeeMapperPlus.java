package com.smc.mybatis.dao;


import com.smc.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Date 2022/4/2
 * @Author smc
 * @Description:
 */
public interface EmployeeMapperPlus {
    Employee findById(Integer id);
    Employee getEmpAndDept(Integer id);
    Employee getEmpByIdStep(Integer id);
    Employee getEmpByIdStep1(Integer id);
}
