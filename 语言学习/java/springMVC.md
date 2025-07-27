SSM：SpringMVC、Spring、Mybatis

![](/Users/smc/Desktop/smc/语言学习/java/springmvc/resources/截屏2022-03-28 22.29.57.png)

### 1、SpringMVC简介

* 什么是mvc：是一种软件架构的丝线个，将软件按照模型、试图、控制器来划分
  * M：Model，模型层，指工程中的JavaBean，作用是处理数据。Java Bean分为两类：
    * 一类称为实体类Bean：专门存储业务数据的，如Student、User等
    * 一类称为业务处理Bean：指Service或Dao对象，专门用户处理业务逻辑和数据访问
  * V：View，试图层，指工程中的html或jsp等页面，作用是与用户进行交互，展示数据
  * C：Controller，控制层，指工程中的servlet，作用是接收请求和响应浏览器
  * MVC的工作流程：用户通过视图层发送请求道服务器，在服务器中亲故被Controller接收mCOntroller调用相应的Model层处理请求，处理完毕将结果返回到Controller，Controller再根据亲故处理的结果找到响应View视图，渲染数据后最终响应给浏览器
* 什么是SpringMVC：是spring的一个后续铲平，是spring的子项目
  * SpringMVC是spring为表述层开发提供的一整套完备的解决方案。在表述层框架历经Strust、WebWork、Strust2等诸多产品的历代更迭之后，目前业界普遍选择了SPringMVC作为JavaEE项目表述层开发的首选方案。
  * 三层架构分为表述层（或表示层）、业务逻辑层、数据访问层，表述层表示前台页面和后台servlet
* SpringMVC的特点
  * Spring家族原生产品，与IOC容器等基础设施无缝对接
  * 基于原生的Servlet，通过了功能的强大的前端控制器**DispatcherServlet**,对请求和响应进行统一处理
  * 表述层各细分领域需要解决的问题全方位覆盖，提供全面解决方案
  * 代码清新简洁，大幅度提升开发效率
  * 内部组件化程度高，可插拔式组件即插即用，想要什么功能配置响应组件即可
  * 性能卓著，尤其适合现代大型，超大型互联网项目要求

### 2、HelloWorld

* 开发环境
  * IDE：idea2021.3.2
  * 构建工具：maven3.8.4
  * 服务器：tomcat9
  * spring版本：5.3.7

* 创建maven工程
  * 创建module工程![](/Users/smc/Desktop/smc/语言学习/java/springmvc/resources/WX20220328-234104@2x.png)![](/Users/smc/Desktop/smc/语言学习/java/springmvc/resources/WX20220328-234138@2x.png)
  * 设置工程![](/Users/smc/Desktop/smc/语言学习/java/springmvc/resources/WX20220328-234212@2x.png)
  * 导入以来**packaging**表示打包类型，**scope**依赖返回，表示在什么范围内提供。provice表示已被提供，打成war包后就不会就不存在该依赖，该依赖以被服务器提供![](/Users/smc/Desktop/smc/语言学习/java/springmvc/resources/WX20220328-234442@2x.png)
  * 配置生成web.xml文件![](/Users/smc/Desktop/smc/语言学习/java/springmvc/resources/WX20220328-234632@2x.png)![](/Users/smc/Desktop/smc/语言学习/java/springmvc/resources/WX20220328-234610@2x.png)![](/Users/smc/Desktop/smc/语言学习/java/springmvc/resources/WX20220328-234708@2x.png)

* 配置web.xml：注册SpringMVC的前端控制器DispatcherServlet

  * 默认配置方式：在配置作用下默认位于WEB-INF下，默认名称为\<servlet\>-servlet.xml,例如，以下配置所对应SpingMVC的配置文件位于WEB-INF下，文件名为sprngMVC-servlet.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
             version="4.0">
        <!--配置sprigMVC前端控制器，对浏览器的请求进行统一处理-->
        <servlet>
            <servlet-name>SpringMVC</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        </servlet>
        <servlet-mapping>
            <servlet-name>SpringMVC</servlet-name>
            <!--设置springMVC核心控制器所能处理的请求的请求路径
                   /所匹配的请求可以是login或htmlh或.js或.css方式的请求路径
                    但是/不能匹配jsp的q请求路径的请求-->
            <url-pattern>/</url-pattern>
        </servlet-mapping>
    </web-app>
    ```

  * 扩展配置方式：可通过init-param标签设置SpringMVC的配置文件的位置和名称，通过load-on-startup标签设置SpringMVC前端控制器DispatcherSerlvet的初始化时间

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
             version="4.0">
        <!--配置sprigMVC前端控制器，对浏览器的请求进行统一处理-->
        <servlet>
            <servlet-name>SpringMVC</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <!--配置SpingMVC的配置文件的位置和名称-->
            <init-param>
                <param-name>contextConfigLocation</param-name>
                <param-value>classpath:springMVC.xml</param-value>
            </init-param>
            <!--将前端控制器DispatcherServlet的初始化时间提前到服务器启动时-->
            <load-on-startup>1</load-on-startup>
        </servlet>
        <servlet-mapping>
            <servlet-name>SpringMVC</servlet-name>
            <!--设置springMVC核心控制器所能处理的请求的请求路径
                   /所匹配的请求可以是login或htmlh或.js或.css方式的请求路径
                    但是/不能匹配jsp的q请求路径的请求-->
            <url-pattern>/</url-pattern>
        </servlet-mapping>
    </web-app>
    ```

  > <url-pattern>标签中使用/和/*的区别：
  >
  > /所匹配的请求可以时/login或.html或.js或.css方式的请求路径，但是/不能匹配.jsp请求路径的请求，因此就可以避免在访问jsp页面时，该请求被DispatcherServlet处理，从而找不到响应的页面
  >
  > /*能够匹配所有请求，例如在使用过滤器时，若需要对所有请求进行过滤，就需要使用/\*的写法

* 创建请求控制器

  * 由于前端控制器对浏览器发送的请求进行了统一的处理，但是具体的请求有不同的处理过程，因此需要创建处理具体请求的类，即请求控制器

  * 请求控制器中每一个处理亲故的方法称为控制器方法

  * 因为SpringMVC的控制器由一个POJO（普通的java类）担任，因此需要通过@Controller注解将起标识为一个控制层组件，交给Sping的IOC容器管理，此时SpringMVC才能够识别控制器的存在

    ```java
    @Controller
    public class HelloController {
        
    }
    ```

* 配置SpringMVC配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <!--扫描组件    -->
    <context:component-scan base-package="com.smc.mvc.controller"></context:component-scan>

    <!-- 配置Thymeleaf视图解析器 -->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">

                        <!-- 视图前缀 -->
                        <property name="prefix" value="/WEB-INF/templates/"/>

                        <!-- 视图后缀 -->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8" />
                    </bean>
                </property>
            </bean>
        </property>
    </bean>
</beans>
```

* 访问index.html

> 1.war 这种模式称为发布模式，先打成war包，再进行发布
>
> 2.war exploded模式是直接把文件夹，jsp页面，classes等等移到Tomcat部署文件夹里面，进行加载部署。因此这种方式支持热部署，一般在开发的时候也是用这种方式。这样我们再修改一些jsp界面的东西的时候，不需要重启服务。

```java
@Controller
public class HelloController {
    //访问"/"->请求到"/WEB-INF/template/index.html"
    @RequestMapping("/")//只有一个参数时，value可省略
    public String index(){
        //返回视图名称
        return "index";
    }
}
```

* 跳转指定页面

```html
<!--
1、这里采用thymeleaf语法，能够访问程序相对路径，访问到控制器后，控制器跳转target.html页面
2、若是直接/target浏览器会访问localhost:8080/target，不会访问程序相对路径，
-->
<a th:href="@{target}">访问目标页面target.html</a>
```

* 总结

浏览器发送请求，若请求地址复合前端控制器的url-pattern，该请求就会被前端控制器DispatcherServlet处理。前端控制器会读取SpringMVC的核心配置文件，通过扫描组件找到控制器，将请求地址和控制器中@RequestMapping注解的value值进行匹配，若匹配成功，该注解所标识的控制器方法就是处理请求的方法。处理请求的方法需要返回一个字符串类型的视图名称，该视图名称会被视图解析器解析，加上前缀和后缀组成视图的路径，通过Thymeleaf对视图进行渲染，最终转发到视图所对应页面

### 3、@RequestMapping注解

* 功能：在注解名称上我们可以看到@RequestMapping注解的作用就是将请求和处理请求的控制器方法关联起来，建立映射关系

  * springMVC接收到指定的请求，就会来找到在映射关系中对应的控制器方法来处理这个请求。
  * mapping不能有两个相同的映射

* 注解的位置

  * 标识一个类：设置映射请求的请求路径的初始信息
  * 标识一个方法：设置映射请求请求路径的具体信息

* value属性

  * 是一个字符串类型的数组可以有多个请求映射

  ```java
  @RequestMapping(
              value = {"/testRequestMapping","test"})
      public String success() {
          return "success";
      }
  ```

* method属性

  * 属性通过请求方式（get或post）匹配请求映射
  * 是一个RequestMethod类型的数组，表示该请求映射能够匹配多种请求方式
  * 若当前请求的请求地址满足请求映射的value属性，但是请求方式不满足method属性，则浏览器报错405

  ```java
  @RequestMapping(
              value = {"/testRequestMapping", "test"},
              method = {RequestMethod.GET,RequestMethod.POST})
      public String success() {
          return "success";
      }
  ```

  * 对于处理指定请求方式的控制器方法SpringMVC中提供了@RequestMapping的派生注解

    * 处理get请求的映射：@GetMapping
    * 处理post请求的映射：@PostMapping
    * 处理put请求的映射：@PutMapping
    * 处理delete请求的映射：@DeleteMapping

  * 常用的请求方式有get，post，put，delete

    * 但是目前浏览器只支持get和post，若在form提交时，为method设置了其他请求方式的字符串，则按照默认的请求方式get处理
    * 若要发送put和delete请求，则需要通过spring提供的过滤器HiddenHttpMehtodFilter，在restful部分

    ```java
        @GetMapping(
                value = {"/testGetMapping"})
        public String getMapping() {
            return "success";
        }
    ```

    

* params属性

  * 通过请求的请求参数匹配请求映射
  * params是一个字符串类型的数组，可以通过四种表达式设置请求参数和请求映射匹配关系
    * param：要求请求映射所匹配的情趣必须携带param请求参数
    * !param：要求请求映射所匹配的请求必须不能携带param请求参数
    * param=value：要求请求映射所匹配的请求必须携带param请求参数且param请求参数且param=value
    * param!=value：要求请求映射所匹配的请求必须携带param请求参数但是param!=value

  ```java
  @RequestMapping(
              value = {"/testParamsAndHeaders"},
              params = {"!username","password"})
      public String testParamsAndHeaders() {
          return "success";
      }
  ```

  * 不满足params属性，此时页面会报400错误

* headers属性

  * 通过请求的请求头信息匹配请求映射
  * headers属性是一个字符串类型的数组，可以通过四种表达式设置请求头信息和请求映射的匹配关系
    * header：要求请求映射所匹配的请求必须携带header请求头信息
    * !header：要求请求映射所匹配的请求必须不能携带header请求头信息
    * header=value：要求请求映射所匹配的请求必须携带header请求头信息且header=value
    * header!=value：要求请求映射所匹配的请求必须携带header请求头信息且header!=value
  * 若当前请求满足@RequestMapping注解的value和method属性，但是不满足header属性，此时页面显示404错误，即资源未找到

* springMVC支持ant风格的路径

  * ?:表示任意的单个字符
  * \*:表示任意的0个或多个字符
  * \*\*:表示任意的一层或多层目录
  * 注意在使用\*\*时，只能使用/\*\*/xxx的方式

  ```java
      @RequestMapping(
  //            value = {"/a?a/testAnt"}
  //            value = {"/a*a/testAnt"}
              value = {"/**/testAnt"}
      )
      public String testAnt() {
          return "success";
      }
  ```

* 支持路径中的占位符（重点）

  * 原始方式：/delete?id=1
  * rest方式：/deleteUser/1
  * SpringMVC路径中的占位符常用于restful风格，当请求鹿筋各种将某些数据通过路径的方式传输到服务器中，就可以在响应的RequestMapping注解的value属性中通过占位符（xxx）表示传递的数据，在通过@PathVariable注解，将占位符所表示的数据赋值给控制器的形参

  ```html
  <a th:href="@{/hello/testRest/1}">测试testRest映射get</a><br>
  ```

  ```java
  @RequestMapping(
              value = {"/testRest/{id}"}
      )
      public String testRest(@PathVariable("id") int id) {
          System.out.println("id:"+id);
          return "success";
      }
  ```

### 4、SpringMVC获取请求参数

* 通过servletAPI获取

  * 通过HttpServletRequest作为控制器方法的形参，此时HttpServletRequest类型的形参表示封装了当前请求的请求报文的对象

  ```java
  @RequestMapping("/testServletAPI")
      public String param(HttpServletRequest req){
          String username = req.getParameter("username");
          String password = req.getParameter("password");
          System.out.println("username:"+username+" password:"+password);
          return "success";
      }
  ```

* 通过控制器方法的形参获取请求参数

  * 在控制器方法的形参位置，设置和请求参数同名的形参，当浏览器发送请求，匹配到请求映射时，在DispatcherServlet中就会将请求参数赋值给响应的形参

  ```html
  <a th:href="@{/param/testParam(username='smchen',password=123456)}">控制器形参获取参数</a><br>
  <form th:action="@{/param/testParam}" method="get">
      用户名：<input type="text" name="username"><br>
      密码：<input type="password" name="password"><br>
      爱好：<input type="checkbox" name="hobby" value="a">a
      爱好：<input type="checkbox" name="hobby" value="b">b
      爱好：<input type="checkbox" name="hobby" value="c">c<br>
      <input type="submit" value="testParams的Post请求">
  </form>
  ```

  

  ```java
  @RequestMapping("/testParam")
      public String testParam(String username,String password,String[] hobby){
          //若请求出现多个同名的请求参数，可以在形参位置设置字符串或字符串数组进行接收
          //若是使用字符串类型，最终的结果会是每个值之间使用","连接
          System.out.println("username:"+username+" password:"+password+" hobby:"+ Arrays.asList(hobby));
          return "success";
      }
  ```

* @RequestParam

  * 当请求参数名不一致时使用@RequestParam来获取
    * 若使用了这个注解则参数必须有，若不存且没有设置defaultValue在则页面报错400。可将require=false来使之不必一定存在，且形参赋值为null
    * defaultValue：不管require为true或false，当value所指定的请求参数没有传输的值为""时，则是默认值为形参赋值

  ```java
  @RequestMapping("/testParam1")
      public String testParam1(@RequestParam(value = "user_name",required = false,defaultValue = "smc") String username, String password, String[] hobby){
          System.out.println("username:"+username+" password:"+password+" hobby:"+ Arrays.asList(hobby));
          return "success";
      }
  ```

* @RequestHeader

  * 是将请求头信息和控制器方法的形参创建映射关系
  * 三个属性：value，require，defaultValue

  ```java
  @RequestMapping("/testParam1")
      public String testParam1(@RequestParam(value = "user_name") String username,
                               String password, String[] hobby,
                               @RequestHeader("Host") String host) {
          System.out.println("username:" + username + " password:" + password + " hobby:" + Arrays.asList(hobby));
          System.out.println("host:"+host);
          return "success";
      }
  ```

  

* @CookieValue

  * 是将cookie数据和控制器方法的形参创建映射关系
  * 三个属性：value，require，defaultValue

  ```java
  @RequestMapping("/testParam1")
      public String testParam1(@RequestParam(value = "user_name") String username,
                               String password, String[] hobby,
                               @RequestHeader("Host") String host,
                               @CookieValue("JSESSIONID") String jSessionID) {
          System.out.println("username:" + username + " password:" + password + " hobby:" + Arrays.asList(hobby));
          System.out.println("host:"+host);
          System.out.println("jSessionID:"+jSessionID);
          return "success";
      }
  ```

* 通过POJO获取请求参数

  * 可以在控制器方法的形参位置设置一个实体类类型的形参，此时若浏览器传输的请求参数的参数名和实体类中的属性名一致，那么请求参数就会为此属性赋值

  ```java
  @RequestMapping("/testPOJO")
      public String testPOJO(User user) {
          System.out.println(user.toString());
          return "success";
      }
  ```

* 通过CharacterEncodingFilter处理获取参数的乱码问题

  * get请求乱码需要tomcat的配置文件修改
  * post请求，通过过滤器来解决

  ```xml
  <filter>
          <filter-name>CharacterEncodingFilter</filter-name>
          <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
          <init-param>
              <!--设置编码为UTF-8            -->
              <param-name>encoding</param-name>
              <param-value>UTF-8</param-value>
          </init-param>
          <init-param>
              <!--由于在源码中请求编码能够被赋值，但回应编码没有赋值，所以在这里进行赋值true，是回应编码赋值条件通过            -->
              <param-name>forceResponseEncoding</param-name>
              <param-value>true</param-value>
          </init-param>
      </filter>
      <filter-mapping>
          <filter-name>CharacterEncodingFilter</filter-name>
          <url-pattern>/*</url-pattern>
      </filter-mapping>
  ```

### 5、域对象共享数据

* request：一次请求；session：一次会话，浏览器关闭（cookie一直存在）；context：整个程序，服务器关闭开关
* 使用servletAPI向request域对象共享数据

```java
//使用ServletAPI想request共享数据
    @RequestMapping("/testRequestAPI")
    public String testRequestAPI(HttpServletRequest request){
        request.setAttribute("testRequestScope","Hello ServletAPI");
        return "success";
    }
```

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
success
<p th:text="${testRequestScope}"></p>
</body>
</html>
```

* 使用ModelAndView想request域对象共享数据

  * 返回数据必须是ModelAndView

  ```java
  //使用ModelAndView想request共享数据
      @RequestMapping("/testModelAndView")
      public ModelAndView testModelAndView(){
          ModelAndView mav = new ModelAndView();
          //处理模型数据，即向request中共享数据
          mav.addObject("testRequestScope","Hello ModelAndView");
          //设置视图名称
          mav.setViewName("success");
          return mav;
      }
  ```

* 使用Model向request域对象共享数据

```java
//使用Model想request共享数据
    @RequestMapping("/testModel")
    public String testModel(Model model){
        model.addAttribute("testRequestScope","Hello Model");
        return "success";
    }
```

* 使用map向request域对象共享数据

```java
//使用Map想request共享数据
    @RequestMapping("/testMap")
    public String testMap(Map<String, Object> map){
        map.put("testRequestScope","Hello Model");
        return "success";
    }
```

* 使用ModelMap向request域共享数据

```java
//使用ModelMap想request共享数据
    @RequestMapping("/testModelMap")
    public String testModelMap(ModelMap modelMap){
        modelMap.addAttribute("testRequestScope","Hello ModelMap");
        return "success";
    }
```

* Model、Map、ModelMap的关系

  * 本质上都是BindingAwareModelMap类型的

  > ```java
  > public interface Model{}
  > 
  > public class ModelMap extends LinkedHashMap<String, Object> {}
  > 
  > public class ExtendedModelMap extends ModelMap implements Model {}
  > 
  > public class BindingAwareModelMap extends ExtendedModelMap {}
  > ```

* 向session域共享数据

```java
//向session域共享数据
    @RequestMapping("/testSession")
    public String testSession(HttpSession session){
        session.setAttribute("testSessionScope","Hello session");
        return "success";
    }
```

```html
<p th:text="${session.testSessionScope}"></p>
```

* 向application域共享数据

```java
//向context域共享数据
    @RequestMapping("/testApplication")
    public String testApplication(HttpSession session){
        ServletContext context = session.getServletContext();
        context.setAttribute("testApplicationScope","Hello Application");
        return "success";
    }
```

```html
<p th:text="${application.testApplicationScope}"></p>
```

### 6、SpringMVC的视图

* SpringMVC中的视图是View接口，视图的作用是渲染数据，将模型Model中的数据展示给用户

* SpringMVC视图的种类很多，默认有转发视图和重定向视图

* 当工程引入jstl的依赖，转发视图会自动转换为JstlView

* 若使用的视图技术为Thymeleaf，在SpringMVC的配置文件中配置了Thymelead的视图解析器，由此视图解析器解析之后所得到的是ThymeleafView

* ThymeleafView

  *    当控制器方法中所设置的视图没有任何前缀是，此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析，视图名称拼接视图前缀和视图后缀所得到的最终路径，会通过转发的方式实现跳转

* 转发视图

  * SpingMVC中个默认的转发视图是InternalResourceView
  * 当控制器方法中所设置的视图名称以"forward:"为前缀时，穿件InternalResourceView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀“forward:”去掉，生育部分作为最终路径通过转发的方式实现跳转

  ```java
  //无法直接转发到html页面，因为跳转html页面都会被thymeleaf解析
  @Controller
  public class ViewController {
      @RequestMapping("/testThymeLeafView")
      public String testThymeLeafView() {
          return "success";
      }
  
      @RequestMapping("/testForward")
      public String testForward() {
          return "forward:/testThymeLeafView";
      }
  }
  ```

* 重定向视图

  * 默认的重定向视图时RedirectView
  * 当控制器方法中所设置的视图名称以“redirect”为前缀时，创建RedirectView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀去掉，生育部分作为最终路径通过重定向的方式实现跳转

  ```java
  //无法直接重定向到html页面，因为跳转html页面都会被thymeleaf解析
  @RequestMapping("/testThymeLeafView")
      public String testThymeLeafView() {
          return "success";
      }
  
      @RequestMapping("/testRedirect")
      public String testRedirect() {
          return "redirect:/testThymeLeafView";
      }
  ```

* 视图控制器view-controller

  * 当控制器方法，仅仅用来实现页面跳转，即只需要设置视图名称时，可以将处理器方法使用view-controller标签进行表示

  ```xml
  <!--springMVC.xml中-->
  <mvc:view-controller path="/" view-name="index"></mvc:view-controller>
  ```

  * 当springMVC中设置任何一个view-controller时，其他控制器中的请求映射将全部失效，此时需要在SpringMVC的核心配置文件中设置开启mvc注解驱动的标签

  ```xml
  <!--开启MVC的注解驱动-->
      <mvc:annotation-driven/>
  ```

* jsp情况下视图
  * jsp情况下的视图只有转发视图和重定向视图

```xml
<context:component-scan base-package="com.smc.mvc.controller"></context:component-scan>

    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/templates/"></property>
        <property name="suffix" value=".jsp"></property>
    </bean>

```

```jsp
<a href="${pageContext.request.contextPath}/suceess">success</a>
```

```java
@RequestMapping("/suceess")
    public String success() {
        return "success";
    }
```

### 7、RESTFul

* REST：Representational State Transfer，表现层资源状态转移
  * 资源：资源是一种看待服务器的方式，即将服务器看作是由很多离散的资源组成。每个资源是服务器上一个可命名的抽象概念。因为资源是一个抽象的概念，所以它不仅仅能代表服务器文件系统中的一个文件、数据库中的一张表等等具体的东西，可以将资源设计的要多抽象有多抽象，只要想象力允许而且客户端应用开发者能够理解。与面向对象设计类似，资源是以名词为核心来组织的，首先关注的是名词。一个资源可以有一个或多个URI来标识。URI既是资源的名称，也是哈资源在web上的地址。对某个资源感兴趣的客户端应用，可以通过资源的URI与其进行交互。
  * 资源的表述：资源的表述是一段对于资源在某个特定时刻的状态的描述，可在客户单-服务器端之间转移（交换）。资源的表述可以有多种格式，例如HTML/XML/JSON/纯文本/图片/视频/音频等等。资源的表述格式可以通过协商机制来确定。请求-响应方法向的表述通常使用不同的格式
  * 状态转移：在客户端和服务器之间转移（transfer）代表资源状态的表述。通过转移和操作资源的表述，来间接实现操作资源的目的。
* RESTFul的实现
  * 具体的说就是HTTP协议里面，四个表示方式的动词：GET、POST、PUT、DELETE
  * 他们分别对应四种基本操作：GET用来获取资源，POST用来新建资源、PUT用来更新资源，DELETE用来删除资源
  * REST风格提产URL地址使用统一风格设计，从前到后各个但是使用斜杠分开，不实用问号键值对方式携带请求参数，而是将要发送给服务器的数据作为URL地址的一部分，以保证整体风格的一致性
* RESTFul风格get请求的实现

```java
// /user   Get请求   查询所有用户
@RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUsers() {
        System.out.println("查询所有用户信息");
        return "success";
    }
// /user/1   Get请求   根据用户id查询用户
@RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public String getUserById(){
        System.out.println("根据用户id查询用户");
        return "success";
    }
```

* RESTFul风格post请求的实现

```java
// /user   Post请求   添加用户信息
@RequestMapping(value = "/user", method = RequestMethod.POST)
    public String insertUser(String username,String password) {
        System.out.println("添加用户信息："+username+","+password);
        return "success";
    }
```



* HiddenHttpMethodFIlter

  * 由于默认请求无法识别put和delete，当视图页请求为put和delete时默认为get，需要采用过滤器实现

  ```xml
  <!--web.xml-->
  <!--配置HiddenHttpMethodFilter-->
      <filter>
          <filter-name>HiddenHttpMethodFilter</filter-name>
          <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
      </filter>
      <filter-mapping>
          <filter-name>HiddenHttpMethodFilter</filter-name>
          <url-pattern>/*</url-pattern>
      </filter-mapping>
  ```

  

  * RESTFul风格put请求的实现，需要满足两个条件：1、请求类型为post；2、带有“_method”参数。

  ```html
  /**
  <form th:action="@{user}" method="post">
      <input type="hidden" name="_method" value="put">
      username:<input type="text" name="username" >
      password:<input type="password" name="password" >
      <input type="submit" value="修改用户信息">
  </form>
  ```

  ```java
  @RequestMapping(value = "/user", method = RequestMethod.PUT)
      public String updateUser(String username,String password) {
          System.out.println("修改用户信息："+username+","+password);
          return "success";
      }
  ```

  * 由于编码过滤器在参数获取之后在尽心编码就会失效，二请求过滤器会在获取数据，所以应该在CharacterEncodingFilter放在HiddenHttpMethodFilter之前

* RESTFul案例

  * 初始化工程

    * 创建工程，添加打包方式依赖
    * 创建webapp/WEB-INF/web.xml
    * 配置web.xml配置文件
      * 配置编码格式过滤器
      * 配置请求过滤
      * 配置前端控制器DispatcherServlet

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
             version="4.0">
        <!--配置编码过滤器    -->
        <filter>
            <filter-name>CharacterEncodingFilter</filter-name>
            <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
            <init-param>
                <!--设置编码为UTF-8            -->
                <param-name>encoding</param-name>
                <param-value>UTF-8</param-value>
            </init-param>
            <init-param>
                <!--由于在源码中请求编码能够被赋值，但回应编码没有赋值，所以在这里进行赋值true，是条件通过            -->
                <param-name>forceResponseEncoding</param-name>
                <param-value>true</param-value>
            </init-param>
        </filter>
        <filter-mapping>
            <filter-name>CharacterEncodingFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
    
        <!--配置HiddenHttpMethodFilter-->
        <filter>
            <filter-name>HiddenHttpMethodFilter</filter-name>
            <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
        </filter>
        <filter-mapping>
            <filter-name>HiddenHttpMethodFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
    
    
        <servlet>
            <servlet-name>DispatcherServlet</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <init-param>
                <param-name>contextConfigLocation</param-name>
                <param-value>classpath:springMVC.xml</param-value>
            </init-param>
        </servlet>
        <servlet-mapping>
            <servlet-name>DispatcherServlet</servlet-name>
            <url-pattern>/</url-pattern>
        </servlet-mapping>
    </web-app>
    ```

    

    * 配置springMVC.xml
      * 扫描包
      * 配置thymeleaf或其他解析器
      * 配置view-controller（访问首页），开启mvc注解驱动

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:context="http://www.springframework.org/schema/context"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">
        <context:component-scan base-package="com.smc.mvc"></context:component-scan>
    
        <!-- 配置Thymeleaf视图解析器 -->
        <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
            <property name="order" value="1"/>
            <property name="characterEncoding" value="UTF-8"/>
            <property name="templateEngine">
                <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                    <property name="templateResolver">
                        <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
    
                            <!-- 视图前缀 -->
                            <property name="prefix" value="/WEB-INF/templates/"/>
    
                            <!-- 视图后缀 -->
                            <property name="suffix" value=".html"/>
                            <property name="templateMode" value="HTML5"/>
                            <property name="characterEncoding" value="UTF-8"/>
                        </bean>
                    </property>
                </bean>
            </property>
        </bean>
    
        <mvc:view-controller path="/" view-name="index"></mvc:view-controller>
        <!--开启MVC的注解驱动-->
        <mvc:annotation-driven/>
    </beans>
    ```

    

  * 查询所有员工数据

  ```java
  @RequestMapping(value = "employee",method = RequestMethod.GET)
      public String getEmployee(Model model){
          Collection<Employee> all = employeeDao.getAll();
          model.addAttribute("empList",all);
          all.stream().forEach(System.out :: println);
          return "success";
      }
  ```

  ```html
  <table border="1" align="center" cellpadding="0" cellspacing="0">
      <tr>
          <th colspan="5">Employee Info</th>
      </tr>
      <tr>
          <th>用户id</th>
          <th>用户名</th>
          <th>邮箱</th>
          <th>性别</th>
          <th>操作</th>
      </tr>
      <tr th:each="employee : ${empList}">
          <td th:text="${employee.id}"></td>
          <td th:text="${employee.lastName}"></td>
          <td th:text="${employee.email}"></td>
          <td th:text="${employee.gender}"></td>
          <td>
  <!--            <a th:href="@{/employee/}+${employee.id}">删除</a>-->
              <a th:href="@{'/employee/'+${employee.id}}">删除</a>
              <a href="#">修改</a>
          </td>
      </tr>
  </table>
  ```

  * 根据id删除指定员工

  ```xml
  <!--由于需要引入vue.js的静态资源，需要使用tomcat的DefaultServlet来访问静态资源，当dispatcherServlet无法访问时会使用DefaultServlet来访问
  需要开启MVC注解驱动，要不然所有请求都会默认被DefaultServlet处理-->
  <!--开放对静态资源的方法    -->
      <mvc:default-servlet-handler/>
      <!--开启MVC的注解驱动-->
      <mvc:annotation-driven/>
  ```

  

  ```html
  <table id="dataTable" border="1" align="center" cellpadding="0" cellspacing="0">
      <tr>
          <th colspan="5">Employee Info</th>
      </tr>
      <tr>
          <th>用户id</th>
          <th>用户名</th>
          <th>邮箱</th>
          <th>性别</th>
          <th>操作</th>
      </tr>
      <tr th:each="employee : ${empList}">
          <td th:text="${employee.id}"></td>
          <td th:text="${employee.lastName}"></td>
          <td th:text="${employee.email}"></td>
          <td th:text="${employee.gender}"></td>
          <td>
  <!--            <a th:href="@{/employee/}+${employee.id}">删除</a>-->
              <a @click="deleteEmployee" th:href="@{'/employee/'+${employee.id}}">删除</a>
              <a href="#">修改</a>
          </td>
      </tr>
  </table>
  <form id="deleteForm" method="post">
      <input type="hidden" name="_method" value="delete">
  </form>
  <script type="text/javascript" th:src="@{/static/js/vue.js}"></script>
  <script type="text/javascript">
      var vue = new Vue({
          el:"#dataTable",
          methods:{
              deleteEmployee:function (event){
                  console.log(event.target.href)
                  //根据id获取表达元素
                  var deleteForm = document.getElementById("deleteForm");
                  //将触发事件超链接的href赋值给提交表单的action
                  deleteForm.action = event.target.href;
                  //提交表单
                  deleteForm.submit();
                  //取消超链接默认行为
                  event.preventDefault();
              }
          }
      });
  </script>
  ```

  ```java
  @RequestMapping(value = "/employee/{id}",method = RequestMethod.DELETE)
      public String deleteEmploy(@PathVariable("id") Integer id){
          employeeDao.delete(id);
          return "redirect:/employee";
      }
  ```

  * 添加员工

  ```html
  <form th:action="@{/employee}" method="post">
          lastName:<input type="text" name="lastName"><br>
          email:<input type="text" name="email"><br>
          gender:<input type="radio" name="gender" value="1">男
          <input type="radio" name="gender" value="0">女<br>
          <input type="submit" value="添加">
      </form>
  ```

  ```java
  @RequestMapping(value = "employee",method = RequestMethod.POST)
      public String employeeAdd(Employee employee){
          employeeDao.save(employee);
          return "redirect:/employee";
      }
  ```

  * 实现回显和修改员工

  ```html
  <form th:action="@{/employee}" method="post">
          <input type="hidden" name="_method" value="put">
          <input type="hidden" name="id" th:value="${emp.id}">
          lastName:<input type="text" name="lastName" th:value="${emp.lastName}"><br>
          email:<input type="text" name="email" th:value="${emp.email}"><br>
          gender:<input type="radio" name="gender" th:value="1" th:field="${emp.gender}">男
          <input type="radio" name="gender" th:value="0" th:field="${emp.gender}">女<br>
          <input type="submit" value="修改">
      </form>
  ```

  ```java
  @RequestMapping(value = "/employee/{id}",method = RequestMethod.GET)
      public String getEmployeeById(@PathVariable("id")Integer id, Model model){
          Employee employee = employeeDao.get(id);
          model.addAttribute("emp",employee);
          return "employee_update";
      }
  
      @RequestMapping(value = "employee",method = RequestMethod.PUT)
      public String employeeUpdate(Employee employee){
          employeeDao.save(employee);
          return "redirect:/employee";
      }
  ```

### 8、HttpMessageConverter

* 报文信息转换器，将请求报文转换为Java对象，或将Java兑现过转换为响应报文

* 提供了两个注解和两个类型：@RequestBody，@ResponseBody，RequestEntity，ResponseEntity

* @RequestBody

  * 可以获取请求体，需要在控制器方法设置一个形参，使用@RequestBody进行表示，当前请求的请求体就会为当前注解所标识的形参赋值

  ```html
  <form th:action="@{/testRequestBody}" method="post">
      username:<input type="text" name="username"><br>
      password:<input type="password" name="password"><br>
      <input type="submit" value="testRequestBody"><br>
  </form>
  ```

  ```java
  @RequestMapping("/testRequestBody")
      public String testRequestBody(@RequestBody String requestBody) {
          System.out.println(requestBody);
          return "success";
      }
  ```

* RequestEntity

  * RequestEntity封装请求报文的一种类型，需要在控制器方法的形参中设置该类型的形参，当前请求的请求报文就会赋值给该形参，可以通过getHeaders()获取请求头信息，通过getBody()获取请求体信息

  ```java
  @RequestMapping("/testRequestEntity")
      public String testHeader(RequestEntity<String> requestEntity) {
          System.out.println("Header:"+requestEntity.getHeaders());
          System.out.println("Body:"+requestEntity.getBody());
          return "success";
      }
  ```

* @ResponseBody

  * 用于表示一个控制器方法，可以将该方法的返回值直接作为响应报文的响应提响应到浏览器
  * 通过ServletAPI的response响应浏览器

  ```java
  @RequestMapping("/testResponse")
      public void testResponseBody(HttpServletResponse response) throws IOException {
          response.getWriter().write("hello");
      }
  ```

  * 通过@ResponseBody响应浏览器数据

  ```java
  @RequestMapping("/testResponseBody")
      @ResponseBody
      public String testResponseBody(){
          return "sucess";
      }
  ```

  * 通过@ResponseBody响应浏览器对象(使用json来处理)

    * 引入jackson依赖
    * 在SpringMVC的核心配置文件中开启mvc的注解驱动，此时在HandlerAdaptor中会自动装配一个消息转换器：MappingJackson2HttpMessageConverter，可以将响应到浏览器的Java对象转换为json格式的字符串

    ```xml
    <!--开启MVC的注解驱动-->
        <mvc:annotation-driven/>
    ```

    * 在处理器方法上使用@ResponseBody注解进行标识
    * 将Java对象直接作为控制器方法的返回值，就会自动转换为json格式的字符串

    ```java
     @RequestMapping("/testResponseBodyUser")
        @ResponseBody
        public Object testResponseBodyUser(){
            User user = new User();
            user.setId(0);
            user.setUsername("书蒙尘");
            user.setPassword("12345");
            user.setEmail("123@163.com");
            return user;
        }
    ```

* springMVC处理ajax

  * 请求超链接

  ```html
  <div id="app">
      <a @click="textAxios" th:href="@{/testAjax}">springMVC处理ajax</a>
  </div>
  ```

  

  * 通过vue和axios处理点击事件

  ```html
  <script type="text/javascript" th:src="@{/static/js/vue.js}"></script>
  <script type="text/javascript" th:src="@{/static/js/axios.min.js}"></script>
  <script type="text/javascript">
      new Vue({
          el:app,
          methods:{
              textAxios:function (event){
                  axios({
                      method:"post",
                      url:event.target.href,
                      params:{
                          username:"admin",
                          password:123456
                      }
                  }).then(function(respnse){
                      alert(respnse.data)
                  })
                  event.preventDefault();
              }
          }
      });
  </script>
  ```

  ```java
  @RequestMapping("/testAjax")
      @ResponseBody
      public String testAjax(String username,String password){
          System.out.println(username+":"+password);
          return "hello,axios";
      }
  ```

* @RestController注解

  * 是springMVC提供的一个复合注解，标识在控制器类上，就相当于为类添加了@Controller注解，并且为其中的每个方法添加了@ResponseBody注解

* ResponseEntity

  * ResponseEntity用于控制器方法的返回值类型，该控制器方法的返回值就是响应到浏览器的响应报文

### 9、上传和下载文件

* 下载文件：使用ResponseEntity下载文件

  ```java
  @RequestMapping("/testDown")
      public ResponseEntity<byte[]> testDown(HttpSession session) throws IOException {
          //获取servletContext对象
          ServletContext servletContext = session.getServletContext();
          //获取服务器中文件的真是路径
          String realPath = servletContext.getRealPath("/static/img/a.jpg");
          System.out.println(realPath);
          //创建输入流
          InputStream is = new FileInputStream(realPath);
          //创建字节数组,is.available()获取流的字节数
          byte[] bytes = new byte[is.available()];
          //将流读到字节数组中
          is.read(bytes);
          //创建HttpHeader对象设置响应头
          MultiValueMap<String,String> headers = new HttpHeaders();
          //设置要下载的方式以及下载文件的名字attachment:以附件形式下载，filename：下载文件名称
          headers.add("content-Disposition","attachment;filename=1.jpg");
          //设置响应状态码
          HttpStatus statusCode = HttpStatus.OK;
          //创建ResponseEntity对象
          ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes,headers,statusCode);
          //关闭输入流
          is.close();
          return responseEntity;
  
      }
  ```

* 上传文件

  * 文件上传要求form表单的请求方法必须为post，并且添加属性enctype="multipart/form-data"SpringMVC中 将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息

  ```html
  <!--enctype="multipart/form-data"以二进制的形式上传数据-->
  <form th:action="@{testUp}" method="post" enctype="multipart/form-data">
      头像：<input type="file" name="photo" ><br>
      <input type="submit" value="上传">
  </form>
  ```

  

  * 添加依赖

  ```xml
  <dependency>
              <groupId>commons-fileupload</groupId>
              <artifactId>commons-fileupload</artifactId>
              <version>1.3.3</version>
          </dependency>
  ```

  * 在springMVC.xml中配置上传解析器

  ```xml
  <!--配置文件上传解析器，将上传文件转化为MultipartFile-->
      <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"></bean>
  ```

  * 文件上传代码实现

  ```java
  @RequestMapping("/testUp")
      public String testUp(MultipartFile photo,HttpSession session) throws IOException {
          String originalFilename = photo.getOriginalFilename();
          System.out.println(photo.getName());
          System.out.println(photo.getOriginalFilename());
          System.out.println(photo.getContentType());
          ServletContext servletContext = session.getServletContext();
          String photoPath = servletContext.getRealPath("photo");
          File file = new File(photoPath);
          //判断文件是否存在
          if(!file.exists()){
              //不存在则创建目录
              file.mkdirs();
          }
          //separator:文件分隔符
          String finalPath = photoPath + File.separator + originalFilename;
          photo.transferTo(new File(finalPath));
          return "success";
      }
  ```

  * 解决文件重名覆盖问题:加上时间戳或者不重复的uuid

  ```java
  @RequestMapping("/testUp")
      public String testUp(MultipartFile photo,HttpSession session) throws IOException {
          String originalFilename = photo.getOriginalFilename();
          //获取文件夹后缀名
          String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
          //将uuid作为文件名
          String uuid = UUID.randomUUID().toString();
          originalFilename= uuid + suffixName;
          //通过ServletContext获取photo文件夹的路径
          ServletContext servletContext = session.getServletContext();
          String photoPath = servletContext.getRealPath("photo");
          File file = new File(photoPath);
          //判断文件是否存在
          if(!file.exists()){
              //不存在则创建目录
              file.mkdirs();
          }
          //separator:文件分隔符
          String finalPath = photoPath + File.separator + originalFilename;
          photo.transferTo(new File(finalPath));
          return "success";
      }
  ```

### 10、拦截器

* 过滤器在浏览器和DispatcherServlet控制器之间
* 拦截器用于拦截器控制器方法的之间： 在控制器执行之前，执行之后，在渲染视图完毕之后
* 拦截器需要实现HandlerInterceptor或者集成HandlerInterceptorAdapter类
* 拦截器必须在SpringMVC的配置文件中进行配置

```xml
 <!--配置拦截器-->
    <mvc:interceptors>

<!--        <bean class="com.smc.mvc.interceptor.FirstInterceptor"></bean>-->
<!--        <ref bean="firstInterceptor"></ref>-->
        <!--以上两种对所有请求都执行拦截-->
        <!--指定路径拦截-->
        <mvc:interceptor>
            <mvc:mapping path="/*"/><!--拦截斜线下的一层，/**为任意层-->
            <mvc:exclude-mapping path="/"/><!--排除/层-->
            <ref bean="firstInterceptor"></ref>
        </mvc:interceptor>

    </mvc:interceptors>
```

```java
@Component
public class FirstInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("FirstInterceptro->preHandle");
        //返回true会拦截
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("FirstInterceptro->postHandle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("FirstInterceptro->afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
```



* 拦截器的三个抽象方法
  * preHandle：控制器方法执行之前执行preHandle()，其中boolean类型的返回值表示是否拦截或放行，返回true为放行，即调用控制器方法；返回false标识拦截，即不调用控制器方法
  * postHandle：控制器方法执行之后执行postHandle()
  * afterComplation：处理完视图和模型数据，渲染视图完毕之后执行afterComplation()
* 多个拦截器的执行顺序
  * 若每个拦截器的preHandler()都返回true，此时多个拦截器的执行顺序和拦截器在SpringMVC的配置文件顺序有关
  * PreHandle()会按照配置的顺序执行，而postHandle()和afterComplation()会按照配置的反序执行
  * 若某个拦截器的preHandle()返回false，preHandler()返回false和它之前的拦截器的preHandle()都会执行，postHandle()都不执行，返回false的拦截器之前的拦截器的afterComplation()都会执行

### 11、异常处理器

* 基于配置的异常处理

  * springMVC提供了一个处理控制器方法执行过程中所出现的异常的接口：HandlerExceptionResolver
  * HandlerExceptionResolver接口的实现类有：DefaultHandlerExceptionResolver和SimpleMappingExceptionResolver
  * SpringMVC提供了自定义的异常处理器SimpleMappingExceptionResolver，使用方法：

  ```xml
  <!--配置异常处理-->
      <bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
          <property name="exceptionMappings">
              <props>
                  <prop key="java.lang.ArithmeticException">error</prop>
              </props>
          </property>
          <!--设置异常信息共享在请求域中的键       -->
          <property name="exceptionAttribute" value="ex"></property>
      </bean>
  ```

* 基于注解的异常处理

```java
//指定异常
    @ExceptionHandler(value = {ArithmeticException.class,NullPointerException.class})
    public String testException(Exception ex, Model model){
        model.addAttribute("ex",ex);
        return "error";
    }
```

### 12、注解配置SpringMVC

* 使用配置类和注解代替web.xml和SpringMVC配置文件的功能

* 创建初始化类，替代web.xmk

  * 在Servlet3.0环境中，容器会在类路径中查找实现javax.servlet.servletContainerInitialzer接口的类，如果找到的话就用它来配置Servle容器
  * Spring提供了这个接口的实现，名为SpringServletContainerInitializer，这个哥类反过来又会查找实现WebApplicationInitalizer基础实现，名为AbstractAnnotationConfigDispatcherServletInitializer，当我们的类扩展了AbstractAnnotationConfigDIspatcherSerlvetInitializer并将其部署到Servlet3.0容器的时候，容器会自动发现它，并用它来配置Servlet上下文

  ```java
  /**
   * @Date 2022/3/31
   * @Author smc
   * @Description:web工程的初始化类，即web.xml
   */
  
  public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
      /**
       * 指定spring的配置
       * @return
       */
      @Override
      protected Class<?>[] getRootConfigClasses() {
          return new Class[]{SpringConfig.class};
      }
  
      /**
       * 指定springMVC的配置类
       * @return
       */
      @Override
      protected Class<?>[] getServletConfigClasses() {
          return new Class[]{WebConfig.class};
      }
  
      /**
       * 注册DispatcherServlet映射规则，即url-pattern
       * @return
       */
      @Override
      protected String[] getServletMappings() {
          return new String[]{"/"};
      }
  
      /**
       * 注册过滤器
       * @return
       */
      @Override
      protected Filter[] getServletFilters() {
          CharacterEncodingFilter cef = new CharacterEncodingFilter();
          cef.setEncoding("UTF-8");
          cef.setForceResponseEncoding(true);
          HiddenHttpMethodFilter hhmf = new HiddenHttpMethodFilter();
          return new Filter[]{cef,hhmf};
      }
  }
  ```

* SpringMVC.xml的配置文件

```java
/**
 * @Date 2022/3/31
 * @Author smc
 * @Description:代替springMVC.xml配置文件 扫描组件，视图解析器，view-controller,default-servlet-hander，mvc注解驱动
 * 文件上传解析器，异常处理，拦截器
 */
//将当前类表示为一个配置类
@Configuration
//扫描组件
@ComponentScan("com.smc.mvc.controller")
//mvc注解驱动
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    //default-servlet-hander
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TestInterceptor testInterceptor = new TestInterceptor();
        registry.addInterceptor(testInterceptor).addPathPatterns("/**");
    }
    //view-controller
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello").setViewName("hello");
        WebMvcConfigurer.super.addViewControllers(registry);
    }
    //异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();

        properties.setProperty("java.lang.ArithmeticException","error");
        exceptionResolver.setExceptionMappings(properties);
        exceptionResolver.setExceptionAttribute("exception");
        resolvers.add(exceptionResolver);
    }

    //
    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        return commonsMultipartResolver;
    }

    //配置生成模板解析器
    @Bean
    public ITemplateResolver templateResolver() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        // ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过WebApplicationContext 的方法获得
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(
                webApplicationContext.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }

    //生成模板引擎并为模板引擎注入模板解析器
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    //生成视图解析器并未解析器注入模板引擎
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
}
```

### 13、SpringMVC的执行流程

* springMVC的执行流程
  * DisPatcherServlet：前端控制器，不需要工程师开发，由框架提供
    * 作用：提供处理请求和响应，整个流程控制的中心，由它来调用其他组件处理用户请求
  * HandlerMapping：处理器映射起，不需要工程师开发，由框架提供
    * 作用：根据请求的url、method、等信息查找Handler，即控制器方法
  * Handler：处理器，需要工程师开发
    * 作用：在DispatcherServlet的控制下Handler对具体的用户请求进行处理
  * HandlerAdapter：处理器适配器，不需要工程师开发，由框架提供
    * 作用通过HandlerAdapter对处理器器（控制器方法）进行执行
  * ViewResolver：视图解析器，不需要工程师开发，由框架提供。
    * 作用：进行视图解析，得到相应的视图，例如：ThymeleafView、InternalResourceView，RedirectView
  * View：视图，不需要工程师开发，由框架或视图技术提供
    * 作用：将模型数据通过页面展示给用户
* DispatcherServlet初始化过程
  * 本质上是一个Servlet，遵循servlet的生命周期
* DispatcherServlet调用组件处理请求
* SpringMVC的运行流程
  * 用户向服务器发送请求，请求被SpringMVC前端控制器DispatcherServlet捕获
  * DispatcherServlet对请求URL进行解析，得到请求资源标识符（URI），判断请求URI对应的映射
    * 不存在
      * 再判断是否配置了mvc:default-servlet-handler
      * 如果没有配置，则控制台报映射查找不到，客户端展示404错误
      * 如果有配置，则访问，则访问目录资源（一般为静态资源，如：JS，CSS，HTML），找不到客户端也会展示404错误
    * 存在
      * 根据该URI，调用HandlerMapping获得改Handler配置的所有相关对象（包括Handler对象以及Handler对象对应的拦截器），最后以HandlerExcutionChain执行链对象的形式返回
      * DispatcherServlet根据获得的Handler，选择一个合适的HandlerAdapter
      * 如果成功获得HandlerAdapter，此时开始执行拦截器的preHandler方法
      * 提取Request中的模型数据，填充Handler入参，开始执行Handler（Controller）方法，处理请求。在填充Handler的入参过程中，根据你的配置，Spring将帮你做一些额外的工作
        * HttpMessageConveter：将请求消息（如json、xml等数据）转换成一个对象，将对象转换为指定的响应信息
        * 数据转换：对请求消息进行数据格式化，如将字符串转换成格式化数字或格式化日期等
        * 数据验证：验证数据的有效性（长度、格式等），验证结果存储到BindingResult或Error中
      * Handler执行完成后，向DispatcherServlet返回一个ModelAndView对象
      * 此时将开始执行拦截器的postHandle（）方法【逆向】
      * 根据返回的ModelAndView（此时会判断是否存在异常：如果存在异常，则执行HandlerExceptionResolver进行异常处理）选择一个适合的ViewResolver进行视图解析，根据Model和View，来渲染视图
      * 渲染视图完毕执行拦截器的afterCompletion方法【逆向】
      * 将渲染结果返回给客户端











