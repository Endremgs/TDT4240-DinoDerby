package com.mygdx.game.Screens;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.awt.Color;
import java.awt.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private MyGdxGame game;
    private SpriteBatch batch;


    public GameOverScreen(MyGdxGame game){
        this.game= game;
        viewport= new FitViewport(MyGdxGame.WIDTH, MyGdxGame.HEIGHT, new OrthographicCamera());
        batch= new SpriteBatch();
        stage= new Stage(viewport, batch);

        Label font= new Label.LabelStyle(new BitmapFont(), Color.white);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
