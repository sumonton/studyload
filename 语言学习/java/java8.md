

### 1、通过参数化传递方法

* 应对不断变化的需求，能够动态的告诉某方法去执行某个方法。
* 遵守DRY（Dont't Repaet Youself）原则
* 多种行为一种参数
```java
    public interface ApplePredicate{
        bollean test(Apple apple)
    }
    public class  AppleHeavyWeightPredicate implements ApplePredicate{
        public boolean test(Apple apple){ 
            return apple.getWeight() > 150; 
        }
    }
    public class AppleGreenColorPredicate implements ApplePredicate{
        public boolean test(Apple apple){ 
            return "green".equals(apple.getColor()); 
        } 
    }

    public static List<Apple> filterApples(List<Apple> inventory, 
    ApplePredicate p){ 
        List<Apple> result = new ArrayList<>(); 
        for(Apple apple: inventory){ 
            if(p.test(apple)){ 
                result.add(apple); 
            } 
        } 
        return result; 
    }   
```
* 使用匿名类
```java
    List<Apple> redApples = filterApples(inventory, new ApplePredicate() { 
        public boolean test(Apple a){ 
            return "red".equals(a.getColor()); 
        } 
    });
```
* 使用Lambda表达式
```java
    List<Apple> result = filterApples(inventory, (Apple apple) -> "red".equals(apple.getColor()));
```
* 是List类型抽象化，即泛型
* List自带sort()排序方法
```java
    public interface Comparator<T> { 
        public int compare(T o1, T o2); 
    }
    inventory.sort(new Comparator<Apple>() { 
        public int compare(Apple a1, Apple a2){ 
            return a1.getWeight().compareTo(a2.getWeight()); 
        } 
    });
```

### 2、Lambba表达式
* 
```java 
(Apple a1,Apple a2) ->a1.getWeight().compareTo(a2.getWeight())
```
* 参数列表
* 箭头：把参数列表和Lambda主题分隔开
* Lambda主体
  * () -> {} 这个lambda没有参数，返回void。类似于空方法
  * () -> "semonten" 这个lambda没有参数，返回字符串
  * () -> {return "semonten"} 这个lambda没有参数，并返回字符串
  * (Interger i) -> return "semonten" + i 这个lambada有一个整型参数i，但无效，因为renturn是控制流语句，必须用"{}"
  * (String s) -> {"semonton";} "semonton"是一个表达式，而不是一个语句，所以不用“{}”
* 在哪使用Lambda表达式
  * 函数式接口：只要接口指定义了一个抽象方法，那么它就是函数式接口
  ```java
    //用Lambda定义个r1的函数式接口
    Runnable r1 = () -> System.out.println("Hello World1");
  
    //使用匿名类定义个函数式接口
    Runnable r2 = new Runnable(){
        public void run(){
            System.out.print("Hello World2");
        }
    };
    public static void process(Runnable r){
        r.run();
    }
    process(r1); //打印Hello World1
    process(r2); //打印Hello World2
  ```
  * Lambda表达式可以被赋给一个变量或传递给一个函数式接口作为参数方法。如Runnable是一个只有run的抽象方法的函数式接口，则代码的可改为：
  ```java
    public static void process(Runnalble r){
        r.run
    }
    process(() -> System.out.println("Hello World"));
  ```
  * @FunctionalInterface 注解表示这个接口会被设计成函数式接口，如果不是函数式接口编译期将会提示错误。
* java 7中带资源声明的try语句（try-with-resource-statement）：在try中声明的资源会在会在try执行结束后自动调用其关闭方法，以结束资源。try也可以带有catch和finally代码块，但是在执行前会close()掉资源。
* 几个函数式接口
  * predicate<T>函数式接口提供了一个test的方法，返回boolean类型。
  ```java
    public static <T> List<T> filter(List<T> list,Predicate<T> p){
        List<T> result =new ArrayList<>();
        for (T item:list
             ) {
            if (p.test(item)){
                result.add(item);
            }
        }
        return result;
    }
    String[] array = {"","aaa","","bbb"};
    Predicate<String> noEmpty = (String s) -> !s.isEmpty(); //用Lambda实现函数式接口的方法
    System.out.println(filter(Arrays.asList(array),noEmpty).toString());
  ```
  * Consumer<T>函数式接口提供了一个accept的抽象方法，没有返回，你如果需要需要访问T对象，并对它执行一些操作，可以使用这个方法。
  ```java
    public static <T> void action(T s, Consumer<T> c){
        c.accept(s);
    }
    action("a,b,c,d",(String s) -> {
        for (String a:s.split(",")
            ) {
            System.out.println(a);
        }
    });
  ```
  * Function<T,R>函数式接口提供一个叫做apply的方法，它接受一个T的泛型对象，返回R型的对象
  ```java
    public static <T,R> List<R> map(List<T> s, Function<T,R> f){
        List<R> r = new ArrayList<>();
        for (T t:s
             ) {
            r.add(f.apply(t));
        }
        return r;
    }
    System.out.println(map(Arrays.asList("aaaa","bbb"),(String s) -> s.length()));
  ```
  *** 自己的感受：lambda就是对函数式接口的方法的实现 ***

* 实际类型：函数结构时否有返回参数，返回的参数类型，Lambda在表示时需要一致。
* Lambda使用局部变量：用户可以使用实例变量和静态变量，因为它们都在堆中，所有线程都是共享。但在使用局部变量时要定义局部为final，否则会造成不安全，因为局部便是保存在栈中，java 在访问时不是访问原始变量，而是访问它的副本。

### 3、函数式数据处理
* 流是什么：它允许你以声明式的方式处理数据集合（通过查询语句，而不是方法）。此外，流可以可以透明的并行处理，而不用额外线程，
* 流与集合：集合相当于在内存中的数据结构，每个元素都要先算出来才能加到集合里去，要生成完后才能对集合进行操作。而流相当于按需生产，当需要时它在提供。集合就像DVD，已经整好再看，而流就相当于直播，只需要网络不断给出需要播放的那几帧。
* 流只能遍历一次，如果遍历完后，要想在遍历只能从数据源再获取一次数据。
* 集合在遍历时需要用户去迭代，这时相当于外部迭代。而流不断的给出你想要的数据，相当于内部帮你迭代了，称为内部迭代，但需要在具体操作时将预定好能够隐藏迭代的操作列表，如filter和map等，这样才能内部迭代生效。
* 可以连接起来的流操作（即返回的也是流）称为中间操作，而结束流的称为终端操作。
* 流的使用三个阶段：数据源->中间操作->终端操作
* stream的操作 
    ![](resource/img/截屏2022-03-05%2014.22.37.png)
### 4、使用流
* 筛选和切片

  * 利用filter来筛选，返回的是一个参数

  ```java
    menu.stream().filter(Dish::isVegetable)
  ```

  * distinct() 让流无重复
  * limit(n) 截断流，返回指定个数
  * skip(n) 跳过指定个数

* 映射：从对象中获取某些信息，可通过map和filterMap来实现。

  * 流支持map，它会接受一个函数，然后将函数应用在每个元素上，并将其映射成一个新的元素

  ```java
    List<String> words = Arrays.asList("Java 8", "Lambdas", "In", "Action"); 
    List<Integer> wordLengths = words.stream() 
    .map(String::length) 
    .collect(toList());
  ```

  * 使用flatMap，各个数组并不是分别映射成一个流，而是把它们映射成流的内容，即把生成的单个流都扁平化为一个流。

  ```java
    List<String> uniqueCharacters = 
    words.stream() 
    .map(w -> w.split("")) 
    .flatMap(Arrays::stream) 
    .distinct() 
    .collect(Collectors.toList());
  ```

  ![](resource/img/截屏2022-03-05 14.53.39.png)

* 查找与匹配

  * anyMatch（一个匹配），allMatch（所有匹配），noMathc（没有匹配）
  * 短路求值：即只取所需
  * findany()和findFirst()等

* 归约：reduce()的各个方法

* 数值流

### 5、用流收集数据，collect，collector

* 归约和汇总

  * ```java
    IntSummaryStatistics menuStatistics =  menu.stream().collect(summarizingInt(Dish::getCalories));
    
    //打印menuStatisticobject会得到以下输出：
    IntSummaryStatistics{count=9, sum=4300, min=120, 
     average=477.777778, max=800}
    ```

  * 连接字符串：joining()

* 分组

  * 单级分组
  * 多级分组
  * 按子组收集数据

* 分区

* 静态方法汇总![](/Users/smc/Desktop/smc/语言学习/java/resource/img/iShot2022-03-05 21.47.32.png)

### 6、并行数据处理与性能

* 并行流：可以通过parallelStream把集合转化为并行流。并行流就是把一个内容分为几个模块，并用几个线程对每个模块执行。

  * 将顺序流转化为并行流： 

    ```java
    Stream.iterate(1L,i->i+1).limit(n).parallel().reduce(0L,Long::sum)
    ```

    ![](/Users/smc/Desktop/smc/语言学习/java/resource/img/截屏2022-03-06 11.44.50.png)

  * 只需要对并行流调用sequantial()就能转换为顺序流

  * 并行流的线程来自默认的ForkJoinPool，默认的线程数量就是处理器数量

  * 错误的使用并行，就是去更改某些共享的数据

  * 自动装箱和拆箱的操作会影响并行流的性能

  * 较小的数据流使用并行是个不明智的决定

  * 流的数据源和可分解性

    * ArrayList ：极佳
    * LinkedList :差
    * IntStream : 极佳
    * Stream.Iterate:差
    * HashSet:好
    * TreeSet：好

* 分支/合并结构（java7 引入）

  * 使用RecursiveTask:要把任务提交的池，必须创建RecursiveTask<R>的一个子类，R表示产生结果的类型，若不返回类型，则是RecursiveAction类型。要实现它唯一的抽象方法compute（基本逻辑是若不能分支，则顺序执行，若能则进行分支，等待所有分支运行结束再进行合并）

* Spliterator：和iterate一样是用来遍历数据源的元素，但spliterator是为了并行执行而设计的

### 7、重构、测试和调试

* 从匿名类到Lambda表达式的转换

  ```java
  Runnable r1 =new Runnable() {
    @Override
    public void run() {
      System.out.println("匿名类");
    }
  };
  Runnable r2 = () -> System.out.println("Lambda");
  ```

  * 匿名类和Lambda的this和super的含义是不同的。在匿名类中this表示本身，而在Lambda中表示包含类

    ```java
    int a =10;
    Runnable r1 =new Runnable() {
      @Override
      public void run() {
        int a = 20; //能通过
        System.out.println("匿名类");
      }
    };
    Runnable r2 = () -> {
      int a = 20; //编译错误
      System.out.println("Lambda");
    };
    ```

  * 当涉及重载的上下文时，Lambda可能会无法识别，因为Lambda时通过上下文来识别的，而匿名类是在初始化时就确认了。

* 从Lambda表示式到方法引用的转换：因为方法名更能让人识别代码的意图

  ```java
  Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = 
  menu.stream().collect(groupingBy(dish -> {
    if (dish.getCalories() <= 400) return CaloricLevel.DIET; 
    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL; 
    else return CaloricLevel.FAT; 
  }));
  
  //转化为
  Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = 			menu.stream().collect(groupingBy(Dish::getCaloricLevel));//Dish::getCaloricLevel方法引用
  //在Dish类中添加方法
  public class Dish{ 
    … 
     public CaloricLevel getCaloricLevel(){
      if (this.getCalories() <= 400) return CaloricLevel.DIET; 
      else if (this.getCalories() <= 700) return CaloricLevel.NORMAL; 
      else return CaloricLevel.FAT; 
   	} 
  }
  ```

  * 尽量考虑使用静态辅助方法，如comparing，maxBy。这些方法在设计之初就考虑会与方法引用合用。

    ```java
    inventory.sort(
      (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
    
    inventory.sort(comparing(Apple::getWeight));
    ```

  * 很多通用的归约操作，比如：sum、maximum，都有内建的方法与方法引用结合使用。使用Collector的接口要更直观的多。

    ```java
    int totalCalories = 
     menu.stream().map(Dish::getCalories) 
     .reduce(0, (c1, c2) -> c1 + c2);
    int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
    ```

* 从命令式数据处理切换到Stream

  ```java
  List<String> dishNames = new ArrayList<>(); 
  for(Dish dish: menu){ 
    if(dish.getCalories() > 300){ 
      dishNames.add(dish.getName()); 
    } 
  }
  //利用Stream，使代码看起来更像是问题陈述
  menu.parallelStream() 
   .filter(d -> d.getCalories() > 300) 
   .map(Dish::getName) 
   .collect(toList());
  ```

  当在转化为Stream形式时有个问题，就是控制流语句break，return，continue

* 使用Lambda重构面向对象的设计模式

  * 策略模式

    ```java
    Validator numericValidator = new Validator((String s) -> s.matches("[a-z]+")); 
    boolean b1 = numericValidator.validate("aaaa"); 
    Validator lowerCaseValidator = new Validator((String s) -> s.matches("\\d+")); 
    boolean b2 = lowerCaseValidator.validate("bbbb");
    ```

  * 模版方法模式

    ```java
    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy){ 
     Customer c = Database.getCustomerWithId(id); 
     makeCustomerHappy.accept(c); 
    }
    new OnlineBankingLambda().processCustomer(1337, (Customer c) -> 
     System.out.println("Hello " + c.getName());
    ```

  * 观察者模式

    ```java
    f.registerObserver((String tweet) -> { 
     if(tweet != null && tweet.contains("money")){ 
     System.out.println("Breaking news in NY! " + tweet); 
     } 
    }); 
    f.registerObserver((String tweet) -> { 
     if(tweet != null && tweet.contains("queen")){ 
     System.out.println("Yet another news in London... " + tweet); 
     } 
    });
    ```

  * 责任链模式

    ```java
    UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
    UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda"); 
    Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing); 
    String result = pipeline.apply("Aren't labdas really sexy?!!");
    ```

  * 工厂模式

    ```java
    final static Map<String, Supplier<Product>> map = new HashMap<>(); 
    static { 
     map.put("loan", Loan::new); 
     map.put("stock", Stock::new); 
     map.put("bond", Bond::new); 
    }
    public static Product createProduct(String name){ 
     Supplier<Product> p = map.get(name); 
     if(p != null) return p.get(); 
     throw new IllegalArgumentException("No such product " + name); 
    }
    ```

* 调试：
  * 流提供peek方法，能将中间值输出到日志，是常用的调试方法

### 8、默认方法

* java8运行在接口内声明静态方法；java8接口引入了一个新功能默认方法，可是指定接口实现的默认方法，如果实现接口的类不实现该方法，则会默认继承接口的实现。
* 为了解决往接口添加新方法时实现类需要相应更改问题。
* 默认方法的使用模式：可选方法被多继承
* 解决多继承时有相同默认方法签名冲突：实现类的方法优先级最高；子接口的默认方法优先级高；显示调用哪一个默认方法

### 9、用Optional替代null

* Optional入门：变量存在时Optional只是对对象的简单包装，不存在时不建模成一个空的optional对象，由方法Optional.isEmpty()返回。创建Optional有多种方法：

  * ```java
    //声明一个空Optional
    Optional<Car> optCar = Optional.empty();
    ```

  * ```java
    //依据一个非空值创建
    Optional<Car> optCar = Optional.of(car);
    ```

  * ```java
    //可接受Null的Optional
    Optional<Car> optCar = Optional.ofNullable(car);
    ```

* FlatMap的使用：对Optional拆装（map会返回一个流，而faltMap会将流的内容来替代新生成的流）

* Optional对象无法序列化serializable

* 默认行为及解引用Optional对象

  * orElse()会在为空是返回默认值

* 两个Optional对象组合

* 使用filter剔除特定值

* Optional类的分类和概括

  ![](/Users/smc/Desktop/smc/语言学习/java/resource/img/iShot2022-03-06 16.39.22.png)

### 10、组合式异步编程

* Future接口：它建模一种异步运算，返回一个运算结果的引用，当运算结束后，这个引用会被返回调用方。比Thread更易用

  ```java
  ExecutorService excutor = Executors.newCachedThreadPool();
          Future<Double> future = excutor.submit(new Callable<Double>() {
              @Override
              public Double call() throws Exception {
                  return doSomething();
              }
          });
          doSomethingEles();
          try {
            Double result = future.get(1, TimeUnit.SECONDS);//获取运行结果，若无结果等待一秒放弃
          } catch (InterruptedException e) {
              e.printStackTrace();
          } catch (ExecutionException e) {
              e.printStackTrace();
          } catch (TimeoutException e) {
              e.printStackTrace();
          }
  ```

  

* Future接口的局限性：很难表述Future结果之间的依赖性。

* CompletableFuture（实现了Future接口）

  * 同步API：调用方需要等待被调用方结束运行，才能继续执行（阻塞式调用）

  * 异步API相反

  * 创建线程池，指定线程数

    ```java
    Executor executor =
                    Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                            new ThreadFactory() {
                                public Thread newThread(Runnable r) {
                                    Thread t = new Thread(r);
                                    t.setDaemon(true);
                                    return t;
                                }
                            });
    ```

  * thenCompose：连接操作

  * thenCombine：合并操作

  * thenAccept：接收异步线程结束返回的值

### 11、新的日期和时间API

* **LocalDate**、**LocalTime**、**Instant**、**Duration** 以及 **Period**

  * 使用 **LocalDate** 和**LocalTime** ：LocalDate是一个不可变对象，它只提供简单的日期，并不含单天的信息。另外也不附带任何时区相关的信息

    ```java
    LocalDate date = LocalDate.of(2014, 3, 18);//2014-03-18
    int year = date.getYear();//2014
    Month month = date.getMonth();//MARCH
    int day = date.getDayOfMonth();//18
    DayOfWeek dow = date.getDayOfWeek();//TUESDAY
    int len = date.lengthOfMonth();//31 (days in March)
    boolean leap = date.isLeapYear();//false (not a leap year)
    LocalDate today = LocalDate.now();//工厂方法获取当前日期
    //使用TemporalField读取LocalDate的值
    int year = date.get(ChronoField.YEAR); 
    int month = date.get(ChronoField.MONTH_OF_YEAR); 
    int day = date.get(ChronoField.DAY_OF_MONTH);
    ```

    LocalTime获取一天的时间

    ```java
    LocalTime time = LocalTime.of(13, 45, 20);//13:45:20
    int hour = time.getHour();//13
    int minute = time.getMinute();//45
    int second = time.getSecond();//20
    ```

    LocalDate和LocalTime都可以通过解析代表它们的字符串创建

    ```java
    LocalDate date = LocalDate.parse("2014-03-18"); 
    LocalTime time = LocalTime.parse("13:45:20");
    ```

  * 合并日期和时间：LocalDateTime复合类，是LocalDate和LocalTime的合体。它同时表示日期和时间，也不含时区信息，可以直接创建，也可以合并日期和时间创建。

    ```java
    // 2014-03-18T13:45:20
    LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20);
    LocalDateTime dt2 = LocalDateTime.of(date, time);
    LocalDateTime dt3 = date.atTime(13, 45, 20);
    LocalDateTime dt4 = date.atTime(time);
    LocalDateTime dt5 = time.atDate(date);
    //从LocalDateTime中提取LocalDate或者LocalTime
    LocalDate date1 = dt1.toLocalDate();//2014-03-18
    LocalTime time1 = dt1.toLocalTime();//13:45:20
    ```

  * 机器的日期和时间格式：Instant类对时间建模的方式，基本上它是以Unix元年时间（传统的设定为UTC时区1970年1月1日午夜时分）开始所经历的秒数进行计算

    ```java
    Instant.ofEpochSecond(3);
    Instant.ofEpochSecond(3, 0);
    Instant.ofEpochSecond(2, 1_000_000_000);// 秒之后再加上100万纳秒（1秒）
    Instant.ofEpochSecond(4, -1_000_000_000);//4秒之前的100万纳秒（1秒）
    ```

  * 定义Duration(持续时间)和Period

    ```java
    //创建两个LocalTimes对象、两个LocalDateTimes对象，或者两个Instant对象之间的duration
    Duration d1 = Duration.between(time1, time2); 
    Duration d1 = Duration.between(dateTime1, dateTime2); 
    Duration d2 = Duration.between(instant1, instant2);
    ```

    由于Duration类主要用于以秒和纳秒衡量时间的长短，你不能仅向between方法传递一个LocalDate对象做参数，如果你需要以年、月或者日的方式对多个时间单位建模，可以使用Period类。使用该类的工厂方法between，你可以使用得到两个LocalDate之间的时长

    ```java
    Period tenDays = Period.between(LocalDate.of(2014, 3, 8), LocalDate.of(2014, 3, 18));
    ```

    Duration和Period类都提供了很多非常方便的工厂类，直接创建对应的实例

    ```java
    Duration threeMinutes = Duration.ofMinutes(3); 
    Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES); 
    Period tenDays = Period.ofDays(10); 
    Period threeWeeks = Period.ofWeeks(3); 
    Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
    ```

    Duration类和Period类共享了很多相似的方法

    ![](/Users/smc/Desktop/smc/语言学习/java/resource/img/iShot2022-03-06 21.15.26.png)

* 操纵、解析和格式化时间

  * 如果你已经有一个LocalDate对象，想要创建它的一个修改版，最直接也最简单的方法是使用withAttribute方法。

    ```java
    //都声明于Temporal
    LocalDate date1 = LocalDate.of(2014, 3, 18); //2014-03-18
    LocalDate date2 = date1.withYear(2011); //2011-03-18
    LocalDate date3 = date2.withDayOfMonth(25); //2011-03-25
    LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 9);//2011-09-25
    //以相对方式修改LocalDate对象的属性
    LocalDate date2 = date1.plusWeeks(1);//2014-03-25
    LocalDate date3 = date2.minusYears(3);//2011-03-25
    LocalDate date4 = date3.plus(6, ChronoUnit.MONTHS);//2011-09-25
    ```

    ![](/Users/smc/Desktop/smc/语言学习/java/resource/img/截屏2022-03-06 21.22.43.png)

  * 使用TemplalAdjuster：有的时候，你需要进行一些更加复杂的操作，比如，将日期调整到下个周日、下个工作日，或者是本月的最后一天。这时，你可以使用重载版本的with方法，向其传递一个提供了更多定制化选择的TemporalAdjuster对象，更加灵活地处理日期。TemporalAdjustor是函数式接口

    ```java
    //使用预定义的TemporalAdjuster
    import static java.time.temporal.TemporalAdjusters.*; 
    LocalDate date1 = LocalDate.of(2014, 3, 18); 
    LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY)); //2014-03-23
    LocalDate date3 = date2.with(lastDayOfMonth());//2014-03-31
    ```

    ![](/Users/smc/Desktop/smc/语言学习/java/resource/img/iShot2022-03-06 21.27.11.png)

  * 处理日期和时间对象时，格式化以及解析日期时间对象是另一个非常重要的功能。新的java.time.format包就是特别为这个目的而设计的。

    ```java
    LocalDate date = LocalDate.of(2014, 3, 18);
    String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE); //20140318
    String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);//2014-03-18
    //可以使用工厂方法parse达到重创该日期对象的目的
    LocalDate date1 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE); 
    LocalDate date2 = LocalDate.parse("2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE);
    //DateTimeFormatter类还支持一个静态工厂方法，它可以按照某个特定的模式创建格式器
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate date1 = LocalDate.of(2014, 3, 18); 
    String formattedDate = date1.format(formatter); 
    LocalDate date2 = LocalDate.parse(formattedDate, formatter);
    //ofPattern方法也提供了一个重载的版本，使用它你可以创建某个Locale的格式器
    DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN); 
    LocalDate date1 = LocalDate.of(2014, 3, 18); 
    String formattedDate = date.format(italianFormatter); // 18. marzo 2014 
    LocalDate date2 = LocalDate.parse(formattedDate, italianFormatter);
    //更加细粒度的控制，DateTimeFormatterBuilder类还提供了更复杂的格式器，你可以选择恰当的方法，一步一步地构造自己的格式器
    DateTimeFormatter italianFormatter = new DateTimeFormatterBuilder() 
     .appendText(ChronoField.DAY_OF_MONTH) 
     .appendLiteral(". ") 
     .appendText(ChronoField.MONTH_OF_YEAR) 
     .appendLiteral(" ") 
     .appendText(ChronoField.YEAR) 
     .parseCaseInsensitive() 
     .toFormatter(Locale.ITALIAN);
    ```

* 处理不同时区和历法

  * java.time.ZoneId

    ```java
    //为时间点添加时区信息
    ZoneId romeZone = ZoneId.of("Europe/Rome");
    //通过Java 8的新方法toZoneId将一个老的时区对象转换为ZoneId
    ZoneId zoneId = TimeZone.getDefault().toZoneId();
    //为时间点添加时区信息
    LocalDate date = LocalDate.of(2014, Month.MARCH, 18); 
    ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
    
    LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45); 
    ZonedDateTime zdt2 = dateTime.atZone(romeZone);
    
    Instant instant = Instant.now(); 
    ZonedDateTime zdt3 = instant.atZone(romeZone);
    
    //通过ZoneId，你还可以将LocalDateTime和Instant相互转换
    LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45); 
    Instant instantFromDateTime = dateTime.toInstant(romeZone);
    
    Instant instant = Instant.now(); 
    LocalDateTime timeFromInstant = LocalDateTime.ofInstant(instant, romeZone);
    
    ```

    

  * 利用和 UTC/格林尼治时间的固定偏差计算时区

    ```java
    //“纽约落后于伦敦5小时
    ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
    //创建这样的OffsetDateTime，它使用ISO-8601的历法系统，以相对于UTC/格林尼治时间的偏差方式表示日期时间。
    LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45); 
    OffsetDateTime dateTimeInNewYork = OffsetDateTime.of(date, newYorkOffset);
    ```

  * 使用别的日历系统:Java 8中另外还提供了4种其他的日历系统。这些日历系统中的每一个都有一个对应的日志类，分别是ThaiBuddhistDate、MinguoDate 、 JapaneseDate 以 及 HijrahDate

    ```java
    LocalDate date = LocalDate.of(2014, Month.MARCH, 18); 
    JapaneseDate japaneseDate = JapaneseDate.from(date);
    ```


### 12、实现和维护

* 如果一个方法既不修改它内嵌类的状态，也不修改其他对象的状态，使用return返回所有的计算结果，

  那么我们称其为纯粹的或者无副作用的

* 哪些因素会造成副作用

  * 除了构造器内的初始化操作，对类中数据结构的任何修改，包括字段的赋值操作
  * 抛出一个异常
  * 进行输入/输出操作，比如向一个文件写数据

* 函数式编程

  * 声明式编程：“要做什么”，更接近问题陈述
  * 当谈论“函数式”时，我们想说的其实是“像数学函数那样——没有副作用”
  * 如果程序有一定的副作用，不过该副作用不会为其他的调用者感知
  * 我们的准则是，被称为“函数式”的函数或方法都只能修改本地变量。除此之外，它引用的对象都应该是不可修改的对象
  * 抛出异常是不符合函数式编程的，所以有了optional
  * 引用透明性：“没有可感知的副作用”（不改变对调用者可见的变量、不进行I/O、不抛出异常）的这些限制都隐含着引用透明性。如果一个函数只要传递同样的参数值，总是返回同样的结果，那这个函数就是引用透明的
  * 面对对象编程和函数式编程（极端的面对对象编程）
  * 递归和迭代：递归-尾调优化
  * 尽量使用Stream取代迭代操作，从而避免变化带来的影响
  * 用递归可以取得迭代式的结构

### 13、函数式编程技巧

* 高阶函数

* 科里化

  ```java
  static double converter(double x, double f, double b) { 
   return x * f + b; 
  }
  
  //科里化
  static DoubleUnaryOperator curriedConverter(double f, double b){ 
   return (double x) -> x * f + b; 
  }
  DoubleUnaryOperator convertCtoF = curriedConverter(9.0/5, 32); 
  DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0); 
  DoubleUnaryOperator convertKmtoMi = curriedConverter(0.6214, 0);
  double gbp = convertUSDtoGBP.applyAsDouble(1000);
  ```

### 14、java8总结

* 回顾特性

  * 形势变化要求

    1. 代码具备并行运算的能力
    2. 更简洁地调度以显示风格处理数据的数据集合，这一趋势不断增长。这一风格与使用不变对象和集合相关，它们之后会进一步生成不变值。

  * 行为参数化：

    1. 传递一个Lambda表达式，即一段精简的代码片段 ` apple -> apple.getWeight() > 150`
    2. 传递一个方法引用，该方法引用指向了一个现有的方法` Apple::isHeavy`

    这些值具有类似Function<T, R>、Predicate<T>或者BiFunction<T, U, R>这样的类型，值的接收方可以通过apply、test或其他类似的方法执行这些方法。Lambda表达式自身是一个相当酷炫的概念，不过Java 8对它们的使用方式——将它们与全新的Stream API相结合，最终把它们推向了新一代Java的核心。

  * 流：如果你有一个数据量庞大的集合，你需要对这个集合应用三个操作，比如对这个集合中的对象进行映射，对其中的两个字段进行求和，这之后依据某种条件过滤出满足条件的和，最后对结果进行排序，即为得到结果你需要分三次遍历集合。Stream API则与之相反，它采用延迟算法将这些操作组成一个流水线，通过单次流遍历，一次性完成所有的操作。对于大型的数据集，这种操作方式要高效得多。不过，还有一些需要我们考虑的因素，比如内存缓存，数据集越大，越需要尽可能地减少遍历的次数。

  * **CompletableFuture** 

    1. Java从Java 5版本就提供了Future接口。Future对于充分利用多核处理能力是非常有益的，因为它允许一个任务在一个新的核上生成一个新的子线程，新生成的任务可以和原来的任务同时运行。原来的任务需要结果时，它可以通过get方法等待Future运行结束（生成其计算的结果值）。
    2. Completable-Future对于Future的意义就像Stream之于Collection

  * **Optional** :这个类允许你在代码中指定哪一个变量的值既可能是类型T的值，也可能是由静态方法Optional.empty表示的缺失值

  * 默认方法

### 面试题

* 1、java8有哪些新特心？
  * **Lambda 表达式** − Lambda 允许把函数作为一个方法的参数（函数作为参数传递到方法中）。
  * **方法引用** − 方法引用提供了非常有用的语法，可以直接引用已有Java类或对象（实例）的方法或构造器。与lambda联合使用，方法引用可以使语言的构造更紧凑简洁，减少冗余代码。
  * **默认方法** − 默认方法就是一个在接口里面有了一个实现的方法。
  * **新工具** − 新的编译工具，如：Nashorn引擎 jjs、 类依赖分析器jdeps。
  * **Stream API** −新添加的Stream API（java.util.stream） 把真正的函数式编程风格引入到Java中。
  * **Date Time API** − 加强对日期与时间的处理。
  * **Optional 类** − Optional 类已经成为 Java 8 类库的一部分，用来解决空指针异常。
  * **Nashorn, JavaScript 引擎** − Java 8提供了一个新的Nashorn javascript引擎，它允许我们在JVM上运行特定的javascript应用。
* 2、HashMap 在 jdk 1.7 和 1.8 的区别？
  * hashmap在java7之前是数组和链表，java8后是数组+链表+红黑树，在长度超过指定时间会使用红黑树





### 复习

* 1、断言：断言机制允许在测试期间向代码中插入一些检査语句。当代码发布时，这些插人的检测语句将会被自动地移走

  * ```java
    n = in.nextInt();
    //将 x 的实际值传递给 AssertionError 对象， 从而可以在后面显示出来。
    assert x >= 0 : x;
    ```

* 部署java应用程序
* 线程池：当有大量的短期线程时应考虑线程池，固定线程数，减少并行线程数量





### 易错点

* string是final修饰的，会将创建的变量放入字符串常量池，当再创建同样的字符串时，发现常量池中有则直接使用

* 导入java.util.*不能读取其子目录的类，因为如果java.util里面有个a类，java.util.regex里面也有个a类，我们若是要调用a类的方法或属性时，应该使用哪个a类呢。

* ![](https://raw.githubusercontent.com/sumonton/img/master/typora/202203132318362.png)

* 父类的静态代码块

  子类的静态代码块

  父类的构造方法

  子类的构造方法

* ![](https://raw.githubusercontent.com/sumonton/img/master/typora/202203151334278.png)

* Spring并没有为我们提供日志系统，我们需要使用AOP（面向方面编程）的方式，借助Spring与日志系统log4j实现我们自己的日志系统。

* char < short < int < float < double 不同类型运算结果类型向右边靠齐。

* 向上转型，父类的引用无法访问子类独有的方法，

* 算术右移：保留符号为进行右移；逻辑右移：无视符号位右移

* 数值型变量在默认情况下为Int型，byte和short型在计算时会自动转换为int型计算，结果也是int 型

* ConcurrentHashMap 使用segment来分段和管理锁，segment继承自ReentrantLock，因此ConcurrentHashMap使用ReentrantLock来保证线程安全

* volatile与synchronized的区别：

  volatile本质是在告诉jvm当前变量在寄存器中的值是不确定的,需要从主存中读取,synchronized则是锁定当前变量,只有当前线程可以访问该变量,其他线程被阻塞住.

  volatile仅能使用在变量级别,synchronized则可以使用在变量,方法.

  volatile仅能实现变量的修改可见性,但不具备原子特性,而synchronized则可以保证变量的修改可见性和原子性.

  volatile不会造成线程的阻塞,而synchronized可能会造成线程的阻塞.

  volatile标记的变量不会被编译器优化,而synchronized标记的变量可以被编译器优化.

* 一般情况下，采用ISO8859-1编码方式时，一个中文字符与一个英文字符一样只占1个字节；采用GB2312GBK编码方式时，一个中文字符占2个字节；而采用UTF-8编码方式时，一个中文字符会占3个字节
* 父类静态域——》子类静态域——》父类成员初始化——》父类构造块——》1**父类构造方法**——》2**子类成员初始化**——》子类构造块——》3**子类构造方法；**
* int类型与Integer类型比较时, 先将Integer拆箱, 再比较值
* 对于-128到127之间的数, Integer直接从数组中取
* 单目运算符后无法跟字面量
