package com.joysdk.user.dao;

import com.joysdk.user.model.UserInfo;


public interface UserInfoMapper {

    public void createUserInfo(UserInfo userInfo);
    
    public UserInfo getUserInfo(int userId);
}
