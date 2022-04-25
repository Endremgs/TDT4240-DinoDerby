package com.mygdx.game;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public Boolean checkGameStarted(String gameID) {
        AtomicBoolean reqFinished = new AtomicBoolean(false);
        AtomicBoolean gameStarted = new AtomicBoolean(false);

        if (gameExists(gameID)) {

            myRef = database.getReference(gameID+"/players");
            myRef.get().addOnCompleteListener(snapshot -> {
                if (snapshot.isSuccessful()) {
                    if (snapshot.getResult().exists()) {
                            Map<String, Map<String, Float>> players = new HashMap<>();
                            System.out.println("snapshot exists checkGameStarted()");
                            System.out.println(snapshot.getResult().getChildren());
                            for (DataSnapshot player : snapshot.getResult().getChildren()) {
                                System.out.println("i for løkke i checkGameStarted()");
                                System.out.println(player);
                                Map<String, Float> playerMap = new HashMap();
                                Map<String, Long> firebasePlayerMap = (Map<String, Long>) player.getValue();
                                for (String firebasePlayerMapKey: firebasePlayerMap.keySet()) {
                                    playerMap.put(firebasePlayerMapKey, firebasePlayerMap.get(firebasePlayerMapKey).floatValue());
                                }
                                System.out.println("||||||||||||");
                                System.out.println(playerMap);
                                System.out.println(playerMap.get("gameStarted"));
                                Float playerReady = playerMap.get("gameStarted");
                                if (playerReady == 0) {
                                    System.out.println("Not all players are ready");
                                    gameStarted.set(false);
                                }
                                players.put(player.getKey(), playerMap);
                            }
                            parent.setPlayers(players);
                            if (gameStarted.get()) {
                                System.out.println("all players are ready, starting ...");
                            }
                        }
                        else {
                            gameStarted.set(false);
                    }
                }
                else {
                    throw new IllegalArgumentException("Failed to retrieve data for reference: ");
                }
                    reqFinished.set(true);
            });
            while (!reqFinished.get()) {
                System.out.println("fetching i checkGameStarted");
            }
        }
        System.out.println("returning game started variable");
        System.out.println(gameStarted.get());
        return gameStarted.get();

    }

    public void setParent(MyGdxGame parent) {
        this.parent = parent;
    }

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
                parent.setCurrGameID("");
                parent.gameStarted = false;
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
        if (!gameExists(gameID)) {


        try {
            myRef = database.getReference(gameID);
            myRef.child("winner").setValue("");
            myRef = database.getReference(gameID+"/players/"+playerID);
            myRef.setValue(this.createPlayerMap());
            this.getPlayersInGame(gameID, playerID);
            parent.setCurrGameID(gameID);
            this.listenToGameFinish(gameID);
        } catch (Error err) {
            throw new IllegalArgumentException("Failed creating game with gameID: " + gameID + " for player: " + playerID + err);
        }
        } else {
            throw new IllegalArgumentException("Game already exists");
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
                            if (snapshot.exists()) {
                                //clear List
                                Map<String, Map<String, Float>> players = new HashMap<>();
                                System.out.println("snapshot exists getPlayersInGame()");
                                Boolean gameStarted = true;
                                System.out.println(snapshot.getChildren());
                                for (DataSnapshot player : snapshot.getChildren()) {
                                    System.out.println("i for løkke i getPlayersInGame()");
//                                    try {
                                    System.out.println(player);
                                    Map<String, Float> playerMap = new HashMap();
                                    Map<String, Long> firebasePlayerMap = (Map<String, Long>) player.getValue();
                                    for (String firebasePlayerMapKey: firebasePlayerMap.keySet()) {
                                        playerMap.put(firebasePlayerMapKey, firebasePlayerMap.get(firebasePlayerMapKey).floatValue());
                                    }
                                    System.out.println("||||||||||||");
                                    System.out.println(playerMap);
                                    System.out.println(playerMap.get("gameStarted"));
                                    Float playerReady = playerMap.get("gameStarted");
                                    if (playerReady == 0) {
                                        System.out.println("Not all players are ready");
                                        gameStarted = false;
                                    }
                                    players.put(player.getKey(), playerMap);
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


