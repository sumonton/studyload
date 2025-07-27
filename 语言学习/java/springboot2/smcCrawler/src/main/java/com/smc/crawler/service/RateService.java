package com.smc.crawler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smc.crawler.bean.Rate;

/**
* @author smc
* @description 针对表【rete(汇率表)】的数据库操作Service
* @createDate 2022-05-29 11:46:21
*/
public interface RateService extends IService<Rate> {
    int insertRate(Rate rate);
}
