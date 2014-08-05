package com.joysdk.common.user.service.remote;

import com.joysdk.common.exception.RemoteException;
import com.joysdk.common.user.model.User;


public interface UserServiceRemote {

    public User getLoginUser(String account) throws RemoteException;
}
