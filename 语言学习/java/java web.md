#### javaweb是根据请求响应的程序

#### web资源按实现的技术和呈现的效果不同，又分为静态资源和动态资源两种

#### 常用的web服务器

* tomcat：提供对jsp和servlet的支持，是一宗轻量级的javaWeb容器，也是当前应用最广的javaWeb服务器
* Jboss：是一个遵从JavaEE规范的、开放源代码的、纯Java的EJB服务器，它支持所有JavaEE规范
* Resin：是CAUCHO公司的产品，是一个非常流行的服务器，对servlet和JSP提供了良好的支持，性能也比较有两，resin自身采用JAVA语言开发（收费）
* WebLoginc：是目前引用最广泛的Web服务器，支持JavaEE规范，并且不断完善的大型服务器

### 1、tomcat

* 目录介绍

  * bin：专门用来存放tomcat服务器的可执行程序
  * conf：专门用来存放tomcat服务器的配置文件
  * lib：专门用来存放tomcat服务器的jar包
  * logs：专门用来存放tomcat服务器运行时输出的日志信息
  * temp：专门用来存放tomcat运行时产生的临时数据
  * webapps：专门用来存放部署的web工程
  * work：是tomcat工作时的目录，用来存放tomcat运行时jsp翻译为servlet的源码，和session钝化的目录

* 通过命令行启动catalina.bat能看到错误日志

* 如何部署web工程

  * 直接把web工程的目录拷贝到tomcat的webapp目录下

  * 找到tomcat的conf目录/catalina/localhotst下，创建配置文件

    ```xml
    <!-- context表示一个工程的上下文；path表示工程的访问路金哥：/abc；docBase表示你的工程目录在哪里 -->
    <Context path="/abc" docBase = ""/>
    ```

* IDEA动态创建JAVAWEB工程
  * 创建web工程![](/Users/smc/Desktop/smc/语言学习/java/java web/resources/截屏2022-03-19 13.25.04.png)
  * web工程目录介绍![](/Users/smc/Desktop/smc/语言学习/java/java web/resources/截屏2022-03-19 13.16.59.png)
    * src:保存在即编写的java源代码
    * webapp：目录专门用来存放web工程的资源文件，如html页面，css文件，js文件等
    * WEB-INF目录是一个受服务器保护的目录，浏览器无法直接访问到此目录的内容
    * web.xml是整个动态啊web工程的配置部署描述文件，可以在这配置很多web工程的组件，比如：Servlet程序，Filter过滤器，Listener监听器，Session超时。。等
    * lib目录用来存放第三方的jar
  * 给web工程添加第三方jar包
    * 可以打开项目结构菜单，可以添加一个java的jar包类库
    * 在类库中添加jar
    * 选择类库给哪些工程使用

### 2、Servlet

* 什么是servlet

  * servlet是JavaEE规范之一。规范就是接口
  * servlet是JavaWeb三大组件之一。三大组件分别是Servlet程序、Filter过滤器、Listener监听器
  * servlet是云心挂在服务器上的一个Java小程序，它可以接受客户端发送过来的请求，并响应数据给客户端

* 手动实现Servlet程序

  * 编写一个类去实现servlet接口

  * 实现service方法，处理请求，并响应数据

    ```xml
     <servlet>
            <servlet-name>HeServlet</servlet-name>
            <servlet-class>com.example.web_05.HeServlet</servlet-class>
       <init-param>
                <param-name></param-name>
                <param-value></param-value>
            </init-param>
        </servlet>
    
        <servlet-mapping>
            <servlet-name>HeServlet</servlet-name>
            <url-pattern>/hello</url-pattern>
        </servlet-mapping>
    ```

* servlet的声明周期：1，2只在创建时使用

  * 1、构造器方法
  * 初始化方法
  * service方法
  * destroy方法

* servlet的请求分发处理

  * 通过httpServlet.getMethod()来获得请求方式

* 通过集成HttpServlet来实现servlet

* 使用IDEA创建Servlet

* ServletConfig类：servlet程序的配置信息类,serlvet程序和ServletConfig对象都是由tomcat负责创建，我们负责使用。servlet程序默认时第一次访问的时候创建，ServletConfig是每个Servlet程序创建时，就创建一个对应的ServletConfig对象。

  * ServletConfig类的三大作用
    * 可以获取Servlet程序的别名servlet-name的值
    * 获取初始化参数init-param
    * 获取servletContext对象

* ServletContext类：是一个接口，它表示Servlet上下文对象；一个web工程只有一个ServletContext对象实例；ServletContext对象是一个域对象

  * 域对象：是可以向Map一下样存取数据的对象，叫域对象，这里的域指的是存取数据的操作范围。
  * setAttribute();getAttribute();removeAttribute()
  * 四个作用
    * 获取web.xml中配置的上下文参数context-param
    * 获取当前的工程路径，格式：/工程路径
    * 获取工程部署后在服务器硬盘上的绝对路径
    * 像Map一样存取数据

* Http协议

  * 什么事协议：协议是指双方或多方相互约定好，大家都需要遵守的规则，叫协议
  * 所谓HTTP协议就是值客户端和服务器之间通信时，发送的数据，需要遵守的规则，叫HTTP协议
  * HTTP协议中的数据又叫报文

* 请求的HTTP协议格式

  * 请求又分为GET请求和POST请求
  * GET请求
    * 请求行
      * 请求的方式	GET
      * 请求的资源路径+？+请求参数
      * 请求的协议的版本号HTTP/1.1
    * 请求头
      * key:value	组成，不同的键值对，表示不同的含义
  * POST请求
    * 请求行
      * 请求的方式	POST
      * 请求的资源路径+？+请求参数
      * 请求的协议的版本号		HTTP/1.1
    * 请求头
      * key value	不同的请求头，有不同的含义
      * 空行
    * 请求提：发送给服务器的数据

* Servlet常用请求头

  * Accept：表示客户端可以接受的数据类型
  * Accpet-Languege：表示客户端可以接收的语言类型
  * User-Agent：表示客户端浏览器信息
  * Host：表示请求时的服务器ip和端口号

* GET请求有哪些：

  * form标签 method=get
  * a标签
  * link标签引入css
  * script标签引入js文件
  * img标签引入js文件
  * iframe引入html页面
  * 在浏览器地址栏中输入地址后敲回车

* POST请求有哪些：

  * form标签 method=post

* 响应的HTTP协议格式

  * 响应行
    * 响应的协议和版本好	HTTP/1.1
    * 响应状态码
    * 响应状态描述符
  * 响应头
    * key：value
    * 空行
  * 响应体：回传给客户端的数据	

* 常见的响应码

  * 200	表示请求成功
  * 302	表示请求重定向
  * 404	表示请求服务器已经收到了，但是你要的数据不存在（请求资源不存在）
  * 500	表示服务器已经收到请求，但是服务器内部错误（代码错误）

* MIME：是HTTP协议中的数据类型

* HttpServletRequest类：每次只要有请求进入Tomcat服务器，tomcat服务器就会把请求过来的HTTP协议信息解析好封装到Request对象中，然后传递service方法（doGet和doPost）中给我们使用。我们可以通过HttpServletRequest对象，获取到所有请求的信息。

* HttpServletRequest类的常用方法

  * getRequestURI()	获取请求的资源路径
  * getRequestURL()	获取请求的统一资源定位符（绝对路径）
  * getRemoteHost()	获取客户端的ip地址
  * getHeader()	获取请求头
  * getParameter()	获取请求的参数
  * getParameterValues()	获取请求的参数（多个值的时候使用）
  * getMethod()	获取请求的方式GET或POST
  * setAttribute()	设置域数据
  * getAttribute()	获取域数据
  * getRequestDispatcher()	获取请求转发对象

* Servlet请求转发

  * 多个Servlet可以共同完成一个业务功能，Servlet之间可以相互转发

  * ```java
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/hello-servlet");
            requestDispatcher.forward(request,response);
    ```

  * 请求转发的特点：

    * 浏览器地址栏没有变化
    * 它们是一次请求
    * 它们共享Request域中的数据
    * 可以转发到WEB-INF目录下
    * 不可以访问工程以外的资源

* base标签的作用

  * 相对路径跳转是相对于浏览器地址的
  * 在head中设置base标签，可以设置当前页面中所有相对路径都相对于指定地址

* web中/斜杠的不同意义

  * 在web中/斜杆是一种绝对路径
  * /斜杠如果被浏览器解析，得到的地址是：http://ip:port/
  * /斜杠如果被服务器解析，得到的地址是：http://ip:port/工程路径
  * 特殊情况：response.sendRediect("/")：把斜杠发送给浏览器解析。得到http://ip:port/

*  HTTPServletResponse类：

  * 每次请求进来，tomcat服务器都会创建一个Response兑现过传递Servlet程序去使用。HttpServletRequest表示请求过来的信息，HttpServletResponse表示所有响应的信息。
  * 如果需要设置返回给客户端的信息，都可以通过HttpServletResponse对象来进行设置。
  * 两个输出流的说明
    * 字节流：getOutputStream()常用于下载（传递二进制数据）
    * 字符流：getWriter()常用于回传字符串（常用）
    * 两个流同时只能使用一个。

* 如何往客户单回传数据

  * ```java
    response.setCharacterEncoding("utf-8");//设置服务器字符集为utf-8
            //通过响应头，设置浏览器也使用utf-8字符集
            response.setHeader("content-type","text/html;charset=UTF-8");
    //        response.setContentType("text/html;charset=UTF-8");
            // Hello
            PrintWriter out = response.getWriter();
    ```

* 请求重定向

  * ```java
    response.setStatus(302);
            response.setHeader("Location","/web_05_war_exploded/hello-servlet");
    ```

  * 请求重定向的特点

    * 浏览器地址栏会发生变化
    * 两次请求
    * 不共享Request域中的数据
    * 不能访问WEB-INF下的资源
    * 可以访问工程外的资源

  * 请求重定向的第二种方案（推荐）

    ```java
    response.sendRedirect("/web_05_war_exploded/hello-servlet");
    ```

* JAVAEE三层架构

  * ![](/Users/smc/Desktop/smc/语言学习/java/java web/resources/截屏2022-03-19 18.07.43.png)
  * web层	controller
  * service层	service；service.impl
  * dao层	dao；dao.impl
  * 实体对象bean对象：entity.domain.bean;
  * 测试包	junit
  * 工具类	utils

### 4、jsp

* 什么是jsp，它有什么用？
  * jsp的全称是java server pages。java的服务器页面
  * jsp的主要作用是代替servlet程序回传html页面的数据
  * 因为servlet程序回传html页面数据是一件非常繁琐的事情。开发成本和维护成本都极高。
* jsp的本质就是servlet
* Listener监听器（javaweb三大组件之一）
  * Listerner它是javaEE的规范，就是接口
  * 监听器的作用是监听某种事物的变化。然后通过回调函数，反馈给客户（程序）去做一些响应的处理
  * ServletContextListener监听器：可以监听servletContext对象的创建和销毁。监听到创建和销毁之后分别调用ServletContextListener监听器的方法反馈。

### 5、EL表达

* 什么是EL表达式，EL表达式的作用？
  * EL表达式的全称是：Expression Language。是表达式语言
  * EL表达式的作用：EL表达式主要是替代jsp页面中更尽兴数据的输出。因为EL表达式在输出的时候，要比jsp的表达式脚本要简洁很多。
  * EL表达式的格式是：${表达式}
  * EL表达式在输出NULL值的时候，输出的事空串。jsp表达式脚本输出null值的时候，输出的事NUll字符串。
* EL表达式输出域数据的顺序
  * EL表达his主要是在jsp页面中输出数据
  * 主要是输出域对象中的数据
  * 当四个域中都有相同的key的数据的时候，EL表达式会按照四个域的从小到大的顺序进行搜索，找到就输出。
* EL表达式输出Bean的普通属性，数组属性。List集合属性，map集合属性
  * 需求-输出Person类中普通属性，数组属性。List集合属性和map集合属性

### 6、JSTL标签库

* 全称是JSP Standard Tag Library，JSP标准标签库。是一个不断完善的开放源代码的JSP标签库。
* EL表达式主要是为了替换jsp中的表达式脚本，而标签库是为了替换代码脚本。这样使得整个jsp页面变得更加简洁。
* JSTL的标签库的使用步骤
  * 先导入jstl标签库的jar包
  * 第二步，使用taglib引入标签库

### 7、文件上传与下载

* 文件上传介绍

  * 要有一个form标签，method=post请求
  * form标签的enctype属性值必须为multipart/form-data值
  * 在form标签中使用input type=file添加上传文件
  * 编写服务器代码接收，处理上传的数据

* Commons-fileupload.jar

  * 依赖于commons-io.jar

  * ```java
    //先判断上传的数据是否是多段数据（只有多端数据才是文件上传）
            if (ServletFileUpload.isMultipartContent(request)){
                //创建FileItemFactory工厂实现类
                FileItemFactory fileItemFactory = new DiskFileItemFactory();
                //创建用于解析上传数据的工具类ServlerFileUpload类
                ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
                //解析上传的数据，得到每一个表单项FileItem
                try {
                    List<FileItem> list = servletFileUpload.parseRequest(request);
                    for (FileItem fileItem : list) {
                        if (fileItem.isFormField()){
                            //普通表单项
                            System.out.println("表单项name属性值："+fileItem.getFieldName());
                            System.out.println("表单项value属性值值："+fileItem.getString("UTF-8"));
                        }else {
                            System.out.println("表单项的name属性值："+fileItem.getFieldName());
                            System.out.println("上传文件名："+fileItem.getName());
                            fileItem.write(new File("/Users/smc/Desktop/smc/语言学习/java/java web/JavaWeb/src/"+fileItem.getName()));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    ```

    

* 文件下载介绍

```java
//1、获取下载文件名
        String downloadFileName = "srcxmut.jpg";
        //2、读取要下载的文件内容（通过ServletContext对象可以读取）
        ServletContext servletContext = getServletContext();
        //获取要下载的文件类型
        String mimeType = servletContext.getMimeType("/file/" + downloadFileName);
        System.out.println(mimeType);
        //3、在回传前通过响应头告诉客户端返回的数据类型
        response.setContentType(mimeType);
        //4、还要告诉客户端收到的数据是用于下载使用（还是使用响应头）
        //content-Disposition响应头，表示收到的数据怎么处理
        //attachement表示附件，表示下载使用
        //filename = 指定下载的文件名
        response.setHeader("Content-Disposition","attchement;filename = " + downloadFileName);
        InputStream resourceAsStream = servletContext.getResourceAsStream("/file/" + downloadFileName);
        //获取响应的输出流
        OutputStream outputStream = response.getOutputStream();
        //读取输入流中的所有数据传递给输出流
        IOUtils.copy(resourceAsStream,outputStream);
```

* 使用URLEncoder.encode("中文.jpg","UTF-8")来解决谷歌和IE浏览器中文乱码问题

```java
        response.setHeader("Content-Disposition","attchement;filename = "+ URLEncoder.encode("厦门理工.jpg","UTF-8"));

```

* BASE64编解码

  ```java
  String content = "这是需要Base64编码的内容";
  //创建一个Base64编码器
  BASE64Encoder base64Encoder = new BASE64Encoder();
  //执行base64编码操作
  String encodeString = base64Encoder.encode(content.getBytes(StandardCharsets.UTF_8));
  System.out.println(encodeString);
  //创建Base64解码器
  BASE64Decoder base64Decoder = new BASE64Decoder();
  try {
      byte[] bytes = base64Decoder.decodeBuffer(encodeString);
      String str = new String(bytes,StandardCharsets.UTF_8);
      System.out.println(str);
  } catch (IOException e) {
      e.printStackTrace();
  }
  ```

* 使用user-agent来判断浏览器来进行编码切换 

* beanUtils.jar包：注入bean

### 8、Cookie

* 什么是cookie
  * cookie是服务器通知客户端的保存键值对的一种技术
  * 客户端有了cookie后，每次请求都发送给服务器
  * 每个cookie的大小不能超过4kb
* 如何创建cookie

```java
Cookie cookie = new Cookie("key1","value1");
resp.addCookie(cookie);
resp.getWriter().write("Cookie创建成功");
```

* 服务器如何获取Cookie

  * 服务器获取客户端的Cookie只需要一行代码：req.getCookies().Cookie[]

  ```java
  Cookie[] cookies = req.getCookies();
  for(Cookie cookie : cookies){
    resq.getWriter().write("Cookie["+cookie.getName()+"="+cookie.getValue()+ "]<br/>");
  }
  ```

* Cookie值修改

  * 方案一
    * 先创建一个要修改的同名的Cookie对象
    * 在构造器，同时赋予新的Cookie值
    * 调用response.addCookie(Cookie)
  * 方案二
    * 先查找到需要修改的Cookie对象
    * 调用setValue()方法赋予新的Cookie值
    * 调用response.addCookie()通知客户端保存修改

* Cookie的生命控制

  * cookie的生命控制指的是如何管理Cookie什么时候被销毁
  * setMaxAge()
    * 正值：表示在指定的秒数后过期
    * 负数：表示浏览器一关，Cookie就会被销毁
    * 零：表示马上删除Cookie

* Cookie的有效路径path的设置

  * Cookie的path属性可以有效的过滤那些Cookie可以发送给服务器，哪些不发。
  * path属性是通过请求的地址来进行有效的过滤

  ```java
  Cookie cookie = new Cookie("path1","path1");
  cookie.setPath(req.getContextPath()+ "/abc");//只能在路径abc下才能有该Cookie
  resp.addCookie(cookie);
  resp.getWriter().write("创建一个带有path的路径Cookie")；
  ```

* Cookie练习-免用户名登录 ：用Cookie保存用户名信息

### 9、session会话

* 什么是session
  * session就一个接口（HttpSession）
  * Session就是会话，它是用来维护一个客户端和服务器之间的关联的一种技术
  * 每个客户端都有自己的一个session会话
  * Session会话中，我们经常用来保存用户登录之后的信息
* 如何创建session和获取（id号，是否为新）
  * 如何创建和获取session。他们的API是一样的
  * request.getSeesion()
    * 第一次调用：创建seesion会话
    * 之后调用都是：获取前面创建好的seesion会话对象
  * isNew():判断到底是不是刚创建出来的（新的）
    * true():表示刚创建
    * false():表示获取之前创建
  * 每个会话都有一个身份证号，也就是ID值。而且这个ID是唯一的。
  * getId()得到session的会话的id值
* 创建session

```java
//创建session会话
        HttpSession session = request.getSession();
        //判读当前session是否微信
        boolean aNew = session.isNew();
        //获取session唯一表示id
        String id  = session.getId();
        response.getWriter().write("得到的session，它的id是："+ id + "<br/>");
        response.getWriter().write("得到的session是否为新："+ aNew + "<br/>");

```

* session存值和取值

```java
/**
     * session存值
     * @param request
     * @param response
     */
    public void  setAttribute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建session会话
        HttpSession session = request.getSession();
        //存值
        session.setAttribute("key1","value1");
        response.getWriter().write("已经在session中保存了值");

    }

/**
     * session取值
     * @param request
     * @param response
     * @throws IOException
     */
    public void  getAttribute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建session会话
        HttpSession session = request.getSession();
        //取值
        String key1 = (String)session.getAttribute("key1");
        response.getWriter().write("key1值为："+ key1 + "<br/>");
    }
```

* session生命周期控制

  * public void setMaxInactiveInterval(int interval):设置session的超过时间（以秒为单位），超过指定的时长，session就会被销毁

  * public int getMaxInactiveInteraval():获取session的超过时间。

    * 值为正数的时候，，设定session的超时时长
    * 负数表示用不超时
    * public void invalidate()：表示销毁session

  * session默认超时时长为30分钟。

  * 因为在tomcat服务器的配置文件we b.xml中默认有一下的配置，它就是表示配置了当前tomcat服务器下所有的session超时时长默认时长为30分钟

    >  <session-timeout>

  * 因为爱Tomcat服务器的配置文件web.xml中默认有一下的配置，它就是表示配置了当前tomcat服务器下所有的session超时配置默认时长为：30分钟

  * 如果说你希望你的web工程，默认的session的超时时长为其他时长，你可以在你自己的web.xml配置文件中做以上相同的配置。就可以修改你的web工程所有session的默认时长。

  * session超时的概念指两次点击请求的间隔时间

* 浏览器和session之间关联的技术内幕

  ![](/Users/smc/Desktop/smc/语言学习/java/java web/resources/截屏2022-03-26 11.40.18.png)

* session技术，底层其实是基于Cookie技术来实现的。

### 10、Filter

* Filter过滤器是Javaweb的三大组件之一。三大组件分别是：servlet程序，listener监听器、filter过滤器

* filter过滤器它是javaee的规范，也是接口

* filter过滤器它的作用是：拦截请求，过滤响应

* 拦截请求常见的场景有：

  * 权限检查
  * 日记操作
  * 事务管理
  * 等等

* ![](/Users/smc/Desktop/smc/语言学习/java/java web/resources/截屏2022-03-26 14.57.02.png)

* 

* filter初体验

  * 要求在你的web工程下，有一个admin目录。这个admin目录下的所有资源（html页面、jpg图片、jsp文件、等等）都必须是用户登录之后才允许访问

    ```java
    @WebFilter(filterName = "AdminFilter",urlPatterns = "/admin/*")
    public class AdminFilter implements Filter {
        @Override
        public void init(FilterConfig config) throws ServletException {
        }
    
        @Override
        public void destroy() {
        }
    
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();
            Object user = session.getAttribute("user");
            if (user == null){
                request.getRequestDispatcher("/index.jsp").forward(request,response);
                return;
            }else {
                //让用户继续往下执行请求目标资源
                chain.doFilter(request,response);
            }
        }
    }s
    ```

* filter过滤器的使用

  * 编写一个类取实现Filter接口
  * 实现过滤方法doFilter()
  * 设置拦截路径

* Filter的生命周期

  * Filter的生命周期包含几个方法

    * 构造器方法

    * init初始化方法

      第1，2步在工程启动的时候执行

    * doFilter过滤方法

      第3步。每次拦截到请求就会执行

    * destroy销毁方法

      第4步，停止web工程的时候，就会执行

* FilterConfig类

  * 它是FIlter过滤器的配置文件类

  * Tomcat每次创建Filter的时候，也会同时创建一个FIlterConfig类，这里包含了Filter配置文件的配置信息

  * FilterConfig类的作用是获取filter过滤器的配置内容：

    * 获取filter的名称filter-name的内容
    * 获取在Filter中配置的init-param初始化参数
    * 获取ServletContext对象

    ```java
    @WebFilter(filterName = "AdminFilter",urlPatterns = "/admin/*",
            initParams = {@WebInitParam(name = "username",value="abc"),@WebInitParam(name = "url",value="abc")})
    public class AdminFilter implements Filter {
        @Override
        public void init(FilterConfig config) throws ServletException {
            System.out.println(config.getFilterName());
            System.out.println(config.getInitParameter("username"));
            System.out.println(config.getInitParameter("url"));
            System.out.println(config.getServletContext());
    
        }
    
        @Override
        public void destroy() {
        }
    
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();
            Object user = session.getAttribute("user");
            if (user == null){
                request.getRequestDispatcher("/index.jsp").forward(request,response);
                return;
            }else {
                //让用户继续往下执行请求目标资源
                chain.doFilter(request,response);
            }
        }
    }
    ```

* FilterChain多个过滤器执行细节

![](/Users/smc/Desktop/smc/语言学习/java/java web/resources/截屏2022-03-26 15.36.19.png)

* Filter的拦截路径
  * 精确匹配
  * 目录匹配
  * 后缀名匹配
  * 过滤器只关心请求地址是否匹配，不关系资源是否存在

### 11、JSON

* JSON是一种轻量级的数据交换格式。易于人阅读和编写。同时也抑郁机器解析和生成。JSON采用完全独立于语言的文本格式，而且很多语言都提供了对json的支持。这样JSON就成为了理想的数据交换格式
  * json是一种轻量级的交换格式
  * 轻量级指定是xml比较
  * 数据交换指定客户端和服务器之间的业务数据的传递格式
* JSON在javaScript中的使用
  * json是由键值对组成，并且有花括号（大括号）包围。每个键有引号引起来，键和值之间使用冒号尽心分隔，多组键值对之间尽心个逗号进行分隔。
* json的访问
  * json本身就是一个对象
  * json中的key我们可以理解为对象中的一个属性
  * json中的key访问就跟访问对象的属性一样：json对象
* json的两种常用方法
  * 两种存在形式
    * 对象的形式存在
    * 字符串的形式存在
  * 相互转换
    * JSON.stringify():把json对象转换为json字符串
    * JSON.parse():把json字符串转换成为json对象

* JSON在java中使用
  * javaBean和json的相互转换
  * list和json的相互转换
  * Map集合和json的相互转换

### 12、AJAX请求

* AJAX即“Asynchronous Javascript And XML”（异步JavaScript和XML），是一种创建交互式网页应用的网页开发技术
  * ajax是一种浏览器通过js异步发送氢气，局部更新页面的技术
* 原生AJAX请求

```javascript
function ajaxRequest(){
            //1、首先创建XMLHttpRequest对象
            var xmlhttpRequest = new XMLHttpRequest();
            //2、调用open方法设置请求参数
            xmlhttpRequest.open("GET","AjaxServlet?action=javascriptAjaxRequest",true);
            //3、调用send方法进行发送请求
            xmlhttpRequest.send();
            //4、在方法中绑定onreadyChange事件，处理请求完成后的操作
            xmlhttpRequest.onreadystatechange = function (){
                if (xmlhttpRequest.readyState == 4 && xmlhttpRequest.status == 200){
                    let responseText = xmlhttpRequest.responseText;
                    // var responseText = JSON.parse(xmlhttpRequest.responseText);

                    document.getElementById("div01").innerText = responseText;
                }
            }

        }
```

* JQuery中的AJAX请求

  * $.ajax方法

    * url：表示请求的地址
    * type：表示请求的类型GET或POST请求
    * data:表示发送给服务器的数据
      * 格式有两种
        * name=value&name=value
        * {key:value}
    * success：请求响应，响应的回调函数
    * dataType：数据类型
      * text
      * xml
      * json

  * $.get方法和$.post方法

    * url：请求的url地址
    * data：发送的数据
    * callback：成功的回调函数
    * type：返回的数据类型

    ![](/Users/smc/Desktop/smc/语言学习/java/java web/resources/截屏2022-03-26 17.09.19.png)

  * $.getJSON方法：固定的get请求，返回类型是json
    * url：请求地址
    * data：发送给服务器的数据
    * callback：成功的回调函数
  * serialize方法：可以把表单中所有表单项都获取到，并以name=value&name=value的形式进行拼接

### 13、i18n国际化

* 三要素

  ![](/Users/smc/Desktop/smc/语言学习/java/java web/resources/截屏2022-03-26 17.22.27.png)

*  通过请求头实现国际化
  * 谷歌可以更改语言优先级
* JSTL标签库实现国际化
