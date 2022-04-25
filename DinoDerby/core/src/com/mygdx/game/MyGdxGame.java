package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.views.GameWinScreen;
import com.mygdx.game.views.MenuScreen;
import com.mygdx.game.views.PlayScreen;
import com.mygdx.game.views.GameOverScreen;
import com.mygdx.game.views.TutorialScreen;

public class MyGdxGame extends Game {

	public static final String TITLE = "Dino Derby";

	private MenuScreen menuScreen;
	private PlayScreen playScreen;
	private GameOverScreen gameOverScreen;
	private TutorialScreen tutorialScreen;
	private GameWinScreen gameWinScreen;

	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int GAMEOVER = 2;
	public static final int TUTORIAL = 3;
	public static final int GAMEWIN = 4;

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
				if (gameOverScreen == null) gameOverScreen = new GameOverScreen(this);
				this.setScreen(gameOverScreen);
				break;
			case TUTORIAL:
				if (tutorialScreen == null) tutorialScreen = new TutorialScreen(this);
				this.setScreen(tutorialScreen);
				break;
			case GAMEWIN:
				if (gameWinScreen == null) gameWinScreen = new GameWinScreen(this);
				this.setScreen(gameWinScreen);
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
