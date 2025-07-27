package com.smc.ssm.controller;

import com.smc.ssm.bean.Employee;
import com.smc.ssm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Date 2022/4/8
 * @Author smc
 * @Description:
 */
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/getEmpById/{id}")
    public String getEmpById(@PathVariable("id")Integer id, Model model){
        Employee empById = employeeService.getEmpById(id);
        model.addAttribute("emp",empById);
        return "empView";
    }

    @RequestMapping("/getEmps")
    public String getEmps(Model model){
        List<Employee> employees = employeeService.getEmployees();
        model.addAttribute("emps",employees);
        return "success";
    }
}
