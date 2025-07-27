package com.smc;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import sun.lwawt.macosx.CSystemTray;

/**
 * @Date 2022/8/10
 * @Author smc
 * @Description:
 */
public class TestPing {
    public static void main(String[] args) {
        // 1、new Jedis对象
        Jedis jedis = new Jedis("192.168.0.100",6379);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user1","world");
        jsonObject.put("user2","smc");
        //开启事物
        Transaction multi = jedis.multi();
        String result = jsonObject.toJSONString();
        try {
            multi.set("user1", result);
            multi.set("user2",result);
            multi.exec();
        } catch (Exception e) {
            multi.discard();
            e.printStackTrace();
        }finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            //关闭连接
            jedis.close();
        }

    }
}
