package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

public class JoinGameScreen implements Screen {

    private final MyGdxGame parent;
    private Stage stage;

    public JoinGameScreen(MyGdxGame dinoDerby){
        parent = dinoDerby;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = new Skin(Gdx.files.internal("skin/buttonskin.json"));

        final TextField lobbyIdField = new TextField("", skin);
        TextButton joinGame = new TextButton("Join game", skin);
        TextButton backBtn = new TextButton("Back", skin);
//        Label text = new Label("Enter game ID:", skin);

//        table.add(text).fillX().uniformX();
//        table.row();
        table.add(lobbyIdField).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(joinGame).fillX().uniformX();
        table.row();
        table.add(backBtn).fillX().uniformX();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        joinGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    System.out.println("GameID: " + lobbyIdField.getText());
                    parent.getFirebaseInstance().joinGame(lobbyIdField.getText(),parent.getPlayerID());
//                    parent.setPlayers(parent.getFirebaseInstance().getPlayersInGame(parent.getCurrGameID(), parent.getPlayerID()));
                    parent.changeScreen(MyGdxGame.LOBBY);
                }catch (IllegalArgumentException i) {
//                    System.out.println("----------");
//                    System.out.println("du fikk en exception");
//                    System.err.println(i);
                }
            }
        });

        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(MyGdxGame.MENU);
            }
        });
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