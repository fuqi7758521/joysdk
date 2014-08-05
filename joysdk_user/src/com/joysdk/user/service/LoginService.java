package com.joysdk.user.service;

import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.user.model.User;
import com.joysdk.user.exception.LoginException;

public interface LoginService {
    
	public OnlineUser login(String userName, String password, int gameId) throws LoginException;
	
	public void logout(String userName) throws LoginException;
	
	public void writeLoginUser(String userName, User user) throws LoginException;
	
	public User readLoginUser(String userName)throws LoginException;

	public User readLoginUserByCache(String userName)throws LoginException;
	
	public boolean checkUser(String username, String password);
	
	public OnlineUser createOnlineUser(String account, int gameId, User user) throws LoginException;

}

