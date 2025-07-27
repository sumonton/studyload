package com.smc.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.mp.bean.Employee;

/**
 * @Date 2022/4/13
 * @Author smc
 * @Description:mapper接口，集成BaseMapper抽象类,泛型就是指定的bean类
 */
public interface EmployeeMapper extends BaseMapper<Employee> {
    int logicDelete();
}
