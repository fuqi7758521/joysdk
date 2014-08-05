package com.joysdk.game.service;

import com.joysdk.common.game.model.CP;
import com.joysdk.game.exception.GameException;


public interface CPService {

    public CP createOrUpdateCP(CP cp) throws GameException;
    
    public CP getCPById(int id) throws GameException;
}
