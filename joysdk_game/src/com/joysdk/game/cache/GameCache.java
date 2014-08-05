package com.joysdk.game.cache;

import java.util.Map;

import com.joysdk.common.game.model.CP;
import com.joysdk.common.game.model.Game;
import com.joysdk.game.exception.GameException;


public interface GameCache {

    public void writeGame(Game game) throws GameException;
    
    public Game readGameById(int id) throws GameException;
    
    public void writeGameConfig(int gameId, int channelId, Map<String, String> config) throws GameException;
    
    public Map<String, String> readGameConfig(int gameId, int channelId) throws GameException;
    
    public void removeGame(Game game) throws GameException;
    
    public void writeCP(CP cp) throws GameException;
    
    public CP readCPById(int id) throws GameException;
    
    public void removeCPById(int id) throws GameException;
}
