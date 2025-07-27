package com.smc.java;

import java.util.ArrayList;
import java.util.Random;

/**
 * @Date 2023/8/22
 * @Author smc
 * @Description:
 */
public class OOMTest {
    public static void main(String[] args) {
        ArrayList list =new ArrayList();
        while (true){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new Picture(new Random().nextInt(1024*1024)));
        }
    }
}
class Picture{
    private byte[] pixels;
    public Picture(int size){
        this.pixels=new byte[size];
    }
}
