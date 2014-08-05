package com.joysdk.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.common.game.model.CP;
import com.joysdk.game.cache.GameCache;
import com.joysdk.game.dao.CPMapper;
import com.joysdk.game.exception.GameException;
import com.joysdk.game.service.CPService;

@Service(value="cpService")
public class CPServiceImpl implements CPService {

    @Autowired
    private CPMapper cpMapper;
    
    @Autowired
    private GameCache gameCache;
    
    public CP createOrUpdateCP(CP cp) throws GameException{
        if(cp.getId()==0){
            cpMapper.createCP(cp);
        }else{
            cpMapper.updateCP(cp);
        }
        return cp;
    }
    
    public CP getCPById(int id) throws GameException {
        CP cp=gameCache.readCPById(id);
        if(cp == null){
            cp=cpMapper.getCPById(id);
            if(cp != null){
                gameCache.writeCP(cp);
            }
        }
        return cp;
    }

}
