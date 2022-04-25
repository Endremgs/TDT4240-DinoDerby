package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Table rightTable;
    private Skin skin = new Skin(Gdx.files.internal("skin/buttonskin.json"));

    public LobbyScreen(MyGdxGame dinoDerby){
        parent = dinoDerby;
        stage = new Stage(new ScreenViewport());
    }
    @Override
    public void show() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
        drawStage();

    }
    private void drawStage() {
        Table table = new Table();
        Table leftTable = new Table();
        rightTable = new Table();


        table.setFillParent(true);
        stage.addActor(table);

        table.add(leftTable);
        table.add(rightTable);


        TextButton startGame = new TextButton("Start Game", skin);
        TextButton leaveBtn = new TextButton("Leave Game", skin);

        leftTable.add(startGame).fillX().uniformX();
        leftTable.row().pad(10, 0, 10, 0);
        leftTable.row();
        leftTable.add(leaveBtn).fillX().uniformX();


        Label lobbyText = new Label("Players", skin);
        rightTable.add(lobbyText);
        rightTable.row().pad(10, 0, 10, 0);
        System.out.println("-----------");
        System.out.println(parent.getPlayers());
        for (String player: parent.getPlayers().keySet()) {
            System.out.println("-----------");
            System.out.println(player);
            Label playerText = new Label(player, skin);
            rightTable.add(playerText);
            rightTable.row().pad(10, 0, 10, 0);
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        if (stage.getBatch().isDrawing()) {
            stage.getBatch().end();
        }
        else {
            stage.getBatch().begin();
        }
        stage.draw();

        startGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    parent.getFirebaseInstance().setGameStarted(parent.getCurrGameID(), parent.getPlayerID());
                    parent.changeScreen(MyGdxGame.PLAY);
                }catch (IllegalArgumentException i) {
                    System.out.println(i);
                }
            }
        });
        leaveBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    parent.getFirebaseInstance().leaveGame(parent.getCurrGameID(), parent.getPlayerID());
                    parent.changeScreen(MyGdxGame.MENU);
                } catch (IllegalArgumentException i) {
                    System.err.println(i);
                }
            }
        });
    }
    public void reDrawPlayerTable() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.clear();
        drawStage();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        if (stage.getBatch().isDrawing()) {
            stage.getBatch().end();
        }
        else {
            stage.getBatch().begin();
        }
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
