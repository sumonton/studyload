package com.example.base64;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

/**
 *@author smc
 */
public class Base64Test {
    public static void main(String[] args) {
        String content = "这是需要Base64编码的内容";
        //创建一个Base64编码器
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //执行base64编码操作
        String encodeString = base64Encoder.encode(
                content.getBytes(StandardCharsets.UTF_8));
        System.out.println(encodeString);
        //创建Base64解码器
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            byte[] bytes = base64Decoder.decodeBuffer(encodeString);
            String str = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
