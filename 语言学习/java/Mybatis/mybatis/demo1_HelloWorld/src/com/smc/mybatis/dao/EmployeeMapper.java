package com.smc.mybatis.dao;

import com.smc.mybatis.bean.Employee;

/**
 * @Date 2022/4/2
 * @Author smc
 * @Description: 1、接口式编程
 *  原生： Dao -> DaoImpl
 *  mybatis: Mapper -> Mapper.xml
 * 2、sqlSession是每一会话都要创建一次，使用完要要关闭
 * 3、sqlSession是非线程安全的，不能全局定义，造成资源竞争
 * 4、mapper接口没有实现类，但是Mybatis会为它创建一个代理对象，
 *  //会为接口创建一个代理对象，代理对象去实现增删改查
 *  EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的配置文件
 *  Mybaatis全局配置文件，实现数据源的连接
 *  sql映射文件，保存sql的映射信息：将sql抽取出来
 *
 */
public interface EmployeeMapper {
    Employee findEmpById(Integer id);


}
