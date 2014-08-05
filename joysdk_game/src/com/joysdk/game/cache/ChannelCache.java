package com.joysdk.game.cache;

import java.util.List;

import com.joysdk.game.exception.GameException;
import com.joysdk.game.model.Channel;


public interface ChannelCache {

    public void writeAllChannels(List<Channel> channels) throws GameException;
    
    public List<Channel> readAllChannels() throws GameException;
}
