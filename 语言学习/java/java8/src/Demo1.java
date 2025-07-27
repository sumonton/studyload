import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @program: java8
 * @description:
 * @author: smc950910@163.com
 * @create: 2022-03-03 20:34
 **/
public class Demo1 {
    public static void main(String[] args) {
//        try {
//            System.out.println(processFile((BufferedReader br) -> br.readLine()));
//            System.out.println(processFile((BufferedReader br) -> br.readLine()+br.readLine()));

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String[] array = {"","aaa","","bbb"};
//        Predicate<String> noEmpty = (String s) -> !s.isEmpty();
//        System.out.println(filter(Arrays.asList(array),noEmpty).toString());
//        action("a,b,c,d",(String s) -> {
//            for (String a:s.split(",")
//                 ) {
//                System.out.println(a);
//            }
//        });
//        System.out.println(map(Arrays.asList("aaaa","bbb"),(String s) -> s.length()));
//        Callable<Integer> c = () -> 42;
//        try {
//            System.out.println(3+ c.call());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
    public static String processFile(BufferReaderProcess p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
            return p.process(br);
        }
    }
    public static <T> List<T> filter(List<T> list,Predicate<T> p){
        List<T> result =new ArrayList<>();
        for (T item:list
             ) {
            if (p.test(item)){
                result.add(item);
            }
        }
        return result;
    }

    public static <T> void action(T s, Consumer<T> c){
        c.accept(s);
    }

    public static <T,R> List<R> map(List<T> s, Function<T,R> f){
        List<R> r = new ArrayList<>();
        for (T t:s
             ) {
            r.add(f.apply(t));
        }
        return r;
    }

}
