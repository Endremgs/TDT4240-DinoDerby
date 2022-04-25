package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;

public class DesktopInterfaceClass implements FireBaseInterface {


    //TODO - Add support for firebase RTDB on desktop
    public DesktopInterfaceClass() {

    }


    @Override
    public void createGame(String playerID, String gameID) {
    }

    @Override
    public void setGameStarted(String gameID, String playerID) {

    }

    @Override
    public void finishGame(String gameID, String playerID) {

    }

    @Override
    public void leaveGame(String gameID, String playerID) {

    }

    @Override
    public void joinGame(String gameID, String playerID) {
    }

    @Override
    public void  getPlayersInGame(String gameID, String playerID) {
    }

    @Override
    public void updatePlayerInGame(String gameID, String playerID, Float xPos, Float yPos) {

    }

    @Override
    public void setParent(MyGdxGame parent) {

    }


}
