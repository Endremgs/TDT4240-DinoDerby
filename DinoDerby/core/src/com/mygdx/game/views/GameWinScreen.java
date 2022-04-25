package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

public class GameWinScreen implements Screen {

    private final MyGdxGame game;
    private Stage stage;
    private Texture texture;
    private Texture background;
    private final static int GAMEO_WIDTH= 650;
    private final static int GAMEO_HEIGHT= 200;
    private final SpriteBatch batch;



    public GameWinScreen(MyGdxGame game){

        this.game= game;
        this.texture= new Texture("win.png");
        background = new Texture("gamefinishBG.png");
        stage= new Stage(new ScreenViewport());
        batch = new SpriteBatch();

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table= new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = new Skin(Gdx.files.internal("skin/buttonskin.json"));
        TextButton mainMenu= new TextButton("Main Menu", skin);
        table.add(mainMenu).fillX().uniformX();

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.leaveGame();
                game.changeScreen(MyGdxGame.MENU);
            }
        });



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(texture,Gdx.graphics.getWidth()/2-GAMEO_WIDTH/2, Gdx.graphics.getHeight()-GAMEO_HEIGHT-50, GAMEO_WIDTH,GAMEO_HEIGHT );
        batch.end();
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

    }

    @Override
    public void dispose() {

    }
}
