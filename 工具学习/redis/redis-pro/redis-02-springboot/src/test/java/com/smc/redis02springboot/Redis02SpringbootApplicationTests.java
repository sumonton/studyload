package com.smc.redis02springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smc.redis02springboot.pojo.User;
import com.smc.redis02springboot.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class Redis02SpringbootApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {
        //获取连接
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushAll();
//        connection.flushDb();
//        connection.select(3); //切换数据库
        //redisTemplate 操作不同的数据类型，api和我们指令类似
        //操作字符串
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("hello","书蒙尘redis");
        System.out.println(valueOperations.get("hello"));
        //操作list
        ListOperations listOperations = redisTemplate.opsForList();
        //操作hash
        HashOperations hashOperations = redisTemplate.opsForHash();
    }

    @Test
    void test() throws JsonProcessingException {
        User user = new User("书蒙尘", 26);
        ValueOperations valueOperations = redisTemplate.opsForValue();
//        String jsonUser = new ObjectMapper().writeValueAsString(user);
        valueOperations.set("user",user);
        System.out.println(valueOperations.get("user"));
    }

}
