package com.smc;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @Date 2022/8/11
 * @Author smc
 * @Description:
 */
public class TestTx {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.103", 6379);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello","world");
        jsonObject.put("name","smc");
        int i = 1 / 0;
        Transaction multi = jedis.multi();
        try {
            multi.set("user1",jsonObject.getString("hello"));
            multi.set("user2",jsonObject.getString("name"));
            multi.exec();
        } catch (Exception e) {
            multi.discard();
            e.printStackTrace();
        } finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.close();
        }

    }
}
