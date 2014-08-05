package com.joysdk.user.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.user.model.User;
import com.joysdk.common.util.EncryptUtil;
import com.joysdk.common.web.context.SystemProperties;
import com.joysdk.user.cache.LoginCache;
import com.joysdk.user.exception.LoginException;
import com.joysdk.user.exception.UserException;
import com.joysdk.user.service.LoginService;
import com.joysdk.user.service.UserLogService;
import com.joysdk.user.service.UserService;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginCache loginCache;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserLogService userLogService;

    public OnlineUser login(String account, String password, int gameId) throws LoginException {
        User user=loginCache.readLoginUser(account);
        if(user == null) {
            try {
                user=userService.checkLogin(account, password);
            } catch(UserException e) {
                e.printStackTrace();
                throw new LoginException(10000);
            }
            if(user != null) {
                loginCache.writeLoginUser(account, user);
            } else {
                throw new LoginException(1004);
            }
        } else {
            String encodedPassword=EncryptUtil.encoderByMd5(password + SystemProperties.getProperty("yun.pwd.salt"));
            if(!user.getPassword().equals(encodedPassword)) {
                throw new LoginException(1004);
            }
        }
        OnlineUser onlineUser=null;
        if(user != null) {
            onlineUser=createOnlineUser(account, gameId, user);
            
        }
        return onlineUser;
    }
    
    public OnlineUser createOnlineUser(String account, int gameId, User user) throws LoginException{
        String token=null;
        OnlineUser onlineUser=new OnlineUser();
        Date currentDate=new Date();
        onlineUser.setGameId(gameId);
        onlineUser.setLastActDate(currentDate);
        onlineUser.setLoginAccount(account);
        onlineUser.setLoginDate(currentDate);
        onlineUser.setCpId(user.getCpId());
        onlineUser.setUserId(user.getId());
        onlineUser.setUserType(user.getUserType());
        String str=user.getId() + gameId + System.currentTimeMillis() + user.getPassword();
        token=EncryptUtil.encoderByMd5(str);
        onlineUser.setToken(token);
        loginCache.writeOnlineUser(onlineUser, gameId);
        return onlineUser;
    }
    
    public void logout(String userName) throws LoginException {
        // TODO Auto-generated method stub

    }

    public void writeLoginUser(String userName, User user) throws LoginException {
        // TODO Auto-generated method stub

    }

    public User readLoginUser(String userName) throws LoginException {
        // TODO Auto-generated method stub
        return null;
    }

    public User readLoginUserByCache(String userName) throws LoginException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean checkUser(String username, String password) {
        // TODO Auto-generated method stub
        return false;
    }

}
