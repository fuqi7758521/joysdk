package com.joysdk.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.common.user.model.User;
import com.joysdk.common.util.AccountUtil;
import com.joysdk.common.util.EncryptUtil;
import com.joysdk.common.web.context.SystemProperties;
import com.joysdk.user.dao.UserInfoMapper;
import com.joysdk.user.dao.UserMapper;
import com.joysdk.user.exception.UserException;
import com.joysdk.user.model.UserInfo;
import com.joysdk.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserInfoMapper userInfoMapper;

    public int createUser(User user) throws UserException{
        userMapper.createUser(user);
        return user.getId();
    }

    public User getUserById(int id) throws UserException {
        return userMapper.getUserById(id);
    }

    public User checkLogin(String account, String password) throws UserException {
        String encodedPassword = EncryptUtil.encoderByMd5(password+SystemProperties.getProperty("yun.pwd.salt"));
        Map<String, String> login=new HashMap<String, String>();
        if(AccountUtil.isAvailableUsername(account)){
            login.put("userName", account);
        }else if(AccountUtil.isEmail(account)){
            login.put("email", account);
        }else if(AccountUtil.isPhoneNum(account)){
            login.put("phone", account);
        }
        login.put("password", encodedPassword);
        return userMapper.checkLogin(login);
    }

    public void updateUser(User user) throws UserException {
        userMapper.updateUser(user);
    }

    public User checkAccount(String account) throws UserException {
        Map<String, String> accountMap=new HashMap<String, String>();
        if(AccountUtil.isAvailableUsername(account)){
            accountMap.put("userName", account);
        }else if(AccountUtil.isEmail(account)){
            accountMap.put("email", account);
        }else if(AccountUtil.isPhoneNum(account)){
            accountMap.put("phone", account);
        }
        return userMapper.checkAccount(accountMap);
    }

    @Override
    public void createUserInfo(UserInfo userInfo) throws UserException {
        userInfoMapper.createUserInfo(userInfo);
    }

    @Override
    public UserInfo getUserInfo(int userId) throws UserException {
        return userInfoMapper.getUserInfo(userId);
    }
    
    

}
