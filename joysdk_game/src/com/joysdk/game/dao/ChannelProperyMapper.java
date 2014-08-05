package com.joysdk.game.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.joysdk.game.model.ChannelPropery;


public interface ChannelProperyMapper {

    public void insertChannelPropery(ChannelPropery pro);
    
    public List<ChannelPropery> getProperysByChannelId(@Param(value="channelId") int channelId);
    
    public void updateChannelPropery(@Param(value="id") int id, @Param(value="properyName") String properyName);
}
