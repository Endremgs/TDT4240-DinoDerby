package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

//TODO - Old code, rm if unneccessary
public class MenuState extends State{


    private Texture background;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("dinoDerby.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set((new PlayState(gsm)));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
