package com.joysdk.game.service;

import java.util.List;

import com.joysdk.common.model.Paging;
import com.joysdk.game.exception.GameException;
import com.joysdk.game.model.Channel;
import com.joysdk.game.model.ChannelPropery;


public interface ChannelService {

    public void createChannel(Channel channel) throws GameException;
    
    public Channel getChannelById(int id) throws GameException;
    
    public Paging<Channel> getChannels(int pid, int cpId, Paging<Channel> paging) throws GameException;
    
    public List<Channel> getAllChannels() throws GameException;
    
    public void updateChannel(int id, String name) throws GameException;
    
    public void createChannelPropery(ChannelPropery pro) throws GameException;
    
    public List<ChannelPropery> getProperysByChannelId(int channelId) throws GameException;
    
    public void updateChannelPropery(int id, String properyName) throws GameException;
    
}
