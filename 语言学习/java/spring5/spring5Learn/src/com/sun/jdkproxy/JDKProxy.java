package com.sun.jdkproxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDKProxy {
    public static void main(String[] args) {
        //创建实现子类的代理对象
        Class[] interfaces = {UserDao.class};
        UserDao userDao = new UserDaoImpl();
        UserDao dao = (UserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
        int result = dao.add(1, 2);
        System.out.println(result);
        System.out.println(dao.update("11111"));

    }

}
//创建代理对象代码
class UserDaoProxy implements InvocationHandler{
    //把被代理对象传递过来
    private Object obj;
    public UserDaoProxy(Object obj) {
        this.obj = obj;
    }

    //增强的逻辑
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法之前
        System.out.println("方法执行之前。。。。"+method.getName()+ " 传递的参数" + Arrays.toString(args));
        //被增强方法执行
        Object res = method.invoke(obj,args);
        //方法之后
        System.out.println("方法执行之后。。。"+res);

        return res;
    }
}