package com.joysdk.game.model;

import java.io.Serializable;
import java.util.Date;


public class GameRole implements Serializable{

    private static final long serialVersionUID=1L;

    private int id;
    
    private int gameId;
    
    private int serverId;
    
    private int userId;
    
    private int roleId;
    
    private String roleName;
    
    private int roleLevel;
    
    private Date dtCreateTime;

    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id=id;
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

    
    public int getUserId() {
        return userId;
    }

    
    public void setUserId(int userId) {
        this.userId=userId;
    }

    
    public int getRoleId() {
        return roleId;
    }

    
    public void setRoleId(int roleId) {
        this.roleId=roleId;
    }

    
    public String getRoleName() {
        return roleName;
    }

    
    public void setRoleName(String roleName) {
        this.roleName=roleName;
    }

    
    public int getRoleLevel() {
        return roleLevel;
    }

    
    public void setRoleLevel(int roleLevel) {
        this.roleLevel=roleLevel;
    }

    
    public Date getDtCreateTime() {
        return dtCreateTime;
    }

    
    public void setDtCreateTime(Date dtCreateTime) {
        this.dtCreateTime=dtCreateTime;
    }
    
}
