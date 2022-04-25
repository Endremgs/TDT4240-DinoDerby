package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.views.GameWinScreen;
import com.mygdx.game.views.CreateGameScreen;
import com.mygdx.game.views.JoinGameScreen;
import com.mygdx.game.views.LobbyScreen;
import com.mygdx.game.views.GameOverScreen;
import com.mygdx.game.views.MenuScreen;
import com.mygdx.game.views.PlayScreen;
import com.mygdx.game.views.SettingsScreen;
import com.mygdx.game.views.TutorialScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class MyGdxGame extends Game {

	public static final String TITLE = "Dino Derby";
	private static final String MUSIC_ENABLED = "music.enabled";
	private MenuScreen menuScreen;
	private PlayScreen playScreen;
	private CreateGameScreen createGameScreen;
	private JoinGameScreen joinGameScreen;
	private LobbyScreen lobbyScreen;
	private GameOverScreen gameOverScreen;

	private SettingsScreen settings;
	public boolean musicEnabled = true;
  
	private TutorialScreen tutorialScreen;
	private GameWinScreen gameWinScreen;

	public boolean gameStarted = false;
	
	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int TUTORIAL = 2;
  	public static final int SETTINGS = 3;
	public static final int CREATEGAME = 4;
	public static final int JOINGAME = 5;
	public static final int LOBBY = 6;
	public static final int GAMEOVER = 7;
	public static final int GAMEWIN = 8;

	private int currentScreen;
//	private Boolean gameStarted = false;



	FireBaseInterface FBIC;

	public Music music;

	protected OrthographicCamera camera;
	Viewport viewport;

	private String playerID;
	private String currGameID;
	private Map<String, Map<String, Float>> players;

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

	public void finishGame(String winner) {
		System.out.println("playerID: " + playerID);
		if (winner.equals(playerID)) {
			System.out.println("You won");
		}
		else {
			System.out.println("you lost");
			this.changeScreen(GAMEOVER);
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

	public void setPlayers(Map<String, Map<String, Float>> players) {
		this.players = new HashMap(players);
		System.out.println("setting players");
		System.out.println(this.players);

//		if (this.currentScreen == LOBBY) {
//			this.lobbyScreen.show();
//		}
	}

	public Map<String, Map<String, Float>> getPlayers() {
		return new HashMap(players);
	}

	public FireBaseInterface getFirebaseInstance() {
		return this.FBIC;
	}
	public void changeScreen(int screen) {
		System.out.println("navigerer til" + screen);
		this.currentScreen = screen;
		switch (screen) {
			case MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PLAY:
				if (playScreen == null)playScreen = new PlayScreen(this);
				this.setScreen(playScreen);
				break;
			case SETTINGS:
				if (settings == null) settings = new SettingsScreen(this);
				this.setScreen(settings);
				break;
			case CREATEGAME:
				if (createGameScreen == null) createGameScreen = new CreateGameScreen(this);
				this.setScreen(createGameScreen);
				break;
			case JOINGAME:
				if (joinGameScreen == null) joinGameScreen = new JoinGameScreen(this);
				this.setScreen(joinGameScreen);
				break;
			case LOBBY:
				if (lobbyScreen == null) lobbyScreen = new LobbyScreen(this);
				this.setScreen(lobbyScreen);
				break;
			case GAMEOVER:
				if (gameOverScreen == null) gameOverScreen= new GameOverScreen(this);
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
		this.FBIC.setParent(this);
		this.playerID = UUID.randomUUID().toString();
//		this.playScreen = new PlayScreen(this);
	}

	public Preferences getPrefs(){
		return Gdx.app.getPreferences(SettingsScreen.PREFERENCES_NAME);
	}

	public void setMusicEnabled(boolean musicEnabled) {
		this.musicEnabled = musicEnabled;
	}

	@Override
	public void create () {

		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);

		music = Gdx.audio.newMusic(Gdx.files.internal("kahoot.wav"));
		music.setLooping(true);
		music.setVolume(0.05f);
		music.play();

		camera  = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}


	public SettingsScreen getSettings(){
		return this.settings;
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
