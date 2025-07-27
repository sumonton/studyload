package com.smc.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smc.boot.bean.User;
import com.smc.boot.mapper.UserMapper;
import com.smc.boot.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Date 2022/5/15
 * @Author smc
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
