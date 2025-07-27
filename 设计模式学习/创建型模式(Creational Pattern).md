###类模型
### 1、简单工厂模式（Simple Factory Pattern） -> 工厂方法模式  (Factory Method Pattern)
* 简单工程模式：又称为静态工厂模式。
* 在实际软件开发中，有时需要创建一些相同父类的实例，为此可以专门定义一工厂类来负责创建这些类的实例。在这种情况下，可以通过传入不同的参数从而获得不同的对象，利用Java语言特征，习惯将创建其他类的实例定义为static方法，外部不需要实例化这个工厂类就能直接创建实例类。
<br>

![](https://www.plantuml.com/plantuml/png/SoWkIImgAStDuL9_lgxXQU_KfzkNFkrhUhPYuTCz_LnScNabgKLfYScf2Y5XrLmAGA1Tbf-Peb2GarYfeA79LSkLd9DONApWaPYPMaH0IMPnQbvnAdv58M50oqhoYogXYXI0pYl9J0QAAnGKvQUK9WFrQmIN56NcfIia5INcfO2qSW5bRMhqz62kWF4HLdA1PK32G56u7LnGUGnnXzIy562G0000)
<br>
* 工厂方法模式：是对简单工厂模式的进一步抽象和推广，克服了简单工厂模式的不够灵活，在增加新的产品时需要改工厂类的判断逻辑，修改代码的违反开闭原则的缺点。
* 工厂类不在负责所有产品的创建，而是将具体创建工作交给子类去做。
<br>

![](https://www.plantuml.com/plantuml/png/hPB1QiCm38RlUWhVrZBctaCeXS7O0uJSZQsq1aSEih9IjhtxadGkgrUs7PPBiFJz-lyetYKnB6CVuEuWCgHginc2eRcYRUmtDfYK7fmtUpOzm79KZy4Z2NWIvCvOL4txPAn4Fhs2cfxxWQCRBJRjQbqrBRnVePa9vZIrNw9t4t0U_6myyg_3A0ggW9ses1MIghhGYrXhlRmPPMHIXCSiMAq1oQbv5cHtdv0I_isV_Vwm1CYg_uYGEp-iADlTPYufLFscNJoIBXPqYoY_02Rec_vSAnK0dsrLw_cFb7QggclBQ0ItMyYg39VE1VurgXu7dQzTqrwVTqXkxCC7)
***
###对象模型
### 2、抽象工厂模式(Abstract Factory Pattern)
* 抽象工厂模式是工厂方法的泛化版，工厂方法模式是一种特殊的抽象工厂模式。在工厂方法模式中，每一个具体工厂只能生产一种具体产品，而在抽象工厂方法中，每一个具体工厂可以生产多个具体产品。
* 如一个电器工厂可以产生多种类型的电器，如海尔工厂可以生产海尔电视机、海尔空调等，Tcl工厂可以生产TCL电视机，TCL空调等，相同品牌的电器构成一个产品族，而相同类型的电器构成一个产品等级结构。
<br>

![](https://www.plantuml.com/plantuml/png/jPB1JiCm38RlVehU027Q-yHX285uWBs0bPWjKff4JZ8q0UzEG9rIhm6qmtgh_Tc__xzTnuISycZXQfTIYAjX21RTTwLNVXoKnHZljC4jumXWN29-HKNTqpEgvFdm3brvRWDxdHLjoDBUHEFTzSsglWZcmV2ZTzgaqY0kd2p0vqobhZZgM3oKaRqtMe1onmIh7RfjkMaCn9WoKvtyyTMze26-e959PgFinKI4bR_ZgjVymy5yPz5JvoTRHCuTXEClrTr_zDg_kLvydqQxvWfGBPsCrqQBaMa0o7GhreZYDtRf7K1UKkJx74xiDHHQ68Y39s8Mn49yYQp9wJpQBm00)
***
### 3、建造者模式(Builder Pattern)
* 将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。建造者模式是一步一步创建一个复杂的对象，它允许用户只通过制定复杂对象的类型和内容就可以构建它们，用户不需要知道内部的具体构建细节。
* 如KFC创建套餐：套餐是一个复杂对象，它一般包含主食（如汉堡、鸡肉卷）和（饮料、可乐）等组成部分，不同的套餐有不同的组成部分，而KFC的服务员可以根据顾客的要求，一步一步装配这些组成部分，构造一份完整的套餐，然后返回给顾客。
<br>

![](https://www.plantuml.com/plantuml/png/bL6xJiCm5Dtz5NUZ1JKVu08g1NL1J1sOyt1BH19NzZKCfL8Nn5A9Nu01YPMLyZijy1VuKHm7AYIsg-FpE3dfIOm5gh8WEwlbw-VyuMD-ktv-NBtTaxI8fOGp5XTJ0W3G6N6U0OKXYhmQEoZJvsKJsvCC1vhPSXWOLLjJhdYUUSA9qNqph4cJCjvujBstnvqqU9WH4YSIHPmYr9txAYyo9ZRLIuq0jGzm01MxjgVpJ0pJfVaE7lTfpN96RL5pc0Ok-7HmV1xdMCScGCCs_li3h1NSK2RDg9HN4eLACGohE390FW4-gXCjVxsuMo-Uz2zyVraILcLWg8ulyl45DXIKUdShBfCecABhiM061mg6IbJcpI_HhTFS4aEL1DzrDFqrDTq-j8DdMrtw_tOnOjYFebq8HwPGcY7z400yYWv32lbH7pfgqhdvYzBVHkdfKLHPV040)

***
### 4、原型模式(Phototype Pattern)
* 为了简化创建过程，可以只需要创建一个对象，然后通过克隆方式复制出多个相同的对象。原型模式的基本工作原理是通过一将一个原型对象传给那个要发动创建的对象，这个要发动创建的对象通过请求原型对象复制原型来实现创建过程。克隆分为浅克隆和深克隆：
* 浅克隆：只克隆所考虑的对象，而不复制它所引用的对象，也就是其中的成员对象并不复制。
<br>

![](https://www.plantuml.com/plantuml/png/SoWkIImgAStDuL9_sZV_wRpkPoiMFjtJ_txFh6TRMv-sTh-SrSQLd9DONApWdvILcbnIpQK01Dh9EVbvgHgQLX11hbgkpBoIrAAqnEHK1MUGL69IJYfKdsjkOcOEH9jkOab9Od96RcfUYPKZd0an4QnCGnERyH2nbCpYWfp4Ig1eMNvc2aGiFRK4OGvG1idGMgZr2A5gg3dvvNav-OafK8E02i5MA-ZgrjX1FGaLqTC3YY2y0nNLqir9JIlHjLD0iimXDIy568O0)
* 深克隆：深克隆把要复制的对象所引用的成员对象也都复制了一遍。
<br>

![](https://www.plantuml.com/plantuml/png/PK-xJWCn4EptAzoHW7pNfuY243K5Nx2nbyHe_P2z1uBZ_ft3Ps4XtCrZnpEd8fXbyWmtLv5K3kEO6CCFCvZeHyDOYhOEBnazW0j2-GKDgMVA3jbzufa9gaizUdGyWrgVHX4qLqz1r47T_u8gtbNZRhy1IvGUE0RQxLVhq_cLZ3J5bSJuKVctwEp6NaXwNjKwXALLwTJSik9xu8YsXorGQwXX-3h-NTEF_uljw5RhO_kaRUo3uKJ1Jfw_0G00)
```
public class Email implements Serializable {
    private Attachment attachment;

    public Email() {
        this.attachment = new Attachment();
    }
    public Object deepClone() throws IOException, ClassNotFoundException {
        //需要将使用流来实现深克隆
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(this);

        ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }

    public Attachment getAttachment() {
        return attachment;
    }
    public void display(){
        System.out.println("查看邮件");
    }
}
```
***
### 5、单例模式(Singleton Pattern)
* 确保某一个类只有一个实例，而且自行实例化并向整个系统提供这个实例，这个类称为单例类，它提供全局访问。分为饿汉式单例和懒汉式单例。
* 饿汉式：在类加载时就初始化实例。构造函数私有话，避免外界利用构造函数创建更多实例。

* 懒汉式：构造函数也私有化，但是在第一次引用时将自己实例化。饿汉式在资源利用效率上不如懒汉式，单懒汉式在初始化实例时有可能耗费大量时间，意味着可能多个线程在此期间引用，需要做好同步化机制进行控制。
<br>

![](https://www.plantuml.com/plantuml/png/TL51IiH04BptA_gsMIHvG9QLlNWGlFc28Tk9WTaUc4oGNV4idWJ_u0Ey2xx61NzXd8cDqxfphAgkhkhP3C7uCEuiB3vVte-lZnzlJm2DDSE05wOZVzrpPoaulWESNjdpDC4DOQruN2FJhGBZp5cE5Sk4TXGkPxDY-SSExa7vkx8yrpjh_CcZrLNrZxZnP081B8uFpwdhNFNA7FQdcmejHKThKud8w6R3dfiRxxW_q1Rpck8bBO5T8BJK1dHjpjRrh-5etRTOIAvr2XANnYTC-WgLRHLrdiBeMQGh8DxYTmI0gC_FBP2wjj9MbKZrhJUJ_xYpNm00)
