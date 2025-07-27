### 一、简介

#### 1、区别

* JDBC -> Dbutils(QueryRunner)->JdbTemplate:功能简单：sql编写在java代码里面，硬编码高耦合的方式
* Hibernate：全自动全映射ORM框架：旨在消除sql，HQL
* 希望：sql语句交给我们开发人员编写，希望sql不是去灵活性
* mybatis：sql与java编码分离；sql是开发人员控制，只需要掌握好sql。半自动，是一个轻量级的框架。

#### 2、下载

* <https://github.com/mybatis/mybatis-3/releases>

#### 3、HelloWorld

* 测试方法

```java
 /**
     * 1、根据xml配置w文件（全局配置文件）创建一个SqlSessionFFactory对象
     *  有数据源的一些环境信息
     *  2、sql映射文件，配置每一个sql，以及sql的封装规则
     *  3、将sql文件注册在全局文件中
     *  4、写代码
     *      a、根据全局文件得到SqlSessionFactory
     *      b、使用sqlSessiong工厂得到，获取sqlSession对象使用他来增删改查，一个sqlSession就代表和数据库的一次会话
     *      用完需要关闭
     *      c、使用sql的唯一标识来告诉mybatis执行哪一个sql
     * @throws IOException
     */
    @Test
    public void Test() throws IOException {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        //这个实例能够执行已经映射的sql语句
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Employee employee = sqlSession.selectOne("com.smc.mybatis.EmployeeMapper.selectEmp", 1);
            System.out.println(employee.toString());
        }finally {
            sqlSession.close();
        }
    }
```

* 全局配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql:///mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="smchen123"/>
            </dataSource>
        </environment>
    </environments>
    <!--写好的sql映射文件一定要注册到全局文件中-->
    <mappers>
        <mapper resource="EmployeeMapper.xml"/>
    </mappers>
</configuration>
```

* sql映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.smc.mybatis.EmployeeMapper">
    <!--名称空间namespace：com.smc.mybatis.EmployeeMapper
    id:唯一表示
    resultType：返回值类型
    #{id}：从传递过来的参数中取出id
    -->
    <select id="com.smc.mybatis.EmployeeMapper.selectEmp" resultType="com.smc.mybatis.Employee">
        select *
        from tb1_employee
        where id = #{id}
    </select>
</mapper>
```

* 要将配置文件放置在source folder中，才能直接通过文件名获取，否则要通过路径获取。设置source folder：![](/Users/smc/Desktop/smc/语言学习/java/Mybatis/resources/WX20220402-000323@2x.png)

#### 4、接口式编程

* 测试类

```java
@Test
    public void Test1() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();

        try{
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee empById = mapper.findEmpById(1);
            System.out.println(mapper.getClass());
            System.out.println(empById);
        }finally {
            openSession.close();
        }



    }
```

* 接口类

```java
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
```

* sql映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.smc.mybatis.dao.EmployeeMapper">
    <!--名称空间namespace：只要把接口路径复制过来，可实现接口绑定，改id为方法名实现绑定
    id:唯一表示
    resultType：返回值类型
    #{id}：从传递过来的参数中取出id
    -->
    <select id="findEmpById" resultType="com.smc.mybatis.bean.Employee">
        select *
        from tb1_employee
        where id = #{id}
    </select>
</mapper>
```

### 二、全局配置文件

#### 1、引入dtd约束

* 规定mybatis的xml语法约束和标签提示
* idea默认配置

#### 2、引入外部配置文件propertites

* 全局配置文件

```xml
<!--mybatis可以引入外部properties
    resource：引入类路径下的资源
    url：引入磁盘路径或者网络路径下的资源-->
    <properties resource="dbConfig.properties"></properties>
```

#### 3、setting运行时行为设置

* mapUnderscoreToCamelCaseEnables：是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名A_COLUMN到经典Java属性名aColumn的类似映射，默认为false

```xml
<!--
        1、setting：用来设置每一个设置项
            name：设置项名
            value：设置项取值
    -->
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
```

#### 4、typeAliases，别名

* 第一种

```xml
<typeAliases>
        <!--typeAlias,别名处理器，为java类取别名
            type:java类全路径；默认别名为类小写
            alias：指定别名
        -->
       <typeAlias type="com.smc.mybatis.bean.Employee" alias="emp"></typeAlias>
    </typeAliases>
```

* 第二种方式

```xml
<typeAliases>
       
        <!--package：为包下的类批量取别名
            name：指定包名（为当前包和子包指定默认别名）
        -->
        <package name="com.smc.mybatis.bean"/>
    </typeAliases>
```

* 第三种方式

```java
@Alias("emp")//注解方式请别名
public class Employee {}
```

* 一般建议在自定义类时使用全类名，不使用别名
* mybatis为java实现的一些别名![](/Users/smc/Desktop/smc/语言学习/java/Mybatis/resources/iShot2022-04-02_22.10.48.png)

#### 5、typeHandlers标签

* 架起java和数据库类型兼容![](/Users/smc/Desktop/smc/语言学习/java/Mybatis/resources/iShot2022-04-02_22.16.28.png)

#### 6、plugins（插件）

* MyBatis 允许你在映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：
  - Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
  - ParameterHandler (getParameterObject, setParameters)
  - ResultSetHandler (handleResultSets, handleOutputParameters)
  - StatementHandler (prepare, parameterize, batch, update, query)
* 这些类中方法的细节可以通过查看每个方法的签名来发现，或者直接查看 MyBatis 发行包中的源代码。 如果你想做的不仅仅是监控方法的调用，那么你最好相当了解要重写的方法的行为。 因为在试图修改或重写已有方法的行为时，很可能会破坏 MyBatis 的核心模块。 这些都是更底层的类和方法，所以使用插件的时候要特别当心。

#### 7、enviroment_运行环境

```xml
<!--    enviroments：mybatis可以配置多种环境，default指定使用哪个环境
        enviroment:配置一个具体的环境，必须有两个标签，id环境唯一标识
            transactionManager：事务管理器
                type：事务管理器类型（JDBC；Managed
                    也可自定义事务管理器类型：实现TransactionFactory接口，type指定全类名
            dataSource：配置事务管理器类型的数据源
                type：数据源类型
                自定义数据源：实现DataSourceFactory接口，type指定全类名
-->
    <environments default="development">
        <environment id="test">
            <transactionManager type=""></transactionManager>
            <dataSource type=""></dataSource>
        </environment>
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
```

#### 8、databaseIdProvider

* ```xml
  <!--    支持多数据库厂商的
          type="DB_VENDOR"：作用是得到数据库厂商的标识，mybatis就能够通过标识来执行不同的sql
  -->
      <databaseIdProvider type="DB_VENDOR">
  <!--        为不同的数据库厂商取别名-->
          <property name="MySQL" value="mysql"/>
          <property name="Oracle" value="oracle"/>
          <property name="SQL_Server" value="sqlServer"/>
      </databaseIdProvider>
  ```

* ```xml
  <!--    databaseId来指定什么数据库厂商时执行该sql-->
      <select id="findEmpById" resultType="employee" databaseId="mysql">
          select *
          from tb1_employee
          where id = #{id}
      </select>
  ```

* 一般配合enviroment使用



#### 9、mappers

* 将sql映射文件注册到全局文件中
* 第一种，重要的简单最好使用映射文件

```xml
<!--写好的sql映射文件一定要注册到全局文件中
        resource：引用类路径下的sql映射文件
        url：引用磁盘和网络上的的sql映射文件
    -->
    <mappers>
       <mapper resource="EmployeeMapper.xml"/>
       
    </mappers>
```

* 第二种：不重要的可以使用注解方法

```xml
<!--写好的sql映射文件一定要注册到全局文件中
    
        class：引用接口
            1、有sql映射文件，映射文件必须与接口同名，并且放在与接口在同一目录下
            2、没有sql映射文件，所有sql利用注解写在接口上
    -->
    <mappers>
        <mapper class="com.smc.mybatis.dao.EmployeeMapperAnnotation"/>
    </mappers>
```

```java
public interface EmployeeMapperAnnotation {
    @Select("select * from tb1_employee where id = ${id}")
    Employee findEmpById(Integer id);

}

```

* 第三种，批量注册

```xml
<!--写好的sql映射文件一定要注册到全局文件中
<!--        批量注册,指定包的sql映射文件xml-->
        <package name="com.smc.mybatis.dao"/>
    </mappers>
```

* 在资源目录下见同一个包名，能让资源逻辑路径一致

### 三、映射文件

#### 1、增删改查

* ```xml
  <select id="findEmpById" resultType="com.smc.mybatis.bean.Employee" databaseId="mysql">
          select *
          from tb1_employee
          where id = #{id}
      </select>
      
      <insert id="addEmp" parameterType="com.smc.mybatis.bean.Employee">
          insert into tb1_employee(last_name,email,gender) values(#{lastName},#{email},#{gender})
      </insert>
      
      <update id="updateEmp">
          update tb1_employee set last_name=#{lastName},email=#{email},gender=#{gender} where id=#{id}
      </update>
  
      <delete id="deleteEmpById">
          delete from tb1_employee where id = #{id}
      </delete>
  ```

* ```java
  public interface EmployeeMapper {
      Employee findEmpById(Integer id);
  
      Long addEmp(Employee employee);
  
      Long updateEmp(Employee employee);
  
      Boolean deleteEmpById(Integer id);
  }
  ```

* 测试方法

```java
@Test
    public void Test3() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        ////获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交，若是添加true参数会自动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee empById = mapper.findEmpById(1);

            System.out.println(mapper.getClass());
            System.out.println(empById);
            //添加
            Employee employee = new Employee();
            employee.setId(2);
            employee.setGender("0");
            employee.setEmail("1234@163.com");
            employee.setLastName("smchen");
            System.out.println(mapper.addEmp(employee));
            //修改
            employee.setEmail("4321@163.com");

            System.out.println(mapper.updateEmp(employee));
            //删除

            System.out.println(mapper.deleteEmpById(3));
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
```

#### 2、获取自增主键的值

* ```xml
  <!--mysql支持自增主键，自增主键值的获取，mybatis也是利用statement.getGeneralKeys();
      useGenerateKey="true";使用自增主键获取主键值策略
      keyProperty：指定对应的主键属性，也就是mybatis获取到的主键，将这个主键赋给bean的哪个属性-->
      <insert id="addEmp" parameterType="com.smc.mybatis.bean.Employee" useGeneratedKeys="true"
      keyProperty="id">
          insert into tb1_employee(last_name,email,gender) values(#{lastName},#{email},#{gender})
      </insert>
  ```

* 测试方法

```java
@Test
    public void Test4() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee empById = mapper.findEmpById(1);

            System.out.println(mapper.getClass());
            System.out.println(empById);
            //添加
            Employee employee = new Employee();
            employee.setGender("0");
            employee.setEmail("123456789@163.com");
            employee.setLastName("smchen123");
            mapper.addEmp(employee);
            System.out.println(employee.getId());
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
```

#### 3、oracle使用序列生成主键

* Oracle不支持自增；Oracle使用序列来模拟自增
* 每次插入的数据的逐渐都是从序列中拿到的值

```xml
<!--
            keyProperty:封装的主键值封装给javaBean的哪个属性
            order="Before"：当前sql在插入sql语句执行前执行
                 ="After":当前sql在插入sql语句之后执行
             resultType：运行之后返回值类型
        -->
        <selectKey keyProperty="id" order="BEFORE" resultType="Integer">
            select EMPLOYEES_SEQ.nextval from dual
        </selectKey>
        insert into tb1_employee(id,email,gender) values(#{id},#{email},#{gender})
    </insert>
```

#### 4、参数处理

* 单个参数：#{参数名}

* 多个参数

  * 通过#{pram1},#{param2}来获取指定位置的参数
  * 命名参数：明确指定参数名称

  ```java
  Employee findEmpByIdAndLastName(@Param("id")Integer id,@Param("lastName")String lastName);
  ```

  ```xml
  <select id="findEmpByIdAndLastName" resultType="com.smc.mybatis.bean.Employee" databaseId="mysql">
          select *
          from tb1_employee
          where id = #{id} and last_name = #{lastName}
      </select>
  ```

* POJO

  * 如果多个参数正好是我们业务逻辑数据模型，我们就可以直接传如pojo
  * #{属性名}:取出传入pojo的属性值

* Map

  * 如果多个不是业务模型中的数据，没有对应的pojo，为了方便，我们也可以传入map

  ```java
  Employee findEmpByMap(Map<String,Object> map);
  ```

  ```xml
  <select id="findEmpByMap" resultType="com.smc.mybatis.bean.Employee" databaseId="mysql">
          select *
          from tb1_employee
          where id = #{id} and last_name = #{lastName}
      </select>
  ```

* TO

  * 如果多个参数不是业务模型中的数据，但是中经常要使用，推荐编写一个TO（Transfer Object）数据传输对象

    > Page{
    >
    > ​	int index;
    >
    > ​	int size;
    >
    > }

* 参数封装扩展思考

  * 若是参数是Collection类型或者数组，也会特殊处理。也是把传入的list或者数组封装在map中

  * > Key:Collection,如果是List还可以使用各种各key（list），数组（array）

* 参数封装map的过程
* #与$的区别
  * #{}：是以预编译的形式，将参数设置到sql语句中：preparedStatement，可以防止sql注入
  * ${}：取出的值直接拼装在sql语句中
  * 大多数情况下，我们取参数的值应该使用#{}
  * 原生jdbc不支持占位符的地方我们就可以使用${}进行取值：比如分表、排序；按照年份拆分表
  * #{}：更丰富的用法
    * 规定参数的一些规则：javaType，jdbcType，mode（存储过程）、numericScale、resultMap、typeHandler、jdbcTypeName、expression（未来准备支持的功能）
    * jdbcType通常需要在某种特定的条件下被设置
      * 在我们的数据为null的时候，有些数据库可能不能识别mybatis对null的默认处理。比如oracle
      * 因为mybatis对所有的null都映射的事原生jdbc的other类型，二oracle无法识别other类型
      * 由于全局配置中：jdbcTypeForNull=OTHER，oracle不支持:两种解决办法
        * #{email,jdbcType=null}
        * 全局配置：jdbcTypeForNull = Null

#### 5、select

* 返回list

```java
List<Employee> findEmployees();
```

```xml
<!--返回list
        resultType:如果返回的是一个list，类型要是数据类型
    -->
    <select id="findEmployees" resultType="com.smc.mybatis.bean.Employee" databaseId="mysql">
        select *
        from tb1_employee
    </select>
```

* 记录封装map

  * 查询单条记录

  ```java
  
  Map<String,Object> findEmpByIdReturnMap(Integer id);
  ```

  ```xml
  <select id="findEmpByIdReturnMap" resultType="map" databaseId="mysql">
          select *
          from tb1_employee
          where id = #{id}
      </select>
  ```

  * 多条记录分装一个map：Map<Integer,Employee>

  ```java
  //告诉mybatis哪个属性值作为key
      @MapKey("id")
      Map<String,Employee> findEmpByIdMap();
  ```

  ```xml
  <select id="findEmpByIdMap" resultType="com.smc.mybatis.bean.Employee" databaseId="mysql">
          select *
          from tb1_employee
      </select>
  ```

* resultMap

  * 自定义结果映射规则

    * 全局setting设置：
      * autoMappingBehavior默认是PARTIAL，开启自动映射的功能。唯一的要求是别名和javaBean的属性名一致
      * 如果autoMappingBehavior设置为null，则会取消自动映射
      * 数据库字段命名规范，POJO属性复合驼峰命名发，我们可以开启自动驼峰命名规则映射功能，mapUnderscoreToCamelCase=true
    * 自定义resultMap，实现高级映射

    ```xml
    <!--自定某个javaBean的封装规则
        type：自定义规则的java类型
        id：唯一
        -->
        <resultMap id="myMap" type="com.smc.mybatis.bean.Employee">
    <!--        指定主键的封装规则
            id定义主键会有底层优化
            column：指定哪一列
            property：指定的javaBean属性
    -->
            <id column="id" property="id"></id>
    <!--        定义普通列封装规则-->
            <result column="last_name" property="lastName"></result>
    <!--        其他不指定的列会自动封装；但我们只要写resultMap就把所有的映射规则都写上-->
            <result column="email" property="email"></result>
            <result column="gender" property="gender"></result>
        </resultMap>
        <!--resultType和resultMap只能二选一-->
        <select id="findById" resultMap="myMap">
            select *
            from tb1_employee
            where id = #{id}
        </select>
    ```

    

  * 关联查询

    * 场景1：查询Employee的同时查出dapartment，一个员工有其对应的部分信息

    ```xml
    <resultMap id="MyEmp" type="com.smc.mybatis.bean.Employee">
            <id column="id" property="id"></id>
            <result column="last_name" property="lastName"></result>
            <result column="gender" property="gender"></result>
            <result column="did" property="dept.id"></result>
            <result column="dName" property="dept.deptName"></result>
        </resultMap>
        <select id="getEmpAndDept" resultMap="MyEmp">
            select a.id,a.last_name,a.gender,b.id did,b.dept_name dName
            from tb1_employee a,tb1_dept b
            where a.d_id = b.id AND a.id = #{id}
        </select>
    ```

    * 另一种resultMap：association可以指定联合的javaBean对象;type指定哪个属性是联合的对象；javaType：指定这个属性对象的类型（不能省略）

    ```xml
    <resultMap id="MyEmp2" type="com.smc.mybatis.bean.Employee">
            <id column="id" property="id"></id>
            <result column="last_name" property="lastName"></result>
            <result column="gender" property="gender"></result>
            <association property="dept" javaType="com.smc.mybatis.bean.Department">
                <id column="did" property="id"></id>
                <result column="dName" property="deptName"></result>
            </association>
        </resultMap>
        <select id="getEmpAndDept" resultMap="MyEmp2">
            select a.id,a.last_name,a.gender,b.id did,b.dept_name dName
            from tb1_employee a,tb1_dept b
            where a.d_id = b.id AND a.id = #{id}
        </select>
    ```

    * 使用association进行分布查询:1、先按照员工id查询员工信息。2、根据查询员工信息中的d_id值去部门表查处部门信息。3、部分设置员工

    ```xml
    <resultMap id="MyEmp3" type="com.smc.mybatis.bean.Employee">
            <id column="id" property="id"></id>
            <result column="last_name" property="lastName"></result>
            <result column="gender" property="gender"></result>
            <result column="email" property="email"></result>
            <!--select表明当前属性是调用select指定的方法查出的结果
            column指定那一列的值传给这个方法-->
            <association property="dept" select="com.smc.mybatis.dao.DepartmentMapper.findById"
            column="d_id">
    
            </association>
        </resultMap>
        <select id="getEmpByIdStep" resultMap="MyEmp3">
            select *
            from tb1_employee
            where id = #{id}
        </select>
    ```

    * 分布查询&延迟加载(按需加载)

      * 每次查询Employee对象的时候，都将一起查询出来。部门信息在我们使用的时候再去查询；

      ```xml
      <!--        开启懒加载-->
              <setting name="lazyLoadingEnabled" value="true"/>
      <!--        开启时，任一方法的调用都会加载该对象的所有延迟加载属性。 否则，每个延迟加载属性会按需加载-->
              <setting name="aggressiveLazyLoading" value="true"/>
      ```

    * collection定义关联集合封装规则

      * 查询部门获取所有员工

      ```xml
      <resultMap id="MyDept" type="com.smc.mybatis.bean.Department">
              <id column="id" property="id"></id>
              <result column="dept_name" property="deptName"></result>
      <!--        collection定义关联的集合类型
                  offType:指定集合中放的元素类型
      -->
              <collection property="employees" ofType="com.smc.mybatis.bean.Employee">
      <!--            定义集合中元素的封装规则-->
                  <id column="eid" property="id"></id>
                  <result column="last_name" property="lastName"></result>
                  <result column="email" property="email"></result>
                  <result column="gender" property="gender"></result>
              </collection>
          </resultMap>
          <select id="findByIdAndEmps" resultMap="MyDept">
              select d.id,d.dept_name,e.id eid,e.last_name,e.email,e.gender
              from tb1_dept d LEFT JOIN tb1_employee e on d.id = e.d_id
              where d.id = #{id}
          </select>
      ```

    * collection分布查询&延迟查询

    ```xml
    <resultMap id="MyDept1" type="com.smc.mybatis.bean.Department">
            <id column="id" property="id"></id>
            <result column="dept_name" property="deptName"></result>
            <collection property="employees" select="com.smc.mybatis.dao.EmployeeMapper.findEmpsByDId"
            column="id">
    
            </collection>
        </resultMap>
        <select id="findDeptByIdStep" resultMap="MyDept1">
            select *
            from tb1_dept
            where id = #{id}
        </select>
    ```

    * 分布查询传递多列值&fetchType
      * 将多列的值封装map传递：column="{key1=column1,key2=column2}"
      * fetchType="lazy"：表示延迟加载；eager：立即

  * discriminator鉴别器

    * 判断某列的值，然后根据某列的值改变封装行为

    ```xml
    <resultMap id="MyEmp4" type="com.smc.mybatis.bean.Employee">
            <id column="id" property="id"></id>
            <result column="last_name" property="lastName"></result>
            <result column="gender" property="gender"></result>
            <result column="email" property="email"></result>
    <!--        column：指定要判定的列名
                javaType：列值对应的java类型
    -->
            <discriminator javaType="String" column="gender">
    <!--            resultType:指定封装结果类型;resultType和resultMap二选一-->
                <case value="0" resultType="com.smc.mybatis.bean.Employee">
                    <association property="dept" select="com.smc.mybatis.dao.DepartmentMapper.findById"
                                 column="d_id">
    
                    </association>
                </case>
                <case value="1" resultType="com.smc.mybatis.bean.Employee">
                    <result column="last_name" property="email"></result>
                </case>
            </discriminator>
    
        </resultMap>
        <select id="getEmpByIdStep1" resultMap="MyEmp4">
            select *
            from tb1_employee
            where id = #{id}
        </select>
    ```

### 四、动态SQL

#### 1、简介和环境搭建

* sql灵活创建
* OGNL:对象图导航语言，这是一种强大的表达式语言，通过它可以非常方便的来操作对象属性。类似于我们的EL，SpEL等
  * 访问对象属性
  * 调用方法
  * 调用静态属性/方法
  * 调用构造方法
  * 运算符
  * 逻辑运算符

#### 2、if

* 查询员工：要求，携带了哪个字段查询条件就带上这个字段的值

```xml
<select id="findEmpsByConditionIf" resultType="com.smc.mybatis.bean.Employee" databaseId="mysql">
        select * from tb1_employee
        where
        <!--test:判断表达式（OGNL）类似c:if test -->
        <if test="id != null">
            id = #{id}
        </if>
        <if test="lastName != null and lastName != ''">
            and last_name like #{lastName}
        </if>
        <if test="email !=null and email.trim()!=''">
            and email = #{email}
        </if>
        <if test="gender == 0 or gender == 1">
            and gender = #{gender}
        </if>
    </select>
```

* where_查询条件

  * 当if的第一个条件不满足时，sql拼装可能会出错。
  * 处理方法：第一种

  ```xml
  <select id="findEmpsByConditionIf" resultType="com.smc.mybatis.bean.Employee" databaseId="mysql">
          select * from tb1_employee
          where 1=1
          <!--test:判断表达式（OGNL）类似c:if test -->
          <if test="id != null">
              and id = #{id}
          </if>
          <if test="lastName != null and lastName != ''">
              and last_name like #{lastName}
          </if>
          <if test="email !=null and email.trim()!=''">
              and email = #{email}
          </if>
          <if test="gender == 0 or gender == 1">
              and gender = #{gender}
          </if>
      </select>
  ```

  * 处理方法：第二种方法，采用where标签,会将第一个的and和or去掉

  ```xml
  <select id="findEmpsByConditionIf" resultType="com.smc.mybatis.bean.Employee" databaseId="mysql">
          select * from tb1_employee
          <where>
              <!--test:判断表达式（OGNL）类似c:if test -->
              <if test="id != null">
                  and id = #{id}
              </if>
              <if test="lastName != null and lastName != ''">
                  and last_name like #{lastName}
              </if>
              <if test="email !=null and email.trim()!=''">
                  and email = #{email}
              </if>
              <if test="gender == 0 or gender == 1">
                  and gender = #{gender}
              </if>
          </where>
      </select>
  ```

#### 2、trim_自定义字符串的截取

```xml
<select id="findEmpsByConditionTrim" resultType="com.smc.mybatis.bean.Employee" databaseId="mysql">
        select * from tb1_employee
        <!--where标签只能解决前面多出的and，后面多出的and无法解决
            prefix="":前缀，trim标签中是整个字符串拼接后的结果，prefix个整个拼串后的整个字符串加一个前缀
            prefixOverrides="":前缀覆盖，去掉整个字符串前面多余的字符
            suffix="":给拼串后的字符串添加后缀
            suffixOverrides=""：去掉整个字符串后面多余的字符
        -->
        <trim prefix="where" suffixOverrides="and">
            <!--test:判断表达式（OGNL）类似c:if test -->
            <if test="id != null">
                id = #{id} and
            </if>
            <if test="lastName != null and lastName != ''">
                last_name like #{lastName} and
            </if>
            <if test="email !=null and email.trim()!=''">
                email = #{email} and
            </if>
            <if test="gender == 0 or gender == 1">
                gender = #{gender}
            </if>
        </trim>
    </select>
```

#### 3、choose_分支选择

* 类似switich-case
* 场景：如果带了id就用id查，如果带了lastName就用lastName查，只会进入其中一个

```xml
 <select id="findEmpsByConditionChoose" resultType="com.smc.mybatis.bean.Employee">
        select * from tb1_employee
        <where>
            <choose>
                <when test="id!=null">
                    id=#{id}
                </when>
                <when test="lastName != null">
                    last_name like #{lastName}
                </when>
                <when test="email != null">
                    email = #{email}
                </when>
                <otherwise>
                    gender = 0
                </otherwise>
            </choose>
        </where>
    </select>
```

#### 4、set_与if结合的动态更新

* 使用set标签

```xml
<update id="updateEmployee">
    update tb1_employee
    <set><!--防止多出一些无用的逗号-->
        <if test="lastName != null">
            last_name=#{lastName},
        </if>
        <if test="email != null">
            email=#{email},
        </if>
        <if test="gender != null">
            gender=#{gender}
        </if>
    </set>
    where id=#{id}
</update>
```

* 也可以使用trim标签

#### 5、foreach

* 遍历集合

```xml
<select id="findEmployeeForeach" resultType="com.smc.mybatis.bean.Employee">
        select * from tb1_employee
        <!--collection:指定要遍历的集合，list类型的参数会特殊处理封装在map中，map的key就叫list
            将遍历出的元素赋值给item指定的变量
            #{变量名}就能取出当前遍历的元素
            separator:每个元素之间的分隔符
            open:遍历出的结果加一个开始的字符
            close:遍历出的结果加一个结束的字符
            index:索引，遍历list的时候是索引，item是值，遍历map的时候index就是map的key，item是值
        -->
        <foreach collection="ids" item="item_id" separator="," open="where id in (" close=")">
            #{item_id}
        </foreach>

    </select>
```

```java
List<Employee> findEmployeeForeach(@Param("ids")List<Integer> ids);
```

* mysql下foreach批量插入的两种方法

  * 第一种

  ```XML
  <insert id="addEmpsForeach">
          insert into tb1_employee(last_name,email,gender,d_id) values
          <foreach collection="emps" item="emp" separator=",">
              (#{emp.lastName},#{emp.email},#{emp.gender},#{emp.dept.id})
          </foreach>
      </insert>
  ```

  ```java
  Long addEmpsForeach(@Param("emps") List<Employee> emps);
  ```

  * 第二种:一次执行多条sql语句，需要开启数据库连接属性allowMultiQueries=true，以";"分隔多条语句一次执行配置

  ```properties
  jdbc.url=jdbc:mysql:///mybatis?allowMultiQueries=true
  ```

  ```xml
  <insert id="addEmpsForeach">
          <foreach collection="emps" item="emp" separator=";">
              insert into tb1_employee(last_name,email,gender,d_id) values
              (#{emp.lastName},#{emp.email},#{emp.gender},#{emp.dept.id})
          </foreach>
      </insert>
  ```

  ```java
  Long addEmpsForeach(@Param("emps") List<Employee> emps);
  ```

* oracle下批量插入的两种方式

  * 多个insert放在begin和end里面
  * 利用中间表

#### 6、内置参数:_parameter&_databaseId

* 不只是方法传递过来的参数可以被用来判断取之，mybatis默认还有两个内置参数

  * parameter：代表整个参数
    * 单个参数：parameter就是这个参数
    * 多个参数：参数会被封装为一个map；parameter就是代表这个map
  * databaseId：如果配置DatabaseIdProvider标签。_databaseId代表就是当前数据库别名

  ```xml
  <select id="getEmpsTestInnerParameter" resultType="com.smc.mybatis.bean.Employee">
          <if test="_databaseId =='mysql' ">
              select * from tb1_employee
              <if test="_parameter != null">
                  where last_name = #{_parameter.lastName}
              </if>
          </if>
          <if test="_databseId == 'oracle'">
              select * from tb1_employee
          </if>
      </select>
  ```

#### 7、bind_绑定

```xml
 <select id="getEmpsTestInnerParameter" resultType="com.smc.mybatis.bean.Employee">
        <if test="_databaseId =='mysql' ">
            select * from tb1_employee
            <!--bind可以将值绑定到一个变量中-->
            <bind name="_lastName" value="'%'+lastName+'%'"/>
            <if test="_parameter != null">
                where last_name like #{_lastName}
            </if>
        </if>
        <if test="_databaseId == 'oracle'">
            select * from tb1_employee
        </if>
    </select>
```

#### 8、抽取可重用的sql片段

* 抽取可重用的sql片段，方便后面引用,在sql中也能使用if等标签
* include来引用已经抽取的sql
* include还可以自定义一些property，sql标签内部就能使用自定义的属性
  * Include-property:取值的正确方式${prop},不能使用#{}

```xml
<select id="getEmpsTestInnerParameter" resultType="com.smc.mybatis.bean.Employee">
        <if test="_databaseId =='mysql' ">
            <!--使用include标签来调用 -->
            <include refid="insertColumn"></include>
            <!--bind可以将值绑定到一个变量中-->
            <bind name="_lastName" value="'%'+lastName+'%'"/>
            <if test="_parameter != null">
                where last_name like #{_lastName}
            </if>
        </if>
        <if test="_databaseId == 'oracle'">
            select * from tb1_employee
        </if>
    </select>
    <sql id="insertColumn">
        select *
        from tb1_employee
    </sql>
```



### 五、缓存机制

#### 1、缓存介绍

* MyBatis包含一个非常强大的查询缓存特性，它可以非常方便地配置和定制。缓存可以极大的提升查询效率
* Mybatis系统默认定义了两级缓存

#### 2、一级缓存

* 本地缓存

  * 与数据库同一次会话期间查询到的数据会放在本地缓存中。以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库
  * sqlSession级别的缓存，一级缓存是一直开启的

  ```java
  EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
              Employee empById = mapper.findEmpById(1);
              System.out.println(empById);
  
              Employee empById1 = mapper.findEmpById(1);
              System.out.println(empById1);
              System.out.println(empById1 == empById);//true
  ```

  

* 一级缓存失效的情况（没有使用到当前一级缓存的情况，效果就是还需要再向数据库发出查询）

  * sqlSession不同

  ```java
  SqlSession openSession = sqlSessionFactory.openSession();
          SqlSession openSession1 = sqlSessionFactory.openSession();
          try {
              //获取接口实现类对象
              //会为接口创建一个代理对象，代理对象去实现增删改查
              EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
              EmployeeMapper mapper1 = openSession1.getMapper(EmployeeMapper.class);
              Employee empById = mapper.findEmpById(1);
              System.out.println(empById);
  
              Employee empById1 = mapper1.findEmpById(1);
              System.out.println(empById1);
              System.out.println(empById1 == empById);//false
              //提交
              openSession.commit();
              openSession1.commit();
          } finally {
              openSession.close();
              openSession1.close();
          }
  ```

  * sqlSession相同，但查询条件不同（当前缓存中没有这个数据）
  * sqlSession相同，两次查询之间执行了增删改操作（这次增删改可能对当前数据有影响）
  * sqlSession相同，手动清除了缓存

  > ```java
  > openSession.clearCache();
  > ```

#### 3、二级缓存

* 全局缓存

  * 基于namespace级别的缓存，一个namespace对应一个二级缓存

* 工作机制

  * 一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中
  * 如果会话关闭：一级缓存的数据会被保存到二级缓存中。新的会话查询信息，就可以参照二级缓存
  * sqlSession===EmployeeMapper==>Employee
  * DepartmentMapper===>Department
  * 不同的namespace查处的数据就会放在自己对应的缓存中（map中）
  * 数据会从二级缓存中获取。查处的数据都会被默认先放在一级缓存中。只有会话提交或者关闭之后，一级缓存中的数据才会转移到二级缓存中

* 二级缓存的使用

  * 开启全局二级缓存配置；默认是开启的

  > ```xml
  > <setting name="cacheEnabled" value="true"/>
  > ```

  * 去mapper.xml中配置使用二级缓存，直接在xml里配置cache标签
    * eviction:缓存的回收策略，默认是LRU
      * LRU-最近最少使用的：移除最长时间不被使用的对象
      * FIFO：先进先出，按对象进瑞缓存的顺序一处他们
      * SOFT：软引用，移除基于垃圾回收器状态和软引用规则的对象
      * WEAK：弱引用，更积极地移除基于垃圾收集器状态和弱引用规则的对象
    * flushInterval:缓存刷新间隔
      * 默认不清空，设置一个毫秒值
    * readOnly:是否只读
      * ture：只读，mybatis认为所有缓存中获取数据的操作都是只读操作，不会修改数据。mybatis为了加快获取速度，直接就会将数据缓存中的引用交给用户。不安全，速度快
      * false：非只读，mybatis觉得获取的数据可能会被修改。mybatis会利用序列化&反序列化的技术克隆一份新的数据给你。安全，速度慢
    * size：缓存存放多少元素
    * type：指定自定义缓存的全类名
  * 我们的POJO需要实现序列化接口

  > ```xml
  > <mapper namespace="com.smc.mybatis.dao.EmployeeMapperDynamicSQL">
  >     <cache eviction="FIFO" flushInterval="100000" readOnly="false" size="1024"></cache>
  > ```

#### 4、缓存有关的设置及其属性

* cacheEnabled=true；false：关闭二级缓存，不关闭二级缓存
* 每个select标签都有useCache="true"
  * false：不实用缓存（一级缓存可用，二级缓存不可用）
* 每个增删改标签都有flushCache="true"
  * 增删改完成后机会清除缓存
  * 一级和二级缓存都被清除
* 每个查询也有flushCache属性，但默认是false
  * 若为true，则每次查询之后也会清除缓存
* sqlSession.clearCache()只是清除当前session的一级缓存
* localCacheScope：本地作用域（一级缓存SESSION），当前会话的所有数据保存在会话缓存中
  * STATEMENT：可以禁用一级缓存

#### 5、缓存原理图

![](/Users/smc/Desktop/smc/语言学习/java/Mybatis/resources/WX20220407-234614@2x.png)

#### 6、第三方缓存整合原理&ehcache适配包下载

* 需要的jar包

> mybatis-ehcache-1.2.2.jar

* 在mapper中添加cache标签

```xml
<cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
```

* 在项目资源路径下需要一个ehcache.xml文件，若没有则使用默认配置

### 六、MyBatis-Spring整合

#### 1、Spring+SpringMVC+MyBatis

```xml
<!--    创建出sqlSessionFactory对象-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"></property>
<!--        指定mybatis全局配置文件的位置-->
        <property name="configLocation" value="classpath:mybatis-config.xml"></property>
<!--        指定mapper文件的位置，若是在mybatis全局文件中配置需要mapper.xml和mapper类包相同-->
        <property name="mapperLocations" value="classpath:mapper/*"></property>
    </bean>
<!--    扫描所有mapper接口的实现，让这些接口自动注入-->
    <mybatis:scan base-package="com.smc.ssm.mapper" />
<!--    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">-->
<!--        <property name="basePackage" value="com.smc.ssm.mapper"></property>-->
<!--    </bean>-->
```

### 七、MyBatis-逆向工程

#### 1、介绍

* MyBatis Generator：简称MBG，是一个专门为MyBatis框架使用者定制的代码生成器，可以快速的根据表生成对应的映射文件，接口，以及bean类。支持基本的增删改查，以及QBC风格的条件查询。但是表连接、存储过程等这些复杂的sql的定义需要我们手工编写

### 八、Mybatis工作原理

#### 1、框架分层架构

![](/Users/smc/Desktop/smc/语言学习/java/Mybatis/resources/WX20220408-185414@2x.png)

#### 2、SQLSessionFactory的初始化

![](/Users/smc/Desktop/smc/语言学习/java/Mybatis/resources/截屏2022-04-08 21.59.13.png)

* configuration封装了所有配置文件的详细信息
* 把配置文件的信息解析并保存在Configuration对象中，返回包含了Configuration的DefaultSqlSession对象

#### 3、获取SqlSession对象

![](/Users/smc/Desktop/smc/语言学习/java/Mybatis/resources/截屏2022-04-08 22.09.40.png)

* 返回一个DefaultSQLSession对象，包含Executor和Configuration
* 这一步会创建Executor对象

#### 4、getMapper获取到接口的代理对象

![](/Users/smc/Desktop/smc/语言学习/java/Mybatis/resources/截屏2022-04-08 22.19.56.png)

* 使用MapperProxyFactory创建一个MapperProxy的代理对象。
* 代理对象里面包含了DefaultSqlSession(Executor)

#### 5、查询原理

![](/Users/smc/Desktop/smc/语言学习/java/Mybatis/resources/截屏2022-04-08 22.50.38.png)

* 查询流程总结：

![](/Users/smc/Desktop/smc/语言学习/java/Mybatis/resources/截屏2022-04-08 22.57.02.png)

#### 6、原理总结

* 根据配置文件（全局sql映射）初始化出Configuration对象
* 创建一个DefaultSqlSession对象
  * 他里面包含Configuration以及Executor（根据全局配置文件中的defaultExecutorType创建出对象对应的Executor）
* DefaultSqlSession.getMapper()：拿到Mapper接口对应的MapperProxy
* MapperProxy里面有（DefaultSqlSession）
* 执行增删改查方法：
  * 调用DefaultSqlSession的增删改查
  * 会创建一个Statement对象。（同时也会创建出ParameterHandler和ResultSetHandler）
  * 调用StatementHandler预编译参数以及设置参数值
  * 调用statementHandler的增删改查方法
  * ResultSetHandler封装结果
* 注意：四大对象每个创建的时候都有一个interceptorChain.pluginAll(parameterHandler)

### 九、插件原理

#### 1、基本流程

* MyBatis在四大对象的创建过程中，都会有插件进行介入。插件可以利用动态代理机制一层层的包装目标对象，而实现在目标对象执行目标方法之前进行拦截的效果。
* Mybatis允许在已映射语句执行过程中的某一点进行拦截调用
* 在四大对象创建的时候
  * 每个创建出来的对象不是直接返回的，而是
    * interceptorChain.pluginAll(parameterHandler)；
  * 获取到所有的Interceptror（拦截器）（插件需要实现的接口）
    * 调用interceptor.plugin(target);返回target包装后的对象
  * 插件机制，我们可以使用 插件为目标对抗创建一个代理对象；AOP（面向切面）
    * 我们创建可以为四大对象创建出代理对象
    * 代理对象就可以拦截到四大对象的每一个执行

> /**
>  * 插件编写
>  * 1、编写Interceptor的实现类
>  * 2、使用@Interceptors注解完成插件签名
>  * 3、将写好的插件注册到全局配置文件中
>  */

```java
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
```

```xml
<!--    注册插件-->
    <!-- 注意 plugins 在配置文件中的位置
        properties?, settings?, typeAliases?, typeHandlers?, objectFactory?, objectWrapperFactory?, plugins?, environments?, databaseIdProvider?, mappers? -->
    <plugins>
        <plugin interceptor="com.smc.ssm.plugin.MyFirstPlugin">
            <property name="username" value="smchen"/>
            <property name="password" value="123456"/>
        </plugin>
    </plugins>
```

* 多个插件时会从后往前执行

#### 2、开发插件

* 添加依赖jar包

```xml
<dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>5.2.0</version>
        </dependency>
```

* 在Mybatis中配置插件

```xml
<plugins>
        <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
    </plugins>
```

* 在调用mapper方法时

```java
public List<Employee> getEmployees(){
        Page<Object> page = PageHelper.startPage(1, 2);
        List<Employee> emps = employeeMapper.findEmps();
        System.out.println("当前页码" + page.getPageNum());
        System.out.println("总记录数" + page.getTotal());
        System.out.println("每页记录数" + page.getPageSize());
        System.out.println("总页码：" + page.getPages());
        return emps;
    }
```

* 也可以使用pageInfo

```java
public List<Employee> getEmployees(){
//        Page<Object> page = PageHelper.startPage(1, 2);

        List<Employee> emps = employeeMapper.findEmps();
        PageInfo<Employee> pageInfo = new PageInfo<>(emps);
        System.out.println("当前页码" + pageInfo.getPageNum());
        System.out.println("总记录数" + pageInfo.getTotal());
        System.out.println("每页记录数" + pageInfo.getPageSize());
        System.out.println("总页码：" + pageInfo.getPages());
        System.out.println("是否第一页" + pageInfo.isIsFirstPage());
        return emps;
    }
```

#### 3、BatchExecutor&Spring中配置批量sqlSession

* 批量：预编译sql一次=>设置参数===》10000次===〉执行（一次）
* 非批量：预编译sql=设置参数=执行==》10000次

```xml
<!--    配置一个可以进行批量执行的sqlSession-->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"></constructor-arg>
        <constructor-arg name="executorType" value="BATCH"></constructor-arg>
    </bean>
```

```java
@Autowired
    private SqlSession sqlSession;

    public Employee getEmpById(Integer id){
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        return employeeMapper.findEmpById(id);
    }
```

#### 4、oracle中创建一个带游标的存储过程

#### 5、自定义类型处理器_Mybatis中枚举类型的默认处理

