package com.smc.java;

/**
 * @Date 2023/11/25
 * @Author smc
 * @Description:
 */
public class CanReliveObj {
    public static CanReliveObj obj;//类变量，属于GC Root

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("调用当前类重写的finalize方法");
        obj=this;
    }

    public static void main(String[] args) {
        try {
            obj=new CanReliveObj();
            //对象第一次成功拯救自己
            obj=null;
            //调用垃圾回收器
            System.gc();
            System.out.println("第一次GC");
            //因为Finalizer线程优先级很低，暂停2秒，以等待它
            Thread.sleep(2000);
            if (obj==null){
                System.out.println("obj is dead");
            }else {
                System.out.println("obj is still alive");
            }
            System.out.println("第二次gc");
            //下面这段代码与上面的完全相同，但是这次自救失败了
            obj=null;
            System.gc();
            Thread.sleep(2000);
            if (obj==null){
                System.out.println("obj is dead");
            }else {
                System.out.println("obj is still alive");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
