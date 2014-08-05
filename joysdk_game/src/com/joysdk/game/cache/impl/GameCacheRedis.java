package com.joysdk.game.cache.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.common.game.GameCacheConstant;
import com.joysdk.common.game.model.CP;
import com.joysdk.common.game.model.Game;
import com.joysdk.common.resource.RedisService;
import com.joysdk.game.cache.GameCache;
import com.joysdk.game.exception.GameException;

@Service("gameCache")
public class GameCacheRedis implements GameCache {

    @Autowired
    private RedisService gameRedisService;
    
    /**游戏相关缓存方法**/
    public void writeGame(Game game) throws GameException {
        try {
            gameRedisService.set(GameCacheConstant.REDIS_GAME + game.getId(), game);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new GameException();
        }
    }

    public Game readGameById(int id) throws GameException {
        try {
            return gameRedisService.get(GameCacheConstant.REDIS_GAME + id, Game.class);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new GameException();
        }
    }

    public void writeGameConfig(int gameId, int channelId, Map<String, String> config) throws GameException {
        try {
            gameRedisService.set(GameCacheConstant.REDIS_GAME_CONFIG + gameId+":channel"+channelId, config);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new GameException();
        }
    }
    
    
    @SuppressWarnings("unchecked")
    public Map<String, String> readGameConfig(int gameId, int channelId) throws GameException{
        try {
            return gameRedisService.get(GameCacheConstant.REDIS_GAME_CONFIG + gameId+":channel"+channelId, Map.class);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new GameException();
        }
    }
    
    public void removeGame(Game game) throws GameException{
        try {
            gameRedisService.del(GameCacheConstant.REDIS_GAME + game.getId());
            List<String> keys=gameRedisService.keys(GameCacheConstant.REDIS_GAME_CONFIG + game.getId()+":channel*");
            gameRedisService.del((String[])keys.toArray());
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new GameException();
        }
    }

    public void writeCP(CP cp) throws GameException {
        try {
            gameRedisService.set(GameCacheConstant.REDIS_CP_ID+ cp.getId(), cp);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new GameException();
        }
    }

    public CP readCPById(int id) throws GameException {
        try {
            return gameRedisService.get(GameCacheConstant.REDIS_CP_ID+ id, CP.class);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new GameException();
        }
    }

    public void removeCPById(int id) throws GameException {
        try {
            gameRedisService.del(GameCacheConstant.REDIS_CP_ID + id);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new GameException();
        }
        
    }

    
}
