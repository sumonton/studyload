import java.io.BufferedReader;
import java.io.IOException;

/**
 * @program: java8
 * @description:
 * @author: smc950910@163.com
 * @create: 2022-03-03 20:34
 **/
@FunctionalInterface
public interface BufferReaderProcess {
    String process(BufferedReader b) throws IOException;
}
