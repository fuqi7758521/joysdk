package com.joysdk.game.model;

import java.io.Serializable;


public class GameConfig implements Serializable{

    private static final long serialVersionUID=1L;

    private int gameId;
    
    private int channelId;
    
    private String proName;
    
    private String proValue;

    
    public int getGameId() {
        return gameId;
    }

    
    public void setGameId(int gameId) {
        this.gameId=gameId;
    }

    
    public int getChannelId() {
        return channelId;
    }

    
    public void setChannelId(int channelId) {
        this.channelId=channelId;
    }

    
    public String getProName() {
        return proName;
    }

    
    public void setProName(String proName) {
        this.proName=proName;
    }

    
    public String getProValue() {
        return proValue;
    }

    
    public void setProValue(String proValue) {
        this.proValue=proValue;
    }
    
    
}
