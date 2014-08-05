package com.joysdk.user.cache;

import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.user.model.User;
import com.joysdk.user.exception.LoginException;

public interface LoginCache {
	public void writeLoginUser(String userName, User user) throws LoginException;
	public User readLoginUser(String userName)throws LoginException;
	public Long removeLoginUser(String... userName) throws LoginException;
	public void writeOnlineUser(OnlineUser onlineUser, int gameId) throws LoginException;
	 public void updateOnlineUser(String token, int gameId)throws LoginException;
	public OnlineUser readOnlineUser(String token, int gameId) throws LoginException;
}
