package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class GameOverScreen implements Screen {

    private final MyGdxGame game;
    private Stage stage;
    private Texture texture;
    private final static int GAMEO_WIDTH= 650;
    private final static int GAMEO_HEIGHT= 200;
    private final SpriteBatch batch;



    public GameOverScreen(MyGdxGame game){

        this.game= game;
        this.texture= new Texture("Game Over.png");
        stage= new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

    }


    @Override
    public void show() {
        Table table= new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = new Skin(Gdx.files.internal("skin/buttonskin.json"));
        TextButton retry= new TextButton("Retry", skin);
        table.add(retry).fillX().uniformX();

        TextButton backToMenu= new TextButton("Back To Menu", skin);
        table.add(backToMenu).fillX().uniformX();

        retry.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(MyGdxGame.PLAY);
            }
        });

        backToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(MyGdxGame.MENU);
            }
        });





    }

    @Override
    public void render(float delta) {
        batch.begin();
        stage.draw();
        batch.draw(texture,Gdx.graphics.getWidth()/2-GAMEO_WIDTH/2, Gdx.graphics.getHeight()-GAMEO_HEIGHT-50, GAMEO_WIDTH,GAMEO_HEIGHT );
        batch.end();

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
