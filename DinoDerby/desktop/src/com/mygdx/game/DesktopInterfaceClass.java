package com.mygdx.game;

import java.util.HashMap;
import java.util.List;

public class DesktopInterfaceClass implements FireBaseInterface {


    //TODO - Add support for firebase RTDB on desktop
    public DesktopInterfaceClass() {

    }


    @Override
    public String createGame(String playerID) {
        return "";
    }

    @Override
    public void joinGame(String gameID, String playerID) {
    }

    @Override
    public List<HashMap> getPlayersInGame(String gameID, String playerID) {
        return null;
    }

    @Override
    public void updatePlayerInGame(String gameID, String playerID, Integer xPos, Integer yPos, Integer zPos) {

    }


}
