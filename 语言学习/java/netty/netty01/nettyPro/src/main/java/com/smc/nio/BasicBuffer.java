package com.smc.nio;

import java.nio.IntBuffer;

/**
 * @Date 2022/9/5
 * @Author smc
 * @Description:
 */
public class BasicBuffer {
    public static void main(String[] args) {
        /**
         * buffer简单使用
         */
        //创建一个buffer，大小为5，可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向buffer存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }
        //如果从buffer读取数据
        //将buffer转换，读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
