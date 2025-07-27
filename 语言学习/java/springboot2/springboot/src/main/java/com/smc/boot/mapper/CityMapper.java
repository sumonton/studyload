package com.smc.boot.mapper;

import com.smc.boot.bean.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Date 2022/5/15
 * @Author smc
 * @Description:
 */
@Mapper
public interface CityMapper {
    @Select("select * from city wehre id = #{id}")
    public City getById(Long id);
}
