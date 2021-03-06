package com.mygdx.game.util;

import com.mygdx.game.MyGdxGame;

public interface FireBaseInterface {

    void createGame(String playerID, String gameID);
    void setGameStarted(String gameID, String playerID);
    void finishGame(String gameID, String playerID);
    void leaveGame(String gameID, String playerID);
    void joinGame(String gameID, String playerID);
    void getPlayersInGame(String gameID, String playerID);
    void updatePlayerInGame(String gameID, String playerID, Float xPos, Float yPos);
    void setParent(MyGdxGame parent);
    Boolean checkGameStarted(String gameID);
}
