package com.joysdk.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.common.user.model.User;
import com.joysdk.user.exception.UserException;
import com.joysdk.user.model.UserInfo;
import com.joysdk.user.service.RegisterService;
import com.joysdk.user.service.UserService;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserService userService;

    @Override
    public void createUser4CP(User user, UserInfo userInfo) throws UserException {
        userService.createUser(user);
        
        userInfo.setUserId(user.getId());
        userService.createUserInfo(userInfo);
    }

}
