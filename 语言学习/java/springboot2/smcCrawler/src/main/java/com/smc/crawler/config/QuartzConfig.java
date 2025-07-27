package com.smc.crawler.config;

import com.smc.crawler.util.CrawlerAutoERJob;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2022/5/23
 * @Author smc
 * @Description:
 */
//@Configuration
public class QuartzConfig {
    /**
     * 定义调度器
     */
//    @Bean
//    public SchedulerFactoryBean schedulerFactory(CronTrigger[] cronTriggerImpl) {
//        SchedulerFactoryBean bean = new SchedulerFactoryBean();
//        bean.setTriggers(cronTriggerImpl);
//        return bean;
//    }

    /**
     * 定义定时爬取评测任务
     */
//    @Bean("crawlerAutoERJob")
//    public JobDetailFactoryBean crawlerAutoHomeJob() {
//        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
//        jobDetailFactoryBean.setApplicationContextJobDataKey("context");
//        jobDetailFactoryBean.setJobClass(CrawlerAutoERJob.class);
//        jobDetailFactoryBean.setDurability(true);
//
//        return jobDetailFactoryBean;
//    }


//    @Bean("crawlerAutoHomeJobTrigger")
//    public CronTriggerFactoryBean crawlerAutoHomeJobTrigger(
//            @Qualifier(value = "crawlerAutoERJob") JobDetailFactoryBean itemJobBean) {
//        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
//        tigger.setJobDetail(itemJobBean.getObject());
//        tigger.setCronExpression("0/30 * * * * ? ");
////        tigger.setCronExpression("0 0/30 9,10,11,12,13,14,15,16,17,18 * * ? ");
//        return tigger;
//    }
}
