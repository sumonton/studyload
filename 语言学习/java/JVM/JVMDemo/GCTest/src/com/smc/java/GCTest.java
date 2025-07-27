package com.smc.java;

/**
 * -XX:+PrintFlagsInitial ：查看所有的参数的默认值
 * -XX:+PrintFlagsFinal ：查看所有的参数的最终值（可能会存在修改，不再是初始值）
 *  具体查看某个参数的指令：jps：查看当前运行中的进程
 *                      jinfo -flag SurvivorRatio 进程id
 * -Xms ：初始堆空间内存（默认为物理内存的1/64）
 * -Xmx ：最大堆空间内存（默认为物理内存的1/4）
 * -Xmn ：设置新生代的大小（初始值及最大值）
 * -XX:NewRation ：配置新生代与老年代在堆结构的占比
 * -XX:SurvivorRatio ：设置新生代中Eden和S0/S1空间占比
 * -XX:MaxTenuringThreshold ：设置新生代垃圾的最大年龄
 * -XX:+PrintGCDetails ：输出详细的GC处理日志
 * 打印gc简要信息：-XX:+PrintGC -verbos:gc
 * -XX:HandlePromotionFailure：是否设置空间分配担保
 *
 * @Date 2023/8/24
 * @Author smc
 * @Description:
 */
public class GCTest {
    public static void main(String[] args) {
        System.out.println("GCTest");
    }
}
