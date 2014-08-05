package com.joysdk.game.model;

import java.io.Serializable;
import java.util.Date;


public class LoginServerLog implements Serializable{

    private static final long serialVersionUID=1L;

    private int id;
    
    private int userId;
    
    private int gameId;
    
    private int serverId;
    
    private int roleId;
    
    private Date dtCreateTime;

    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id=id;
    }

    
    public int getUserId() {
        return userId;
    }

    
    public void setUserId(int userId) {
        this.userId=userId;
    }

    
    public int getGameId() {
        return gameId;
    }

    
    public void setGameId(int gameId) {
        this.gameId=gameId;
    }

    
    public int getServerId() {
        return serverId;
    }

    
    public void setServerId(int serverId) {
        this.serverId=serverId;
    }

    
    public int getRoleId() {
        return roleId;
    }

    
    public void setRoleId(int roleId) {
        this.roleId=roleId;
    }

    
    public Date getDtCreateTime() {
        return dtCreateTime;
    }

    
    public void setDtCreateTime(Date dtCreateTime) {
        this.dtCreateTime=dtCreateTime;
    }
    
    
}
