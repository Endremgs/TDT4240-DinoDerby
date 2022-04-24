package com.mygdx.game.views;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.LevelFactory;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entity.components.BodyComponent;
import com.mygdx.game.entity.components.TextureComponent;
import com.mygdx.game.entity.components.TransformComponent;
import com.mygdx.game.entity.systems.B2dContactListener;
import com.mygdx.game.entity.systems.CollisionSystem;
import com.mygdx.game.entity.systems.PhysicsSystem;
import com.mygdx.game.entity.systems.PlayerControlSystem;
import com.mygdx.game.entity.systems.RenderingSystem;

public class PlayScreen implements Screen {

    private final MyGdxGame parent;
    private final World world;
    private final BodyFactory bodyFactory;
    private final LevelFactory levelFactory;
    private final SpriteBatch sb;
    private final OrthographicCamera cam;
    private final PooledEngine engine;

    public PlayScreen(MyGdxGame myGdxGame) {
        parent = myGdxGame;

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new B2dContactListener());
        bodyFactory = BodyFactory.getInstance(world);

        engine = new PooledEngine();

        levelFactory = new LevelFactory(engine, world);
        levelFactory.createPlayer();
//        levelFactory.createPlayer();

        levelFactory.createMap();

        /* creates all obstacles for the level */
        for (int i = 0; i <= 150; i++) {
            if (i < 7) {
                continue;
            }
            int yLevel = i % 5;

            System.out.println(yLevel);
            if (yLevel >= 2) {
                levelFactory.createObstacle(70 * i, 200 - (25 * yLevel));
            }
            if (i % 3 != 0) {
                levelFactory.createObstacle(70*i, 10);
            }
        }

        levelFactory.createFinish();

        sb = new SpriteBatch();
        RenderingSystem renderingSystem = new RenderingSystem(sb, levelFactory.getMap());
        cam = renderingSystem.getCamera();
        sb.setProjectionMatrix(cam.combined);


        engine.addSystem(renderingSystem);
        engine.addSystem(new PlayerControlSystem(cam));
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new CollisionSystem(parent));

    }
    private Vector2 getTextureSize(TextureRegion region) {
        return new Vector2(RenderingSystem.PixelToMeters(region.getRegionWidth()) / 2,
                RenderingSystem.PixelToMeters(region.getRegionHeight()) / 2);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        engine.update(delta);

    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.setToOrtho(false, cam.viewportWidth/2,
                cam.viewportHeight/2);

        cam.update();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
