package com.joysdk.common.game.proxy;

import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joysdk.common.exception.RemoteException;
import com.joysdk.common.game.GameCacheConstant;
import com.joysdk.common.game.model.CP;
import com.joysdk.common.game.model.Game;
import com.joysdk.common.game.service.remote.GameServiceRemote;
import com.joysdk.common.resource.RedisService;

public class GameProxy {

    private static final Logger logger=LoggerFactory.getLogger(GameProxy.class);

    private RedisService gameRedisService;

    private GameServiceRemote gameRemoteService;

    public void setGameRedisService(RedisService gameRedisService) {
        this.gameRedisService=gameRedisService;
    }

    public void setGameRemoteService(GameServiceRemote gameRemoteService) {
        this.gameRemoteService=gameRemoteService;
    }

    public CP getCPById(int id) {
        CP cp=null;
        try {
            cp=gameRedisService.get(GameCacheConstant.REDIS_CP_ID + id, CP.class);
            if(cp == null) {
                cp=gameRemoteService.getCPById(id);
            }
        } catch(TimeoutException e) {
            e.printStackTrace();
            logger.error("TimeoutException " + e.getMessage());
        } catch(RemoteException e) {
            e.printStackTrace();
            logger.error("RemoteException " + e.getMessage());
        }
        return cp;
    }
    
    public CP createOrUpdateCP(CP cp){
        try {
            return gameRemoteService.createOrUpdateCP(cp);
        } catch(RemoteException e) {
            e.printStackTrace();
            logger.error("RemoteException " + e.getMessage());
        }
        return null;
    }
    
    public Game getGameById(int id){
        Game game=null;
        try {
           game = gameRedisService.get(GameCacheConstant.REDIS_GAME + id, Game.class);
           if(game == null){
               game=gameRemoteService.getGameById(id);
           }
        } catch(TimeoutException e) {
            e.printStackTrace();
            logger.error("TimeoutException " + e.getMessage());
        } catch(RemoteException e) {
            e.printStackTrace();
            logger.error("RemoteException " + e.getMessage());
        }
        return game;
    }

}
