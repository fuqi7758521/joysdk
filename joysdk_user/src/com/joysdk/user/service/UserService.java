package com.joysdk.user.service;

import com.joysdk.common.user.model.User;
import com.joysdk.user.exception.UserException;
import com.joysdk.user.model.UserInfo;

/**
 * 用户服务接口
 * @author Sunxc
 *
 */
public interface UserService {

    public int createUser(User user) throws UserException;
    
    public User getUserById(int id) throws UserException;

    public User checkAccount(String account) throws UserException;

    public User checkLogin(String account, String password) throws UserException;
    
    public void updateUser(User user) throws UserException;
    
    public void createUserInfo(UserInfo userInfo) throws UserException;
    
    public UserInfo getUserInfo(int userId) throws UserException;
}
