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

    //loading the map
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private Entity ground;


    public int currentLevel = 0;
    private TextureRegion groundTexture;

    public LevelFactory(PooledEngine eng) {
        world = new World(new Vector2(0, 10f), true);
        map = new TiledMap();
        engine = eng;

        bodyFactory = BodyFactory.getInstance(world);
    }

    private Vector2 getTextureSize(TextureRegion region) {
        return new Vector2(RenderingSystem.PixelToMeters(region.getRegionWidth()) / 2,
                RenderingSystem.PixelToMeters(region.getRegionHeight()) / 2);
    }


    public void createMap(){
        //load the map and renderer
        mapLoader = new TmxMapLoader();
        this.map = mapLoader.load("maps/DinoDerbyMap2.tmx");
    }

    public TiledMap getMap() {
        return this.map;
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



}
