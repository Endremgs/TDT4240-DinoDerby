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

public class LobbyScreen implements Screen {
    /**
     * Called when this screen becomes the current screen for a {link_Game}.
     */
    private final MyGdxGame parent;
    private Stage stage;

    //ui elements
    private Table table;
    private TextButton createGame;
    private TextButton joinGame;
    private TextField lobbyIdField;


    public LobbyScreen(MyGdxGame dinoDerby){
        parent = dinoDerby;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void show() {

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = new Skin(Gdx.files.internal("skin/buttonskin.json"));

        joinGame = new TextButton("Join game", skin);
        createGame = new TextButton("create game", skin);
        lobbyIdField = new TextField("game-code", skin);

        table.add(lobbyIdField).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(joinGame).fillX().uniformX();
        table.row();
        table.add(createGame).fillX().uniformX();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        joinGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //temporary play link
                parent.changeScreen(MyGdxGame.PLAY);
            }
        });
        createGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //temporary play link
                parent.changeScreen(MyGdxGame.PLAY);
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

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
}
