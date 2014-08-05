package com.joysdk.game.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.common.game.model.Game;
import com.joysdk.game.cache.GameCache;
import com.joysdk.game.dao.GameMapper;
import com.joysdk.game.exception.GameException;
import com.joysdk.game.service.GameService;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper gameMapper;
    
    @Autowired
    private GameCache gameCache;
    
    public void createOrUpdateGame(Game game) throws GameException {
        if(game.getId()==0){
            gameMapper.createGame(game);
        }else{
            gameMapper.updateGame(game);
        }
    }
    
    public Game getGameById(int id)  throws GameException{
        Game game=gameCache.readGameById(id);
        if(game == null){
            game = gameMapper.getGameById(id);
            if(game != null){
                gameCache.writeGame(game);
            }
        }
        return game;
    }

    public List<Game> getGamesByCpId(int cpId) throws GameException{
        List<Integer> gameIds=gameMapper.getGameIdsByCpId(cpId);
        List<Game> games= new ArrayList<Game>(gameIds.size());
        for(Integer id : gameIds){
            games.add(getGameById(id));
        }
        return games;
    }
    
    public boolean checkGameAlias(String alias) throws GameException{
        return (gameMapper.checkGameAlias(alias)==0);
    }
    
    public Map<String, String> getGameConfigByGameId(int gameId, int channelId)  throws GameException{
        Map<String, String> config=gameCache.readGameConfig(gameId, channelId);
        if(config == null){
            config = gameMapper.getGameConfigByGameId(gameId, channelId);
            if(config != null){
                gameCache.writeGameConfig(gameId, channelId, config);
            }
        }
        return config;
    }

}
