package com.joysdk.common.user;

public interface UserCacheConstant {
    public static final String PREFIX_REDIS_LOGIN="user:authuser:login:";

    public static final String PREFIX_REDIS_EMAIL="user:email:";

    public static final String PREFIX_REDIS_PHONE="user:phone:";

    public static final String PREFIX_REDIS_ONLINEUSER="user:online:";
    
    public static final String PREFIX_REDIS_SORTSET="user:online:sortset";
    
    public static final long SESSION_TIMEOUT=1000 * 60 * 20;
}
