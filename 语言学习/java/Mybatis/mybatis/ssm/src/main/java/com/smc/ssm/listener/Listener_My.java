package com.smc.ssm.listener;

/**
 * @Date 2022/4/9
 * @Author smc
 * @Description:
 */
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

@WebListener
public class Listener_My implements HttpSessionListener, ServletRequestListener, ServletContextListener, ServletContextAttributeListener,
        ServletRequestAttributeListener, HttpSessionAttributeListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("请求初始化");
    }
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("请求销毁");

    }

    @Override
    public void contextInitialized(ServletContextEvent sce){
        try{
            System.out.println("Servlet初始化");
        }catch (Exception e){
            System.out.println("初始化失败");
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            System.out.println("Servlet销毁");
//            SqlConnection.dbDestroyed();
            Enumeration drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = (Driver) drivers.nextElement();
                DriverManager.deregisterDriver(driver);
                System.out.println("deregistering jdbc driver: " + driver);
            }
            AbandonedConnectionCleanupThread.uncheckedShutdown();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("销毁工作异常");
        }

    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {

    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {

    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {

    }

    @Override
    public void attributeAdded(ServletRequestAttributeEvent servletRequestAttributeEvent) {

    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent servletRequestAttributeEvent) {

    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent servletRequestAttributeEvent) {

    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }
}


