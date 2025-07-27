package com.smc.ssm.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;


import java.util.Properties;

/**
 * @Date 2022/4/8
 * @Author smc
 * @Description:
 */

/**
 * 完成插件签名：
 *  告诉Mybatis当前插件用来拦截哪个对象的哪个方法
 */
@Intercepts({
        @Signature(type = StatementHandler.class,method = "parameterize",args = java.sql.Statement.class)
})
public class MyFirstPlugin implements Interceptor {
    /**
     * 拦截目标对象目标方法的执行
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MyFirstPlugin_intercept" + invocation.getMethod());
        //动态改变sql运行的参数，以前是1号员工，实际是3号员工
        Object target = invocation.getTarget();
        System.out.println("当前拦截对象" + target);
        //拿到statementHandler=》ParametersHandler=>ParameterObject
        MetaObject metaObject = SystemMetaObject.forObject(target);
        Object value = metaObject.getValue("parameterHandler.parameterObject");
        System.out.println("sql语句使用的参数是" + value);
        //修改sql要用的参数
        metaObject.setValue("parameterHandler.parameterObject",4);
        //执行目标方法
        Object proceed = invocation.proceed();
        //返回执行后的返回值
        return proceed;
    }

    /**
     * plugin：
     *  包装目标对象的：包装；为目标对象创建一个代理对象
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        System.out.println("MyFirstPlugin_plugin:mybatis将要包装的对象"+target);
        //借助Plugin的wrap方法来使用当前Interceptor包装我们目标对象
        Object wrap = Plugin.wrap(target, this);
        //返回为当前target创建的动态代理
        return wrap;
    }

    /**
     * setProperties
     *  将插件注册时的property属性设置进来
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的信息" + properties);
    }
}
