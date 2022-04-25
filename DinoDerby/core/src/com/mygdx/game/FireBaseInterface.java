package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;

public interface FireBaseInterface {

    void createGame(String playerID);
    void setGameStarted(String gameID, String playerID);
    void finishGame(String gameID, String playerID);
    void joinGame(String gameID, String playerID);
    void getPlayersInGame(String gameID, String playerID);
    void updatePlayerInGame(String gameID, String playerID, Float xPos, Float yPos);
    void setParent(MyGdxGame parent);
}
