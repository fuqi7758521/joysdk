package com.joysdk.game.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.joysdk.game.model.Channel;


public interface ChannelMapper {

    public void createChannel(Channel channel);
    
    public Channel getChannelById(@Param(value="id") int id);
    
    public List<Channel> getChannels(@Param(value="pid") int pid, @Param(value="cpId") int cpId, @Param(value="start") int start, @Param(value="size") int size);
    
    public int countChannels(@Param(value="pid") int pid, @Param(value="cpId") int cpId);
    
    public void updateChannel(@Param(value="id") int id, @Param(value="name") String name);
}
