package com.smc.java;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @Date 2023/11/25
 * @Author smc
 * @Description:
 */
public class PhantomReferenceTest {
    public static PhantomReferenceTest obj;//当前类对象声明
    static ReferenceQueue<PhantomReferenceTest> phantomQueue=null;//引用实例

    public static class CheckRefQueue extends Thread{
        @Override
        public void run() {
            while (true){
                if (phantomQueue !=null){
                    PhantomReference<PhantomReferenceTest> objt=null;
                    try {
                        objt= (PhantomReference<PhantomReferenceTest>) phantomQueue.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (objt!=null){
                        System.out.println("追踪垃圾回收过程：PhantomReferenceTest实例被GC了");
                    }
                }
            }
        }
    }
    //finalize方法只能被调用一次
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("调用当前类的finalize()方法");
        obj=this;
    }

    public static void main(String[] args) {
        Thread t=new CheckRefQueue();
        //设置守护线程:当程序中没有非守护线程时，守护线程也就执行结束了
        t.setDaemon(true);
        t.start();
        phantomQueue=new ReferenceQueue<>();
        obj=new PhantomReferenceTest();
        //构造PhantomReferenceTest对象的虚引用，并制定了引用队列
        PhantomReference<PhantomReferenceTest> phantomRef = new PhantomReference<>(obj, phantomQueue);
        try {
            //不可获取虚引用中的对象
            System.out.println(phantomRef.get());

            //将强引用去除
            obj=null;
            //第一次进行GC，由于对象可复活，GC无法回收该对象
            System.gc();
            Thread.sleep(1000);
            if (obj==null){
                System.out.println("obj is null");
            }else {
                System.out.println("obj 可用");
            }
            //将强引用去除
            obj=null;
            //第一次进行GC，由于对象可复活，GC无法回收该对象
            System.gc();
            Thread.sleep(1000);
            if (obj==null){
                System.out.println("obj is null");
            }else {
                System.out.println("obj 可用");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
