package com.mygdx.game;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;


public class AndroidInterfaceClass implements FireBaseInterface {

    private final FirebaseDatabase database;
    private DatabaseReference myRef;
    private MyGdxGame parent;

    public AndroidInterfaceClass() {
        this.database = FirebaseDatabase.getInstance("https://dino-derby-default-rtdb.europe-west1.firebasedatabase.app");
    }

    public void setParent(MyGdxGame parent) {
        this.parent = parent;
    }

//    public void listenToGameStart(String gameID) {
//        if (gameExists(gameID)) {
//            try {
//                myRef = database.getReference(gameID+"/players");
//                myRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            Boolean gameStarted = snapshot.getValue(Boolean.class);
//                            parent.startGame(gameStarted);
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            } catch (Error err) {
//                throw new IllegalArgumentException(err);
//            }
//        }
//        else {
//            throw new IllegalArgumentException("Game does not exist ListenToGameStart()");
//        }
//    }

    public void finishGame(String gameID, String playerID) {
        if (gameExists(gameID)) {
            try {
                myRef = database.getReference(gameID);
                myRef.child("winner").setValue(playerID);
            }catch (Error err) {
                throw new IllegalArgumentException(err);
            }
        }
        else {
            throw  new IllegalArgumentException("Game does not exist");
        }
    }

    @Override
    public void leaveGame(String gameID, String playerID) {
        if (this.gameExists(gameID)) {
            System.out.println("gamet du prøver å leave fins");
            try {
                myRef = database.getReference(gameID+"/players/"+playerID);
                myRef.removeValue();
//                myRef.setValue(this.createPlayerMap());
//                this.getPlayersInGame(gameID, playerID);
                parent.setCurrGameID("");
                parent.gameStarted = false;
//                this.listenToGameStart(gameID);
//                this.listenToGameFinish(gameID);
            } catch (Error err) {
                System.out.println("kaster exception");
                throw new IllegalArgumentException("Failed leaving game: " + gameID + " for player: " + playerID + err);
            }
        }
        else {
            throw new IllegalArgumentException("Game does not exist");
        }
    }

    public void listenToGameFinish(String gameID) {
        if (gameExists(gameID)) {
            try {
                myRef = database.getReference(gameID+"/winner");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String winner = snapshot.getValue(String.class);
//                            parent.startGame(gameStarted);
                            if (!winner.isEmpty()) {
                                System.out.println("The winenr is:  " + winner);
                                parent.finishGame(winner);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } catch (Error err) {
                throw new IllegalArgumentException(err);
            }
        }
        else {
            throw new IllegalArgumentException("Game does not exist ListenToGameStart()");
        }
    }

    public void setGameStarted(String gameID, String playerID) {
        if (gameExists(gameID)) {
            try {
            myRef = database.getReference(gameID+"/players/"+playerID);
            myRef.child("gameStarted").setValue(1);
            }catch (Error err) {
                throw new IllegalArgumentException(err);
            }
        }
        else {
            throw new IllegalArgumentException("Game does not exist");
        }
    }

    @Override
    public void createGame(String playerID, String gameID) {
//        String gameID = UUID.randomUUID().toString();
//        String gameID = "hei";
        try {
            myRef = database.getReference(gameID);
            myRef.child("winner").setValue("");
            myRef = database.getReference(gameID+"/players/"+playerID);
            myRef.setValue(this.createPlayerMap());
            this.getPlayersInGame(gameID, playerID);
            parent.setCurrGameID(gameID);
//            this.listenToGameStart(gameID);
            this.listenToGameFinish(gameID);
        } catch (Error err) {
            throw new IllegalArgumentException("Failed creating game with gameID: " + gameID + " for player: " + playerID + err);
        }
    }

    @Override
    public void joinGame(String gameID, String playerID) {
        if (this.gameExists(gameID)) {
            System.out.println("gamet du prøver å joine fins");
            try {
                myRef = database.getReference(gameID+"/players/"+playerID);
                myRef.setValue(this.createPlayerMap());
                this.getPlayersInGame(gameID, playerID);
                parent.setCurrGameID(gameID);
//                this.listenToGameStart(gameID);
                this.listenToGameFinish(gameID);
            } catch (Error err) {
                System.out.println("kaster exception");
                throw new IllegalArgumentException("Failed joining game: " + gameID + " for player: " + playerID + err);
            }
        }
        else {
            throw new IllegalArgumentException("Game does not exist");
        }
    }

    public void updatePlayerInGame(String gameID, String playerID, Float xPos, Float yPos) {
        if (this.playerIsInGame(gameID, playerID)) {
            try {
                myRef = database.getReference(gameID+"/players/"+playerID);
                Map playerMap = this.createPlayerMap(xPos, yPos);
                playerMap.put("gameStarted", 1);
                myRef.setValue(playerMap);
            } catch (Error err) {
                throw new IllegalArgumentException("Failed updating player in game: " + gameID + " for player: " + playerID + err);
            }
        }
        else {
            throw new IllegalArgumentException("Player: " + playerID +  " haven't joined game: " + gameID);
        }
    }

    @Override
    public void getPlayersInGame(String gameID, String playerID) {
        AtomicBoolean reqFinished = new AtomicBoolean(false);
            try {
                myRef = database.getReference(gameID+"/players");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            System.out.println("player data changed, refetching i getPlayersInGame()");
//                            System.out.println(snapshot.getChildren());
                            if (snapshot.exists()) {
                                //clear List
                                Map<String, Map<String, Float>> players = new HashMap<>();
//                                System.out.println("-----------");
//                                System.out.println("checking if game has started");
//                                System.out.println(snapshot.child("gameStarted").getValue());
//                                parent.checkGameStarted((Boolean) snapshot.child("gameStarted").getValue());
//                                DataSnapshot playersSnapshot = snapshot.child("players");
                                System.out.println("snapshot exists getPlayersInGame()");
                                Boolean gameStarted = true;
                                System.out.println(snapshot.getChildren());
                                for (DataSnapshot player : snapshot.getChildren()) {
                                    System.out.println("i for løkke i getPlayersInGame()");
//                                    try {
                                    System.out.println(player);
////                                System.out.println(player.);
//                                    System.out.println(player.getKey());
//                                    System.out.println(player.getValue());
//                                    Map<String, Float> playerMap = (HashMap<String, Float>) player.getValue();
                                    Map<String, Float> playerMap = new HashMap();
                                    Map<String, Long> firebasePlayerMap = (Map<String, Long>) player.getValue();
                                    for (String firebasePlayerMapKey: firebasePlayerMap.keySet()) {
                                        playerMap.put(firebasePlayerMapKey, firebasePlayerMap.get(firebasePlayerMapKey).floatValue());
                                    }
                                    System.out.println("||||||||||||");
                                    System.out.println(playerMap);
//                                    System.out.println("-------");
//                                    System.out.println("Skal sjekke long greier nå");
//                                    System.out.println(playerMap.get("gameStarted"));
//                                    System.out.println("-------------");
//                                    System.out.println(playerMap.get("gameStarted").getClass());
//                                    System.out.println("--------");
//                                    Long blabla = playerMap.get("gameStarted").longValue();
//                                    System.out.println(blabla);
                                    System.out.println(playerMap.get("gameStarted"));
//                                    System.out.println(playerMap.get("gameStarted").getClass().toString());
                                    Float playerReady = playerMap.get("gameStarted");
                                    if (playerReady == 0) {
                                        System.out.println("Not all players are ready");
                                        gameStarted = false;
                                    }
                                    players.put(player.getKey(), playerMap);
//                                    } catch (Error err) {
//                                        throw new IllegalArgumentException(err);
//                                    }
                                }
                                parent.setPlayers(players);
                                if (gameStarted) {
                                    System.out.println("all players are ready, starting ...");
                                    parent.gameStarted = true;
                                }
                            }
                        } catch (Error err) {
                            throw new IllegalArgumentException(err);
                            }

                        reqFinished.set(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } catch (Error err) {
                throw new IllegalArgumentException("Failed retrieving players in game: " + gameID + err);
            }
        //While loop to pause code until request is finished
        while (!reqFinished.get()) {
            System.out.println("fetching i getPlayersInGame");
        }

    }

    private HashMap createPlayerMap() {
        HashMap playerData = new HashMap<String, Float>();
        playerData.put("xPos", Double.valueOf(0).longValue());
        playerData.put("yPos", Double.valueOf(0).longValue());
        playerData.put("gameStarted",Double.valueOf(0).longValue());
        return playerData;
    }

    private HashMap createPlayerMap(Float xPos, Float yPos) {
        HashMap playerData = new HashMap<String, Long>();
        playerData.put("xPos", Double.valueOf(xPos.doubleValue()).longValue());
        playerData.put("yPos",Double.valueOf(yPos.doubleValue()).longValue());
//        playerData.put("gameStarted", Float.valueOf(0));
        return playerData;
    }

    public Boolean gameExists(String gameID) throws IllegalArgumentException{
        if (gameID.isEmpty()) {
            throw new IllegalArgumentException("GameID is empty");
        }
        AtomicBoolean reqFinished = new AtomicBoolean(false);
        AtomicBoolean gameExists = new AtomicBoolean(false);
        myRef = database.getReference(gameID);

        System.out.println("før oncompletelistener");
        System.out.println(myRef.getRef());
    //        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println("du er i on data change");
//                System.out.println(snapshot.getValue());
//                if (snapshot.exists()) {
//                    System.out.println("game exists");
//                    gameExists.set(true);
//                }
//                    reqFinished.set(true);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                System.out.println("cancelled");
//            }
//        });
            myRef.get().addOnCompleteListener(task -> {
                System.out.println("før successful i gameExists()");
                if (task.isSuccessful()) {
                    System.out.println("før exists i gameExists()");
                    if (task.getResult().exists()) {
                        gameExists.set(true);
                    }
                        reqFinished.set(true);
                } else {
                    throw new IllegalArgumentException("Failed to retrieve data for reference: " + myRef.getRef());
                }
            });
        //While loop to pause code until request is finished
        while (!reqFinished.get()) {
            System.out.println("fetching ... i gameExists()");
        }

        return gameExists.get();
    }

    private Boolean playerIsInGame(String gameID, String playerID) {
        if (gameID.isEmpty() || playerID.isEmpty()) {
            throw new IllegalArgumentException("GameID or playerID is empty");
        }
        AtomicBoolean reqFinished = new AtomicBoolean(false);
        AtomicBoolean playerInGame = new AtomicBoolean(false);
        String refString = gameID+"/players/"+playerID;
        myRef = database.getReference(refString);
        myRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    playerInGame.set(true);
                }
                reqFinished.set(true);
            }
            else {
                throw new IllegalArgumentException("Failed to retrieve data for reference: " + refString);
            }
        });
        while (!reqFinished.get()) {
            System.out.println("fetching ...");
        }

        return playerInGame.get();
    }

}


