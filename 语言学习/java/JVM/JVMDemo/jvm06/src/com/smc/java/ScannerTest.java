package com.smc.java;


import java.util.Scanner;

/**
 * @Date 2023/12/23
 * @Author smc
 * @Description:
 */
public class ScannerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一个整数：");
        int num = scanner.nextInt();
        System.out.println("输入的整数为：" + num);
    }
}
