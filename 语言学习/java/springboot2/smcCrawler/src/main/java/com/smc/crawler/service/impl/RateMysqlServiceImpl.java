package com.smc.crawler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smc.crawler.bean.RateMysql;
import com.smc.crawler.bean.RateOracle;
import com.smc.crawler.service.RateMysqlService;
import com.smc.crawler.mapper.RateMysqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author smc
* @description 针对表【rate_mysql】的数据库操作Service实现
* @createDate 2022-06-12 13:03:21
*/
@Service
public class RateMysqlServiceImpl extends ServiceImpl<RateMysqlMapper, RateMysql>
    implements RateMysqlService{
    @Autowired
    private RateMysqlMapper rateMysqlMapper;
    @Override
    public int insertRate(RateMysql rateMysql) {

        int result = rateMysqlMapper.insert(rateMysql);
        return result;
    }
}




