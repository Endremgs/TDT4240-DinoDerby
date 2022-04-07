package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;

public class MyGdxGame extends ApplicationAdapter {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	public static final String TITLE = "Dino Derby";

	FireBaseInterface FBIC;

	private GameStateManager gsm;
	SpriteBatch batch;
	protected OrthographicCamera camera;
	Viewport viewport;

	public MyGdxGame(FireBaseInterface FBIC) {
		this.FBIC = FBIC;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new MenuState(gsm));
		FBIC.SomeFunction();
		FBIC.firstFireBaseText();

		camera  = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

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
}
