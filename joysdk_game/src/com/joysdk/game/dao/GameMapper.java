package com.joysdk.game.dao;

import java.util.List;
import java.util.Map;

import com.joysdk.common.game.model.Game;

public interface GameMapper {

    public void createGame(Game game);

    public void updateGame(Game game);
    
    public Game getGameById(int id);
    
    public List<Integer> getGameIdsByCpId(int cpId);
    
    public int checkGameAlias(String alias);
    
    public Map<String, String> getGameConfigByGameId(int gameId, int channelId);
    
    
}
