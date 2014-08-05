package com.joysdk.game.service;

import java.util.List;

import com.joysdk.game.model.ChannelConfig;
import com.joysdk.game.model.GameSDKConfig;


public interface ChannelConfigService {

    public void insertOrUpdateChannelConfig(ChannelConfig channnelConfig, List<GameSDKConfig> configs);
    
    public ChannelConfig getChannelConfigByGameId(int gameId, int channelId);
    
}
