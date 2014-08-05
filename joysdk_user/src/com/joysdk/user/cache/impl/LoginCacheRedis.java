package com.joysdk.user.cache.impl;

import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.resource.RedisService;
import com.joysdk.common.user.UserCacheConstant;
import com.joysdk.common.user.model.User;
import com.joysdk.user.cache.LoginCache;
import com.joysdk.user.exception.LoginException;

@Service
public class LoginCacheRedis implements LoginCache {

    @Autowired
    private RedisService userRedisService;

    private int expiredTime=60 * 60 * 24 * 30 * 3;// 3个月的数据

    public void setExpiredTime(int expiredTime) {
        this.expiredTime=expiredTime;
    }

    public void writeLoginUser(String userName, User user) throws LoginException {
        try {
            // 放入redis中并设置过期时间。
            userRedisService.setAndExpire(UserCacheConstant.PREFIX_REDIS_LOGIN + userName, user, expiredTime);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new LoginException();
        }
    }

    public User readLoginUser(String userName) throws LoginException {
        try {
            return userRedisService.get(UserCacheConstant.PREFIX_REDIS_LOGIN + userName, User.class);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new LoginException();
        }

    }

    public Long removeLoginUser(String... userName) throws LoginException {
        try {
            String[] keys=null;
            if(userName != null && userName.length > 0) {
                keys=new String[userName.length];

                for(int i=0; i < keys.length; i++) {
                    keys[i]=UserCacheConstant.PREFIX_REDIS_LOGIN + userName[i];
                }
            }
            return userRedisService.del(keys);
        } catch(TimeoutException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 登录成功后写入在线用户对象，写入两个值，一个是zset 用户判断用户是否超时
     * 通过zset来可以直接获取超时用户的列表，如果使用普通的数据结构，需要进行key的模糊匹配并进行循环遍历
     */
    public void writeOnlineUser(OnlineUser onlineUser, int gameId) throws LoginException {
        try {
            String onlineUserKey=UserCacheConstant.PREFIX_REDIS_ONLINEUSER;
            String onlineUserZsetKey=UserCacheConstant.PREFIX_REDIS_SORTSET;
            if(gameId==0){
                String suffix="web:";
                onlineUserKey=suffix+onlineUserKey;
                onlineUserZsetKey=suffix+onlineUserZsetKey;
            }
            userRedisService.setAndExpire(onlineUserKey + onlineUser.getToken(), onlineUser, expiredTime);
            userRedisService.zadd(onlineUserZsetKey, System.currentTimeMillis(), onlineUser.getToken());
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new LoginException();
        }
    }
    
    /**
     * 客户端维持心跳刷新
     * @param token
     * @throws LoginException
     */
    public void updateOnlineUser(String token, int gameId)throws LoginException{
        try {
            String onlineUserZsetKey=UserCacheConstant.PREFIX_REDIS_SORTSET;
            if(gameId==0){
                onlineUserZsetKey="web:"+onlineUserZsetKey;
            }
            userRedisService.zadd(onlineUserZsetKey, System.currentTimeMillis(), token);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new LoginException();
        }
    }

    /**
     * 访问在线用户，如果在scoreset中的值大于20分钟，则删除该token对应的数据
     */
    public OnlineUser readOnlineUser(String token, int gameId) throws LoginException {
        try {
            OnlineUser online=null;
            String onlineUserKey=UserCacheConstant.PREFIX_REDIS_ONLINEUSER;
            String onlineUserZsetKey=UserCacheConstant.PREFIX_REDIS_SORTSET;
            if(gameId==0){
                String suffix="web:";
                onlineUserKey=suffix+onlineUserKey;
                onlineUserZsetKey=suffix+onlineUserZsetKey;
            }
            
            String tokenKey=onlineUserKey + token;
            long score=userRedisService.zscore(onlineUserZsetKey, token);
            if(System.currentTimeMillis() - score < UserCacheConstant.SESSION_TIMEOUT && score != 0) {// 20分钟
                online=userRedisService.get(tokenKey, OnlineUser.class);
            }else{
                userRedisService.zrem(onlineUserZsetKey, token);
                userRedisService.del(tokenKey);
            }
            return online;
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new LoginException();
        }
    }

}
