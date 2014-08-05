package com.joysdk.game.dao;

import com.joysdk.common.game.model.CP;


public interface CPMapper {

    public void createCP(CP cp);
    
    public void updateCP(CP cp);
    
    public CP getCPById(int id);
}
