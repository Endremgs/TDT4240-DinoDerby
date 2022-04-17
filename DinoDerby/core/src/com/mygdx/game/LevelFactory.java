package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entity.components.BodyComponent;
import com.mygdx.game.entity.components.PlayerComponent;
import com.mygdx.game.entity.components.TextureComponent;
import com.mygdx.game.entity.components.TransformComponent;
import com.mygdx.game.entity.systems.RenderingSystem;

public class LevelFactory {

    private BodyFactory bodyFactory;
    public final World world;
    private PooledEngine engine;
    private MyGdxGame parent;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private OrthographicCamera camera;

    public int currentLevel = 0;
    private TextureRegion groundTexture;

    public LevelFactory(PooledEngine eng, MyGdxGame parent) {
        world = new World(new Vector2(0, 10f), true);

        engine = eng;
        this.parent = parent;
        //world.setContactListener();

        bodyFactory = BodyFactory.getInstance(world);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("DinoDerbyMap2.tmx");
    }



    private Vector2 getTextureSize(TextureRegion region) {
        return new Vector2(RenderingSystem.PixelToMeters(region.getRegionWidth()) / 2,
                RenderingSystem.PixelToMeters(region.getRegionHeight()) / 2);
    }


    public void createMap(TiledMap map){
        Entity entity = engine.createEntity();
        BodyComponent box2D = engine.createComponent(BodyComponent.class);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);


    }

    public void createPlayer(){
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

    /*public void createRoad() {
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
    }*/

    public void update(float dt){

    }

}
