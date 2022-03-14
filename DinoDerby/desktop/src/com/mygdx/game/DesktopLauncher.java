package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
		config.setWindowSizeLimits(MyGdxGame.WIDTH, MyGdxGame.HEIGHT, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
		config.setTitle(MyGdxGame.TITLE);
		new Lwjgl3Application(new MyGdxGame(new DesktopInterfaceClass()), config);
	}
}
