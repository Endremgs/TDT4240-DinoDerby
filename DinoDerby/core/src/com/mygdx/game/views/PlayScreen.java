package com.mygdx.game.views;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.LevelFactory;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SimpleDirectionGestureDetector;
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
    private final InputProcessor inputProcessor;

    public int jump = 0;

    public PlayScreen(MyGdxGame myGdxGame) {
        parent = myGdxGame;

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new B2dContactListener());
        bodyFactory = BodyFactory.getInstance(world);

        engine = new PooledEngine();

        levelFactory = new LevelFactory(engine, world);
        levelFactory.createMap();

        for (String ghostPlayerID: parent.getPlayers().keySet()) {
            if (!ghostPlayerID.equals(parent.getPlayerID())) {
                levelFactory.createGhost(ghostPlayerID);
            }
        }

                inputProcessor = (new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
                    @Override
                    public void onUp() {
                        System.out.println("up input");
                        jump = 25;
                    }

                    @Override
                    public void onDown() {

                    }
                }));
        sb = new SpriteBatch();
        RenderingSystem renderingSystem = new RenderingSystem(sb, levelFactory.getMap(), parent);
        cam = renderingSystem.getCamera();
        sb.setProjectionMatrix(cam.combined);


        engine.addSystem(renderingSystem);
        engine.addSystem(new PlayerControlSystem(cam, parent, this));
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new CollisionSystem(parent));

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
        levelFactory.createPlayer();

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

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!parent.gameStarted) {
            return;
        }

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
        engine.removeAllEntities();
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(int i = 0; i < bodies.size; i++) {
            world.destroyBody(bodies.get(i));
        }
    }

    @Override
    public void dispose() {
    }
}
