package com.smc.mybatis.dao;


import com.smc.mybatis.bean.Department;
import com.smc.mybatis.bean.Employee;

/**
 * @Date 2022/4/2
 * @Author smc
 * @Description:
 */
public interface DepartmentMapper {
    Department findById(Integer id);
    Department findByIdAndEmps(Integer id);
    Department findDeptByIdStep(Integer id);
}
