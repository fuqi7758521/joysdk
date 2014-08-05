package com.joysdk.game.model;

import java.util.List;


public class ChannelConfig {

    private int id;
    
    private int gameId;
    
    private int channelId;
    
    private String icon;
    
    private String splashPic;
    
    private String packageName;
    
    private List<GameSDKConfig> sdkConfigs;

    
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

    
    public int getChannelId() {
        return channelId;
    }

    
    public void setChannelId(int channelId) {
        this.channelId=channelId;
    }

    
    public String getIcon() {
        return icon;
    }

    
    public void setIcon(String icon) {
        this.icon=icon;
    }

    
    public String getSplashPic() {
        return splashPic;
    }

    
    public void setSplashPic(String splashPic) {
        this.splashPic=splashPic;
    }

    
    public String getPackageName() {
        return packageName;
    }

    
    public void setPackageName(String packageName) {
        this.packageName=packageName;
    }


    
    public List<GameSDKConfig> getSdkConfigs() {
        return sdkConfigs;
    }


    
    public void setSdkConfigs(List<GameSDKConfig> sdkConfigs) {
        this.sdkConfigs=sdkConfigs;
    }
    
    
}
