package com.joysdk.game.service;

import java.util.List;
import java.util.Map;

import com.joysdk.common.game.model.Game;
import com.joysdk.game.exception.GameException;


public interface GameService {

    public void createOrUpdateGame(Game game) throws GameException;
    
    public Game getGameById(int id) throws GameException;
    
    public List<Game> getGamesByCpId(int cpId) throws GameException;
    
    public boolean checkGameAlias(String alias) throws GameException;
    
    public Map<String, String> getGameConfigByGameId(int gameId, int channelId) throws GameException;
}
