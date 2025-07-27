### 1、什么是SQL

* structure query language

### 2、什么是MySql

* DBMS :数据库管理工具

### 3、适用MySql

* 连接：主机名+端口+用户名+密码（用户口令）
* 选择数据库
  * 关键字：USE
  * 可用show来显示信息
    * SHOW DATABASES ：显示数据库表
    * SHOW TABLE ：显示当前选择库的表
    * SHOW COLUMNS FROM TABLE ：显示表的字段信息（= DESCRIBE TABLE） 
    * SHOW STATUS：显示服务器状态信息
    * SHOW GRANTS:显示授权用户（所有用户或特定用户）的安全权限
    * SHOW ERRORS 和SHOW WARNINGS，用来显示服务器错误和警告信息
  * 自动增量

### 4、检索数据

* SELECT
  * 最好别使用通配符，搜索不需要的列会降低性能
* 检索不同的行
  * DISTINCT 返回的值不重复，它作用于所有列，如SELECT DISTINCT ven_id,prod_price，除非指定的两个列都相同，否则所有行都会被检索出来
* 限制结果：LIMIT
  * LIMIT 3 ：从行0开始的三行
  * LIMIT 3, 4：从行3开始的4行

### 5、排序检索数据

* ORDER BY
  * 通过非选择列排序是允许的
* 多个列排序：按指定列的顺序进行排序
* 指定排序方向
  * 默认是递增排序（ASC），递减是DESC
  * 若对多条列进行不同的顺序排列，可如：`SELECT DISTINCT prod_id,prod_name,prod_price FROM products ORDER BY prod_price DESC,prod_name`
  * 通过ORDER BY和LIMIT配合可以找出列中的最大值和最小值，LIMIT需要用在ORDER BY之后

### 6、过滤数据

* WHERE 
  * 用AND和OR来连接条件
  * 执行时AND的优先顺序是大于OR的（多使用圆括号，不要依赖执行顺序）
  * IN ：用来指定匹配值清单的关键字，功能与OR相当，IN操作符一般比OR操作符速度快，而且可以包含其他SELECT语句
  * NOT：否定它之后的条件，MySQL支持NOT对IN，BETWEEN，EXISTS子句的取反
* 用通配符进行过滤
  * LIKE操作符
    * 通配符：用来匹配一部分的特殊字符
    * %通配符：表示任何字符出现的次数，“%”无法匹配NULL值
    * 下划线（_）通配符：只匹配单个字符
    * 通配符要仔细使用而不要过度使用，使用通配符相对于操作符运行慢

### 7、用正则表达式进行搜索

* REGEXP
* REGEXP默认不区分大小写，若需要区分可在REGEXP后加BINARY

### 8、创建计算字段

* 拼接：使用Concat()来拼接列，需要一个或多个指定的串，各个串之间用逗号分隔。
  * 可使用Rtrim()去掉右边的所有空格
  * 别名：用AS关键字

* 执行算术计算

### 9、使用数据处理函数

* 函数的可移植性不强，一般每种DBMS的函数都是独立的
* 文本处理函数：
  * Upper()：将文本转换为大写
  * Left()：返回串左边的字符
  * Length()：返回串的疮毒
  * Locate()：找出串的一个子串
  * Lower()：将串转位小写
  * Ltrim()：去掉串左边的空格
  * Right()：返回串右边的字符
  * Rtrim()：去掉串右边的空格
  * Soundex()：返回串的SOUNDEX值，SOUNDEX改为类似发音字符和音节
  * SubString()：截取子串
* 日期和时间处理函数
  * AddDate()：增加一个日期（天，周等）
  * AddTime()：增加一个时间（时，分等）
  * CurDate()：返回当前日期
  * CurTime()：返回当前时间
  * Date()：返回日期时间的日期部分
  * DateDiff()：计算两个日期之差
  * Date_Add()：高度灵活的日期运算函数
  * Date_Format()：返回一个格式化的日期或时间串
  * Day()：返回一个日期的天数部分
  * DayOfWeek()：对于一个日期，返回对应的星期几
  * Hour():返回一个时间的消失部分
  * Minute()：返回一个时间的分钟部分
  * Month()：返回一个日期的月份部分
  * Now()：返回当前的日期和时间
  * Second()：返回一个时间的秒部分
  * Time()：返回一个日期时间的时间部分
  * Year()：返回一个日期的年份部分
  * 日期的基本给是时yyyy-mm-dd
* 数组处理函数：是各DBMS最具统一的函数
  * Abs()：返回一个值的绝对值
  * Cos():返回一个角度的余弦
  * Exp()：返回一个数的指数值
  * Mod()：返回一个除操作的余数
  * Pi()：返回圆周率
  * Rand()：返回一个随机数
  * Sin()：返回一个角度的正弦
  * Sqrt()：返回一个数的平方根
  * Tan()：返回一个角度的正切

### 10、汇总数据

* 聚集函数
  * AVG()：返回某列的平均值，只能对单列使用，忽略NULL的行
  * COUNT()：返回某列的行数
    * COUNT(*)：不管表列中包含的是NULL还是非NULL
    * COUNT(column)：对特定列中具有值的行进行计算，忽略NULL的行
  * MAX()：返回某列的最大值
  * MIN()：返回某列的最小值
  * SUM()：返回某列值之和
* 聚集不同值：以上的五个聚集函数都可以使用distinct来去除重复值，COUNT只有在指定列时能够使用
* 组合聚集函数

### 11、分组函数

* 创建分组GROUP BY：
  * GROUP BY子句可以包含任意数目的列。这使得能对分组进行嵌套，为数据分组提供更细致的控制
  * 如果在GROUP BY子句中嵌套了分组，数据将在最后规定的分组上进行汇总。换句话说，在建立分组时，指定的所有列都一起计算
  * GROUP BY子句中列出的每个列都必须时检索列货有效的表达式（但不能是聚集函数）。如果在SELECT中使用表达式，则必须在GROUP BY子句中指定相同的表达式。不能使用别名
  * 除聚集计算语句外，SELECT语句中的每个列都必须在GROUP BY子句中给出
  * 如果分组列中具有NULL值，则NULL将作为一个分组返回。如果列中有多行NULL值，它们将分为一组。
  * GROUP BY子句必须出现在WHERE子句之后，ORDER BY子句之前。
  * WIHT ROLLUP :对每个分组进行汇总

* 过滤分组HAVING：支持所有WHERE子句的操作
* 分组和排序：ORDER BY总是在最后
* SELECT子句顺序:SELECT -> FROM -> WHERE ->GROUP BY ->HAVING -> ORDER BY ->LIMIT

### 12、使用子查询

* 利用子查询过滤

  * 多层嵌套，查询时会会从内到外执行

  * 子查询并不是数据检索的有效方法

* 作为计算字段使用子查询

### 13、联结表

* 等值联结（内部联结）：不要忘记where（Inner JOIN on一样）
* 就是一张表与另一张表的每一样组合，用where来删除不必要的行

### 14、创建高级联结

* 自联结：有时自联结远比子查询快的多
* 外部联结：有时需要包含哪些没有关联行的行
  * OUNTER JOIN ON:在使用时必须使用RIGHT货LEFT关键字指定包括其所有行的表（RIGHT指出的是OUTER JOIN右边的表，而LEFT指出的是OUTER JOIN左边的表）
  * MySQL中没有\*=和=\*的使用
* 使用带聚集函数的联结

### 15、组合查询

* UNION：在单个查询中从不同的表返回类似结构的数据；对单个表执行多个查询，按单个查询返回数据
* UNION中的每个查询必须包含相同的列，表达式或聚集函数（不过各个列不需要以相同的次序列出）
* 列数据类型必须兼容：类型不必完全相同，但必须是DBMS可以隐含地转换的类型（例如，不同的数值类型或不同的日期类型）
* 包含或取消重复的行：UNION默认是取消重复的行，而UNION ALL不会
* 对组合查询结果排序：只能使用一条ORDER BY语句，它必须出现在最后一条SELECT语句之后，能作用整个结果集。

### 16、全文本搜索

* 需要分贝查看每个行，不需要分别分析和处理每个词。MySQL创建指定列中各词的一个索引，搜索可以针对这些词进行。这样，MySQL可以快速有效地决定哪些词匹配，那些词不匹配，它们匹配的频率，等等。
* 为了进行全文本搜索，必须索引被搜索的列，而且要随着数据的改变不断的重新索引。对表进行适当的设计后，MySQL会自动进行所有的索引和重新索引。
  * 一般在创建表时启用全本文搜索。FULLTEXT，它给出被索引列的一个逗号分隔的列表。
  * 不要在导入数据时启用索引，这回很耗时间
  * Match()指定被搜索的列，Against()指定要使用的搜索表达式
  * 传递给Match()的值必须与FULLTEXT()定义中的相同。如果指定多个列，必须列出它们（而且次序正确）
  * 搜索不区分大小，除非使用BINARY
* 使用查询扩展
  * 在Against()中加入 WITH QUERY EXPANSION
* 布尔文本搜索：
  * 不同于前面的全文搜索，即使没有定义FULLTEXT索引，也可以使用它，但这是一种非常缓慢的操作
  * 要匹配的词；要排斥的词；排列提示；表达式分组；另外一些内容。
  * IN BOOLEAN MODE：若没有指定boolean值，则跟没有指定boolean方式的结果一样，但其行为有差别
  * Against('heavy -rope*' IN BOOLEAN MODE):匹配heavy，但-rope\*明确地指示MySQL排除包含rope\*（任何以rope开始的词，包括ropes）的行
  * 全文本布尔操作符：

* 50%原则：多于50%不建议使用全文搜索
* 忽略单引号

### 17、插入数据

* 插入完整的行
  * INSERT INTO 表名 values(各列按次数输入数据)，不需要值的要也要NULL
  * INSERT INTO 表名(各字段名称,可自己排序) values(按排序的字段名称，输入值) 
  * 一般不要使用没有明确给出列的列表的INSERT语句
  * 如果某个表的定义允许，则可以在INSERT操作中省略某些列。省略的列必须满足一下某个条件。
    * 该列定义允许微NULL值
    * 在表定义中给出默认值
* 插入多个行
  * values(),(),()
* 插入检索出的数据
  * INSERT INTO 表名(字段名) SELECT
  * 可以不使用相同的列名，关心的是列的位置

### 18、更新和删除数据

* 更新数据（不要忽略WHERE，防止修改所有行）
  * UPDATE 表名 SET 字段名='' WHERE
  * 更新多个列时用逗号隔开
  * IGNORE关键字：当更新数据时一行或多行出现了一个错误，可用IGNORE跳过，在表名前加入
* 删除数据（不要忽略WHERE）
  * DELETE FROM 表名 WHERE
  * 如果想从表中删除所有行，不要使用DELETE，可使用TRUNCATE

* MySQL没有撤销操作，要非常小心的使用更新删除

### 19、创建和操纵表

* CREATE TABLE 表名()编码

* NOT NULL：不允许为空

* 使用AUTO_INCREMENT：自增+1，可覆盖，只要是唯一的；可以使用` SELECT last_insert_id()`获取自增长的id

* 指定默认值：DEFAULT，不允许使用函数作为默认值，多使用默认值而不是NULL值

* 引擎类型：引擎隐藏在DBMS内，多数时候不需要过多的关注它，二MySQL与其他DBMS不一样，它具有多种引擎，这些引擎全部都能执行CREATE TABLE和SELECT的命令

  * 如果忽略ENGINE=语句，则使用默认引擎（MYISAM）
  * InnoDB是一个可靠的事物处理引擎，它不支持全文搜索
  * MEMORY在功能等同于MyISAM，但由于数据存储在内存中，速度很快（特别适合临时表）
  * MyISAM是一个性能极高的引擎，它支持全文搜索，但不支持事物处理
  * 引擎类型可以混用，但有一个缺陷，外健不能跨引擎

* 更新表：ALTER TABLE语句。有数据的情况表就不应该被更新。

  * ` ALTER TABLE vendors ADD vend_phone CHAR(20)` 给表增加一列

  * ` ALTER TABLE vendors DROP COLUMN vend_phone` 删除表的一列

  * 常见的一种用途是定义外健：

    ``` mysql
     ALTER TABLE orderitems ADD CONSTRAINT fk_orderitems_orders FOREIGN KEY orders (order_num)
    ```

  * 更改表前应该做好备份，修改后无法撤回

* 删除表：` DROP TABLE 表名`

* 重命名表：` RENAME TABLE 原表名 TO 重命名表名`

### 20、使用视图

* 视图是虚拟的表。与包含数据的表不同，视图只包含使用时动态检索数据的查询
* 为什么使用视图
  * 重用SQL语句
  * 简化复杂的SQL操作。在编写查询后，可以方便地冲用它而不必知道的基本查询细节。
  * 使用表的组成部分而不是整个表
  * 保护数据。可以给用户授予表的特定部分的访问权限而不是整个表的访问权限
  * 更改数据格式和表示。视图可返回与底层表的表示和格式不同的数据
  * 在部署使用大量视图的应用前，应该进行测试。
* 视图的规则和限制
  * 与表一样，视图必须是为一命名
  * 对于可以创建的视图数目没有限制
  * 为了创建视图，必须具有足够的访问权限。这些权限通常由数据库管理元授予
  * 视图可以嵌套，即可以利用从其他视图中检索数据的查询来构造一个视图
  * ORDER BY可以用在视图中，但如果从该视图的检索数据SELECT中也含有ORDER BY，那么该视图中的ORDER BY将被覆盖
  * 视图不能索引，也不能有关联的触发器和默认值
  * 视图可以和表一起使用
* 使用视图
  * 视图用CREATE VIEW语句来创建
  * 使用SHOW CREATE VIEW viewname来查看创建视图的语句
  * 用DROP删除视图，其语法为DROP VIEW viewname
  * 更新视图是，可以先用DROP在用CREATE，也可以直接用CREATE OR REPLACE VIEW。
* 更新视图：通常，视图是可更新的，修改视图将作用于基表，但如果视图定义中有一下操作则不能进行视图更新：
  * 分组
  * 联结
  * 子查询
  * 并
  * 聚集函数
  * DISTINCT
  * 导出（计算）列

### 21、使用存储过程

* 存储过程简单来说既是为以后的使用而保存的一条或多条MySQL语句的集合。可以将其视为批文件，虽然它们的作用不仅限于批处理

* 为什么要使用存储过程

  * 通过把处理封装在容易使用的单元中，简化复杂的操作
  * 不要求反复建立一系列处理步骤，保证了数据的完整性。防止不同的开发人员所使用的代码不同
  * 简化变动的管理
  * 提高性能。因为使用存储过程比使用单独的SQL语句要快
  * 存在一些只能用单个请求中的MySQL语速和特性，存储过程可以使用它们来编写功能更强更灵活的代码
  * 一般来熟说，存储过程的编写比基本的SQL语句复杂，编写存储过程需要更高的技能，更丰富的经验

* 使用存储过程

  * 执行存储过程：MySQL执行存储过程的语句为CALL。CALL接收存储过程的名字以及需要传递它的参数。如

    ```mysql
    CALL productpricing(@pricelow,@pricehigh,@priceaverage)
    ```

  * 创建存储过程

    ```mysql
    BEGIN
    	SELECT AVG(prod_price) AS priceaverage FROM products;
    END;
    ```

  * 可使用DELIMITER来修改分隔符

  * 删除存储过程

    ```mysql
    DROP PROCEDURE productpricing;
    ```

  * IF EXISTS的使用

  * 使用参数

    ```mysql
    #创建有参数的存储过程
    CREATE PROCEDURE productpricing(
    	OUT pl DECIMAL(8,2),
    	OUT ph DECIMAL(8,2),
    	OUT pa DECIMAL(8,2)
    )
    BEGIN
    	SELECT MIN(prod_price) INTO pl FROM products;
    	SELECT MAX(prod_price) INTO ph FROM products;
    	SELECT AVG(prod_price) INTO pa FROM products;
    END;
    #out返回值参数，INTO为输入值
    CALL productpricing1(@pricelow,@pricehigh,@priceaverage)
    #输出参数
    SELECT @pricelow
    ```

    ```mysql
    #创建存储过程
    CREATE PROCEDURE ordertotal(
    	IN onumber INT,
    	OUT ototal DECIMAL(8,2)
    )
    BEGIN
    	SELECT SUM(item_price*quantity) FROM orderitems WHERE order_num = onumber INTO ototal;
    END
    #执行过程，IN的参数为输入值
    CALL ordertotal(20005,@total)
    #输出参数
    SELECT @total
    #若需要另一个订单显示
    CALL ordertotal(20009,@total)
    ```

  * DECLARE定义局部变量

  * IF语句和ELSEIF、ELSE的使用

* 检查存储过程

  * ` SHOW CREATE PROCEDURE ordertotal`

### 22、使用游标

* 有时需要在检索出来的行中前进或后退一行或多行。这就是使用游标的原因。游标（cursor）是一个存储在MySQL服务器上的数据库查询，它不是一条SELECT语句，而是被该语句检索粗来的结果集。在存储游标之后，应用程序可以根据需要滚动或浏览其中的数据。

* 使用游标：

  * 在能够使用游标前，必须声明（定义）它，在这个过程实际上没有检索数据，它只是定义要使用的SELECT语句
  * 一旦声明后，必须打开游标以供使用，这个过程用前面定义的SELECT语句把数据实际检索出来
  * 对于填有数据的游标，根据需要取出（检索）各行
  * 在结束游标使用时，必须关闭游标

* 创建游标

  * 游标DECLARE语句创建，并定义相应的SELECT语句，根据需要带WHERE和其他子句

    ```mysql
    CREATE PROCEDURE processorders()
    BEGIN
    	DECLARE ordernumbers CURSOR
    	FOR
    	SELECT order_num FROM orders;
    END
    ```

  * 打开和关闭游标

    * 游标用OPEN CURSOR语句来打开

      ```mysql
      #打开游标
      OPEN ordernumbers
      #关闭游标，但是如果你不明确关闭游标，MySQL将会在到达END语句时自动关闭它
      CLOSE ordernumbers
      ```

  * 使用游标

    * 使用FETCH语句分别访问它的每一行。FETCH指定检索什么数据（所需的列），检索出来的数据存储在什么地方，它还向前移动游标中的内部行指针，使下一条FETCH语句检索下一行
    * DECLARE语句定义的局部变量必须在定义人意游标或句柄之前定义，而句柄必须在游标之后定义
    * MySQL还支持for循环，直到使用LEAVE语句手动退出为止，通常REPEAT更适合游标

### 23、使用触发器

* 触发器是MySQL是响应对数据库数据修改时自动执行的一条SQL语句（或位于BEGIN和END语句之间的一组语句）

* 创建触发器，需要给出4条信息

  1. 唯一的触发器名

  2. 触发器关联的表

  3. 触发器应该响应的活动

  4. 触发器何时执行（处理之前或之后）

  * 触发器在名必须在每个表中是唯一的，两个表可以具有相同名字的触发器

  * ```mysql
    #AFTER INSERT:在每条插入语句之后执行
    #FOR EACH ROW：每个插入都执行
    #文本‘Product added’将对每个插入行显示一次
    CREATE TRIGGER newproduct AFTER INSERT ON products FOR EACH ROW SELECT 'Product added'
    ```

  * 触发器仅支持表，单一触发器不能与多个事件或多个表关联，所以你需要对INSERT和UPDATE操作执行的触发器就应该是两个

  * 每个表最多有6个触发器：INSERT，UPDATE，DELETE的前后

  * 如果BEFORE触发器失败，则MySQL不执行请求的操作；如果BEFORE触发器或语句本身失败，MySQL不执行AFTER触发器。

* 删除触发器

  ```mysql
  DROP TRIGGER newproduct
  ```

* 使用触发器

  * 在INSERT触发器的代码内，可饮用一个为NEW的虚拟表，访问被插入的行

  * 在BEFORE INSERT触发器中，NEW中的值也可以被更新（允许更改被插入的值）

  * 对于AUTO_INCREMENT列，NEW在INSERT执行之前包含0，在INSERT之后包含新的自动生成值

    ```mysql
    CREATE TRIGGER neworder AFTER INSERT ON orders FOR EACH ROW SELECT NEW.order_num INTO @result;
    
    ```

  * DELETE触发器，你可以一个名为OLD的虚拟表，访问被删除的行

  * OLD中的值全都是只读，不能更新

    ```mysql
    #删除数据后将被删数据保存到archive_orders的存档表中
    CREATE TRIGGER deleteorder BEFORE DELETE ON orders FOR EACH ROW
    BEGIN
    	INSERT INTO archive_orders(order_num,order_date,cust_id,) values(OLD.order_num,OLD.order_date,OLD.cust_id);
    ```

  * UPDATE触发器代码中，你可以饮用一个名为OLD的虚拟表访问以前的值，引用一个NEW的虚拟表访问新更新的值

  * 在BEFORE UPDATE触发器中，NEW中的值可能也被更新（允许更改将要用于UPDATE语句中的值）

  * OLD中的值全都是只读，不能更新

* 不能从触发器内条用存储过程，所需的存储过程代码需要复制到触发器内

### 24、管理事务处理

* 并非所有引擎支持数据处理，MyISAM和InnoDB是两种最常使用的引擎，前者不支持明确的事务处理管理，而后者支持
* 事务处理可以用来维护数据库的完整性，它保证成批的MySQL操作要么执行要么完全不执行。
* 关于事务处理需要知道的几个术语：
  * 事务（transaction）指一组SQL语句
  * 回退（rollback）值撤销指定SQL语句的过程
  * 提交（commit）指将为存储的SQL语句结果写入数据库表
  * 保留点（savepoint）指事务处理中设置的临时占位符（place-holder），你可以对它发布回退（与回退整个事务处理不同）。
* 控制事务处理
  * 使用ROLLBACK：只能在一个事务处理内使用（在执行一条START TRANSACTION命令之后）
  * 使用COMMIT：平常我们的提交都是自动进行的，也就是隐含提交，在事务处理块中，提交不会隐含地进行。为进行明确的提交，使用COMMIT语句。
* 使用保留点：简单的ROLLBACK和COMMIT语句就可以写入或撤销整个事务处理，但是，只是对简单的事务处理才能这样做，更复杂的事务处理可能需要部分提交和回退。
  * SAVEPOINT delete1；ROBACK TO delete1
  * 事务处理完成后会自动释放保留点，也可以用RELEASE SAVEPOINT明确地释放保留点
* 更改默认提交行为
  * SET autocommit = 0
  * SET autocommit = 1

### 25、全球化和本地化

* 在讨论多种语言和字符集时，将会遇到一下重要术语

  * **字符集**：字母和符号的集合
  * **编码**：某个字符集成员的内部表示
  * **校对**：为规定字符如何表的合集

* 使用字符集和校对顺序

  * 查看所支持的完整列表：` SHOW CHARACTER SET` 

  * ```mysql
    #指定默认字符集和校对
    CREATE TABLE mytable()DEFAULT CHARACTER SET HEBREW
    COLLATE hebrew_general_ci
    ```

  * 可对指定列指定字符集和校对

### 26、安全管理

* 在日常工作中最好少使用root用户，应该创建一系列的账号。

* 管理用户：MySQL的用户账号和信息都存储在名为mysql的MySQL数据库中

  * ```mysql
    USE mysql
    SELECT user FROM user
    ```

* 创建用户账户

  * ```mysql
    #IDENTIFIED指定密码口令
    CREATE USER ben IDENTIFIED BY 'Sumonton0@.'
    ```

  * 使用GRANT语句也可以创建用户，但一般使用CREATE USER。此外也可以用add，但不推荐

  * 重新命名用户账号` RENAME USER ben TO bforta`

* 删除一个用户账户

  * ` DROP USER ben`

* 设置访问权限

  * 新创建的用户不能看到数据，不能执行任何数据库的操作

    ```mysql
    SHOW GRANTS FOR smc
    ```

  * 使用GRANT赋予权限：要授予的权限；被授予访问权限的数据库或表；用户名

    ```mysql
    GRANT SELECT on rookie.* TO smc
    ```

  * GRANT的反操作为REVOKE，用它来撤销特定的权限

    ```mysql
    REVOKE SELECT ON rookie.* FROM smc
    ```

  * GRANT和REVOKE刻字啊几个层次上控制访问权限：

    * 整个服务器，使用GRANT ALL和REVODE ALL；
    * 整个数据库，使用ON database.*
    * 待定的表，使用ON database.table
    * 特定的列
    * 特定的存储过程

  * 简化多次授权：可通过列出各权限并用逗号分隔，将多条GRANT语句串在一起

* 更改密码口令

  * ```mysql
    #mysql 8弃用
    SET PASSWORD FOR smc = Password('Sumonton1.@')
    
    alter user 'smc'@'localhost' identified by 'Sumonton1.@';
    ```

### 27、数据库维护

* 备份数据
* CHECK TABLE用来正对许多问题对表进行检查。在MyISAM表上还对索引进行检查。CHECK TABLE支持依稀了的用于MyISAM表的方式。CHANGED检查自最后一次检查以来改动过的表。EXTENDED执行最彻底的检查。FAST只检查未正常关闭的表，MEDIUM检查所有被删除的链接并进行键检验，QUICK只进行快速扫描。
* 查看日志文件
  * 错误日志：通常名为hostname.err,位于data目录中。此日志名可用--log-error命令行选项更改
  * 查询日志：记录所有MySQL活动，hostname.log,此名字可用--log命令行选项更改
  * 二进制日志：记录更新过的数据或者可能更新过的数据的所有语句，通常名为hostname-bin，此名字用--log-bin命令行选项更改。
  * 缓慢查询日志：记录执行缓慢的任何查询。这个日志在确定数据库何处需要优化有用。此日志通常名为hostname-slow.log，此名字可以用--log-slow-queries命令行选项更改
  * 在使用日志时，可以使用FLUSH LOGS语句刷新和重新开始所有日志文件

### 28、改善性能

* MySQL使用一系列的默认设置预先配置的，从这些设置开始通常是很好的。但过一段时间后你可能需要调整内存分配、缓冲区大小等。（为查看当前设置，可食用SHOW VARIABLES；和SHOW STATUS；）

* MySQL一个多用户多线程的DBMS，换言之，它经常同时执行多个任务。如果这些任务中的某个执行缓慢，则所有的请求都会执行缓慢。如果你遇到显著的性能不良，可使用SHOW PROCESSLIST显示所有活动。你可以用KILL命令来终结某个特定的进程。

* 总是不止一种方法编写同一条SELECT语句，应该实验联结、并、子查询等，找出最佳的方法

* 使用EXPLAIN语句让MySQL解释它如何执行一条SELECT语句。

* 一般来说，存储过程执行的比一条一条地执行其中的歌条MySQL语句快

* 应该总是使用正确的数据类型

* 绝不要检索比需求还要多的数据

* 在导入数据时应该关闭自动提交。你可能还要删除索引（包括FULLTEXT索引），然后在导入完成后再重建它们

* 必须索引数据表以改善它们数据检索的性能。确定索引什么不是一件微不足道的任务，需要分析使用的SELECT语句以找出重复的WHERE和ORDER BY子句。如果一个简单的WHERE子句返回结果所花的时间太长，则可以断定其中使用的列（或几个列）就是需要索引的对象

* 你的SELECT语句中有一系列复杂的OR条件吗？可以使用东条SELECT语句和联结它们的UNION语句，你能看到极大的性能改善

* 索引改善数据检索的能力，但损害数据插入、删除和更新的性能。

* Like很慢、一般来说最好是使用FULLTEXT而不是LIKE

  
