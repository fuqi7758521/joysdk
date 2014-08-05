package com.joysdk.common.user.proxy;

import java.util.concurrent.TimeoutException;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joysdk.common.Constants;
import com.joysdk.common.exception.RemoteException;
import com.joysdk.common.game.proxy.GameProxy;
import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.resource.RedisService;
import com.joysdk.common.user.UserCacheConstant;
import com.joysdk.common.user.model.User;
import com.joysdk.common.user.service.remote.UserServiceRemote;
import com.joysdk.common.util.CookieUtil;
import com.joysdk.common.util.EncryptUtil;


public class UserProxy {

    private static final Logger logger=LoggerFactory.getLogger(GameProxy.class);

    private RedisService userRedisService;
    
    private UserServiceRemote userRemoteService;

    
    public void setUserRedisService(RedisService userRedisService) {
        this.userRedisService=userRedisService;
    }

    
    public void setUserRemoteService(UserServiceRemote userRemoteService) {
        this.userRemoteService=userRemoteService;
    }
    
    /**
     * 检查在线用户登录是否超时，超时则删除
     * @param token
     * @return
     */
    public OnlineUser getOnlineUser(String token, int gameId){
        OnlineUser onlineUser=null;
        String onlineUserKey=UserCacheConstant.PREFIX_REDIS_ONLINEUSER;
        String onlineUserZsetKey=UserCacheConstant.PREFIX_REDIS_SORTSET;
        if(gameId==0){
            String suffix="web:";
            onlineUserKey=suffix+onlineUserKey;
            onlineUserZsetKey=suffix+onlineUserZsetKey;
        }
        try {
            String tokenKey=onlineUserKey + token;
            long score=userRedisService.zscore(onlineUserZsetKey, token);
            if((System.currentTimeMillis() - score < UserCacheConstant.SESSION_TIMEOUT && score != 0) || (gameId == 0)) {// 20分钟
                onlineUser=userRedisService.get(tokenKey, OnlineUser.class);
                updateOnlineUser(token, gameId);
            }else{
                userRedisService.zrem(onlineUserZsetKey, token);
                userRedisService.del(tokenKey);
            }
        } catch(TimeoutException e) {
            e.printStackTrace();
        }
        return onlineUser;
    }
    
    /**
     * 从web端获取
     * @param request
     * @return
     */
    public OnlineUser getOnlineUser(ServletRequest request){
        final Cookie loginCookie=CookieUtil.getCookie(request, Constants.COOKIE_USER_LOGIN_KEY);
        if(loginCookie != null) {
            String token=EncryptUtil.decodeCookieValue(loginCookie.getValue(), Constants.ENCODE_KEY);
            return getOnlineUser(token, 0);
        }
        return null;
    }
    /**
     * 更新在线用户在zset中的排序值
     * @param token
     */
    public void updateOnlineUser(String token, int gameId){
        try {
            String onlineUserZsetKey=UserCacheConstant.PREFIX_REDIS_SORTSET;
            if(gameId==0){
                onlineUserZsetKey="web:"+onlineUserZsetKey;
            }
            userRedisService.zadd(onlineUserZsetKey, System.currentTimeMillis(), token);
        } catch(TimeoutException e) {
            e.printStackTrace();
        }
    }
    
    public User getLoginUser(String loginAccount){
        User user=null;
        try {
            user=userRedisService.get(UserCacheConstant.PREFIX_REDIS_LOGIN + loginAccount, User.class);
            if(user == null){
                user=userRemoteService.getLoginUser(loginAccount);
            }
        } catch(TimeoutException e) {
            e.printStackTrace();
            logger.error("TimeoutException " + e.getMessage());
        } catch(RemoteException e) {
            e.printStackTrace();
            logger.error("RemoteException " + e.getMessage());
        }
        return user;
    }
}
