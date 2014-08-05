package com.joysdk.game.model;


public class ChannelPropery {

    private int id;
    
    private int channelId;
    
    private int gameId;
    
    private String properyName;

    public ChannelPropery(){}
    
    public ChannelPropery(int id){
        this.id=id;
    }
    
    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id=id;
    }

    
    public int getChannelId() {
        return channelId;
    }

    
    public void setChannelId(int channelId) {
        this.channelId=channelId;
    }

    
    public String getProperyName() {
        return properyName;
    }

    
    public void setProperyName(String properyName) {
        this.properyName=properyName;
    }


    
    public int getGameId() {
        return gameId;
    }


    
    public void setGameId(int gameId) {
        this.gameId=gameId;
    }
    
    
}
