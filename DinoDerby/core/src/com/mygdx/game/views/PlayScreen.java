package com.mygdx.game.views;

import com.badlogic.ashley.core.Engine;
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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entity.components.BodyComponent;
import com.mygdx.game.entity.components.CollisionComponent;
import com.mygdx.game.entity.components.PlayerComponent;
import com.mygdx.game.entity.components.TextureComponent;
import com.mygdx.game.entity.components.TransformComponent;
import com.mygdx.game.entity.systems.CollisionSystem;
import com.mygdx.game.entity.systems.PhysicsSystem;
import com.mygdx.game.entity.systems.PlayerControlSystem;
import com.mygdx.game.entity.systems.RenderingSystem;

public class PlayScreen implements Screen {

    private final MyGdxGame parent;
    private final World world;
    private final BodyFactory bodyFactory;
    private final SpriteBatch sb;
    private final OrthographicCamera cam;
    private final Engine engine;

    public PlayScreen(MyGdxGame myGdxGame) {
        parent = myGdxGame;
        world = new World(new Vector2(0, -10f), true);
        bodyFactory = BodyFactory.getInstance(world);


        sb = new SpriteBatch();
        RenderingSystem renderingSystem = new RenderingSystem(sb);
        cam = renderingSystem.getCamera();
        sb.setProjectionMatrix(cam.combined);

        engine = new PooledEngine();

        engine.addSystem(renderingSystem);
        engine.addSystem(new PlayerControlSystem());
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new CollisionSystem());

        createPlayer();
        createRoad();
        createObstacle(3, 50);
    }

    private void createObstacle(int posX, int posY) {
        Entity entity = engine.createEntity();

        BodyComponent body = engine.createComponent(BodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        //Configures the obstacle to have a static position
        position.setIsStatic(true);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        CollisionComponent collision = engine.createComponent(CollisionComponent.class);

        //TODO - create obstacle textures
        texture.region = new TextureRegion(new Texture("player2.png"));
        body.body = bodyFactory.makeBody(posX, posY,
                getTextureSize(texture.region).x, getTextureSize(texture.region).y,
                BodyDef.BodyType.DynamicBody, true);
        position.position.set(posX, posY, 0);
        body.body.setUserData(entity);

        entity.add(body);
        entity.add(position);
        entity.add(texture);
        entity.add(collision);

        engine.addEntity(entity);
    }

    private void createPlayer() {
        Entity entity = engine.createEntity();

        BodyComponent body = engine.createComponent(BodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);


        texture.region = new TextureRegion(new Texture("player2.png"));
        body.body = bodyFactory.makeBody(5, 10,
                getTextureSize(texture.region).x, getTextureSize(texture.region).y,
                BodyDef.BodyType.DynamicBody, true);
        position.position.set(5, 10, 0);
        body.body.setUserData(entity);

        entity.add(body);
        entity.add(position);
        entity.add(texture);
        entity.add(player);

        engine.addEntity(entity);
    }

    private Vector2 getTextureSize(TextureRegion region) {
        return new Vector2(RenderingSystem.PixelToMeters(region.getRegionWidth()) / 2,
                RenderingSystem.PixelToMeters(region.getRegionHeight()) / 2);
    }
    private void createRoad() {
        Entity entity = engine.createEntity();
        BodyComponent body = engine.createComponent(BodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);


        position.position.set(0,0, -1);
        texture.region = new TextureRegion(new Texture("road.png"));

        body.body = bodyFactory.makeGround(0, 0,
                getTextureSize(texture.region).x, getTextureSize(texture.region).y);
        body.body.setUserData(entity);

        entity.add(body);
        entity.add(position);
        entity.add(texture);

        engine.addEntity(entity);
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
