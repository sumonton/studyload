package com.smc.boot;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date 2022/5/15
 * @Author smc
 * @Description:
 */

@DisplayName("junit5功能测试类")
@SpringBootTest
class MainApplicationTest {
    @DisplayName("测试displayname的注解")
    @Test
    public void testDisplayName(){
        System.out.println(1);
    }

    @DisplayName("测试displayname的注解")
    @Test
    @Timeout(value = 5,unit = TimeUnit.MILLISECONDS)
    public void testTimeout() throws InterruptedException {
        Thread.sleep(500);
    }

    @DisplayName("测试RepeatTest的注解")
    @Test
    @RepeatedTest(5)
    public void testRepeatedTest() {
        System.out.println(5);
    }

    @Disabled
    @DisplayName("测试test2的注解")
    @Test
    public void test2(){
        System.out.println(2);
    }

    @BeforeEach
    public void testBeforEach(){
        System.out.println("测试就要开始了");
    }

    @AfterEach
    public void testAfterEach(){
        System.out.println("测试就要结束了");
    }

    @BeforeAll
    public static void testBeforAll(){
        System.out.println("所有测试就要开始了");
    }

    @AfterAll
    public static void testAfterAll(){
        System.out.println("所有测试就要解说了");
    }


    @Test
    public void testSimpleAssertions(){
        int cal = cal(3,3);
        assertEquals(5,cal,"业务逻辑计算失败");
    }

    private int cal(int i, int i1) {
        return i+i1;
    }

    @Test
    public void all(){
        assertAll("test",()->assertTrue(true && true),
                ()->assertEquals(1,2));
    }
    @DisplayName("异常断言")
    @Test
    public void exceptionTest(){
        ArithmeticException exception = Assertions.assertThrows(
                ArithmeticException.class,() -> System.out.println(1 % 1),"业务逻辑异常"
        );
    }
    @Test
    void testAssumptions(){
        Assumptions.assumeTrue(false,"结果不是true");
        System.out.println("111");
    }

}
