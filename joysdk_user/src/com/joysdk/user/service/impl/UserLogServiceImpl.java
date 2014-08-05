package com.joysdk.user.service.impl;

import org.springframework.stereotype.Service;

import com.joysdk.user.dao.UserLogMapper;
import com.joysdk.user.model.UserLog;
import com.joysdk.user.service.UserLogService;


@Service
public class UserLogServiceImpl implements UserLogService {

    private UserLogMapper userLogMapper;
    
    @Override
    public void createUserLog(UserLog log) {
        userLogMapper.createUserLog(log);
    }
    

}
