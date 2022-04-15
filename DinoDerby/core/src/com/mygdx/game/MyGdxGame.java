package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;

import com.mygdx.game.views.MenuScreen;
import com.mygdx.game.views.PlayScreen;

public class MyGdxGame extends Game {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Dino Derby";

	private MenuScreen menuScreen;
	private PlayScreen playScreen;

	public static final int MENU = 0;
	public static final int PLAY = 1;

	FireBaseInterface FBIC;


	public void changeScreen(int screen) {
		switch (screen) {
			case MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PLAY:
				if (playScreen == null) playScreen = new PlayScreen(this);
				this.setScreen(playScreen);
				break;
		}

	}

	public MyGdxGame(FireBaseInterface FBIC) {
		this.FBIC = FBIC;
	}


	public FireBaseInterface getFireBaseInstance() {
		return this.FBIC;
	}

	@Override
	public void create () {
		/*batch = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new MenuState(gsm));
		 */

		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	/*
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);

	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	 */
}
