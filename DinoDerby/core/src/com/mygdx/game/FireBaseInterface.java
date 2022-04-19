package com.mygdx.game;

import java.util.HashMap;
import java.util.List;

public interface FireBaseInterface {

    String createGame(String playerID);

    void joinGame(String gameID, String playerID);
    List<HashMap> getPlayersInGame(String gameID, String playerID);
    void updatePlayerInGame(String gameID, String playerID, Integer xPos, Integer yPos, Integer zPos);

}
