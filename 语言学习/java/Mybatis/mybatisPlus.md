### 一、简介

* 简称mp，是Mybatis的增强工具包，只做增强不做改变，为简化工作，提高生产率而生
* 我们的愿景是曾为Mybatis最好的搭档。

#### 1、MAVEN添加依赖

```xml
<dependencies>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.1</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.21</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.2.8</version>
        </dependency>

        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>

        <!--spring整合包-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.3.18</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>5.3.18</version>
        </dependency>

    </dependencies>
```

#### 2、集成MP

* Mybatis-plus的集成非常简单，对于Spring，我们仅仅需要把Mybatis自带的MybatisSqlSessionFactoryBean替换为MP自带的即可。

```xml
<!--    创建出sqlSessionFactory对象
        Mybatis:org.mybatis.spring.SqlSessionFactoryBean
        Mybatis-plus:com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean
    -->
<bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"></property>
<!--        指定mybatis全局配置文件的位置-->
        <property name="configLocation" value="classpath:mybatis-config.xml"></property>

    </bean>
```

### 二、通用CRUD

* 基于MyBatis

  * 需要编写EmployeeMapper接口，并手动编写CRUD方法
  * 提供EmployeeMapper.xml映射文件，并手动编写每个方法对应的SQL语句

* 基于MP

  * 只需要创建EmployeeMapper接口，并集成BaseMapper接口，这就是使用MP需要完成的所有操作，甚至不需要创建SQL映射文件。

  ```java
  public interface EmployeeMapper extends BaseMapper<Employee> {
  }
  
  ```

* @TableId注解

  * 设置主键策略

  ```java
  /**
   * value:指定表中主键列的列名，若属性名与列名相同则不用设置
   * type：设置主键策略
   */
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  ```

* @TableName注解

  * MybatisPlus会默认使用实体类的类名到数据库中找对应的表
  * 使用TableName配置映射库

  ```java
  @TableName(value = "tb1_employee")
  public class Employee {
    ...
  }
  ```

* MP全局策略配置

  * 在sqlsessionFactoryBean中注入mp全局策略

  ```xml
  <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
          <property name="dataSource" ref="dataSource"></property>
  <!--        指定mybatis全局配置文件的位置-->
          <property name="configLocation" value="classpath:mybatis-config.xml"></property>
  <!--        注入全局MP策略配置-->
          <property name="globalConfig" ref="globalConfig"></property>
      </bean>
  ```

  

  * MybatisPlus默认解决驼峰命名
  * 设置所有id自增和表名前缀

  ```xml
  <!--    定义MybatisPlus的全局策略配置-->
      <bean id="globalConfig" class="com.baomidou.mybatisplus.core.config.GlobalConfig">
          <property name="dbConfig" ref="dbConfig"></property>
      </bean>
      <bean id="dbConfig" class="com.baomidou.mybatisplus.core.config.GlobalConfig$DbConfig">
          <property name="idType" value="AUTO"></property>
  <!--        全部表前缀策略配置-->
          <property name="tablePrefix" value="tb1_"></property>
      </bean>
  ```

* @TableField注解

  * 当属性不是数据库的字段，使用TableField设置

  ```java
  @TableField(exist = false)
  private Double salary;
  ```

* 插入数据获取主键值

  * MP能够自动将主键值插入对象

* updateById方法

```java
int i = employeeMapper.updateById(employee);
```

* selectById方法

```java
Employee employee = employeeMapper.selectById(4);
```

* selectBatchIds方法

```java
List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(5);
        list.add(6);
        List<Employee> employees = employeeMapper.selectBatchIds(list);
```

* selectByMap方法

```java
//通过map封装条件查询
        Map<String, Object> map = new HashMap<>();
        map.put("last_name","ddddd");
        map.put("age",20);
        List<Employee> employees = employeeMapper.selectByMap(map);
```

* selectPage方法
* MP启动注入SQL原理分析

### 三、条件构造器Wrapper

#### 1、QueryWrapper简介

* Mybatis-Plus通过EntityWrapper（简称EW，MP封装的一个查询条件构造器）或者Condition（与EW类似）来让用户自由的构建查询条件，简单便捷，没有额外的负担，能够有效提高开发效率
* 实体包装器，主要用于处理sql拼接，排序，实体参数查询等
* 注意：使用的数据库字段，不是java属性

```java
Page<Employee> employeePage = employeeMapper.selectPage(new Page<>(1, 2),
                new QueryWrapper<Employee>().eq("gender", "1").between("age",19,21));

```

#### 2、带条件的查询

```java
List<Employee> employees = employeeMapper.selectList(new QueryWrapper<Employee>().
                eq("gender", 1).like("last_name", "mc")
                .or()   //WHERE (gender = ? AND last_name LIKE ? OR email LIKE ?)
                .like("email", "163"));
```

#### 4、修改操作

```java
 Employee employee = new Employee();
        employee.setLastName("bbbb");
        employee.setAge(16);
        employee.setGender("0");
        int update = employeeMapper.update(employee, new UpdateWrapper<Employee>()
                .eq("age", 20)
                .like("last_name", "ssss"));
```

#### 5、删除操作

```java
int delete = employeeMapper.delete(new UpdateWrapper<Employee>()
                .eq("age", 16)
                .like("last_name", "bbbb"));
```

### 四、ActiveRecord（活动记录）

* 十一中领域模型模式，特点是一个模型类对应关系型数据库中的一个表，而模型类的一个实例对应表的一行记录
* ActiveRecode一直广受动态语言（PHP、Ruby等）的喜爱，而Java作为准静态语言，对于ActiveRecord往往只能感叹其优雅，所有MP也在AR道路上尽心管理一定的探索

#### 1、如何使用AR模式

* 积极需要让实体类集成Model类且实现主键指定方法，即可开启AR之旅

```java
public class Employee extends Model {
  ...
}
```

#### 2、AR基本CRUD

* 插入操作

```java
/**
     * AR的插入操作
     */
    @Test
    public void testARInsert(){
        Employee employee = new Employee();
        employee.setAge(26);
        employee.setLastName("林建国");
        employee.setGender("1");
        employee.setEmail("ljg@169.com");
        boolean insert = employee.insert();
        System.out.println(insert);

    }
```

* 修改操作

```java
/**
     * AR的根据id修改
     */
    @Test
    public void testARUpdate(){
        Employee employee = new Employee();
        employee.setId(5);
        employee.setAge(26);
        employee.setLastName("书蒙尘");
        employee.setGender("1");
        employee.setEmail("smc@169.com");
        boolean b = employee.updateById();
        System.out.println(b);

    }
```

* 查询操作

```java
@Test
    public void testARSelect(){
        Employee employee = new Employee();
        Model model = employee.selectById(5);
        System.out.println(model);
        employee.setId(6);
        Model model1 = employee.selectById();
        System.out.println(model1);
    }

//selectAll()
//selectList(Wrapper)
//selectCount(Wrapper)
```

* 删除操作

> deleteById(Integer)
>
> deleteById()
>
> delete(Wrapper)

* 分页操作

> selectPage(new Page,Wrapper)

#### 3、小结

* AR模式提供了一种更加便捷的方式实现CRUD操作，其本质还是调用Mybatis对应的方法，类似于语法糖
  * 语法糖是指计算机语言中添加的某种语法，这种语法对原本语言的功能并没有影响。可以更方便开发者使用，可以避免出错的机会，让程序可读性更好

### 五、代码生成器

* MP提供了大量的自定义设置，生成的代码完全能够满足各类型的需求
* MP的代码生成器和Mybatis MBG代码生成器
  * MP的代码生成器都是基于java代码来生成。MPG基于xml文件进行代码生成
  * MyBatis的代码生成器可生成：实体类、Mapper接口、Mapper映射文件
  * MP的代码生成器可生成：实体类（可以选择是否支持AR）、Mapper接口、Mapper映射文件、service层，controller层
* 表及字段命名策略选择
  * 在MP中，我们建议数据库表名和表字段名采用驼峰命名方式，如果采用下划线命名方式，请开启全局下划线开关，如果表名字段名命名方式不一致请注解指定，我们建议最好保持一致
  * 这么做的原因是为了避免在对应实体类时产生的性能损耗，那么你采用下划线也是没问题的，只需要在生成代码时配置dbColumnUnderline

#### 1、代码生成器依赖

* 模版引擎
  * MP的代码生成器默认使用的是Apache的Velocity模版，当然也可以更换为别的模板技术，例如freemarker。

```java
				//1、全局配置

        //2、数据源配置
        //3、策略配置
        //4、包名策略配置
        //5、整合配置
```

### 六、插件扩展

#### 1、Mybatis插件机制的简介

* 插件机制
  * Mybatis通过插件（Interceptor）可以做到拦截四大对象相关方法的执行，根据需求完成相关数据的动态改变
    * Executor
    * StatementHandler
    * ParameterHandler
    * ResultSetHandler

* 插件原理
  * 四大对象的每个对象在创建时，都会执行interceptroChain.pluginAll(),会经过每个插件兑现过的plugin()方法，目的是为当前的四大兑现过创建代理。代理对象就可以拦截到四大对象相关方法的执行，因为执行四大对象的方法经过代理

#### 2、分页插件

* PagenationInterceptor

#### 3、执行分析插件

* SqlExplainInterceptor

>Explain sql语句

* 建议在开发环境中使用，正式环境不实用

#### 4、性能拦截插件

* PerformanceInterceptor
* 用于输出每条SQL语句及其执行时间
* SQL性能执行分析，开发环境使用，超过指定时间，停止运行。有助于发现问题

#### 5、乐观锁插件

* OptimisticLockerInterceptor
* 如果想实现如下需求：当要更新一条记录的时候，希望这条记录没有被别人更新
* 乐观锁的实现原理：
  * 取出记录时，获取当前version
  * 更新时，带上这个version
  * 执行更新时，set version = yourVersion+1 where version = yourVersion，如果version不对，就更新失败
* @Version用于注解实体字段，必须要有

### 七、自定义全局操作

* 根据MybatisPlus的AutoSqlInjector可以自定义各种你想要的sql，注入到全局中，相当于自定义Mybatisplus自动注入方法
* 之前需要在xml中配置的SQL语句，现在通过扩展AutoSqlInjector在加载mybatis环境时就注入

* xml配置

```xml
<!--注入sql-->
    <bean id="myLogicSqlInjector" class="com.smc.mp.injector.MyLogicSqlInjector"></bean>
<!--    定义MybatisPlus的全局策略配置-->
    <bean id="globalConfig" class="com.baomidou.mybatisplus.core.config.GlobalConfig">
        <property name="sqlInjector" ref="myLogicSqlInjector"></property>
    </bean>
```

```java
public class MyLogicSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new MySqlInjector());
        return methodList;
    }
}
```

```java
public class MySqlInjector extends AbstractMethod {
    /**
     * 扩展inject方法，完成自定义操作
     * @param mapperClass
     * @param modelClass
     * @param tableInfo
     * @return
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        //注入sql语句
        String sql = "update "+ tableInfo.getTableName()+" set is_delete = 1 where id = 9";
        //注入方法名，要要mapper中的方法名一只
        String id = "logicDelete";
        //构造sqlSource
        SqlSource sqlSource = languageDriver.createSqlSource(configuration,sql,modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, id, sqlSource, new NoKeyGenerator(), null, null);
    }
}
```

#### 2、自定义注入器应用只逻辑删除

### 八、公共字段自动填充

#### 1、元数据处理器接口

* MetaObjectHandler
* InsetFill（MetaObject）
* updateFill（MetaObject）
* metaobject：元对象，是Mybatis提供的一个用于更加方便，更加优雅的访问对象的属性，给对象的属性设置值的一个对象，还会用于包装对象，支持对Object、Map、Collection等对象进行包装
  * 本质上metaObject获取对象的属性值或者是给对象的属性设置值，最终是要通过Reflector获取到属性的对应方法的Invoker，最终invoke

#### 2、开发步骤

* 注解填充字段@TableFile(fill = FieldFill.INSERT) 查看FieldFill
* 自定义公共字段填充处理器
* MP全局注入，自定义公共字段填充处理器

### 九、Oracle主键Sequence

* MySQL：支持主键自增：IdType.Auto

* Oracle：序列（Sequence）

* 实体类配置主键Sequence @KeySequence（value="序列号"，clazz=xxx.class主键属性类型）

* 全局MP主键生成策略为IdType.INPUT

* 全局MP中配置Oracle主键Sequence

  * OracleKeyGenerator

* Oracle驱动：因为Oracle授权问题，不能从Maven的仓库中下载到Oracle驱动

* sequence的常用操作

  * 查询序列的下一个值

  > nextval

  * 当前值：currval



