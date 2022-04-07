package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class MenuState extends State{
    private Texture background;
    private Texture playButton;
    private Texture settingsButton;
    private Texture lbButton;


    //common button values
    private static final int buttonWidth = 278;
    private static final int buttonHeight = 60;
    private static final int buttonPosX = (int) Math.round(MyGdxGame.WIDTH*0.350);

    //unique button values
    private static final int playButtonPosY = (int) Math.round(MyGdxGame.HEIGHT*0.7);
    private static final int settingsButtonPosY = (int) Math.round(MyGdxGame.HEIGHT*0.5);
    private static final int lbButtonPosY = (int) Math.round(MyGdxGame.HEIGHT*0.3);


    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("dinoDerby.png");
        playButton = new Texture("playButton.png");
        settingsButton = new Texture("settingsButton.png");
        lbButton = new Texture("leaderboardButton.png");

    }

    @Override
    public void handleInput() {
        if(Gdx.input.getX() < (buttonPosX + buttonWidth) &&
                (Gdx.input.getX() > buttonPosX) &&
                MyGdxGame.HEIGHT - Gdx.input.getY() > (playButtonPosY - buttonHeight) &&
                MyGdxGame.HEIGHT - Gdx.input.getY() < playButtonPosY ) {
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
        sb.draw(playButton, buttonPosX, playButtonPosY, buttonWidth, buttonHeight);
        sb.draw(settingsButton, buttonPosX, settingsButtonPosY, buttonWidth, buttonHeight);
        sb.draw(lbButton, buttonPosX, lbButtonPosY, buttonWidth, buttonHeight);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        settingsButton.dispose();
        lbButton.dispose();
    }
}
