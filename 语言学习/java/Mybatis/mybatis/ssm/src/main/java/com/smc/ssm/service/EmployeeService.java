package com.smc.ssm.service;

/**
 * @Date 2022/4/8
 * @Author smc
 * @Description:
 */

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.ssm.bean.Employee;
import com.smc.ssm.mapper.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SqlSession sqlSession;

    public Employee getEmpById(Integer id){
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        return employeeMapper.findEmpById(id);
    }

    public List<Employee> getEmployees(){
//        Page<Object> page = PageHelper.startPage(1, 2);

        List<Employee> emps = employeeMapper.findEmps();
//        PageInfo<Employee> pageInfo = new PageInfo<>(emps);
        //传入连续显示多少页
        PageInfo<Employee> pageInfo = new PageInfo<>(emps,5);
        System.out.println("当前页码" + pageInfo.getPageNum());
        System.out.println("总记录数" + pageInfo.getTotal());
        System.out.println("每页记录数" + pageInfo.getPageSize());
        System.out.println("总页码：" + pageInfo.getPages());
        System.out.println("是否第一页" + pageInfo.isIsFirstPage());
        return emps;
    }
}
