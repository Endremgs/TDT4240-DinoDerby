package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;

public class LobbyScreen implements Screen {

    private final MyGdxGame parent;
    private Stage stage;

    public LobbyScreen(MyGdxGame dinoDerby){
        parent = dinoDerby;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void show() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Table table = new Table();
        Table leftTable = new Table();
        Table rightTable = new Table();


        table.setFillParent(true);
        stage.addActor(table);

        table.add(leftTable);
        table.add(rightTable);

        Skin skin = new Skin(Gdx.files.internal("skin/buttonskin.json"));

        TextButton startGame = new TextButton("Start Game", skin);
        TextButton backBtn = new TextButton("Back", skin);

        //Create a list view rendering a text element for each player in the lobby.
        //Ensure rerender of this list upon update.
//        List listView = new List(List.ListStyl);

        TextButton checkPlayers = new TextButton("check", skin);

        leftTable.add(startGame).fillX().uniformX();
        leftTable.row().pad(10, 0, 10, 0);
        leftTable.row();
        leftTable.add(backBtn).fillX().uniformX();
        leftTable.row();
        leftTable.add(checkPlayers).fillX().uniformX();


        TextButton lobbyText = new TextButton("Players", skin);
        rightTable.add(lobbyText);
        rightTable.row().pad(10, 0, 10, 0);
        System.out.println("-----------");
        System.out.println(parent.getPlayers());
        for (String player: parent.getPlayers().keySet()) {
            System.out.println("-----------");
            System.out.println(player);
            TextButton playerBtn = new TextButton(player, skin);
            rightTable.add(playerBtn);
            rightTable.row().pad(10, 0, 10, 0);
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        if (stage.)
//            stage

        try {

        stage.draw();
        } catch (IllegalStateException i) {
            stage.getBatch().end();
            stage.draw();
            System.out.println("Restarting stage");
        }

        startGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    parent.getFirebaseInstance().setGameStarted(parent.getCurrGameID(), parent.getPlayerID());
//                    parent.changeScreen(MyGdxGame.PLAY);
//                    parent.getFirebaseInstance().setGameStarted(parent.getCurrGameID(), true);
                    parent.changeScreen(MyGdxGame.PLAY);
                }catch (IllegalArgumentException i) {
                    System.out.println(i);
                }
            }
        });
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(MyGdxGame.MENU);
            }
        });
        checkPlayers.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("--------");
                System.out.println("players in game are:");
                System.out.println(parent.getPlayers());
            }
        });
    }

    public void startGame() {

        parent.changeScreen(MyGdxGame.PLAY);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.clear();
    }

    @Override
    public void dispose() {

    }
}
