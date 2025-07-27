package com.smc.crawler.config;

import com.smc.crawler.util.CrawlerAutoERJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Date 2022/11/10
 * @Author smc
 * @Description:
 */
@Component
@Slf4j
public class SchedualConfig {
    @Autowired
    private CrawlerAutoERJob crawlerAutoERJob;

//    @Scheduled(cron = "*/20 * * * * ?")
    @Scheduled(cron = "0 0/30 9,10,11,12,13,14,15,16,17,18 * * ? ")
    private void crawlerRate() {
        crawlerAutoERJob.executeInternal();
    }
}
