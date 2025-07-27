package com.smc.crawler.config;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2022/5/23
 * @Author smc
 * @Description:
 */
@Configuration(proxyBeanMethods = false)
public class CrawlerConfig {
    @Bean
    public WebClient webClient() {
        // 1创建WebClient
        WebClient webClient=new WebClient(BrowserVersion.CHROME);
        // 2 启动JS
        webClient.getOptions().setJavaScriptEnabled(true);
        // 3 禁用Css，可避免自动二次請求CSS进行渲染
        webClient.getOptions().setCssEnabled(false);
        // 4 启动客戶端重定向
        webClient.getOptions().setRedirectEnabled(true);
        // 5 js运行错誤時，是否拋出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // 6 设置超时
        webClient.getOptions().setTimeout(50000);       //获取网页
        return webClient;
    }
}
