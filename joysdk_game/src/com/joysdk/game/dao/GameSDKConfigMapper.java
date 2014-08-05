package com.joysdk.game.dao;

import java.util.List;

import com.joysdk.game.model.GameSDKConfig;


public interface GameSDKConfigMapper {

    public void insertSDKConfig(GameSDKConfig config);
    
    public void updateSDKConfig(GameSDKConfig config);
    
    public List<GameSDKConfig> getSDKConfigByChannelConfigId(int id, int gameId, int channelId);
}
