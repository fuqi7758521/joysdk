package com.joysdk.game.model;

import java.io.Serializable;


public class GameServer implements Serializable{

    private static final long serialVersionUID=1L;

    private int id;
    
    private String name;
    
    private int gameId;
    
    private String notifyUrl;
    
    private String serverHost;
    
    private String serverPort;
    
    private int limitUser;
    
    private int status;
    
    private String seqNum;

    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id=id;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name=name;
    }

    
    public int getGameId() {
        return gameId;
    }

    
    public void setGameId(int gameId) {
        this.gameId=gameId;
    }

    
    public String getNotifyUrl() {
        return notifyUrl;
    }

    
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl=notifyUrl;
    }

    
    public String getServerHost() {
        return serverHost;
    }

    
    public void setServerHost(String serverHost) {
        this.serverHost=serverHost;
    }

    
    public String getServerPort() {
        return serverPort;
    }

    
    public void setServerPort(String serverPort) {
        this.serverPort=serverPort;
    }

    
    public int getLimitUser() {
        return limitUser;
    }

    
    public void setLimitUser(int limitUser) {
        this.limitUser=limitUser;
    }

    
    public int getStatus() {
        return status;
    }

    
    public void setStatus(int status) {
        this.status=status;
    }

    
    public String getSeqNum() {
        return seqNum;
    }

    
    public void setSeqNum(String seqNum) {
        this.seqNum=seqNum;
    }
    
    
}
