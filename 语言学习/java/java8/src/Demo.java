import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @program: java8
 * @description:
 * @author: smc950910@163.com
 * @create: 2022-03-03 20:24
 **/
public class Demo {
    private static final Logger logger = Logger.getLogger("");
    public static int factorial (int n){
        System.out.println("factorial(" + n + "):");
        Throwable t = new Throwable ();
        StackTraceElement[] frames = t.getStackTrace();
        for (StackTraceElement f : frames)
            System.out.println(f);
        int r;
        if (n <= 1)
            r = 1;
        else
            r = n * factorial (n - 1);
        System.out.println("return " + r);
        return r;
    }
    public static void main(String[] args) {

        if (System.getProperty("java,util.logging.config.dass") == null
                && System.getProperty("java.util.logging.config.file") == null) {
            try
            {
                Logger.getLogger("1111").setLevel(Level.ALL);
                final int L0C_R0TATI0N_C0UNT = 10;
                Handler handler = new FileHandler("myapp.log", 10, L0C_R0TATI0N_C0UNT);
                Logger.getLogger("").addHandler(handler);
            }
            catch (IOException e) {
                logger.log(Level.SEVERE, "Can't create log file handler", e);
            }
        }
        Scanner in = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = in.nextInt();;


        factorial (n);
    }
}
