package com.joysdk.game.dao;

import org.apache.ibatis.annotations.Param;

import com.joysdk.game.model.ChannelConfig;


public interface ChannelConfigMapper {

    public void insertChannelConfig(ChannelConfig channnelConfig);

    public void updateChannelConfig(ChannelConfig channnelConfig);
    
    public ChannelConfig getChannelConfigByGameId(@Param(value="gameId") int gameId, @Param(value="channelId") int channelId);
}
