package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.views.MenuScreen;
import com.mygdx.game.views.PlayScreen;

public class MyGdxGame extends Game {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Dino Derby";

	private MenuScreen menuScreen;
	private PlayScreen playScreen;
	private GameOverScreen gameOverScreen;

	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int GAMEOVER= 2;

	FireBaseInterface FBIC;


	protected OrthographicCamera camera;
	Viewport viewport;

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
			case GAMEOVER:
				if(gameOverScreen==null) gameOverScreen= new GameOverScreen(this);
				this.setScreen(gameOverScreen);
				break;
		}

	}

	public MyGdxGame(FireBaseInterface FBIC) {
		this.FBIC = FBIC;
	}

	@Override
	public void create () {

		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);

		camera  = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	/*
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		gsm.update(Gdx.graphics.getDeltaTime());
		camera.update();
		gsm.render(batch);

	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	 */
}
