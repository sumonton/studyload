# 二、鸿蒙基础-状态管理-美团外卖

## 自定义构建函数

### 1. 构建函数-[@Builder ]() 

如果你不想在直接抽象组件，ArkUI还提供了一种更**轻量**的UI元素复用机制 `@Builder`，可以将重复使用的UI元素抽象成一个方法，在 `build` 方法里调用。称之为**自定义构建函数**

- 用法- 使用@Builder修饰符修饰

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120006205.png)

```typescript
@Entry
@Component
struct BuilderCase02 {
  build() {
    Row() {
      Column() {
        Row() {
          Row() {
            Text("异常时间")
            Text("2023-12-12")
          }
          .width('100%')
          .justifyContent(FlexAlign.SpaceBetween)
          .padding({
            left: 15,
            right: 15
          })
          .borderRadius(8)
          .height(40)
          .backgroundColor(Color.White)
        }.padding({
          left: 10,
          right: 10
        })

      }
      .width('100%')
    }
    .height('100%')
    .backgroundColor('#ccc')
  }
}
```

假设你有N个这样的单个元素，但是重复的去写会浪费大量的代码，丧失代码的可读性，此时我们就可以使用

builder构建函数

1. 全局定义- @Builder function name () {}

```typescript
@Builder
function getCellContent(leftTitle: string, rightValue: string) {
  Row() {
    Row() {
      Text(leftTitle)
      Text(rightValue)
    }
    .width('100%')
    .justifyContent(FlexAlign.SpaceBetween)
    .padding({
      left: 15,
      right: 15
    })
    .borderRadius(8)
    .height(40)
    .backgroundColor(Color.White)
  }.padding({
    left: 10,
    right: 10
  })

}
```

- 在组件中使用

```typescript
  Column({ space: 10 }) {
        getCellContent("异常时间", "2023-12-12")
        getCellContent("异常位置", "回龙观")
        getCellContent("异常类型", "漏油")
      }
      .width('100%')
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120006230.png)



全局自定义函数的问题

- 全局的自定义构建函数可以被整个应用获取（**下一代可用-当前4.0暂不支持**），不允许使用this和bind方法。
- 如果不涉及组件状态变化，建议使用全局的自定义构建方法。
- 补一句-如果数据是响应式的-此时该函数不会自动渲染-哪怕是全局自定义函数，**不可被其他文件引用**

将数据声明为State响应式数据

```typescript
class CardClass {
  time: string = ""
  location: string = ""
  type: string = ""
}
@State formData: CardClass = {
    time: "2023-12-12",
    location: '回龙观',
    type: '漏油'
  }
```

传递数据，绑定为对应字段

```typescript
Column({ space: 10 }) {
        getCellContent("异常时间", this.formData.time)
        getCellContent("异常位置", this.formData.location)
        getCellContent("异常类型", this.formData.type)
        Button("修改数据").onClick(() => {
          this.formData.location = "望京"
        })
      }
      .width('100%')
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120006333.png)

我们发现，点击修饰是没有任何反应的，说明此时即使你用了State，但是此时的全局builder依然不更新

那怎么办？ 我们试试在组件内部定义

2. 组件内定义- 语法 @Builder name () {}

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120018871.png)

调用

```typescript
   this.getCellContent("异常时间", this.formData.time)
   this.getCellContent("异常位置", this.formData.location)
   this.getCellContent("异常类型", this.formData.type)
```



调用多了this，其他和全局属性一样，没有任何变化，此时我们发现修改数据依然没有任何变化



**why??????**

注意： 我们刚刚传过去的是什么类型，string是一个基础数据类型，它是按值传递的，不具备响应式更新的特点



- 总结

 全局Builder函数和组件Builder构建函数可以实现一种轻量级的UI复用，

 区别: 全局自定义构建函数不允许使用this，bind，它适合一种纯渲染的UI结构

​       组件内自定义Builder可以实现this调用

### 2. 构建函数-传参传递

自定义构建函数的参数传递有[按值传递](https://developer.harmonyos.com/cn/docs/documentation/doc-guides-V3/arkts-builder-0000001524176981-V3#section163841721135012)和[按引用传递](https://developer.harmonyos.com/cn/docs/documentation/doc-guides-V3/arkts-builder-0000001524176981-V3#section1522464044212)两种，均需遵守以下规则：

- 参数的类型必须与参数声明的类型一致，不允许undefined、null和返回undefined、null的表达式。
- 在自定义构建函数内部，不允许改变参数值。如果需要改变参数值，且同步回调用点，建议使用[@Link](https://developer.harmonyos.com/cn/docs/documentation/doc-guides-V3/arkts-link-0000001524297305-V3)。
- @Builder内UI语法遵循[UI语法规则](https://developer.harmonyos.com/cn/docs/documentation/doc-guides-V3/arkts-create-custom-components-0000001473537046-V3#section1150911733811)。

我们发现上一个案例，使用了string这种基础数据类型，即使它属于用State修饰的变量，也不会引起UI的变化

- 按引用传递参数时，传递的参数可为状态变量，且状态变量的改变会引起@Builder方法内的UI刷新。ArkUI提供**$$**作为按引用传递参数的范式。

```typescript
ABuilder( $$ : 类型 );
```

- 也就是我们需要在builder中传入一个对象， 该对象使用$$(可使用其他字符)的符号来修饰，此时数据具备响应式了



```typescript
class CellParams {
  leftTitle: string = ""
  rightValue: string = ""
}
@Builder
function getCellContent($$: CellParams) {
  Row() {
    Row() {
      Text($$.leftTitle)
      Text($$.rightValue)
    }
    .width('100%')
    .justifyContent(FlexAlign.SpaceBetween)
    .padding({
      left: 15,
      right: 15
    })
    .borderRadius(8)
    .height(40)
    .backgroundColor(Color.White)
  }.padding({
    left: 10,
    right: 10
  })

}
```

- 传值

```typescript
this.getCellContent({ leftTitle: '异常位置', rightValue: this.formData.location })
this.getCellContent({ leftTitle: '异常时间', rightValue: this.formData.time })
this.getCellContent({ leftTitle: '异常类型', rightValue: this.formData.type })
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120018316.png)



同样的，全局Builder同样支持这种用法

```typescript
@Entry
@Component
struct BuilderCase {
  @State formData: CardClass = {
    time: "2023-12-12",
    location: '回龙观',
    type: '漏油'
  }
  @Builder
  getCellContent($$: CellParams) {
    Row() {
      Row() {
        Text($$.leftTitle)
        Text($$.rightValue)
      }
      .width('100%')
      .justifyContent(FlexAlign.SpaceBetween)
      .padding({
        left: 15,
        right: 15
      })
      .borderRadius(8)
      .height(40)
      .backgroundColor(Color.White)
    }.padding({
      left: 10,
      right: 10
    })

  }
  build() {
    Row() {
      Column() {
        Column({ space: 10 }) {
          this.getCellContent({ leftTitle: '异常时间', rightValue: this.formData.time })
          this.getCellContent({ leftTitle: '异常位置', rightValue: this.formData.location })
          this.getCellContent({ leftTitle: '异常类型', rightValue: this.formData.type })
        }
        .width('100%')
        Button("修改数据").onClick(() => {
          this.formData.location = "望京"
        })
      }
      .width('100%')
    }
    .height('100%')
    .backgroundColor('#ccc')
  }
}

class CardClass {
  time: string = ""
  location: string = ""
  type: string = ""
}
class CellParams {
  leftTitle: string = ""
  rightValue: string = ""
}
@Builder
function getCellContent($$: CellParams  ) {
  Row() {
    Row() {
      Text($$.leftTitle)
      Text($$.rightValue)
    }
    .width('100%')
    .justifyContent(FlexAlign.SpaceBetween)
    .padding({
      left: 15,
      right: 15
    })
    .borderRadius(8)
    .height(40)
    .backgroundColor(Color.White)
  }.padding({
    left: 10,
    right: 10
  })

}
```



- 使用 `@Builder` 复用逻辑的时候，支持传参可以更灵活的渲染UI
- 参数可以使用`状态数据`，不过建议通过对象的方式传入 `@Builder`



### 3. 构建函数-简单示例

- 接下来，我们来灵活的使用builder完成这样一个示例，页面初始化后两秒后加载一个用户信息，然后使builder函数渲染出来

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120040317.png)



```typescript
class UserInfo {
  name: string = ""
  age: number = 0
  sex: "男" | "女" = "男"
  address: string = ""
}
class CaseCellParams {
  left: string = ""
  right: string = ""
}
@Entry
@Component
struct BuilderNoParams02 {
 @State userInfo: UserInfo  = {
    name: '',
    age: 0,
    sex: '男',
    address: ''
  }
  aboutToAppear() {
    setTimeout(() => {
      this.userInfo = {
        name: '老高',
        age: 34,
        sex: "男",
        address: '北京顺义'
      }
    }, 2000)
  }
  @Builder
  getContentItem ($$: CaseCellParams) {
    Row() {
      Row() {
        Text($$.left)
        Text($$.right)
      }
      .width('100%')
      .justifyContent(FlexAlign.SpaceBetween)
      .padding({
        left: 15,
        right: 15
      })
      .borderRadius(8)
      .height(40)
      .backgroundColor(Color.White)
    }.padding({
      left: 10,
      right: 10
    })
  }
@Builder
  getContent () {
    this.getContentItem({ left: "姓名", right: this.userInfo.name  })
    this.getContentItem({ left: "年龄", right: this.userInfo.age.toString()  })
    this.getContentItem({ left: "性别", right: this.userInfo.sex  })
    this.getContentItem({ left: "地址", right: this.userInfo.address  })
  }

  build() {
    Column() {
      Column({ space: 10 }){
        this.getContent()
      }
      .margin({
        top: 50
      })
    }
    .height('100%')
    .backgroundColor('#ccc')
  }
}
```

### 4. 构建函数-[@BuilderParam ]() 传递UI 

- Component可以抽提组件
- Builder可以实现轻量级的UI复用

 完善了吗？ 其实还不算，比如下面这个例子

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120040084.png)![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120040160.png)![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120040231.png)

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120040277.png)



大家发现没有，我们后面的神领物流项目中会有很多地方用到这种类似卡片Card的地方，但是里面的内容各有不同，怎么办？

 前端同学应该知道Vue里面有个叫做slot插槽的东西，就是可以传入自定义的结构，整体复用父组件的外观

ArkTS提供了一个叫做BuilderParam的修饰符，你可以在组件中定义这样一个函数属性，在使用组件时直接传入

- BuilderParam只能应用在Component组件中，不能使用Entry修饰的组件中使用

语法： @BuilderParam  name: () => void

- 声明一个HmCard组件

```typescript
@Component
struct  HMCard {
  @BuilderParam
  content: () => void
  build() {
    Column () {
      Text("卡片组件")
      Divider()
      Text("传入内容")
      if(this.content) {
        this.content()
      }
    }
  }
}
```

- 父组件调用传入

```typescript
@Entry
@Component
struct BuilderParamCase {

  @Builder
  getContent () {
    Row() {
      Text("插槽内容")
        .fontColor(Color.Red)
    }
  }
  build() {
    Row() {
      Column() {
        HMCard({ content: this.getContent })
      }
      .width('100%')
    }
    .height('100%')
  }
}
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120040332.png)



需要注意的是，传入的函数必须是使用Builder修饰符修饰的

BuilderParams类似于 Vue中的插槽

1. 子组件中定义一个用BuilderParam修饰的函数
2. 父组件需要给子组件传入一个用Builder修饰的函数来赋值给子组件
3. 子组件要在想要显示插槽的地方来调用传入的方法



- 尾随闭包

当我们的组件**只有一个BuilderParam**的时候，此时可以使用**尾随闭包**的语法 也就是像我们原来使用Column或者Row组件时一样，直接在大括号中传入， 如下

```typescript
 HMCard() {
          Text("自定义组件").fontColor(Color.Blue)
          this.getContent()
 }
```

- 如果有多个呢，不好意思，你必须在组件的函数中老老实实的传入多个builder自定义函数

```typescript
@Component
struct  HMCard {
  @BuilderParam
  content: () => void
  @BuilderParam
  header: () => void
  build() {
    Column () {
      Text("卡片组件")
      if(this.header) {
        this.header()
      }
      Divider()
      Text("传入内容")
      if(this.content) {
        this.content()
      }
    }
  }
}

@Entry
@Component
struct BuilderParamCase {

  @Builder
  getContent () {
    Row() {
      Text("插槽内容")
        .fontColor(Color.Red)
    }
  }
  @Builder
  getHeader () {
    Row() {
      Text("头部内容")
        .fontColor(Color.Red)
    }
  }
  build() {
    Row() {
      Column() {
         HMCard({
          header: () => {
            this.getHeader()
          },
          content: () => {
            this.getContent()
          }
        })
      }
      .width('100%')
    }
    .height('100%')
  }
}
```



案例- 封装HmCard 和HmCardItem组件， 使用BuilderParam属性完成神领物流的效果图



![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120040277.png)



![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401120040805.png)

代码

```typescript
@Entry
@Component
struct BuilderParamCard02 {
  @State message: string = 'Hello World'

  build() {
    Column() {
      HmCard() {
        HmCardItem({ leftTitle: '员工姓名', rightValue: '张三' })
        HmCardItem({ leftTitle: '员工编号', rightValue: '30032' })
        HmCardItem({ leftTitle: '员工权限', rightValue: '普通' })
        HmCardItem({ leftTitle: '员工组织', rightValue: '研发部' })
        HmCardItem({ leftTitle: '员工上级', rightValue: '老高' })
      }
    }
    .height('100%')
    .backgroundColor("#ccc")
  }
}

@Component
struct HmCard {
  @BuilderParam
  CardFn: () => void
  build() {
   Column() {
     Column() {
       if(this.CardFn) {
         this.CardFn()
       }
     }.borderRadius(8)
     .backgroundColor(Color.White)

   }.padding({
     left: 15,
     right: 15
   })
    .margin({
      top: 10
    })


  }
}
@Component
struct HmCardItem {
  leftTitle: string = ''
  rightValue: string = ''
  build() {
    Row() {
      Text(this.leftTitle)
      Text(this.rightValue).fontColor("#ccc")
    }.width('100%')
    .justifyContent(FlexAlign.SpaceBetween)
    .padding({
      left: 10,
      right: 10
    })
    .height(50)
    .border({
      width: {
        bottom: 1
      },
      color: '#f4f5f6'
    })
  }
}
```



## 2.组件状态共享

State是当前组件的状态， 用State修饰的数据变化会驱动UI的更新（只有第一层）

父传子的时候，子组件定义变量的时候，如果没有任何的修饰符，那么该值只会在第一次渲染时生效

接下来，我们学习组件状态传递

我们知道 State是当前组件的状态，它的数据变化可以驱动UI，但是子组件接收的数据没办法更新，我们需要

更多的修饰符来帮助我们完成数据的响应式传递

### 1. 状态共享-父子单向

`@Prop` 装饰的变量可以和父组件建立单向的同步关系。`@Prop` 装饰的变量是可变的，但是变化不会同步回其父组件。-Prop是用在子组件中的

Prop只能修饰string number boolean类型的数据- （Next全部都支持了各种类型）

- 完成父 - 子的单向同步

```typescript
@Entry
@Component
struct PropCase {
  @State pnum: number = 0
  build() {
    Row() {
      Column() {
        Text(this.pnum.toString())
        Button("+1")
          .onClick(() => {
            this.pnum++
          })

        Divider()
        Child({ num: this.pnum })
      }
      .width('100%')
    }
    .height('100%')
  }
}

@Component
struct Child {
  @Prop num: number
  build() {
    Column() {
      Text("子组件")
      Text(this.num.toString())
    }.height(60)
    .width('100%')
    .backgroundColor(Color.Pink)
  }
}
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401122325941.png)

如果子组件修改这个Prop呢？

我们来试试

```typescript
 Button("修改子组件Prop")
        .onClick(() => {
          this.num++
        })
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401122325008.png)

我们发现使用Prop修饰的状态，只会在当前子组件生效，不会传导到父组件，所以它属于一种单向传递

- 支持类型 `string、number、boolean、enum` 类型- **下个版本支持的类型更多**
- 子组件可修改 `Prop` 数据值，但不同步到父组件，父组件更新后覆盖子组件 `Prop` 数据
- 子组件可以初始化默认值，**注意：目前编译器会提示错误，请忽略，下个版本将修复**

## 总结复习

- 环境问题-调试-预览-解决问题
- 语法问题-TS-传值-赋值-声明
- 响应式更新问题

- 环境问题-  预览 + 模拟器
- 解决问题- console的使用-只能打印字符串-模拟器-会有更多的日志在日志系统中
- 可以使用弹出信息的方式-或者显示文本到页面的形式
- ALertDialog.show-  Text(JSON.stringfiy())
- 预览器报错-99%代码的问题

- 响应式更新的问题

- ArkTS所有的响应式更新都只能监听到我们的第一层数据

let a: User = { name: '老高'，address: { city: '北京'  }  }

a.name = ""  // 第一层数据

a.address.city = "上海"

### 2. 状态共享-父子双向

- Prop修饰符- 父组件数据更新-让子组件更新- 子组件更新-父组件不为所动

Prop是单向的，而Link修饰符则是双向的数据传递，只要使用Link修饰了传递过来的数据，这个时候就是双向同步了

注意点： 在父组件传入Link属性时，需要使用$来修饰该变量，去掉this

- 将刚刚的案例改造成双向的

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401122327459.png)

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401122327483.png)

子组件中被@Link装饰的变量与其父组件中对应的数据源建立双向数据绑定。



需要注意的是，Link修饰的变量类型变得更为宽泛，支持string、number、boolean、enum Object Class以及这些类型对应的数组

- 做一个购物车对象的传参

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401122327502.png)

```typescript
class FoodClass {
  order_id: number = 0
  food_name:  string = ""
  food_price: number = 0
  food_count: number = 0
}

@Entry
@Component
struct LinkCase {
  @State cartList: FoodClass[] = [{
    order_id: 1,
    food_name: '鱼香肉丝',
    food_price: 18.8,
    food_count: 1
  },{
    order_id: 2,
    food_name: '粗溜丸子',
    food_price: 26,
    food_count: 2
  },{
    order_id: 3,
    food_name: '杂粮煎饼',
    food_price: 12,
    food_count: 1
  }]
  build() {
    Row() {
      Column() {
        ForEach(this.cartList, (item: FoodClass) => {
          Row() {
            Text(item.food_name)
            Text("价格："+item.food_price.toString())
            Text("数量："+item.food_count.toString())
          }
          .height(60)
          .width('100%')
          .justifyContent(FlexAlign.SpaceBetween)
          .padding({
            left: 20,
            right: 20
          })
        })
        BottomCart({
          foodList: $cartList
        })
      }
      .width('100%')
    }
    .height('100%')
  }
}


@Component
struct BottomCart {
  @Link foodList: FoodClass[]
  build() {
    Row() {
      Button("加菜")
        .onClick(() => {
          this.foodList = this.foodList.map(item => {
            item.food_count++
            return item
          })
        })
    }
  }
}
```



大家一定在想，为什么不把每个菜封装成一个组件，然后用Link传递过去岂不是更方便？？？

我们试试

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401122327645.png)

看到没有，ArtTS不支持这么做，也就是Link修饰的数据必须得是最外层的 State数据，想要实现我们刚刚的设想，我们还得另辟蹊径。-后续ObjectLink 和Observerd会解决这个问题

### 3. 状态共享-后代组件

如果我们的组件层级特别多，ArkTS支持跨组件传递状态数据来实现双向同步@Provide和 @Consume   

这特别像Vue中的依赖注入

- 假设我们有三层组件，Index-Child-Grand， Index的数据不想经过Child而直接给到Grand可以使用该修饰器

```typescript
@Entry
@Component
struct ProvideCase02 {
 @Provide count: number = 0

  build() {
    Row() {
      Column({ space: 15 }) {
        Text(this.count.toString())
          .fontSize(50)
        Button("顶级组件+1")
          .onClick(() => {
            this.count++
          })
        Divider()
        Child()
      }
      .width('100%')
    }
    .height('100%')
  }
}

@Component
struct Child {
  build() {
    Column() {
      Text("子组件")
        .fontSize(40)
      Divider()
      Grand()
    }
  }
}

@Component
struct Grand {
  @Consume count: number
  build() {
    Column() {
      Text("孙组件")
        .fontSize(30)
      Text(this.count.toString())
    }
  }
}
```



注意： 在不指定Provide名称的情况下，你需要使用相同的名字来定义和接收数据

1）通过相同的变量名绑定

```typescript
@Entry
@Component
struct Index {
  @Provide
  money: number = 0

  build() {
    Column({ space: 20 }) {
      Text('父组件：' + this.money)
        .onClick(() => {
          this.money++
        })
      Parent()
    }
    .width('100%')
    .height('100%')
    .alignItems(HorizontalAlign.Center)
    .justifyContent(FlexAlign.Center)
  }
}

@Component
struct Parent {
  @Consume
  money: number

  build() {
    Column({ space: 20 }) {
      Text('父组件：' + this.money)
        .onClick(() => {
          this.money++
        })
      Child()
    }
  }
}

@Component
struct Child {
  @Consume
  money: number

  build() {
    Text('子组件：' + this.money)
      .onClick(() => {
        this.money++
      })
  }
}
```

- `Object、class、string、number、boolean、enum` 类型均支持
- 通过相同的变量别名绑定 `@Provide('key')` 和 `@Consume('key')` key需要保持一致

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401261809783.png)



ArkTS所有内容都不支持深层数据更新 UI渲染

### 4. 状态共享-状态监听器

修饰符- entry-component-State-Prop-Link-Provide Consume

如果开发者需要关注某个状态变量的值是否改变，可以使用 `@Watch` 为状态变量设置回调函数。

Watch("回调函数名")中的回调必须在组件中声明，该函数接收一个参数，参数为修改的属性名

注意：Watch修饰符要写在 State Prop Link Provide的修饰符下面，否则会有问题

- 在第一次初始化的时候，@Watch装饰的方法不会被调用

如下

```typescript
 @Provide('aa')
 @Watch('updateCount')
 count: number = 0
  updateCount(keyName: string) {
    promptAction.showToast({
      message: this.count.toString()
    })
    console.log(keyName,this.count.toString())
}
```



- `@State、@Prop、@Link` 等装饰器在 `@Watch` 装饰之前



有了watch 我们就可以随心所欲的搞监听了，比如

- 父组件数据变化了， 子组件随机而动
- 子组件双向更新了父组件数据，父组件随机而动

后续项目中我们会在上传组件的位置去使用它



### 5. @Observed与@ObjectLink



之前讲解Link的时候，我们遇到了一个问题，就是循环生成的item没办法用item传递给子组件的Link，也就是

封装的组件没办法做双向更新同步，那么ArtTS支持 Observed和@ObjectLink来实现这个需求



使用步骤：

- 类 `class` 数据需要定义通过构造函数，使用 `@Observed` 修饰这个类
- 初始化数据：需要通过初始化构造函数的方式添加
- 通过 `@ObjectLink` 关联对象，可以直接修改被关联对象来更新UI

需求：改造下之前的购物车案例

1）定义构造函数和使用`@Observed` 修饰符，以及初始化数据



```typescript
@Entry
@Component
struct ObjectLinkCase {
  @State message: string = 'Hello World'
  @State
  list: FoodObjectClass[] = [new FoodObjectClass({
    order_id: 1,
    food_name: '鱼香肉丝',
    food_price: 18.8,
    food_count: 1
  }) ,new FoodObjectClass({
    order_id: 2,
    food_name: '粗溜丸子',
    food_price: 26,
    food_count: 2
  }) , new FoodObjectClass({
    order_id: 3,
    food_name: '杂粮煎饼',
    food_price: 12,
    food_count: 1
  }) ]
  build() {
    Row() {
      Column({ space: 20 }) {
         ForEach(this.list, (item: FoodObjectClass) => {
           FoodItem({ item: item })
         })

        BottomCart({ myList: $list  })
      }
      .width('100%')
    }
    .height('100%')
  }
}
@Extend(Text)
function TextStyle () {
  .layoutWeight(1).textAlign(TextAlign.Center).fontSize(20)
}

@Extend(Text)
function AddCutStyle () {
  .width(40)
  .height(40)
  .borderRadius(20)
  .backgroundColor(Color.Grey)
  .textAlign(TextAlign.Center)
  .fontSize(20)
}

@Component
struct FoodItem {
  // Observed必须和ObjectLink才有UI更新的效果
  @ObjectLink
  item: FoodObjectClass
  build() {
    Row() {
      Text(this.item.food_name).TextStyle()
      Text(this.item.food_price.toFixed(2)).TextStyle()
      Row() {
          Text("-").AddCutStyle()
            .onClick(() => {
              this.item.food_count--
            })
            .visibility(this.item.food_count > 0 ? Visibility.Visible : Visibility.Hidden)
          Text(this.item.food_count.toString()).TextStyle()
            .visibility(this.item.food_count > 0 ? Visibility.Visible : Visibility.Hidden)

        Text("+").AddCutStyle()
          .onClick(() => {
             this.item.food_count++
          })
      }.layoutWeight(1)
    }
    .width('100%')
    .height(40)
  }
}


// 底部组件
@Component
struct BottomCart {
  @Link
  myList: FoodObjectClass[]
  build() {
     Button("更改菜品的数量")
       .onClick(() => {
         this.myList = this.myList.map(item => {
           item.food_count++
           return item
         })
       })
  }
}
// 定义了一个接口
interface IFoodInfo {
  order_id: number
  food_name: string
  food_price: number
  food_count: number
}

// 食品类
@Observed
class FoodObjectClass implements  IFoodInfo  {
  order_id: number = 0
  food_name:  string = ""
  food_price: number = 0
  food_count: number = 0
  constructor(obj: IFoodInfo) {
    this.order_id = obj.order_id
    this.food_name = obj.food_name
    this.food_price = obj.food_price
    this.food_count = obj.food_count
  }
}
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401261847382.png)



上述代码中，我们用了interface，interface声明类型不需要给初始值，class声明类型必须给初始值（下一代要求）

- 我们使用Class继承实现了interface，并且通过传入的对象将我们Class中的属性进行赋值
- 使用了Observed这个装饰器来修饰class，那么只要我们改动class的属性，它就会驱动UI的更新（只是第一层）

注意： 只有Observed修饰的class才可以被 ObjectLink使用，并且Entry修饰的组件不允许使用ObjectLink



同学们是不是觉得代码比之前多了好多，我们不光要定义interface，还要定义基于Interface实现的class，还要实现class的构造函数，这里给大家推荐一个我们黑马研究院自己创建的一个npm包，只要你定义interface，它就会帮你自定义生成你的class - https://www.yuque.com/shuiruohanyu/dhwu2a/sns3wqbglx2k6u15



- 接下来，我们同样改造下我们的知乎案例

```typescript
import { ReplyItem } from './Zhihu'

@Entry
@Component
struct ZhiHu {
  @State commentList: ReplyClass[] = [
    new ReplyClass({
      id: 1,
      avatar: 'https://picx.zhimg.com/027729d02bdf060e24973c3726fea9da_l.jpg?source=06d4cd63',
      author: '偏执狂-妄想家',
      content: '更何况还分到一个摩洛哥[惊喜]',
      time: '11-30',
      area: '海南',
      likeNum: 34
    }),
    new ReplyClass({
      id: 2,
      avatar: 'https://pic1.zhimg.com/v2-5a3f5190369ae59c12bee33abfe0c5cc_xl.jpg?source=32738c0c',
      author: 'William',
      content: '当年希腊可是把1：0发挥到极致了',
      time: '11-29',
      area: '北京',
      likeNum: 58
    }),
    new ReplyClass({
      id: 3,
      avatar: 'https://picx.zhimg.com/v2-e6f4605c16e4378572a96dad7eaaf2b0_l.jpg?source=06d4cd63',
      author: 'Andy Garcia',
      content: '欧洲杯其实16队球队打正赛已经差不多，24队打正赛意味着正赛阶段在小组赛一样有弱队。',
      time: '11-28',
      area: '上海',
      likeNum: 10
    }),
    new ReplyClass({
      id: 4,
      avatar: 'https://picx.zhimg.com/v2-53e7cf84228e26f419d924c2bf8d5d70_l.jpg?source=06d4cd63',
      author: '正宗好鱼头',
      content: '确实眼红啊，亚洲就没这种球队，让中国队刷',
      time: '11-27',
      area: '香港',
      likeNum: 139
    }),
    new ReplyClass({
      id: 5,
      avatar: 'https://pic1.zhimg.com/v2-eeddfaae049df2a407ff37540894c8ce_l.jpg?source=06d4cd63',
      author: '柱子哥',
      content: '我是支持扩大的，亚洲杯欧洲杯扩到32队，世界杯扩到64队才是好的，世界上有超过200支队伍，欧洲区55支队伍，亚洲区47支队伍，即使如此也就六成出现率',
      time: '11-27',
      area: '旧金山',
      likeNum: 29
    }),
    new ReplyClass({
      id: 6,
      avatar: 'https://picx.zhimg.com/v2-fab3da929232ae911e92bf8137d11f3a_l.jpg?source=06d4cd63',
      author: '飞轩逸',
      content: '禁止欧洲杯扩军之前，应该先禁止世界杯扩军，或者至少把亚洲名额一半给欧洲。',
      time: '11-26',
      area: '里约',
      likeNum: 100
    })
  ]
  addComment(item: ReplyClass) {
    this.commentList.unshift(item)
  }

  build() {
    Stack({ alignContent: Alignment.Bottom }) {
      Scroll() {
        Column() {
          // 顶部组件
          HmNavBar()
          // 评论组件
          CommentItem({
            item: new ReplyClass({
              id: 1,
              avatar: $r('app.media.b'),
              author: '周杰伦',
              content: '意大利拌面应该使用42号钢筋混凝土再加上量子力学缠绕最后通过不畏浮云遮望眼',
              time: '11-30',
              area: '海南',
              likeNum: 100
            })
          })
          // 分割线
          Divider()
            .strokeWidth(6)
            .color("#f4f5f6")
          ForEach(this.commentList, (item: ReplyClass) => {
            CommentItem({
              item: item
            })
          })
        }
        .width('100%')
        .backgroundColor(Color.White)
      }.padding({
        bottom: 60
      })
      ReplyInput({
        addComment: item => {
          this.addComment(item)
        }
      })
    }
  }
}

@Component
struct HmNavBar {
  build() {
    Row() {
      Row() {
        Image($r('app.media.ic_public_arrow_left'))
          .width(20)
          .height(20)
      }
      .borderRadius(20)
      .backgroundColor('#f6f6f6')
      .justifyContent(FlexAlign.Center)
      .width(30)
      .aspectRatio(1)
      .margin({
        left: 15
      })

      Text("评论回复")
        .layoutWeight(1)
        .textAlign(TextAlign.Center)
        .padding({
          right: 35
        })
    }
    .width('100%')
    .height(50)
    .border({
      width: {
        bottom: 1
      },
      color: '#f6f6f6',
    })
  }
}

@Component
struct CommentItem {
  @ObjectLink
  item: ReplyClass

  build() {
    Row() {
      Image(this.item.avatar)
        .width(32)
        .height(32)
        .borderRadius(16)
      Column({ space: 10 }) {
        Text(this.item.author)
          .fontWeight(600)
        Text(this.item.content)
          .lineHeight(20)
          .fontSize(14)
          .fontColor("#565656")

        Row() {
          Text(`${this.item.time} . IP属地 ${this.item.area}`)
            .fontColor("#c3c4c5")
            .fontSize(12)
          Row() {
            Image($r('app.media.like'))
              .width(14)
              .aspectRatio(1)
              .fillColor(this.item.likeFlag ? 'red' : "#c3c4c5")

            Text(this.item.likeNum.toString())
              .fontSize(12)
              .margin({
                left: 5
              })
          } .onClick(() => {
            if(this.item.likeFlag) {
              this.item.likeNum--
              this.item.likeFlag = false
            }else {
              this.item.likeNum++
              this.item.likeFlag = true
            }
          })
        }
        .width('100%')
        .justifyContent(FlexAlign.SpaceBetween)

      }.alignItems(HorizontalAlign.Start)
      .layoutWeight(1)
      .padding({
        left: 15,
        right: 5
      })
    }.justifyContent(FlexAlign.Start)
    .alignItems(VerticalAlign.Top)
    .width('100%')
    .padding(15)
  }
}


@Component
struct ReplyInput {
  @State commentStr: string = ''
  addComment: (item: ReplyClass) => void = () => {}
  build() {
    Row() {
      TextInput({ placeholder: '回复', text: this.commentStr })
        .layoutWeight(1)
        .backgroundColor("#f4f5f6")
        .onChange(value => {
          this.commentStr = value
        })
      Text('发布')
        .fontColor("#6ecff0")
        .fontSize(14)
        .margin({
          left: 10
        })
        .onClick(() => {
          if(this.commentStr !== "") {
            let obj: ReplyItem = {
              id: Date.now(),
              content: this.commentStr,
              time: `${new Date().getMonth() + 1} - ${new Date().getDate()}` ,
              likeNum: 0,
              likeFlag: false,
              author: '老高',
              avatar:  $r('app.media.b'),
              area: "北京"
            }
            this.addComment && this.addComment(new ReplyClass(obj))
            this.commentStr = ""
          }
        })
    }
    .height(50)
    .padding({
      left: 10,
      right: 10
    })
    .backgroundColor(Color.White)
    .border({
      width: { top: 1 },
      color: "#f4f5f6"
    })
  }
}


@Observed
class ReplyClass  extends ReplyItem {
  constructor(obj: ReplyItem ) {
    super()
    this.id = obj.id
    this.avatar = obj.avatar
    this.author = obj.author
    this.content = obj.content
    this.time = obj.time
    this.likeNum = obj.likeNum
    this.likeFlag = obj.likeFlag
  }
}
```



此时，我们的头像不再闪动，说明数据已经不需要去更新整条数据来完成UI的更新



注意点：

- ObjectLink只能修饰被Observed修饰的class类型
- Observed修饰的class的数据如果是复杂数据类型，需要采用赋值的方式才可以具备响应式特性-因为它监听的是该属性的set和get
- 如果出现复杂类型嵌套，只需要Observed我们需要的class即可，至于用于类型的class可以用type或者interface



此知识点不太好理解，同学们一定一定多敲几遍！！！！！





# 老师，多层嵌套怎么更新？又不能用解构赋值和延展运算符



- Next版本的文档-明确说了 ... 只能限制使用， 只能用在部分数组的更新

```typescript
@Entry
@Component
struct MulitiStateCase {

   @State
   user: IUserProfileModel  = new IUserProfileModel({
     username: '老高',
     age: 34,
     sex: "男",
     address: new IAddressModel({
       province: '河北',
       city: '衡水',
       area: '深州'
     })
   })

  build() {
    Row() {
      Column() {
        // UI更新只能监听到一层
        Row() {
          Text(this.user.username).fontSize(40)
          Text(this.user.age.toString()).fontSize(40)
          Text(this.user.address.province).fontSize(40)
          Text(this.user.address.city).fontSize(40)
          Text(this.user.address.area).fontSize(40)
        }
        .width('100%')
        .height(50)
        Button("更新名字和年龄")
          .onClick(() => {
            // this.user.username = "老高坏坏的"
            // this.user.age = 25
            this.user.address.city = "廊坊"
            this.user.address = new IAddressModel(this.user.address)

            // this.user.address = { ...this.user.address, city: '廊坊'   }
          })
      }
      .width('100%')
    }
    .height('100%')
  }
}

interface IAddress {
  province: string
  city: string
  area: string
}

interface  IUserProfile {
  username: string
  age: number
  sex: '男' | '女'
  address: IAddress
}
export class IAddressModel implements IAddress {
  province: string = ''
  city: string = ''
  area: string = ''

  constructor(model: IAddress) {
    this.province = model.province
    this.city = model.city
    this.area = model.area
  }
}
export class IUserProfileModel implements IUserProfile {
  username: string = ''
  age: number = 0
  sex: '男' | '女' = '男'
  address: IAddress = new IAddressModel({} as IAddress)

  constructor(model: IUserProfile) {
    this.username = model.username
    this.age = model.age
    this.sex = model.sex
    this.address = model.address
  }
}
```

## 3.应用状态



# 黑马研究院自创插件-interface转class插件

## 一、基本使用

1. 使用npm安装插件

```bash
$ npm i -g interface2class
```

安装完成后验证

```bash
$ i2c -V      
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401261856453.png)

1. 使用

在DevEcoStudio中新建一个文件，如 index.ets，

拷贝进去一段interface类型声明，如下

```typescript
interface IUser{
  username: string
  age: number
  sex: "男" | "女"
}
```



右键打开范围-

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401261856495.png)

执行命令

```bash
$ i2c ./index.ets
```

生成代码

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401261856630.png)





下面是使用interface示例进行深层数据修改的一个代码示例![image.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401261853710.png)



```typescript
@Entry
@Component
struct NextState {
  @State user: UserModel = new UserModel({
    username: '老高',
    age: 34,
    address: new AddressModel({
      province: '北京',
      city: '北京',
      area: '昌平'
    })
  })

  build() {
    Row() {
      Column() {
        Text(JSON.stringify(this.user))
        Button("修改城市")
          .onClick(() => {
            this.user.address.area = "顺义"
            this.user.address = new AddressModel(this.user.address)
          })
      }
      .width('100%')
    }
    .height('100%')
  }
}

interface Address {
  province: string
  city: string
  area: string
}

export interface UserInfoType {
  username: string,
  age: number,
  address: Address
}


export class AddressModel implements Address {
  province: string = ''
  city: string = ''
  area: string = ''

  constructor(model: Address) {
    this.province = model.province
    this.city = model.city
    this.area = model.area
  }
}

export class UserModel implements UserInfoType {
  username: string = ''
  age: number = 0
  address: Address = new AddressModel({} as Address)

  constructor(model: UserInfoType) {
    this.username = model.username
    this.age = model.age
    this.address = model.address
  }
}

```

## 二、版本升级 

`$ npm update -g interface2class`

## 

