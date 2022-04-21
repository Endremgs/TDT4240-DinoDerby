package com.mygdx.game;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class AndroidInterfaceClass implements FireBaseInterface {

    private final FirebaseDatabase database;
    private DatabaseReference myRef;

    public AndroidInterfaceClass() {
        this.database = FirebaseDatabase.getInstance("https://dino-derby-default-rtdb.europe-west1.firebasedatabase.app");
    }

    @Override
    public String createGame(String playerID) {
        String gameID = UUID.randomUUID().toString();
        try {
            myRef = database.getReference(gameID+"/"+playerID);
            HashMap playerData = this.createPlayerMap();
            myRef.setValue(playerData);
            return gameID;
        } catch (Error err) {
            throw new IllegalArgumentException("Failed creating game with gameID: " + gameID + " for player: " + playerID + err);
        }
    }

    @Override
    public void joinGame(String gameID, String playerID) throws IllegalArgumentException{
        System.out.println("playerID: "+ playerID);
        System.out.println("gameID: "+ gameID);
        if (this.gameExists(gameID)) {
            try {
                System.out.println("game exists, gameID: "+ gameID);
                myRef = database.getReference(gameID+"/"+playerID);
                myRef.setValue(this.createPlayerMap());
            } catch (Error err) {
                throw new IllegalArgumentException("Failed joining game: " + gameID + " for player: " + playerID + err);
            }
        }
    }

    public void updatePlayerInGame(String gameID, String playerID, Integer xPos, Integer yPos, Integer zPos) {
        if (this.playerIsInGame(gameID, playerID)) {
            try {
                myRef = database.getReference(gameID+"/"+playerID);
                myRef.setValue(createPlayerMap(xPos, yPos, zPos));
            } catch (Error err) {
                throw new IllegalArgumentException("Failed updating player in game: " + gameID + " for player: " + playerID + err);
            }
        }
        else {
            throw new IllegalArgumentException("Player: " + playerID +  " haven't joined game: " + gameID);
        }
    }

    @Override
    public List<HashMap> getPlayersInGame(String gameID, String playerID) {
        AtomicBoolean reqFinished = new AtomicBoolean(false);
        List<HashMap> players = new ArrayList<>();
        if (gameExists(gameID)) {
            try {
                myRef = database.getReference(gameID);
                myRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            System.out.println(dataSnapshot.getChildren());
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                if (child.getKey() != playerID) {
                                    Integer xPos = (Integer) child.child("xPos").getValue();
                                    Integer yPos = (Integer) child.child("yPos").getValue();
                                    Integer zPos = (Integer) child.child("zPos").getValue();
                                    players.add(this.createPlayerMap(xPos, yPos, zPos));
                                    reqFinished.set(true);
                                }
                            }
                        }
                        throw new IllegalArgumentException("Game " + gameID + "does not exist");
                    } else {
                        throw new IllegalArgumentException("Failed retrieving game: " + gameID);
                    }
                });
            } catch (Error err) {
                throw new IllegalArgumentException("Failed retrieving players in game: " + gameID + err);
            }
        }
        //While loop to pause code until request is finished
        while (!reqFinished.get()) {
        }
        return players;
    }

    private HashMap createPlayerMap() {
        HashMap playerData = new HashMap();
        playerData.put("xPos", 0);
        playerData.put("yPos", 0);
        playerData.put("zPos", 0);
        return playerData;
    }

    private HashMap createPlayerMap(Integer xPos, Integer yPos, Integer zPos) {
        HashMap playerData = new HashMap();
        playerData.put("xPos", xPos);
        playerData.put("yPos", yPos);
        playerData.put("zPos", zPos);
        return playerData;
    }

    private Boolean gameExists(String gameID) throws IllegalArgumentException{
        AtomicBoolean reqFinished = new AtomicBoolean(false);
        AtomicBoolean gameExists = new AtomicBoolean(false);
        String refString = gameID;
        myRef = database.getReference(gameID);
            myRef.get().addOnCompleteListener(task -> {
                System.out.println("checking if task is successfull");
                if (task.isSuccessful()) {
                    System.out.println("Task is succesfull");
                    if (task.getResult().exists()) {
                        System.out.println("game exists");
                        gameExists.set(true);
                        reqFinished.set(true);
                    }
                } else {
                    throw new IllegalArgumentException("Failed to retrieve data for reference: " + refString);
                }
            });
            //While loop to pause code until request is finished
        while (!reqFinished.get()) {
        }

        return gameExists.get();
    }

    private Boolean playerIsInGame(String gameID, String playerID) {
        AtomicBoolean playerInGame = new AtomicBoolean(false);
        String refString = gameID+"/"+playerID;
        myRef = database.getReference(refString);
        myRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    playerInGame.set(true);
                }
            }
            else {
                throw new IllegalArgumentException("Failed to retrieve data for reference: " + refString);
            }
        });
        return playerInGame.get();
    }

}


