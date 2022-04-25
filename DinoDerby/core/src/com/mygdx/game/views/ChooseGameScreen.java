package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

import java.util.HashMap;
import java.util.Map;

public class ChooseGameScreen implements Screen {
    /**
     * Called when this screen becomes the current screen for a {link_Game}.
     */
    private final MyGdxGame parent;
    private Stage stage;


    public ChooseGameScreen(MyGdxGame dinoDerby){
        parent = dinoDerby;
        stage = new Stage(new ScreenViewport());
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = new Skin(Gdx.files.internal("skin/buttonskin.json"));

        TextButton joinGame = new TextButton("Join game", skin);
        TextButton createGame = new TextButton("Create game", skin);
        TextButton backBtn = new TextButton("Back", skin);

        table.add(joinGame).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.row();
        table.add(createGame).fillX().uniformX();
        table.row();
        table.add(backBtn).fillX().uniformX();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        joinGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    parent.changeScreen(MyGdxGame.JOINGAME);

                }catch (IllegalArgumentException i) {
                    System.err.println(i);
                }
            }
        });
        createGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    parent.changeScreen(MyGdxGame.CREATEGAME);

                }catch (IllegalArgumentException i) {
                    System.err.println(i);
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

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * param width
     * param height
     * see_ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * see_ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * see_ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {link_Game}.
     */
    @Override
    public void hide() {
        stage.clear();
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
}
