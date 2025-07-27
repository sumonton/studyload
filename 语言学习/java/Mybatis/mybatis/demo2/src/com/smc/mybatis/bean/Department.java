package com.smc.mybatis.bean;

import java.util.List;

/**
 * @Date 2022/4/6
 * @Author smc
 * @Description:
 */
public class Department {
    private Integer id;
    private String deptName;
    private List<Employee> employees;

    public Department(Integer id) {
        this.id = id;
    }

    public Department() {
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", deptName='" + deptName + '\'' +
                ", employees=" + employees +
                '}';
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
