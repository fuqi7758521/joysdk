package com.joysdk.user.service.remote;

import org.springframework.stereotype.Service;

import com.joysdk.common.exception.RemoteException;
import com.joysdk.common.user.model.User;
import com.joysdk.common.user.service.remote.UserServiceRemote;

@Service("userRemoteService")
public class UserServiceRemoteImpl implements UserServiceRemote {

    public User getLoginUser(String account) throws RemoteException {
        return null;
    }

}
