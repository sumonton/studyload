package completable.future;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;

/**
 * @program: java8
 * @description: 商店
 * @author: smc950910@163.com
 * @create: 2022-03-06 18:36
 **/
public class Shop {
    Random random = new Random();
    private String name;
    public Shop(String name) {
        this.name = name;
    }

    public double getPrice(String product){
        return calculatePrice(product);
    }

    public static void delay(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private double calculatePrice(String product){
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

//    public Future<Double> getPriceAsync(String product){
//        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
//        new Thread(() -> {
//            try {
//                double price = calculatePrice(product);
//                futurePrice.complete(price);
//            }catch (Exception e){
//                futurePrice.completeExceptionally(e);
//            }
//
//        }).start();
//        return futurePrice;//无需等待异步线程的计算结果，直接返回
//    }
    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
