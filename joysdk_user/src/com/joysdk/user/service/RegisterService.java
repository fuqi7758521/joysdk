package com.joysdk.user.service;

import com.joysdk.common.game.model.CP;
import com.joysdk.common.user.model.User;
import com.joysdk.user.exception.UserException;
import com.joysdk.user.model.UserInfo;


public interface RegisterService {

    public void createUser4CP(User user, UserInfo userInfo) throws UserException;
}
