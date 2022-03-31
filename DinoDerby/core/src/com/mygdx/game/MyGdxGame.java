package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;

public class MyGdxGame extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;


	private GameSettings settings;

	public static final int MENU = 0;
	public static final int SETTINGS = 1;

	public static final String TITLE = "Dino Derby";

	FireBaseInterface FBIC;

	private GameStateManager gsm;
	SpriteBatch batch;

	public MyGdxGame(FireBaseInterface FBIC) {
		this.FBIC = FBIC;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		settings = new GameSettings();
		gsm.push(new MenuState(gsm));
		FBIC.SomeFunction();
		FBIC.firstFireBaseText();
	}

	public GameSettings getSettings(){
		return this.settings;
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);

	}

	public void changeScreen(int screen){
		switch (screen){
			case SETTINGS:
				if (settings == null) settings = new GameSettings();
				this.setScreen(settings);
				break;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
