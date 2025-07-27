package com.smc.boot.mapper;

import com.smc.boot.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Date 2022/5/15
 * @Author smc
 * @Description:
 */
@Mapper
public interface AccountMapper {
    public User getUser(String name);
}
