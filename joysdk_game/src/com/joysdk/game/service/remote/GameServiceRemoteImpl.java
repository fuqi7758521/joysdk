package com.joysdk.game.service.remote;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.common.exception.RemoteException;
import com.joysdk.common.game.model.CP;
import com.joysdk.common.game.model.Game;
import com.joysdk.common.game.service.remote.GameServiceRemote;
import com.joysdk.game.exception.GameException;
import com.joysdk.game.service.CPService;
import com.joysdk.game.service.GameService;

@Service(value="gameRemoteService")
public class GameServiceRemoteImpl implements GameServiceRemote{

    @Autowired
    private GameService gameService;
    
    @Autowired
    private CPService cpService;
    
    public Game getGameById(int gameId) throws RemoteException{
        try {
            return gameService.getGameById(gameId);
        } catch(GameException e) {
            e.printStackTrace();
            throw new RemoteException();
        }
    }

    public Map<String, String> getGameConfigByGameId(int gameId, int channelId) throws RemoteException{
        try {
            return gameService.getGameConfigByGameId(gameId, channelId);
        } catch(GameException e) {
            e.printStackTrace();
            throw new RemoteException();
        }
    }
    
    public CP getCPById(int cpId) throws RemoteException{
        try {
            return cpService.getCPById(cpId);
        } catch(GameException e) {
            e.printStackTrace();
            throw new RemoteException();
        }
    }
    
    public CP createOrUpdateCP(CP cp) throws RemoteException{
        try {
            return cpService.createOrUpdateCP(cp);
        } catch(GameException e) {
            e.printStackTrace();
            throw new RemoteException();
        }
    }

}
