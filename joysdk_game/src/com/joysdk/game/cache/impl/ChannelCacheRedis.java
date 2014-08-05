package com.joysdk.game.cache.impl;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.common.game.GameCacheConstant;
import com.joysdk.common.resource.RedisService;
import com.joysdk.game.cache.ChannelCache;
import com.joysdk.game.exception.GameException;
import com.joysdk.game.model.Channel;

@Service("channelCache")
public class ChannelCacheRedis implements ChannelCache {

    @Autowired
    private RedisService gameRedisService;
    
    public void writeAllChannels(List<Channel> channels) throws GameException{
        try {
            gameRedisService.lpush(GameCacheConstant.REDIS_CHANNEL, channels);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new GameException();
        }
    }
    
    public List<Channel> readAllChannels() throws GameException{
        try {
            return gameRedisService.lrange(GameCacheConstant.REDIS_CHANNEL, Channel.class, 0, -1);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new GameException();
        }
    }

}
