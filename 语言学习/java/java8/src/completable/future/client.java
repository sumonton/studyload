package completable.future;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @program: java8
 * @description: 测试器
 * @author: smc950910@163.com
 * @create: 2022-03-06 18:43
 **/
public class client {
    public static void main(String[] args) {
//        Shop shop = new Shop("Best Shop");
//        long start = System.nanoTime();
////        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
//        System.out.println(findPrices("myPhone27S"));
//        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
//        System.out.println("Invocation returned after " + invocationTime
//                + " msecs");
//        try {
//            Thread.currentThread().sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try {
//            double price = futurePrice.get();
//            System.out.printf("Price is %.2f%n", price);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

//        long retrievalTime = ((System.nanoTime() - start) / 1_000);
//        System.out.println("Price returned after " + retrievalTime + " msecs");
        Duration threeMinutes = Duration.ofMinutes(3);

        Duration threeMinutes1 = Duration.of(3, ChronoUnit.MINUTES);
        Period tenDays = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
        System.out.println(threeMinutes.getSeconds());
        System.out.println(threeMinutes1.getSeconds());
        System.out.println(tenDays.getMonths());
        System.out.println(threeWeeks.getDays());
        System.out.println(twoYearsSixMonthsOneDay.getDays());
    }

    public static List<String> findPrices(String product) {
        List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
                new Shop("LetsSaveBig"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"),new Shop("aaa"));
//        return shops.stream()
//                .map(shop -> String.format("%s price is %.2f",
//                        shop.getName(), shop.getPrice(product)))
//                .collect(toList());
//        return shops.parallelStream()
//                .map(shop -> String.format("%s price is %.2f",
//                        shop.getName(), shop.getPrice(product)))
//                .collect(toList());
        Executor executor =
                Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                        new ThreadFactory() {
                            public Thread newThread(Runnable r) {
                                Thread t = new Thread(r);
                                t.setDaemon(true);
                                return t;
                            }
                        });
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " +
                                shop.getPrice(product), executor))
                        .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }
}
