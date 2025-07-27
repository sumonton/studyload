# 一、鸿蒙基础-组件样式-样式-状态

* 官方文档地址：`https://developer.harmonyos.com/cn/documentation/overview/?catalogVersion=V3`

* DevEcoStudio快捷键：`https://github.com/HarmonyOS-Next/awesome-harmonyos/blob/main/DevEco_Studio_Keymap.md`

# 1. 起步

## 1.1. 起步-鸿蒙简介

- HarmonyOS 是新一代的智能终端操作系统，为不同设备的智能化、互联与协同提供了统一的语言。带来简洁，流畅，连续，安全可靠的全场景交互体验。

历程：

| **时间** | **事件**                                                     |
| -------- | ------------------------------------------------------------ |
| 2019     | HarmonyOS 1.0，华为在东莞举行华为开发者大会，正式发布操作系统鸿蒙 OS，主要用于物联网 |
| 2020     | HarmonyOS 2.0，基于开源项目 OpenHarmony 开发的面向多种全场景智能设备的商用版本 |
| 2021     | HarmonyOS 3.0，先后优化游戏流畅度、地图三维体验、系统安全，另外系统的稳定性也得到了增强 |
| 2023.2   | HarmonyOS 3.1，系统纯净能力进一步提升，对后台弹窗、 隐藏应用、后台跳转等情况 |
| 2023.7   | 华为 Mate 50 系列手机获推 HarmonyOS 4.0                      |
| **2024** | **HarmonyOS Next 即将发布，将不在兼容安卓应用**              |



4.0和Next区别大吗？

- 语法和Next版本基本一致-API会更丰富，语言校验会更强。
- 拍照-扫码-导航-API会更多。







## 1.2.  起步-DevEco Studio



![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312696.png)



安装 DevEco Studio 编辑器

1. 下载：https://developer.harmonyos.com/cn/develop/deveco-studio#download 

- - Windows(64-bit)
  - Mac(X86)
  - Mac(ARM)

1. 安装：DevEco Studio → 一路 Next
2. 运行： 

- 基础安装：Node.js >= 16.9.1 + Install ohpm 鸿蒙包管理器

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312750.png)

- SDK 安装

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312850.png)

- 安装完毕

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312027.png)

如果有需要的同学，可以安装中文包

- 安装DevEco Studio的中文版插件- 选择Preferences

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312051.png)

- 选择Plugins

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312668.jpeg)

- 选择右侧的Installed

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312762.jpeg)

对Chinese进行勾选。点击OK

- 重启编辑器

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312822.jpeg)

- 看到效果

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312117.png)



ohpm-> npm 下载第三方包- 配置一个淘宝镜像

## 1.3 起步-HelloWorld

创建一个空项目:



1.  Create Project 

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312379.png)

1.  选择项目模板 

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312463.png)

1.  填写项目信息 

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312516.png)

1.  完成

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312553.png)



## 1.4 起步-效果预览

效果预览方法：

- Previewer 预览模式(写页面用的上-热更新还不错)-不能测试网络通信-原生能力-本地缓存-Ability
- Local Emulator 本地模拟器(99%都可以)
- Remote Emulator 远程模拟器
- Remote Device 远程真机
- Local Device 本地真机-某些机型可以



推荐使用

- Previewer 预览 和 Local Emulator 本地模拟器；
- 尤其推荐使用 `遥遥领先` 真机调试；Meta40 - P50

Previewer 预览

场景：静态页面（没有组件间数据通信、不涉及到网络请求）
条件：有 [@Entry ]() 或 [@Preview ]() 装饰器页面 

-  预览和审查元素 

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312910.png)

-  多设备预览 

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312478.png)

2）Local Emulator 本地模拟器

场景：动态页面（几乎全场景，一些无法模拟的硬件功能）

-  新建模拟器 

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312966.png)

一路 Next ...

-  启动模拟器 

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312172.png)

-  运行项目看效果 

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312562.png)

-  更改后每次需要（打包 → 卸载 → 安装 → 预览），有没有热更新或者刷新这种预览方式？ 

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312043.png)



**其他方式**

- Remote Emulator 远程模拟器
- Remote Device 远程真机
- Local Device 本地真机

条件：

- 需要注册华为账号, 点这 https://developer.harmonyos.com/ 然后点击登录页面，去注册吧~
- 登录之后，需要自动生成签名信息，这个我们后面讲真机调试再给大家演示。
- 真机模拟器- 一根数据线- 手机必须是HarmonyOS4.0- 开启开发者调试即可-设备管理器就会出现你的机型，点击箭头运行即可。

## 1.5 起步-工程结构

我们在哪里写代码呢

### Stage模型

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312085.png)

了解App结构关系，等后面我们项目阶段来讲一些工程配置文件作用，现在你要知道：

- entry 是一个 Module 应用包-(对应根目录的build-profile.json5文件中的modules)
- entryability 是一个 UIAbility 包含用户界面的应用组件-(一个展示UI的窗口任务，对应src/main/module.json5中的abilities)
- pages 是页面
- components是组件

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312135.png)



# 2. HarmonyOS应用模型Stage&FA

官方介绍-[链接](https://developer.harmonyos.com/cn/docs/documentation/doc-guides-V3/application-model-composition-0000001544384013-V3?catalogVersion=V3)

- 应用模型是**HarmonyOS**为开发者提供的应用程序所需能力的抽象提炼，它提供了应用程序必备的组件和运行机制。有了应用模型，开发者可以基于一套统一的模型进行应用开发，使应用开发更简单、高效。

换言之- 应用模型是鸿蒙开发一切的基础，因为只有基于该应用模型我们才可以开发对应的应用和业务。

​    应用模型包含几个要素**应用组件****-**应用进程-应用线程-应用任务管理-应用配置文件

**提问**：应用模型是只有一个吗？

**回答**：鸿蒙前后推出了两种应用模型- FA(Feature Ability)，Stage，**目前FA已经不再主推**。



- HarmonyOS Next(**待发布**)也将Stage模型作为主推模型，所以我们本次训练营将学习Stage模型相关的应用开发能力。



下面是官方对比FA模型和Stage的一个差异变化

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312245.png)





总结：应用模型是开发鸿蒙应用的基础底座，但是鸿蒙先后推出了FA和Stage，鸿蒙4.0和鸿蒙Next都将Stage作为主推方向，所以我们**主要基于Stage模型来学习和开发我们目前的应用**。

# 3. UIAbility-(界面能力组件)

从上一个小节中，我们发现Stage模型提到了**UIAbility组件包含UI界面绘制，主要和用户交互。**

- UIAbility组件是一种包含UI界面的应用组件，主要用于和用户交互。

官网介绍-UIAbility是系统调度的基本单元，可以给应用提供绘制界面的窗口。

也就是我们**开发任务应用都离不开UIAbility**

提问：那UIAbility是什么形式，它是个按钮？ 容器？ 还是一个抽象组件？

回答：UIAbility可以理解成一个对象实例，它基于Stage模型来完成绘制窗口任务，至于按钮-表格这些都是属于UIAbility的**下一层页面**中的内容，UIAbility可以通过多个页面来完成一个功能模块，甚至是一个应用。 



提问：那我们一个Stage模型里面应该有几个UIAbility呢？

回答：默认创建项目的时候是一个，这一个UIAbility下可以管控几个，几十个，几百个我们的页面，也就是我们一个UIAbility足够开发一个应用，当然，我们在一些复杂业务中，也可以多来几个解构一下业务的冗余度



**下图附上多个UIAbility的样子**

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312931.png)



一句话总结：**一个基于Stage模型的鸿蒙4.0/鸿蒙Next的应用使用一个UIAbility就可以完成一个应用的开发。**

**后续，我们会在神领物流项目中实现多个UIAbility间的通讯**



# 4. 组件基础

## 4.1.   组件-什么是ArkTS

一句话概括：

- ArkTS是HarmonyOS优选的主力应用开发语言。ArkTS围绕应用开发在TypeScript（简称TS）生态基础上做了进一步扩展，继承了TS的所有特性，是TS的超集。

说明： 也就是前端开发过程中所有的js/ts语法**大部分支持**的，比如es6中的箭头函数-模板字符串-promise-async/await-数组对象方法- 

**注意： 根据对下一代的Next版本的内部沟通，下一版本的ArkTs对类型最了更一步的限制**

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312957.png)

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312442.png)

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312460.png)



误区： 前端同学，原有的DOM/WebAPI在这里皆不存在 如 document.querySelector/window.location







![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312199.png)

ArtTS绝不是TS

- TS在前端中虽然有类型约束，但是他会编译成js去运行
- ArtTS编译后直接映射字节码-编译过程带类型



扩展能力如下：

1. 基本语法 

- - 定义声明式UI、自定义组件、动态扩展UI元素；
  - 提供ArkUI系统组件，提供组件事件、方法、属性；
  - 共同构成 UI 开发主体

1. 状态管理 

- - 组件状态、组件数据共享、应用数据共享、设备共享；

1. 渲染控制 

- - 条件渲染、循环渲染、数据懒加载；

声明式UI

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312342.png)

**下图是关于ArtTS的一个整体的应用架构（官网）**

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312424.png)



总结：  

- AktTS提供原有前端范畴内的一切TypeScript和JavaScript的类型及方法支持
- 不是所有都支持- 比如解构不支持（Next版本）
- ArkTS采用声明式UI的方法来绘制页面，设置属性，绑定事件

## 4.2.  基础-组件结构

接下来，我们来解析我们的UI的结构

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312604.png)

ArkTS通过装饰器 `@Component` 和 `@Entry` 装饰 `struct` 关键字声明的数据结构，构成一个自定义组件。
自定义组件中提供了一个 `build` 函数，开发者需在该函数内以链式调用的方式进行基本的 UI 描述，UI 描述的方法请参考 UI 描述规范。

- **struct-****自定义组件基于struct实现**

要想实现一段UI的描述，必须使用struct关键字来声明- **注意不能有继承关系-组件名不能系统组件名重名**

语法： struct 组件名 {}

```typescript
@Component
struct Index {
  
}
@CustomDialog 
struct Index2 {
  
}
```

struct关键字声明的UI描述-必须被@Component或者@CustomDialog修饰

- **Component修饰符**

Component装饰器只能修饰struct关键字声明的结构，被修饰后的struct具备组件的描述(渲染)能力

- **build函数**

用于定义组件的UI描述，一个struct结构必须实现build函数

```typescript
@Component
struct MyComponent {
  build() {
  }
}
```

build函数是组件(Component)必须提供以及实现的一个函数，build函数可以没有内容，如果有的话，必须有且只有一个[容器组件](https://developer.harmonyos.com/cn/docs/documentation/doc-references-V3/ts-container-badge-0000001478181417-V3)(可以放置子组件的组件)- 只有entry里面有限制- component里面没有限制

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312694.png)

- 常见容器组件- Flex-Column-Row-List-Grid-Panel

- entry修饰符

entry将自定义组件定义为UI页面的入口，也就是我们原来前端常说的一个页面，最多可以使用entry装饰一个自定义组件(在一个ets文件中)-如下面的代码就是不被允许的

```typescript
@Entry
@Component
struct Index {

  build() {
  
  }
}
@Entry
@Component
struct Index2 {
  build() {

  }
}
```



entry修饰的组件，最终会被注册，具体文件位置-main/resources/base/profile/main_pages.json

1. 自动注册-新建组件时，采用新建Page的方式
2. 手动注册-新建一个ets文件，自己在main_pages.json中手动添加路径

注意：

   **如果你手动删除了某一个带entry的组件，你需要手动去main_page中去删除该路径，否则编译会报错**



- 组件复用

在很多情况下，由于业务的复杂度，我们经常会将一个大的业务拆成若干个组件，进行组装，这里我们非常灵活的复用组件，比如

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312226.png)







- 我们可以把上图抽象成三个组件- Header- Main- Footer

代码

```typescript
@Entry
@Component
struct Index {
  @State message: string = 'Hello World3'

  build() {
    // 它的子组件都会以纵向方式排列
    Column() {
      Header()
      Main()
      Footer()
    }
  }
}

@Component
struct Header {
  build() {
    Row(){
      Text("Header")
    }
      .width('100%')
      .height(50)
      .backgroundColor(Color.Pink)
    .justifyContent(FlexAlign.Center)
  }
}

@Component
struct Main {
  build() {
    Row(){
      Text("Main")
        .fontColor(Color.White)
    }
    .width('100%')
    .height(200)
    .backgroundColor(Color.Blue)
    .justifyContent(FlexAlign.Center)
  }
}

@Component
struct Footer {
  build() {
    Row(){
      Text("Footer")
        .fontColor(Color.White)
    }
    .width('100%')
    .height(50)
    .backgroundColor(Color.Pink)
    .justifyContent(FlexAlign.Center)
  }
}
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312545.png)



总结：

- 一个UI描述必须使用struct来声明，不能继承
- struct必须被Component或者CustomDialog修饰
- struct必须实现build方法，build方法可以没有元素，但是有的话有且只有一个可容纳子组件的容器组件(entry修饰的组件)
- entry修饰符表示该组件是页面级组件，一个文件中只允许修饰一个struct组件
- 采用分拆组件的形式可以有效解解耦我们的业务

## 4.3.  基础-系统组件(ArkUI)

 常用系统组件

   Button Text Column Row Flex Stack Scroll  List TextInput  Image [更多组件](https://developer.harmonyos.com/cn/docs/documentation/doc-references-V3/ts-components-summary-0000001478181369-V3)

  组件使用

- Text 文本组件-（Span子组件）
- Column 列组件，纵向排列，Flex布局主轴是Y （任意子组件）
- Row 行组件，横向向排列，Flex布局主轴是X （任意子组件）
- Flex 以弹性方式布局子组件的容器组件。(**存在二次布局，官方推荐有性能要求，使用Column和Row代替**) （任意子组件）
- Button 按钮组件 （单子组件）
- TextInput 输入框组件 （无子组件）
- Image （无子组件）
- Button (单个子组件)
- List (限制ListItem子组件)
- Scroll (限制单个子组件)

组件使用

- 使用组件采用 **组件名()** 的语法
- 有构造参数采用 **组件名(参数)**的语法
- 组件里放置子组件采用  **组件名() { 子组件的语法  }** 的链式语法
- 组件设置属性采用 **组件名().width().height()** 的语法
- 组件又有属性又有子组件采用 **组件名(){ ... 子组件  }.width().height()** 的语法

接下来，我们来实现一个布局的小例子测试一下我们的能力



横向布局-采用Row

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312041.png)



```typescript
@Entry
@Component
struct ComponentCase {
  @State message: string = 'Hello World'
  build() {
    Row() {
      Column() {
        Text("老高你讲的真烂")
        Text(){
          Span("¥")
            .fontSize(12)
          Span("50.45")
            .fontSize(20)
        }
      // css 支持调整布局
        Row({ space:15 }) {
          Column()
            .width(100)
            .height(200)
            .backgroundColor(Color.Pink)
          Column()
            .width(100)
            .height(200)
            .backgroundColor(Color.Red)
          Column()
            .width(100)
            .height(200)
            .backgroundColor(Color.Blue)
        }
        .width('100%')
        // .justifyContent(FlexAlign.Start)
        .justifyContent(FlexAlign.Center)
        // .justifyContent(FlexAlign.End)
        // .justifyContent(FlexAlign.SpaceBetween)
        // .justifyContent(FlexAlign.SpaceAround)
        // .justifyContent(FlexAlign.SpaceEvenly)

      }
      .width('100%')
    }
    .height('100%')
  }
}
```

纵向布局

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312075.png)



```typescript
@Entry
@Component
struct Layout {

  build() {
    Column() {
      RowCase()
      ColumnCase()
    }.height('100%').backgroundColor(Color.Grey)
  }
}

@Component
struct RowCase {
  build() {
    Row() {
      Column().height(150).width(100).backgroundColor(Color.Pink)
      Column().height(150).width(100).backgroundColor(Color.Red)
      Column().height(150).width(100).backgroundColor(Color.Blue)
    }
    .width('100%')
    .alignItems(VerticalAlign.Top)
    .justifyContent(FlexAlign.SpaceAround)
  }
}

@Component
struct ColumnCase {
  build() {
    Column() {
      Column().height(100).width(150).backgroundColor(Color.Pink)
      Column().height(100).width(150).backgroundColor(Color.Red)
      Column().height(100).width(150).backgroundColor(Color.Blue)
    }.height(400).width('100%').justifyContent(FlexAlign.SpaceEvenly)
  }
}
```



Flex横纵向

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312130.png)

```typescript
@Entry
@Component
struct ComponentCase {
  @State message: string = 'Hello World'
  build() {
    Scroll() {
      Row() {
        Column() {
          Text("老高你讲的真烂")
          Text(){
            Span("¥")
              .fontSize(12)
            Span("50.45")
              .fontSize(20)
          }
          // css 支持调整布局
          Row({ space:15 }) {
            Column()
              .width(100)
              .height(200)
              .backgroundColor(Color.Pink)
            Column()
              .width(100)
              .height(200)
              .backgroundColor(Color.Red)
            Column()
              .width(100)
              .height(200)
              .backgroundColor(Color.Blue)
          }
          .width('100%')
          // .justifyContent(FlexAlign.Start)
          .justifyContent(FlexAlign.Center)
          // .justifyContent(FlexAlign.End)
          // .justifyContent(FlexAlign.SpaceBetween)
          // .justifyContent(FlexAlign.SpaceAround)
          // .justifyContent(FlexAlign.SpaceEvenly)

          Column({ space: 20 }) {
            Column()
              .width(200)
              .height(100)
              .backgroundColor(Color.Pink)
            Column()
              .width(200)
              .height(100)
              .backgroundColor(Color.Red)
            Column()
              .width(200)
              .height(100)
              .backgroundColor(Color.Blue)
          }.margin({
            top: 20
          })

          Flex({ direction: FlexDirection.Row, justifyContent: FlexAlign.SpaceAround }) {
            Column()
              .width(100)
              .height(200)
              .backgroundColor(Color.Pink)
            Column()
              .width(100)
              .height(200)
              .backgroundColor(Color.Red)
            Column()
              .width(100)
              .height(200)
              .backgroundColor(Color.Blue)
          }
          .margin({
            top: 200
          })

        }
        .width('100%')
      }
    }

  }
}
```

在arkUI中，我们的内容如果超过了屏幕显示，则不会显示滚动条，需要使用Scroll来包裹

需要注意的是： **该组件滚动的前提是主轴方向大小小于内容大小。子组件不要设置高度，否则不能滚动**



## 4.4. 基础-组件事件

监听原生组件的事件和设置属性的方式是一样的都是链式调用，值得注意的是，我们注册事件都要使用箭头函数的写法，Next版本会有对于匿名函数function的限制

- 尝试给一个TextInput和一个按钮注册一个值改变事件和点击事件

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312776.png)

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312809.png)

```typescript
@Entry
@Component
struct Event {

  build() {
    Row() {
      Column({ space: 15 }) {
        Row() {
          TextInput({ placeholder: '请输入用户名' })
            .backgroundColor('#f4f5f6')
            .width('100%').onChange((value) => {
            AlertDialog.show({
              message: value
            })
          })
        }.padding({
          left: 20,
          right: 20
        })

        Row() {
          Button("登录")
            .width('100%')
            .onClick(() => {
              AlertDialog.show({
                message: '点击了按钮'
              })
            })

        }.padding({
          left: 20,
          right: 20
        })

      }
      .width('100%')
    }
    .height('100%')
  }
}
```

请注意：在注册事件中的逻辑**务必使用箭头函数 () => {}**，**极不推荐 function() {}**

1. 因为function中this指向为undefind
2. Next下一代不再支持funtion匿名函数声明
3. 箭头函数中的this指向当前struct实例，可以方便的调用方法和获取属性



当我们事件处理逻辑比较复杂，写在UI描述中无法抽提的时候，我们可以在struct结构体中定义

如

```typescript
struct Event {
  login () {
    AlertDialog.show({
      message: '登录成功'
    })
  }

  ...
   Button("登录")
            .width('100%')
            .onClick(() => {
              this.login()
            })
}
```

- 属性定义

当我们需要在组件中记录一些状态时，变量应该显示的在struct中声明，并注明类型

比如-登录账户和密码

```typescript
struct Event {
  loginName: string = ""
  password: string = ""
  
}
```

我们看代码示例时，会发现 public和private关键字，

如果不写或者写public 表示该属性可被外界即父组件赋值,

如果写private表示该属性只会被该组件的this获取

- 实现一个简单的登录过程

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312942.png)

```typescript
import promptAction from '@ohos.promptAction'
@Entry
@Component
struct EventCase {
  // arkTS
  account: string = "" // 公有面量
  password: string = '' // 只能在当前组件使用
  login() {
    if(this.account === 'admin' && this.password === '123456') {
      promptAction.showToast({ message: '登录成功' })
    }else {
      promptAction.showToast({ message: '用户名或者密码错误' })
    }
  }
  getBtnEnable(): boolean {
    // if(this.account !== "" && this.password !== "") {
    //   return true
    // }
    // return false
    return this.account !== "" && this.password !== ""
  }

  build() {
    Row() {
      Column({ space: 20 }) {
        TextInput({
          placeholder: '请输入用户名'
        }).height(40)
          .width('80%')
          .onChange(value => {
            this.account = value
          })
        TextInput({
          placeholder: '请输入密码',
        }).height(40)
          .type(InputType.Password)
          .width('80%')
          .onChange(value => {
            this.password = value
          })
        Button("登录")
          .width('80%')
          .onClick(() => {
            // AlertDialog.show({ message: "登录成功" })
            this.login()
          })
          .enabled(this.getBtnEnable())
      }
      .width('100%')
    }
    .height('100%')
  }
}
```



说明

  promptAction和AlertDailog都可以弹出提示  promptAction需要引入包，AlertDialog不需要引入就可以直接使用



再加一个小需求，当用户名和密码为空时，不让用户点按钮

```typescript
  getBtnEnable (): boolean {
    return this.loginName !== "" && this.password !== ""
  }

 Row() {
          Button("登录")
            .width('100%')
            .onClick(() => {
              this.login()
            }).enabled(this.getBtnEnable())
```

我们发现，好像没有变化！！！为什么？ 因为我们定义的变量是非响应式数据，值的变化无法导致build函数重新执行， 这就需要引出我们的State修饰符了

响应式数据- 数据驱动视图更新

## 4.5. 基础-组件状态

@State装饰的变量，或称为状态变量，一旦变量拥有了状态属性，就和自定义组件的渲染绑定起来。当状态改变时，UI会发生对应的渲染改变。

如何使用 `@State` 定义一个状态变量？

1）组件变量，不具备驱动UI更新能力。

```typescript
@Entry
@Component
struct Event {
  @State loginName: string = ""
  @State password: string = ""
}
```

加上该修饰符后，你惊奇的发现按钮随着数据的变化在变化，因为我们在值改变的时候赋值，造成了build的重新执行，getBtnEnable函数会重新执行，来保证我们状态的变化。

需要注意的是，State修饰的类型-[官网介绍](https://developer.harmonyos.com/cn/docs/documentation/doc-guides-V3/arkts-state-0000001474017162-V3)

Object、class、string、number、boolean、enum类型，以及这些类型的数组。嵌套类型的场景请参考[观察变化](https://developer.harmonyos.com/cn/docs/documentation/doc-guides-V3/arkts-state-0000001474017162-V3#section135631413173517)。

类型必须被指定。

不支持any，不支持简单类型和复杂类型的联合类型，不允许使用undefined和null。



- 接下来，我们完成一个数据修改的案例，来看下state的特性

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062312443.png)

1. 先声明一个User类-class是ArkTS主推的定义对象类型的形式，interface/type也可以用，但是有限制

```typescript
class User {
  name: string
  age: number
  address: {
    province: string
    city: string
    area: string
  }
}
class User {
  name: string
  age: number
  address: {
    province: string
    city: string
    area: string
  }
}

@Entry
@Component
struct StateCase {
  @State user: Partial<User>  = {
    name: '老高',
    age: 34,
    address: {
      province: '北京',
      city: '北京',
      area: '昌平'
    }
  }

  build() {
    Row() {
      Column() {
        Text(JSON.stringify(this.user))
        Row({ space: 15 }) {
          Text("姓名：")
          TextInput({ text: this.user.name }).layoutWeight(1)
        }.padding(10)
        Row({ space: 15 }) {
          Text("年龄：")
          TextInput({ text: this.user.age.toString() }).layoutWeight(1)
        }.padding(10)
        Row({ space: 15 }) {
          Text("地址：")
          TextInput({ text: this.user.address?.province }).layoutWeight(1)
          TextInput({ text: this.user.address?.city }).layoutWeight(1)
          TextInput({ text: this.user.address?.area }).layoutWeight(1)
        }.padding(10)


      }
      .width('100%')
    }
    .height('100%')
  }
}
```

- 监听input的值改变事件然后赋值

```typescript
  TextInput({ text: this.user.name }).layoutWeight(1)
            .onChange(value => {
              this.user.name = value
            })
```



大家发现，如果state是一个对象，我们改第一层的数据没问题，但是第二层的数据不触发更新

怎么办？

我们依然可以改第一层的数据 如

this.user.address = { ...this.user.address, province: value  }

完整代码

```typescript
class User {
  name: string
  age: number
  address: {
    province: string
    city: string
    area: string
  }
}

@Entry
@Component
struct StateCase {
  @State user: Partial<User>  = {
    name: '老高',
    age: 34,
    address: {
      province: '北京',
      city: '北京',
      area: '昌平'
    }
  }

  build() {
    Row() {
      Column() {
        Text(JSON.stringify(this.user))
        Row({ space: 15 }) {
          Text("姓名：")
          TextInput({ text: this.user.name }).layoutWeight(1)
            .onChange(value => {
              this.user.name = value
            })
        }.padding(10)
        Row({ space: 15 }) {
          Text("年龄：")
          TextInput({ text: this.user.age.toString() }).layoutWeight(1)
            .onChange(value => {
              this.user.age = parseInt(value)
            })
        }.padding(10)

        Row({ space: 15 }) {
          Text("地址：")
          TextInput({ text: this.user.address?.province }).layoutWeight(1)
            .onChange(value => {
              this.user.address = { ...this.user.address, province: value }
            })
          TextInput({ text: this.user.address?.city }).layoutWeight(1)
            .onChange(value => {
              this.user.address = { ...this.user.address, city: value }
            })
          TextInput({ text: this.user.address?.area }).layoutWeight(1)
            .onChange(value => {
              this.user.address = { ...this.user.address, area: value }
            })
        }.padding(10)


      }
      .width('100%')
    }
    .height('100%')
  }
}
```

# 5. 回顾总结复盘

- 鸿蒙学习的套路

对前端稍微友好一些， 样式和布局思想基本一致， TypeScript-TS-JS-ES6+

```typescript
箭头函数-改变this指向- 到父级作用域
ArtTS
const add = () => {
  // this指向 父级作用域
  
}
function add () {
  
}
模板字符串
`${变量} and ${变量}`

解构赋值- ArkTS Next版本不支持

延展运算符 ...

  const a = { name: '章三', age: 18, sex: '男‘  }

  const b = { ...a }

promise ES6- 解决回调地狱的一种解决方案

new Promise(function (resolve, reject) {
  resolve(1)
  //reject()
}).then(result => {
  return 2
}).then(result => {
  throw new Error() //失败
}).catch(error => {
  
})
// pending 等待 fullfilled 成功 rejected 失败

async await - 让我们用同步的方式去写异步
```

- 复习

鸿蒙HarmonyOS-State模型开始应用- ArkTS(华为自己造的)- ArkUI(绘制页面)

- Stage(FA不再推荐)- UIAbility- Page写页面
- 装饰器（修饰符）

```typescript
@Entry(页面级组件)
@Component
struct Index {
  @State    // 响应式数据 数据变化 => UI更新
  build() {
    // 可以没有内容
    // entry 如果只放一个组件- 这个组件应该是一个容器组件- 不能同时放两个
  }
}
```

\>

State修饰符声明的数据，只能监听到第一层的数据变化，如果发现第二层变化-不会触发UI更新

```typescript
@State
user: 类型 = {
  a: {
    b: '1323'
  }
}

this.user.a = { ...this.user.a, b: '456'  }
```

- 手搓一个短信验证码的倒计时案例

```typescript
@Entry
@Component
struct CounterCase {
@State count: number = 5
timer: number = -1
  build() {
    Row() {
      Column() {
        Button(this.count < 5 ? `还剩${this.count}秒` : "发送验证码")
          .onClick(() => {
            if(this.count === 5) {
              // 计时器
              this.timer = setInterval(() => {
                if(this.count === 0) {
                  clearInterval(this.timer)
                  this.count = 5 // 回到初始值
                  return
                }
                this.count--
              }, 1000)
            }

          })
      }
      .width('100%')
    }
    .height('100%')
  }
}
```

- 问题

# 6. 样式处理

## 6.1. 样式-语法(链式&枚举)

ArkTS以声明方式组合和扩展组件来描述应用程序的UI；
同时还提供了基本的属性、事件和子组件配置方法，帮助开发者实现应用交互逻辑。

1）样式属性

- 属性方法以 `.` 链式调用的方式配置系统组件的样式和其他属性

```typescript
@Entry
@Component
struct Index {
  build() {
    Text('演示')
      .backgroundColor('red')
      .fontSize(50)
      .width('100%')
      .height(100)
  }
}
```

2）枚举值

- 对于系统组件，ArkUI还为其属性预定义了一些枚举类型。[文档链接](https://developer.harmonyos.com/cn/docs/documentation/doc-references-V3/ts-appendix-enums-0000001478061741-V3)

```typescript
@Entry
@Component
struct Index {
  build() {
    Text('演示')
      .fontSize(50)
      .width('100%')
      .height(100)
      .backgroundColor(Color.Blue)
      .textAlign(TextAlign.Center)
      .fontColor(Color.White)
  }
}
```



- 样式相关属性通过链式函数的方式进行设置
- 如果类型是枚举的，通过枚举传入对应的值

注意： 有的属性**强烈建议**使用枚举（大部分枚举值都是数字，但是数字无法体现代码含义）

有的组件如fontColor可以使用系统自带颜色枚举，也可以使用色值



## 6.2. 样式-单位vp和适配

知道 `vp` 单位，以及适配思想

1） `vp` 是什么？virtual pixel

-  屏幕密度相关像素，根据屏幕像素密度转换为屏幕物理像素，当数值不带单位时，默认单位 `vp`；在实际宽度为1440物理像素的屏幕上，`1vp` 约等于 `3px`（物理像素） 

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062331298.png)  

在样式中，我们如果写px，那么px直接表示的是物理像素，也就是分辨率，那么我们的手机分辨率密度各有不同，无法针对这种密度写一个固定值，所以vp会自动根据手机密度去进行适配，所以vp它提供了一种灵活的方式来适应不同屏幕密度的显示效果。



设计图按照1080设计- 换算成360写vp就可以了

-  上图的意思是，使用这个单位在不同屏幕物理分辨率的实际尺寸一致(A设备1英寸，B设备1英寸)。 

2）之前 `vw` 、`rem` 和 `rpx` 相对于屏幕宽度的单位，可以实现等比例适配，`vp` 可以吗？



```typescript
@Entry
@Component
struct StyleCase {
  @State message: string = 'Hello World'

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(50)
          .fontWeight(FontWeight.Bold)
      }
      .width('100%').alignItems(2)
    }.width('100%')
    .height('100%')
    .onAreaChange((_, newArea) => {
      AlertDialog.show({ message: JSON.stringify(newArea) })
    })
  }
}
```

我们发现：不同的设备屏幕的宽度 `vp` 是不一致的，那怎么适配呢？

3）根据官方的文档，结合自己的理解，采用：伸缩布局，网格系统，栅格系统进行布局适配。

伸缩 `layoutWeight(flex: number)` 占剩余空间多少份，可以理解成CSS的 `flex: 1`

如图-手机端

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062331420.png)

-pad

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062331489.png)

我们可以使用layoutWeight属性，让右侧内容去占满剩余宽度

```typescript
build() {
    Row() {
      Text("左侧内容")
      Text("右侧内容")
        .textAlign(TextAlign.End)
        .width('80%')
        .height(60)
        .backgroundColor('red')
        .layoutWeight(1)
    }.width('100%')
    .height('100%')

  }
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062331583.png)



- 内容等比例缩放-可以使用aspectRatio属性设置宽高比

设置元素宽高比 `aspectRatio(ratio: number)`

如我们如果希望一个元素始终占整个屏幕宽度的50%，且为一个正方形

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062331889.png)

```typescript
 Column()
        .width('50%')
        .height('50%')
        .backgroundColor('blue')
        .aspectRatio(1)
@Entry
@Component
struct Index {
  build() {
    Text('left')
      .width('50%')
        // 宽高比例
      .aspectRatio(1)
      .backgroundColor('red')
  }
}
```



- vp 是鸿蒙默认单位，和屏幕像素有关，最终表现视觉大小在任何设备一致
- 鸿蒙一般以伸缩 `layoutWeight`、网格、栅格进行布局适配，如要等比例缩放可以设置高宽比 `aspectRatio`



## 6.3. Image和资源Resource

项目开发离不开图片-图片在页面中必须使用Image组件

Image为图片组件，常用于在应用中显示图片。Image支持加载string、[PixelMap](https://developer.harmonyos.com/cn/docs/documentation/doc-references-V3/js-apis-image-0000001477981401-V3#ZH-CN_TOPIC_0000001523648994__pixelmap7)和[Resource](https://developer.harmonyos.com/cn/docs/documentation/doc-references-V3/ts-types-0000001477981241-V3#ZH-CN_TOPIC_0000001573928889__resource)类型的数据源，支持png、jpg、bmp、svg和gif类型的图片格式。

- 使用本地图片-拖一张图片放置到ets目录下-比如assets文件下

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062340995.png)

```typescript
 Image('/assets/a.png').width(80).height(80)
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062340009.png)

- 使用Resource下的图片-media

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062340038.png)

```typescript
Image($r('app.media.github')).width(80).height(80)
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062340074.png)

- 使用Resource下的图片-rawfile

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062340099.png)

```typescript
 Image($rawfile("b.png")).width(80).height(80)
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062340171.png)

- 使用网络图片

```typescript
Image("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2F2bf1b169-d217-44c3-a5b3-dd00813bc20d%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1704614176&t=e15a2fd5193aeeb24fc95b5dbe395907").width(80).height(80)
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401062340214.png)

尤其注意： 使用网络图片时，在preview中时，可以预览，但是在模拟器和真实项目中，必须申请网络权限



```plain
"requestPermissions": [{
  "name":"ohos.permission.INTERNET"
}],
```

接下来，我们手写一个知乎的评论



![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401070033469.png)



设计稿一般是1080px：(这里没有设计稿，提供了一些尺寸)



- Nav 

- - 左侧返回按钮24vp高宽背景颜色`#f5f5f5`，图标12vp尺寸颜色`#848484`
  - 标题18vp

- Comment 

- - 头像尺寸32vp高宽，右侧间距10vp
  - 标题15vp，颜色默认
  - 内容16vp，颜色`#565656`
  - 底部12vp，颜色`#c3c4c5`

```typescript
@Entry
@Component
struct ZhiHu {

  build() {
   Column () {
     HmNavBar()
     HmCommentItem()
   }
  }
}

@Component
struct HmNavBar {
  build() {
    Row(){
      Row() {
        Image($r("app.media.ic_public_arrow_left"))
          .width(16)
          .height(16)
          .fillColor('#848484')
      }
      .justifyContent(FlexAlign.Center)
      .width(24)
      .aspectRatio(1)
      .backgroundColor("#f5f5f5")
      .borderRadius(12)
      .margin({
        left: 15
      })
      Text("评论回复")
        .layoutWeight(1)
        .textAlign(TextAlign.Center)
        .fontSize(18)
        .padding({
          right: 39
        })
    }
    .height(40)
    .border({
      color: '#f4f4f4',
      width: {
        bottom: 0.5
      }
    })
  }
}

@Component
struct HmCommentItem {
  build() {
    Row() {
      // 左侧头像
      Image('/assets/test.jpeg')
        .width(32)
        .aspectRatio(1)
        .borderRadius(16)
      // 评论区
      Column({ space: 10 }) {
        Text("周杰伦")
          .fontWeight(FontWeight.Bold)
        Text("意大利拌面应该使用42号钢筋混凝土再加上量子力学缠绕最后通过不畏浮云遮望眼")
          .lineHeight(20)
        .fontSize(16)
        .fontColor('#565656')
        // 底部内容
        Row() {
          Text("10-12 .ip属地北京")
            .fontSize(12)
            .fontColor("#c3c4c5")
          Row() {
           Image($r("app.media.favorite_block"))
             .width(12)
             .aspectRatio(1)
             .fillColor('#c3c4c5')  // 填充颜色
             .margin({
               right: 5
             })
            Text("100")
              .fontSize(12)
              .fontColor("#c3c4c5")
          }
        }.justifyContent(FlexAlign.SpaceBetween)
        .width('100%')
      }
      .alignItems(HorizontalAlign.Start)
      .layoutWeight(1)
      .margin({
        left: 10
      })
    }.padding(15)
    .alignItems(VerticalAlign.Top)
  }
}
```



华为官方图标下载 [链接](https://developer.harmonyos.com/cn/design/harmonyos-icon/)

## 6.4. 样式-[@Styles ]() 复用 

注意： Styles和Extend均只支持在当前文件下的全局或者组件内部定义，如果你想要在其他文件导出一个公共样式，导出公共使用，ArtTS是不支持的，这种方式还是需要考虑组件复用。

在开发过程中会出现大量代码在进行重复样式设置，`@Styles` 可以帮我们进行样式复用

通用属性 通用事件

   在Styles修饰的函数中能够点出来就是通用属性和事件

   Styles修饰的函数不允许传参数

- 当前 `@Styles` 仅支持 [通用属性](https://developer.harmonyos.com/cn/docs/documentation/doc-references-V3/ts-universal-attributes-size-0000001428061700-V3) 和 [通用事件](https://developer.harmonyos.com/cn/docs/documentation/doc-references-V3/ts-universal-events-click-0000001477981153-V3)。

全局Styles不支持箭头函数语法

- 支持 **全局** 定义和 **组件内** 定义，同时存在组件内覆盖全局生效。

```typescript
import promptAction from '@ohos.promptAction'
@Styles function textStyle () {
  .width(100)
  .height(50)
  .backgroundColor(Color.Pink)
  .borderRadius(25)
  .onClick(() => {
    promptAction.showToast({
       message: "测试"
    })
  })
}

@Entry
@Component
struct StyleRepeat {
  @Styles
  textStyle () {
    .width(200)
    .height(50)
    .backgroundColor(Color.Pink)
    .borderRadius(25)
    .onClick(() => {
      promptAction.showToast({
        message: "测试~~~"
      })
    })
  }
  build() {
    Row() {
      Column({ space: 15 }) {
        Text("测试")
          .textStyle()
          .textAlign(TextAlign.Center)
        Text("测试2")
          .textStyle()
          .textAlign(TextAlign.Center)
      }
      .width('100%')
    }
    .height('100%')
  }
}
```



![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401070143520.png)







## 6.5. 样式-[@Extends ]() 复用 

假设我们就想针对 Text进行字体和样式的复用，此时可以使用Extend来修饰一个全局的方法

- 使用 `@Extend` 装饰器修饰的函数只能是 **全局**
- 函数可以进行 **传参**，如果参数是状态变量，状态更新后会刷新UI
- 且参数可以是一个函数，实现复用事件且可处理不同逻辑



![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401070150345.png)



```typescript
// 全局  原生组件                     参数
//  ↓     ↓                          ↓ 
@Extend(Text) function textInputAll (callback?: () => void) {
  .width(100)
  .height(50)
  .backgroundColor(Color.Pink)
  .borderRadius(25)
  .textAlign(TextAlign.Center)
  .fontColor(Color.White)
  .onClick(() => {
    callback && callback()
  })
}
```

需求：把 Text 改成按钮样式，且绑定 click 事件执行不同逻辑

```typescript
@Entry
@Component
struct ExtendCase {
  build() {
    Row() {
      Column({ space: 20 }) {
        Text("测试2")
          .text2Style()
        Button("按钮")
          .btnStyle()
        TextInput()
          .inputStyle()
      }
      .width('100%')
    }
    .height('100%')
  }
}

@Extend(Text) function text2Style () {
  .width(120)
  .height(60)
  .borderRadius(30)
  .backgroundColor(Color.Pink)
  .textAlign(TextAlign.Center)
  .fontSize(20)
}
@Extend(Button) function btnStyle () {
  .width(120)
  .height(60)
  .borderRadius(30)
  .backgroundColor(Color.Pink)
  .fontSize(20)
}
//
@Extend(TextInput) function inputStyle () {
  .width(120)
  .height(60)
  .borderRadius(30)
  .backgroundColor(Color.Pink)
  .textAlign(TextAlign.Center)
  .fontSize(20)
}
```

## 6.6. 多态样式

@Styles和@Extend仅仅应用于静态页面的样式复用，stateStyles可以依据组件的内部状态的不同，快速设置不同样式。这就是我们本章要介绍的内容stateStyles（又称为：多态样式）。

ArkUI 提供以下四种状态：

- focused：获焦态。
- normal：正常态。
- pressed：按压态。
- disabled：不可用态。



- 准备一个扩展样式

```typescript
@Extend(Text) function textStyle() {
  .width(100)
  .height(40)
  .textAlign(TextAlign.Center)
  .borderRadius(20)
  .backgroundColor(Color.Gray)
  .fontColor(Color.White)
}
 Text("登录").textStyle()
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401070159247.png)

我们想要在按压Text的时候 给它一个扩大两倍的效果



![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401070159292.png)



```typescript
@Entry
@Component
struct StateStylesCase {
  @State message: string = 'Hello World'
  @State textEnable: boolean = true
  @Styles
  pressStyle() {
    .width(200)
    .height(80)
    .borderRadius(40)
    .backgroundColor(Color.Pink)
  }

  @Styles
  disableStyle() {
    .backgroundColor(Color.Red)
  }
  @Styles
  inputFocusStyle() {
    .border({
      color: Color.Red,
      width: 1
    })
  }

  @Styles
  inputNormalStyle (){
    .border({
      color: Color.Red,
      width: 0
    })
  }

  build() {
    Row() {
      Column({ space: 20 }) {
        Row() {
          TextInput()
            .height(40)
            .stateStyles({
              focused: this.inputFocusStyle,
              normal: this.inputNormalStyle
            })
        }.padding({
          left: 10,
          right: 10
        })
        Row() {
          TextInput()
            .height(40)
            .stateStyles({
              focused: this.inputFocusStyle,
              normal: this.inputNormalStyle
            })
        }.padding({
          left: 10,
          right: 10
        })

        Text("测试")
          .textStateStyle()
          .stateStyles({
            pressed: this.pressStyle,
            disabled: this.disableStyle
          }).enabled(this.textEnable)
        Button("文本设置禁用")
          .onClick(() => {
            this.textEnable = !this.textEnable
          })
      }
      .width('100%')
    }
    .height('100%')
  }
}

@Extend(Text) function textStateStyle() {
  .width(100)
  .height(40)
  .textAlign(TextAlign.Center)
  .borderRadius(20)
  .backgroundColor(Color.Gray)
  .fontColor(Color.White)
}
```

**踩坑分享**-  目前版本的编辑工具里不能设置通用属性之外的样式，比如fontColor 和TextAlign不可设置-会有异常

- 禁用状态样式

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401070159368.png)

```typescript
  build() {
    Row() {
      Column({ space: 15 }) {
        Text("登录")
          .textStyle()
          .stateStyles({
            pressed: this.textStylePressed,
            disabled: {
              .backgroundColor('#ccc')
            }
          }).enabled(!this.disable)
        Button("文本禁用/开启")
          .onClick(() => {
            this.disable = !this.disable
          })
      }
      .width('100%')
    }
    .height('100%')
  }
```

- 获焦状态

获焦状态最好使用TextInput来测试

坑点- 要同时设置TextInput的normal和focus属性

```typescript
          TextInput()
            .stateStyles({
              normal: {
                .border({
                  width: 0,
                  color: 'red'
                })
              },
              focused: {
                .border({
                  width: 1,
                  color: 'red'
                })
              }
            })

          TextInput()
            .stateStyles({
              focused: {
                .border({
                  width: 1,
                  color: 'red'
                })
              },
              normal: {
                .border({
                  width: 0,
                  color: 'red'
                })
              },
            })
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401070159461.png)

总体代码

```typescript
@Extend(Text) function textStyle() {
  .width(100)
  .height(40)
  .textAlign(TextAlign.Center)
  .borderRadius(20)
  .backgroundColor(Color.Gray)
  .fontColor(Color.White)
}

@Extend(Text) function textStyle2() {
  .width(200)
  .height(80)
  // .textAlign(TextAlign.Center)
  .borderRadius(40)
  .backgroundColor(Color.Gray)
  // .fontColor(Color.Blue)
}


@Entry
@Component
struct MultiStyleState {
  @State disable:boolean = false
  @State focus: boolean = false
  @Styles  textStylePressed() {
  .width(200)
  .height(80)
  .borderRadius(40)
  .backgroundColor(Color.Gray)

}

  build() {
    Row() {
      Column({ space: 15 }) {
        Text("登录")
          .textStyle()
          .stateStyles({
            pressed: this.textStylePressed,
            disabled: {
              .backgroundColor('#ccc')
            }
          })
          .enabled(!this.disable)
          .focusable(this.focus)
        Column({ space: 15 }) {
          TextInput()
            .stateStyles({
              normal: {
                .border({
                  width: 0,
                  color: 'red'
                })
              },
              focused: {
                .border({
                  width: 1,
                  color: 'red'
                })
              }
            })

          TextInput()
            .stateStyles({
              focused: {
                .border({
                  width: 1,
                  color: 'red'
                })
              },
              normal: {
                .border({
                  width: 0,
                  color: 'red'
                })
              },
            })
        }.padding({ left: 15, right: 15 })

        Button("文本禁用/开启")
          .onClick(() => {
            this.disable = !this.disable
          })

      }
      .width('100%')
    }
    .height('100%')
  }
}
```



- 使用比较多的应该是 `normal` `pressed` 结合下的按压效果
- `enabled(true|false)` 开启|禁用 `focusable(true|false)` 开启获取焦点能力|关闭

注意：

- 页面初始化的时候，默认第一个能获取焦点的元素，会自动获取焦点



# 7. 界面渲染

## 7.1.  渲染-条件渲染

在ArkTS中 我们要根据某个状态来控制元素或者组件的显示隐藏 可以采用条件渲染

- if/else（创建销毁元素）
- 元素高宽-透明度-位置控制 （属性控制）
- visibility属性控制

- 使用if/else

通过一个switch开关来控制图片的显示隐藏

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401071408097.png)

```typescript
@Entry
@Component
struct ConditionCase {
  @State showImg: boolean = false

  build() {
    Row() {
      Column() {
        Toggle({
          type: ToggleType.Switch,
          isOn: this.showImg
        })
          .onChange((isON) => {
            this.showImg = isON
          })
        if(this.showImg) {
          Image('/assets/test.jpeg')
            .width(150)
            .height(150)
        }

      }
      .width('100%')
    }
    .height('100%')
  }
}
```

- 多种条件控制

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401071408144.png)![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401071408185.png)

```typescript
  @State selectList: { value: string }[] = [{ value: '勇士' }, { value: '湖人' }, { value: '太阳' }]
  @State selectIndex: number = -1

// 根据不同的条件显示不同的城市
      Select(this.selectList)
        .onSelect(index => {
            this.selectIndex = index
        })

      if(this.selectIndex === 0) {
        Text("史蒂芬库里")
      }
      else if(this.selectIndex === 1) {
        Text("勒布朗詹姆斯")
      }
      else if(this.selectIndex === 2) {
        Text("凯文杜兰特")
      }
```

- 控制元素高宽

```typescript
Image($r('app.media.b'))
        .width(this.isOn ? 100 : 0)
        .height(this.isOn ? 100 : 0)
        .borderRadius(8)
```

- 控制visibility属性- Hidden和None两种

```typescript
 Image($r('app.media.b'))
        .width(100)
        .height(100)
        .borderRadius(8)
        .visibility(this.isOn ? Visibility.Visible: Visibility.Hidden)

Image($r('app.media.b'))
        .width(100)
        .height(100)
        .borderRadius(8)
        .visibility(this.isOn ? Visibility.Visible: Visibility.None)
@Entry
@Component
struct ConditionCase {
  @State showImg: boolean = false
  @State selectIndex: number = -1
  build() {
    Row() {
      Column({ space: 20 }) {
        Toggle({
          type: ToggleType.Switch,
          isOn: this.showImg
        })
          .onChange((isON) => {
            this.showImg = isON
          })
        if(this.showImg) {
          Image('/assets/test.jpeg')
            .width(150)
            .height(150)
        }
        Image('/assets/test.jpeg')
          .width(150)
          .height(150)
          .opacity(this.showImg ? 1 : 0)
        Image('/assets/test.jpeg')
          .width(150)
          .height(150)
           .visibility(this.showImg ? Visibility.Visible : Visibility.None)

        // 放置一个select组件
        Select([{ value: '勇士' },{ value: '湖人' },{ value: '太阳' }])
          .onSelect((index) => {
              this.selectIndex = index
          })
        if(this.selectIndex === 0) {
          Text("库里")
        }else if(this.selectIndex === 1) {
          Text("詹姆斯")
        }
        else if(this.selectIndex === 2) {
          Text("杜兰特")
        }
      }
      .width('100%')
    }
    .height('100%')
  }
}
```

visibility的Hidden会占位，元素隐藏，Node隐藏且不占位



案例-实现加载数据的loading效果

在页面加载后，三秒钟之后才显示数据，之前显示loading进度条

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401071408232.png)

- 封装loading组件

```typescript
@Entry
@Component
struct LoadingCase {
  @State message: string = 'Hello World'
  @State showLoading: boolean = false
  // 初始化执行的回调函数
  aboutToAppear() {
    this.showLoading = true
    setTimeout(() => {
      this.showLoading = false
    }, 3000)
  }

  build() {
    Row() {
      Column() {
        if(this.showLoading) {
          HmLoading()
        }else {
          Text(this.message)
            .fontSize(50)
            .fontWeight(FontWeight.Bold)
        }

      }
      .width('100%')
    }
    .height('100%')
  }
}


@Component
struct HmLoading {
  @State count: number = 0

  aboutToAppear() {
    setInterval(() => {
      if(this.count  === 100) {
        this.count = 0
      }
      this.count++
    }, 10)
  }
  build() {
    Progress({
       value: this.count,
       total: 100,
       type: ProgressType.ScaleRing
    }).style({
      strokeWidth: 6,
      scaleCount: 20,
    })
  }
}
```

- 条件渲染

```typescript
@Entry
@Component
struct ConditionLoading {
  @State showLoading: boolean = false
  aboutToAppear() {
    this.showLoading = true
    setTimeout(() => {
      this.showLoading = false
    }, 3000)
  }
  build() {
    Column() {
      if(this.showLoading) {
        Loading()
      }else {
        Text("Hello World")
          .width('100%')
          .textAlign(TextAlign.Center)
          .fontSize(50)
      }

    }
    .height('100%')
    .justifyContent(FlexAlign.Center)
  }
}
```

## 7.2.  渲染-循环渲染

循环渲染使用 ForEach方法来进行

`ForEach` 接口基于数组类型数据来进行循环渲染，需要与容器组件配合使用。

```typescript
ForEach(
  // 数据源
  arr: Array,
  // 组件生成函数
  itemGenerator: (item: Array, index?: number) => void,
  // 键值生成函数
  keyGenerator?: (item: Array, index?: number): string => string
)
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401071408351.png)

- 新建一个list数据，进行循环

```typescript
class GoodItem {
  goods_name: string
  goods_price: number
  goods_img: string
  goods_count: number
  id: number
}
```

- 拷贝图片到assets

[📎图片.zip](https://www.yuque.com/attachments/yuque/0/2023/zip/8435673/1702294868460-c7ec0240-a8fc-45e5-b463-69256fcd8ea3.zip)

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401071408850.png)

- 声明数据

```typescript
@State list: GoodItem[] = [
    {
      "id": 1,
      "goods_name": "班俏BANQIAO超火ins潮卫衣女士2020秋季新款韩版宽松慵懒风薄款外套带帽上衣",
      "goods_img": "assets/1.webp",
      "goods_price": 108,
      "goods_count": 1,
    },
    {
      "id": 2,
      "goods_name": "嘉叶希连帽卫衣女春秋薄款2020新款宽松bf韩版字母印花中长款外套ins潮",
      "goods_img": "assets/2.webp",
      "goods_price": 129,
      "goods_count": 1,
    },
    {
      "id": 3,
      "goods_name": "思蜜怡2020休闲运动套装女春秋季新款时尚大码宽松长袖卫衣两件套",
      "goods_img": "assets/3.webp",
      "goods_price": 198,
      "goods_count": 1,
    },
    {
      "id": 4,
      "goods_name": "思蜜怡卫衣女加绒加厚2020秋冬装新款韩版宽松上衣连帽中长款外套",
      "goods_img": "assets/4.webp",
      "goods_price": 99,
      "goods_count": 1,
    },
    {
      "id": 5,
      "goods_name": "幂凝早秋季卫衣女春秋装韩版宽松中长款假两件上衣薄款ins盐系外套潮",
      "goods_img": "assets/5.webp",
      "goods_price": 156,
      "goods_count": 1,
    },
    {
      "id": 6,
      "goods_name": "ME&CITY女装冬季新款针织抽绳休闲连帽卫衣女",
      "goods_img": "assets/6.webp",
      "goods_price": 142.8,
      "goods_count": 1,
    },
    {
      "id": 7,
      "goods_name": "幂凝假两件女士卫衣秋冬女装2020年新款韩版宽松春秋季薄款ins潮外套",
      "goods_img": "assets/7.webp",
      "goods_price": 219,
      "goods_count": 2,
    },
    {
      "id": 8,
      "goods_name": "依魅人2020休闲运动衣套装女秋季新款秋季韩版宽松卫衣 时尚两件套",
      "goods_img": "assets/8.webp",
      "goods_price": 178,
      "goods_count": 1,
    },
    {
      "id": 9,
      "goods_name": "芷臻(zhizhen)加厚卫衣2020春秋季女长袖韩版宽松短款加绒春秋装连帽开衫外套冬",
      "goods_img": "assets/9.webp",
      "goods_price": 128,
      "goods_count": 1,
    },
    {
      "id": 10,
      "goods_name": "Semir森马卫衣女冬装2019新款可爱甜美大撞色小清新连帽薄绒女士套头衫",
      "goods_img": "assets/10.webp",
      "goods_price": 153,
      "goods_count": 1,
    }
  ]
```

使用ForEach遍历

```typescript
@Entry
@Component
struct ForEachCase {
  @State list: GoodItem[] = [
    {
      "id": 1,
      "goods_name": "班俏BANQIAO超火ins潮卫衣女士2020秋季新款韩版宽松慵懒风薄款外套带帽上衣",
      "goods_img": "assets/1.webp",
      "goods_price": 108,
      "goods_count": 1,
    },
    {
      "id": 2,
      "goods_name": "嘉叶希连帽卫衣女春秋薄款2020新款宽松bf韩版字母印花中长款外套ins潮",
      "goods_img": "assets/2.webp",
      "goods_price": 129,
      "goods_count": 1,
    },
    {
      "id": 3,
      "goods_name": "思蜜怡2020休闲运动套装女春秋季新款时尚大码宽松长袖卫衣两件套",
      "goods_img": "assets/3.webp",
      "goods_price": 198,
      "goods_count": 1,
    },
    {
      "id": 4,
      "goods_name": "思蜜怡卫衣女加绒加厚2020秋冬装新款韩版宽松上衣连帽中长款外套",
      "goods_img": "assets/4.webp",
      "goods_price": 99,
      "goods_count": 1,
    },
    {
      "id": 5,
      "goods_name": "幂凝早秋季卫衣女春秋装韩版宽松中长款假两件上衣薄款ins盐系外套潮",
      "goods_img": "assets/5.webp",
      "goods_price": 156,
      "goods_count": 1,
    },
    {
      "id": 6,
      "goods_name": "ME&CITY女装冬季新款针织抽绳休闲连帽卫衣女",
      "goods_img": "assets/6.webp",
      "goods_price": 142.8,
      "goods_count": 1,
    },
    {
      "id": 7,
      "goods_name": "幂凝假两件女士卫衣秋冬女装2020年新款韩版宽松春秋季薄款ins潮外套",
      "goods_img": "assets/7.webp",
      "goods_price": 219,
      "goods_count": 2,
    },
    {
      "id": 8,
      "goods_name": "依魅人2020休闲运动衣套装女秋季新款秋季韩版宽松卫衣 时尚两件套",
      "goods_img": "assets/8.webp",
      "goods_price": 178,
      "goods_count": 1,
    },
    {
      "id": 9,
      "goods_name": "芷臻(zhizhen)加厚卫衣2020春秋季女长袖韩版宽松短款加绒春秋装连帽开衫外套冬",
      "goods_img": "assets/9.webp",
      "goods_price": 128,
      "goods_count": 1,
    },
    {
      "id": 10,
      "goods_name": "Semir森马卫衣女冬装2019新款可爱甜美大撞色小清新连帽薄绒女士套头衫",
      "goods_img": "assets/10.webp",
      "goods_price": 153,
      "goods_count": 1,
    }
  ]

  build() {
     List({ space: 20 }) {
       // 自带滚动条 自上向下排列
       // ForEach(数据源，回调函数 => ArtUI, 生成key的函数)
       ForEach(this.list, (item: GoodItem) => {
         ListItem() {
           Row() {
             Image(item.goods_img)
             .width(100)
             .height(160)
             .borderRadius(8)
             Column() {
               Text(item.goods_name)
                 .fontSize(14)
                 .lineHeight(20)
                 .fontWeight(600)
               Text(`¥ ${item.goods_price.toString()}`)
                 .fontSize(20)
                 .fontColor(Color.Red)
             }
             .layoutWeight(1)
             .margin({
               left: 10
             })
             .height(160)
             .justifyContent(FlexAlign.SpaceBetween)
             .alignItems(HorizontalAlign.Start)
           }
         }
       })

     }
    .padding(15)
  }
}

// 声明商品的类型

class GoodItem {
  goods_name: string
  goods_price: number
  goods_img: string
  goods_count: number
  id: number
}
```



关于 `keyGenerator` 键生成函数的一些思考

- 如果你的list只是渲染，不会发生任何变化，没必要给
- ArtTS会依据当前的所以index_ + JSON.stringify(item)生成key
- 如果你有插入行为， 建议给key，否则性能会降低，
- 如果你有在列表中修改对象的行为，key一定要保证在改对象之后也有变化，否则会感知不到变化，无法进行UI渲染（在知乎案例中体现）



# 8. 今日案例-知乎评论

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401102352961.png)



# 简单总结

- 后端同学 java c++ 大数据 UI界面能力可能不理想-别急-Flex-相对定位-绝对定位
- Column/Row
- Text/Button/TextInput
- Flex-List-带滚动条-Scroll(不要对子组件的宽和高进行设置)
- 条件渲染（if else）/循环渲染(ForEach)
- State修饰符(声明响应式数据)- 驱动UI的更新-只会监听第一层数据

## 8.1. 评论列表

- 定义一个评论class

class 可以理解为 前端的一个大括号 {}(字面量写法)-  new Object()

let a: 类型 = { } - 下一代ArkTS

let a = new Class()

注意： 下一代要求使用Class必须给定初始值哦

目前虽然4.0是不报错可用的，但是下一代必须这么做，我们遵守下吧

```typescript
export class ReplyItem {
  id: number = 0
  avatar: string | Resource = ""
  author: string = ""
  content: string = ""
  time: string = ""
  area: string = ""
  likeNum: number = 0
  likeFlag?: boolean = false
}
```

- 定义一个评论列表数据- 在Entry组件中

```typescript
@State commentList: ReplyItem[] = [
    {
      id: 1,
      avatar: 'https://picx.zhimg.com/027729d02bdf060e24973c3726fea9da_l.jpg?source=06d4cd63',
      author: '偏执狂-妄想家',
      content: '更何况还分到一个摩洛哥[惊喜]',
      time: '11-30',
      area: '海南',
      likeNum: 34
    },
    {
      id: 2,
      avatar: 'https://pic1.zhimg.com/v2-5a3f5190369ae59c12bee33abfe0c5cc_xl.jpg?source=32738c0c',
      author: 'William',
      content: '当年希腊可是把1：0发挥到极致了',
      time: '11-29',
      area: '北京',
      likeNum: 58
    },
    {
      id: 3,
      avatar: 'https://picx.zhimg.com/v2-e6f4605c16e4378572a96dad7eaaf2b0_l.jpg?source=06d4cd63',
      author: 'Andy Garcia',
      content: '欧洲杯其实16队球队打正赛已经差不多，24队打正赛意味着正赛阶段在小组赛一样有弱队。',
      time: '11-28',
      area: '上海',
      likeNum: 10
    },
    {
      id: 4,
      avatar: 'https://picx.zhimg.com/v2-53e7cf84228e26f419d924c2bf8d5d70_l.jpg?source=06d4cd63',
      author: '正宗好鱼头',
      content: '确实眼红啊，亚洲就没这种球队，让中国队刷',
      time: '11-27',
      area: '香港',
      likeNum: 139
    },
    {
      id: 5,
      avatar: 'https://pic1.zhimg.com/v2-eeddfaae049df2a407ff37540894c8ce_l.jpg?source=06d4cd63',
      author: '柱子哥',
      content: '我是支持扩大的，亚洲杯欧洲杯扩到32队，世界杯扩到64队才是好的，世界上有超过200支队伍，欧洲区55支队伍，亚洲区47支队伍，即使如此也就六成出现率',
      time: '11-27',
      area: '旧金山',
      likeNum: 29
    },
    {
      id: 6,
      avatar: 'https://picx.zhimg.com/v2-fab3da929232ae911e92bf8137d11f3a_l.jpg?source=06d4cd63',
      author: '飞轩逸',
      content: '禁止欧洲杯扩军之前，应该先禁止世界杯扩军，或者至少把亚洲名额一半给欧洲。',
      time: '11-26',
      area: '里约',
      likeNum: 100
    }
  ]
```

- 在主页中渲染

```typescript
 ForEach(this.commentList, (obj: ReplyItem) => {
      CommentItem({ item: obj })
  })
```

- CommentItem组件接收传入数据

```typescript
@Component
struct CommentItem {
  item:  Partial<ReplyItem> = {}
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

            Text(this.item.likeNum?.toString())
              .fontSize(12)
              .margin({
                left: 5
              })
          }.onClick(() => {
            this.changeLike && this.changeLike(this.item as ReplyItem)
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
```

- 由于数据过多，我们可以使用Scroll 组件来包裹整个的Column，注意Column的高度100%要去掉

```typescript
 build() {
      Scroll() {
        Column() {
          // 顶部组件
          HmNavBar()
          // 评论组件
          CommentItem({
            item: {
              id: 1,
              avatar: $r('app.media.b'),
              author: '周杰伦',
              content: '意大利拌面应该使用42号钢筋混凝土再加上量子力学缠绕最后通过不畏浮云遮望眼',
              time: '11-30',
              area: '海南',
              likeNum: 100
            }
          })
          // 分割线
          Divider()
            .strokeWidth(6)
            .color("#f4f5f6")
          ForEach(this.commentList, (item) => {
            CommentItem({ item, changeLike: item => this.changeLike(item) })
          })
        }
        .width('100%')
        .backgroundColor(Color.White)
      }
  }
```



这里因为头像我们用了media的资源图片，所以在之前声明类型的时候，我们特意使用联合类型来声明



![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401102352459.png)



## 8.2. 底部回复按钮

- 底部按钮固定定位的模式可以采用Stack栈布局，特点是后面的元素级别高于前面元素，会贴在上一个元素上，并且使用alignContent属性可以直接让 后加的元素去到想去的位置

 Stack 采用栈布局，

封装ReplyInput组件

```typescript
// 回复评论组件
@Component
struct ReplyInput {
  build() {
   Row() {
     TextInput({ placeholder: '回复～'})
       .layoutWeight(1)
       .backgroundColor("#f4f5f6")
     Text("发布")
       .fontColor("#6ecff0")
       .margin({
         left: 10
       })
   }
    .border({
      color: '#f4f5f6',
      width: {
        top: 1
      }
    })
    .height(50)
    .backgroundColor(Color.White)
    .width('100%')
    .padding({
      left: 10,
      right: 10
    })
  }
}
```

使用Stack包裹整个的主页组件，并设置alignContent为 Alignment.Bottom

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401110000465.png)

Scroll被盖住的地方 需要加个padding

```typescript
 .padding({
      bottom: 60
    })
```

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401110000644.png)

## 8.3. 实现点赞

现在还没学双向绑定，我们使用函数传递的方式

- 在父组件定义一个函数changeLike， 传递给CommentItem

```typescript
changeLike(item: ReplyItem) {
    if (item.likeFlag) {
      item.likeNum--
      item.likeFlag = false
    } else {
      item.likeNum++
      item.likeFlag = true
    }
    let index = this.commentList.findIndex(obj => obj.id === item.id)
    this.commentList.splice(index, 1)
    this.commentList.splice(index, 0， item)

  }
```

由于不一代延展运算符只能用于数组，对于对象不再支持，所以采用对数组采用替换更新的方式来进行响应式更新

- 子组件接收

```typescript
 ForEach(this.commentList, (item: ReplyItem) => {
            CommentItem({ item, changeLike: item => {
              this.changeLike(item)
            }})
  })
```

- 子组件调用

```typescript
@Component
struct CommentItem {
   item:  Partial<ReplyItem> = {}
  changeLike: (item: ReplyItem) => void  = () => {}

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
             this.changeLike(this.item as ReplyItem)
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
```



## 8.4. 进行回复

- 收集输入框内容
- 点击发布传递给父组件
- 父组件加入数据



- 定义State状态，双向绑定input

```typescript
  @State commentStr: string = ''

TextInput({ placeholder: '回复', text: this.commentStr })
        .layoutWeight(1)
        .backgroundColor("#f4f5f6")
        .onChange(value => {
          this.commentStr = value
        })
```

- 父组件定义函数， 传递子组件

```typescript
  addComment(item: ReplyItem) {
    this.commentList.unshift(item)
  }
```

- 传递给回复组件

```typescript
  ReplyInput({
    addComment: item => {
      this.addComment(item)
    } 
  })
```

- 回复组件逻辑

```typescript
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
            this.addComment(obj)
            this.commentStr = ""
          }
        })
```

![img](https://cdn.nlark.com/yuque/0/2023/png/8435673/1702043983342-60f6ad43-759f-41c3-84e8-1a2ad0fa05a4.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_19%2Ctext_6buR6ams56iL5bqP5ZGY%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



问题1-头像好像闪了一下，整个得学习了Observed 和ObjectLink再来解决这个问题

问题2- 没给key好像也没有问题

## 8.5. 给key的疑惑

- 大家发现没有，我们没有给key，我们的id是唯一的，给个id试试
- (item: ReplyItem) => item.id.toString()



此时发现已经没有办法显示点赞了， 为什么？？？？



- 因为ForEach中更新UI时，key也必须连带更新，否则它会认为你没有发生变化，不去触发UI更新， 怎么办
- 直接把id-点赞-点赞数一起作为key就可以了

```typescript
(item: ReplyItem) => JSON.stringify({ id: item.id, flag: item.likeFlag, num: item.likeNum })
```

- 搞定

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202401112352773.png)



完整代码

```typescript
import promptAction from '@ohos.promptAction'
import util from '@ohos.util'

@Entry
@Component
struct ZhiHu {
  @State commentList: ReplyItem[] = [
    {
      id: 1,
      avatar: 'https://picx.zhimg.com/027729d02bdf060e24973c3726fea9da_l.jpg?source=06d4cd63',
      author: '偏执狂-妄想家',
      content: '更何况还分到一个摩洛哥[惊喜]',
      time: '11-30',
      area: '海南',
      likeNum: 34
    },
    {
      id: 2,
      avatar: 'https://pic1.zhimg.com/v2-5a3f5190369ae59c12bee33abfe0c5cc_xl.jpg?source=32738c0c',
      author: 'William',
      content: '当年希腊可是把1：0发挥到极致了',
      time: '11-29',
      area: '北京',
      likeNum: 58
    },
    {
      id: 3,
      avatar: 'https://picx.zhimg.com/v2-e6f4605c16e4378572a96dad7eaaf2b0_l.jpg?source=06d4cd63',
      author: 'Andy Garcia',
      content: '欧洲杯其实16队球队打正赛已经差不多，24队打正赛意味着正赛阶段在小组赛一样有弱队。',
      time: '11-28',
      area: '上海',
      likeNum: 10
    },
    {
      id: 4,
      avatar: 'https://picx.zhimg.com/v2-53e7cf84228e26f419d924c2bf8d5d70_l.jpg?source=06d4cd63',
      author: '正宗好鱼头',
      content: '确实眼红啊，亚洲就没这种球队，让中国队刷',
      time: '11-27',
      area: '香港',
      likeNum: 139
    },
    {
      id: 5,
      avatar: 'https://pic1.zhimg.com/v2-eeddfaae049df2a407ff37540894c8ce_l.jpg?source=06d4cd63',
      author: '柱子哥',
      content: '我是支持扩大的，亚洲杯欧洲杯扩到32队，世界杯扩到64队才是好的，世界上有超过200支队伍，欧洲区55支队伍，亚洲区47支队伍，即使如此也就六成出现率',
      time: '11-27',
      area: '旧金山',
      likeNum: 29
    },
    {
      id: 6,
      avatar: 'https://picx.zhimg.com/v2-fab3da929232ae911e92bf8137d11f3a_l.jpg?source=06d4cd63',
      author: '飞轩逸',
      content: '禁止欧洲杯扩军之前，应该先禁止世界杯扩军，或者至少把亚洲名额一半给欧洲。',
      time: '11-26',
      area: '里约',
      likeNum: 100
    }
  ]

  addComment(item: ReplyItem) {
    this.commentList.unshift(item)
  }

  changeLike(item: ReplyItem) {
    if (item.likeFlag) {
      item.likeNum--
      item.likeFlag = false
    } else {
      item.likeNum++
      item.likeFlag = true
    }
    let index = this.commentList.findIndex(obj => obj.id === item.id)
    this.commentList.splice(index, 1, item)
  }

  build() {
    Stack({ alignContent: Alignment.Bottom }) {
      Scroll() {
        Column() {
          // 顶部组件
          HmNavBar()
          // 评论组件
          CommentItem({
            item: {
              id: 1,
              avatar: $r('app.media.b'),
              author: '周杰伦',
              content: '意大利拌面应该使用42号钢筋混凝土再加上量子力学缠绕最后通过不畏浮云遮望眼',
              time: '11-30',
              area: '海南',
              likeNum: 100
            }
          })
          // 分割线
          Divider()
            .strokeWidth(6)
            .color("#f4f5f6")
          ForEach(this.commentList, (item: ReplyItem) => {
            CommentItem({
              item,
              changeLike: item => {
                this.changeLike(item)
              }
            })
          }, (item: ReplyItem) => JSON.stringify({ id: item.id, flag: item.likeFlag, num: item.likeNum }))
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
  item:  Partial<ReplyItem> = {}
  changeLike: (item: ReplyItem) => void = () => {}

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
          Text(`${this.item?.time} . IP属地 ${this.item?.area}`)
            .fontColor("#c3c4c5")
            .fontSize(12)
          Row() {
            Image($r('app.media.like'))
              .width(14)
              .aspectRatio(1)
              .fillColor(this.item?.likeFlag ? 'red' : "#c3c4c5")

            Text(this.item?.likeNum?.toString())
              .fontSize(12)
              .margin({
                left: 5
              })
          }.onClick(() => {
             this.changeLike(this.item as ReplyItem)
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
  addComment: (item: ReplyItem) => void = () => {}

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
          if (this.commentStr !== "") {
            let obj: ReplyItem = {
              id: Date.now(),
              content: this.commentStr,
              time: `${new Date().getMonth() + 1} - ${new Date().getDate()}`,
              likeNum: 0,
              likeFlag: false,
              author: '老高',
              avatar: $r('app.media.b'),
              area: "北京"
            }
            this.addComment && this.addComment(obj)
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


export class ReplyItem {
  id: number = 0
  avatar: string | Resource = ""
  author: string = ""
  content: string = ""
  time: string = ""
  area: string = ""
  likeNum: number = 0
  likeFlag?: boolean = false
}
```

