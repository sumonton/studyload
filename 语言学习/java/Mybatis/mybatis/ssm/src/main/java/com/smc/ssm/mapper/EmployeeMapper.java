package com.smc.ssm.mapper;

import com.smc.ssm.bean.Employee;

import java.util.List;

/**
 * @Date 2022/4/8
 * @Author smc
 * @Description:
 */
public interface EmployeeMapper {
    Employee findEmpById(Integer id);

    List<Employee> findEmps();
}
