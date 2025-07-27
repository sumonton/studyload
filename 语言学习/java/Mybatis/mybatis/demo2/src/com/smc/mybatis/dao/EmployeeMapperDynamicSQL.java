package com.smc.mybatis.dao;

import com.smc.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date 2022/4/6
 * @Author smc
 * @Description:
 */
public interface EmployeeMapperDynamicSQL {
    List<Employee> findEmpsByConditionIf(Employee employee);
    List<Employee> findEmpsByConditionTrim(Employee employee);
    List<Employee> findEmpsByConditionChoose(Employee employee);
    Long updateEmployee(Employee employee);
    List<Employee> findEmployeeForeach(@Param("ids")List<Integer> ids);
    Long addEmpsForeach(@Param("emps") List<Employee> emps);
    List<Employee> getEmpsTestInnerParameter(Employee employee);
}
