package com.joysdk.user.model;

import java.io.Serializable;
import java.util.Date;


public class UserLog implements Serializable{

    private static final long serialVersionUID=1L;

    private int userId;
    
    private String loginIp;
    
    private String loginUA;
    
    private int gameId;
    
    private Date loginDate;
    
    private int channelId;

    public UserLog(int userId, int gameId, int channelId, String loginIp, String loginUA){
        this.userId=userId;
        this.gameId=gameId;
        this.channelId=channelId;
        this.loginIp=loginIp;
        this.loginUA=loginUA;
        this.loginDate=new Date();
    }
    
    
    public int getUserId() {
        return userId;
    }

    
    public void setUserId(int userId) {
        this.userId=userId;
    }

    
    public String getLoginIp() {
        return loginIp;
    }

    
    public void setLoginIp(String loginIp) {
        this.loginIp=loginIp;
    }

    
    public String getLoginUA() {
        return loginUA;
    }

    
    public void setLoginUA(String loginUA) {
        this.loginUA=loginUA;
    }

    
    public int getGameId() {
        return gameId;
    }

    
    public void setGameId(int gameId) {
        this.gameId=gameId;
    }

    
    public Date getLoginDate() {
        return loginDate;
    }

    
    public void setLoginDate(Date loginDate) {
        this.loginDate=loginDate;
    }


    
    public int getChannelId() {
        return channelId;
    }


    
    public void setChannelId(int channelId) {
        this.channelId=channelId;
    }
    
    
}
