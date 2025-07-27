package com.smc.crawler.service;

import com.smc.crawler.bean.RateMysql;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smc.crawler.bean.RateOracle;

/**
* @author smc
* @description 针对表【rate_mysql】的数据库操作Service
* @createDate 2022-06-12 13:03:21
*/
public interface RateMysqlService extends IService<RateMysql> {
    int insertRate(RateMysql rateMysql);
}
