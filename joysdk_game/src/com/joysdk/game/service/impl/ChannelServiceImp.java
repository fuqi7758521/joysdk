package com.joysdk.game.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.common.model.Paging;
import com.joysdk.game.cache.ChannelCache;
import com.joysdk.game.dao.ChannelMapper;
import com.joysdk.game.dao.ChannelProperyMapper;
import com.joysdk.game.exception.GameException;
import com.joysdk.game.model.Channel;
import com.joysdk.game.model.ChannelPropery;
import com.joysdk.game.service.ChannelService;

@Service
public class ChannelServiceImp implements ChannelService {

    @Autowired
    private ChannelMapper channelMapper;
    
    @Autowired
    private ChannelProperyMapper channelProperyMapper;
    
    @Autowired
    private ChannelCache channelCache;
    
    public void createChannel(Channel channel) throws GameException {
        channelMapper.createChannel(channel);
    }

    public Channel getChannelById(int id) throws GameException {
        return channelMapper.getChannelById(id);
    }

    public Paging<Channel> getChannels(int pid, int cpId, Paging<Channel> paging) throws GameException {
        int count=channelMapper.countChannels(pid, cpId);
        if(count >0){
            List<Channel> list=channelMapper.getChannels(pid, cpId, paging.getStartNum(), paging.getSize());
            paging.setResult(list);
            paging.setTotalCount(count);
        }
        return paging;
    }
    
    public List<Channel> getAllChannels() throws GameException{
        List<Channel> list=channelCache.readAllChannels();
        if(list == null || list.isEmpty()){
            Paging<Channel> paging=new Paging<Channel>();
            paging.setStartNum(-1);
            paging.setSize(-1);
            list=channelMapper.getChannels(0, 0, paging.getStartNum(), paging.getSize());
            channelCache.writeAllChannels(list);
        }
        return list;
    }

    public void updateChannel(int id, String name) throws GameException {
        channelMapper.updateChannel(id, name);
    }

    public void createChannelPropery(ChannelPropery pro) throws GameException {
        channelProperyMapper.insertChannelPropery(pro);
    }

    public List<ChannelPropery> getProperysByChannelId(int channelId) throws GameException {
        return channelProperyMapper.getProperysByChannelId(channelId);
    }

    public void updateChannelPropery(int id, String properyName) throws GameException {
        channelProperyMapper.updateChannelPropery(id, properyName);
    }

}
