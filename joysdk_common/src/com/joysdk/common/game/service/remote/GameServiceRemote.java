package com.joysdk.common.game.service.remote;

import java.util.Map;

import com.joysdk.common.exception.RemoteException;
import com.joysdk.common.game.model.CP;
import com.joysdk.common.game.model.Game;


public interface GameServiceRemote {

    public Game getGameById(int gameId) throws RemoteException;
    
    public Map<String, String> getGameConfigByGameId(int gameId, int channelId) throws RemoteException;
    
    public CP getCPById(int cpId) throws RemoteException;
    
    public CP createOrUpdateCP(CP cp) throws RemoteException;
}
