package com.joysdk.game.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.game.dao.ChannelConfigMapper;
import com.joysdk.game.dao.GameSDKConfigMapper;
import com.joysdk.game.model.ChannelConfig;
import com.joysdk.game.model.GameSDKConfig;
import com.joysdk.game.service.ChannelConfigService;

@Service
public class ChannelConfigServiceImpl implements ChannelConfigService {

    @Autowired
    private ChannelConfigMapper channelConfigMapper;
    
    @Autowired
    private GameSDKConfigMapper gameSDKConfigMapper; 
    
    public ChannelConfig getChannelConfigByGameId(int gameId, int channelId) {
        return channelConfigMapper.getChannelConfigByGameId(gameId, channelId);
    }

    /**
     * 创建放在service中执行，通过一个事物完成
     * @param channnelConfig
     * @param configs
     */
    public void insertChannelConfig(ChannelConfig channnelConfig, List<GameSDKConfig> configs){
        channelConfigMapper.insertChannelConfig(channnelConfig);
        if(configs!=null && !configs.isEmpty()){
            for(GameSDKConfig config : configs){
                config.setChannelConfigId(channnelConfig.getId());
                gameSDKConfigMapper.insertSDKConfig(config);
            }
        }
    }
    
    public void updateChannelConfig(ChannelConfig channnelConfig, List<GameSDKConfig> configs){
        channelConfigMapper.updateChannelConfig(channnelConfig);
        if(configs!=null && !configs.isEmpty()){
            for(GameSDKConfig config : configs){
                config.setChannelConfigId(channnelConfig.getId());
                gameSDKConfigMapper.updateSDKConfig(config);
            }
        }
    }
    
    public void insertOrUpdateChannelConfig(ChannelConfig channnelConfig, List<GameSDKConfig> configs){
        if(channnelConfig.getId() !=0){
            updateChannelConfig(channnelConfig, configs);
        }else{
            insertChannelConfig(channnelConfig, configs);
        }
    }
    
}
