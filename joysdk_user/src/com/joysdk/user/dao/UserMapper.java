package com.joysdk.user.dao;

import java.util.Map;

import com.joysdk.common.user.model.User;


public interface UserMapper {

    public int createUser(User user);
    
    public User getUserById(int id);
    
    public User checkAccount(Map<String, String> account);
    
    public User checkLogin(Map<String, String> login);
    
    public void updateUser(User user);
}
