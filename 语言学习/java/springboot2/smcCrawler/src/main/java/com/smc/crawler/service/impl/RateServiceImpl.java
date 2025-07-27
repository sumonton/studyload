package com.smc.crawler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.smc.crawler.bean.Rate;
import com.smc.crawler.mapper.RateMapper;
import com.smc.crawler.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author smc
* @description 针对表【rete(汇率表)】的数据库操作Service实现
* @createDate 2022-05-29 11:46:21
*/
@Service
public class RateServiceImpl extends ServiceImpl<RateMapper, Rate>
    implements RateService {
    @Autowired
    private RateMapper rateMapper;


    @Override
    public int insertRate(Rate rate) {

        int result = rateMapper.insert(rate);
        return result;
    }
}




