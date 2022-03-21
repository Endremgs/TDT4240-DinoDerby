package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;


public class PlayState extends State{
    private boolean gameover;
    private Texture gameoverImg;
    private Texture retryButton;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, MyGdxGame.WIDTH/2, MyGdxGame.HEIGHT /2);
        gameoverImg= new Texture("Game Over.png");
        retryButton= new Texture("RetryButton.png");
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            if(gameover){
            }
        }

    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.end();
    }

    @Override
    public void dispose() {
    }

}
