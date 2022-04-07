package com.mygdx.game.views;

import com.badlogic.gdx.Screen;
import com.mygdx.game.MyGdxGame;

public class MenuScreen implements Screen {

    private final MyGdxGame parent;
    public MenuScreen(MyGdxGame dinoDerby) {
        parent = dinoDerby;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        parent.changeScreen(MyGdxGame.PLAY);

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
