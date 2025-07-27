package com.smc.crawler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.crawler.bean.Rate;
import org.apache.ibatis.annotations.Mapper;

/**
* @author smc
* @description 针对表【rete(汇率表)】的数据库操作Mapper
* @createDate 2022-05-29 11:46:21
* @Entity generator.domain.Rete
*/
@Mapper
public interface RateMapper extends BaseMapper<Rate> {

}




