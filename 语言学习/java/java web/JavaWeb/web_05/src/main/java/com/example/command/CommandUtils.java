package com.example.command;

/**
 * @Date 2022/5/25
 * @Author smc
 * @Description:
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;

public class CommandUtils {
    private static final String DEFAULT_CHARSET = "GBK";

    public static void echo(String command) {
        System.err.println(command);
    }

    /**
     * 同步执行指定命令
     *
     * @param command 命令
     * @return 命令执行完成返回结果
     * @throws IOException 失败时抛出异常，由调用者捕获处理
     */
    public static String exeCommandSync(String command, List<Object> paramList) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            int exitCode = execSync(command, paramList, out);
            if (exitCode == 0) {
                System.out.println("命令运行成功！");
            } else {
                System.out.println("命令运行失败！");
            }
            return out.toString(DEFAULT_CHARSET);
        }
    }

    /**
     * 同步执行指定命令
     *
     * @param command 命令
     * @return 命令执行完成返回结果
     * @throws IOException 失败时抛出异常，由调用者捕获处理
     */
    public static int exeCommandSync1(String command, List<Object> paramList) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            int exitCode = execSync(command, paramList, out);
            if (exitCode == 0) {
                System.out.println("命令运行成功！");
            } else {
                System.out.println("命令运行失败！");
            }
//			return out.toString(DEFAULT_CHARSET);
            return exitCode;
        }
    }

    /**
     * 异步执行指定命令
     *
     * @param exeCommandAsync 命令
     * @return 命令执行完成返回结果
     * @throws ExecuteException
     * @throws IOException      失败时抛出异常，由调用者捕获处理
     */
    public static void exeCommandAsync(String command, List<Object> paramList) throws ExecuteException, IOException {
        int result = execAsync(command, paramList);

        if (result == 0) {
            System.out.println("命令运行成功！");
        } else {
            System.out.println("命令运行失败！");
        }
    }

    /**
     * 异步执行指定命令
     *
     * @param exeCommandAsync 命令
     * @return 命令执行完成返回结果
     * @throws ExecuteException
     * @throws IOException      失败时抛出异常，由调用者捕获处理
     */
    public static int exeCommandAsync1(String command, List<Object> paramList) throws ExecuteException, IOException {
        int result = execAsync(command, paramList);

        if (result == 0) {
            System.out.println("命令运行成功！");
            return 0;
        } else {
            System.out.println("命令运行失败！");
            return 1;
        }
    }

    /**
     * @throws
     * @Title: execAsync
     * @Description: 异步执行指定命令
     * @param: @param command
     * @param: @return
     * @return: int
     */
    public static int execAsync(String command, List<Object> paramList) throws ExecuteException, IOException {
        CommandLine commandLine = CommandLine.parse(command);

        if (paramList != null && paramList.size() > 0) {
            for (Object param : paramList) {
                commandLine.addArgument(String.valueOf(param));
            }
        }

        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(1);

        int result = 0;
        try {
            executor.execute(commandLine, resultHandler);
            resultHandler.waitFor(100);
        } catch (Exception e) {
            e.printStackTrace();
            result = 1;
        }
        return result;
    }

    /**
     * 执行指定命令，输出结果到指定输出流中
     *
     * @param execSync 命令
     * @param out      执行结果输出流
     * @return 执行结果状态码：执行成功返回0
     * @throws ExecuteException 失败时抛出异常，由调用者捕获处理
     * @throws IOException      失败时抛出异常，由调用者捕获处理
     */
    public static int execSync(String command, List<Object> paramList, OutputStream out) throws ExecuteException, IOException {
        CommandLine commandLine = CommandLine.parse(command);

        if (paramList != null && paramList.size() > 0) {
            for (Object param : paramList) {
                commandLine.addArgument(String.valueOf(param));
            }
        }

        PumpStreamHandler pumpStreamHandler = null;
        if (null == out) {
            pumpStreamHandler = new PumpStreamHandler();
        } else {
            pumpStreamHandler = new PumpStreamHandler(out);
        }

        // 设置超时时间为10秒
        //ExecuteWatchdog watchdog = new ExecuteWatchdog(100000);

        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        //executor.setWatchdog(watchdog);
        int result = 0;
        try {
//            executor.execute(commandLine, resultHandler);
            executor.execute(commandLine, (Map)null);
            resultHandler.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            result = 1;
        }

        return result;
    }

    public static void main(String[] args) {
        try {
            String result = exeCommandSync("E:\\Program Files\\pdi-ce-7.0.0.0-25\\data-integration\\samples\\bat\\demo.bat", null);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
