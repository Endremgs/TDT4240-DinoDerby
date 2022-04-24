package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.views.CreateGameScreen;
import com.mygdx.game.views.JoinGameScreen;
import com.mygdx.game.views.LobbyScreen;
import com.mygdx.game.views.GameOverScreen;
import com.mygdx.game.views.MenuScreen;
import com.mygdx.game.views.PlayScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyGdxGame extends Game {
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 960;

	public static final String TITLE = "Dino Derby";

	private MenuScreen menuScreen;
	private PlayScreen playScreen;
	private CreateGameScreen createGameScreen;
	private JoinGameScreen joinGameScreen;
	private LobbyScreen lobbyScreen;
	private GameOverScreen gameOverScreen;

	public boolean gameStarted = false;
	
	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int CREATEGAME = 2;
	public static final int JOINGAME = 3;
	public static final int LOBBY = 4;
	public static final int GAMEOVER = 5;
	private int currentScreen;
//	private Boolean gameStarted = false;


	FireBaseInterface FBIC;


	protected OrthographicCamera camera;
	Viewport viewport;

	private String playerID;
	private String currGameID;
	private Map<String, Map<String, Integer>> players;

	public void startGame(Boolean gameStarted) {
		System.out.println("checkGameStarted() i mygdxGame");
//		System.out.println(this.gameStarted);
		System.out.println(gameStarted);
		if (gameStarted) {
//			this.setScreen(this.playScreen);
//			this.changeScreen(PLAY);
			this.lobbyScreen.startGame();
		}
	}

	public String getPlayerID() {
		return this.playerID;
	}

	public String getCurrGameID() {
		return this.currGameID;
	}

	public void setCurrGameID(String gameID) {
		this.currGameID = gameID;
	}

	public void setPlayers(Map<String, Map<String, Integer>> players) {
		this.players = new HashMap<>(players);
		System.out.println("setting players");
		System.out.println(this.players);

		if (this.currentScreen == LOBBY) {
			this.lobbyScreen.show();
		}
	}

	public Map<String, Map<String, Integer>> getPlayers() {
		return new HashMap<>(players);
	}

	public FireBaseInterface getFirebaseInstance() {
		return this.FBIC;
	}
	public void changeScreen(int screen) {
		System.out.println("navigerer til" + screen);
		this.currentScreen = screen;
		switch (screen) {
			case MENU:
				menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PLAY:
				playScreen = new PlayScreen(this);
				this.setScreen(playScreen);
				break;
			case CREATEGAME:
				createGameScreen = new CreateGameScreen(this);
				this.setScreen(createGameScreen);
				break;
			case JOINGAME:
				joinGameScreen = new JoinGameScreen(this);
				this.setScreen(joinGameScreen);
				break;
			case LOBBY:
				lobbyScreen = new LobbyScreen(this);
				this.setScreen(lobbyScreen);
				break;
			case GAMEOVER:
				gameOverScreen= new GameOverScreen(this);
				this.setScreen(gameOverScreen);
				break;
		}

	}

	public MyGdxGame(FireBaseInterface FBIC) {
		this.FBIC = FBIC;
		this.FBIC.setParent(this);
		this.playerID = UUID.randomUUID().toString();
//		this.playScreen = new PlayScreen(this);
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
