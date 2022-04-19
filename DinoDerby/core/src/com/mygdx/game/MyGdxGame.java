package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.views.LobbyScreen;
import com.mygdx.game.views.MenuScreen;
import com.mygdx.game.views.PlayScreen;

import java.util.UUID;

public class MyGdxGame extends Game {
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 960;

	public static final String TITLE = "Dino Derby";

	private MenuScreen menuScreen;
	private PlayScreen playScreen;
	private LobbyScreen lobbyScreen;

	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int LOBBY = 2;




	FireBaseInterface FBIC;

	SpriteBatch batch;
	protected OrthographicCamera camera;
	Viewport viewport;

	private String playerID;
	private String currGameID;

	public String getPlayerID() {
		return this.playerID;
	}

	public String getCurrGameID() {
		return this.currGameID;
	}

	public void setCurrGameID(String gameID) {
		this.currGameID = gameID;
	}

	public FireBaseInterface getFirebaseInstance() {
		return this.FBIC;
	}
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
			case LOBBY:
				if (lobbyScreen == null) lobbyScreen = new LobbyScreen(this);
				this.setScreen(lobbyScreen);
				break;
		}

	}

	public MyGdxGame(FireBaseInterface FBIC) {
		this.FBIC = FBIC;
		this.playerID = UUID.randomUUID().toString();
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
