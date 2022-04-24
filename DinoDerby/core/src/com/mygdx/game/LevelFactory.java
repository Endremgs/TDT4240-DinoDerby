package com.mygdx.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entity.components.BodyComponent;
import com.mygdx.game.entity.components.CollisionComponent;
import com.mygdx.game.entity.components.GhostComponent;
import com.mygdx.game.entity.components.PlayerComponent;
import com.mygdx.game.entity.components.TextureComponent;
import com.mygdx.game.entity.components.TransformComponent;
import com.mygdx.game.entity.components.TypeComponent;
import com.mygdx.game.entity.systems.RenderingSystem;

public class LevelFactory{

    private BodyFactory bodyFactory;
    private PooledEngine engine;

    //loading the map
    private TmxMapLoader mapLoader;
    private TiledMap map;


    public LevelFactory(PooledEngine eng, World world) {

        map = new TiledMap();
        engine = eng;
        bodyFactory = BodyFactory.getInstance(world);
    }

    private Vector2 getTextureSize(TextureRegion region) {
        return new Vector2(RenderingSystem.PixelToMeters(region.getRegionWidth()) / 2,
                RenderingSystem.PixelToMeters(region.getRegionHeight()) / 2);
    }

    public void createMap(){
        //load the map
        mapLoader = new TmxMapLoader();
        this.map = mapLoader.load("maps/DinoDerbySkyMap.tmx");
        //CollisionComponent collision = engine.createComponent(CollisionComponent.class);

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
        CollisionComponent collision = engine.createComponent(CollisionComponent.class);

        texture.region = new TextureRegion(new Texture("player2.png"));
        body.body = bodyFactory.makeBody(5, 0,
                getTextureSize(texture.region).x, getTextureSize(texture.region).y,
                BodyDef.BodyType.DynamicBody, true);


        position.position.set(5, 0, 0);
        body.body.setUserData(entity);

        entity.add(body);
        entity.add(position);
        entity.add(texture);
        entity.add(player);
        entity.add(collision);

        engine.addEntity(entity);

    }

    public void createGhost(String playerID) {
        Entity entity = engine.createEntity();

        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        GhostComponent ghost = engine.createComponent(GhostComponent.class);
        ghost.playerID = playerID;

        texture.region = new TextureRegion(new Texture("player2.png"));
        position.position.set(10, 5, 0);
        entity.add(position);
        entity.add(texture);
        entity.add(ghost);

        engine.addEntity(entity);
    }

    public void createObstacle(int posX, int posY) {
        Entity entity = engine.createEntity();

        BodyComponent body = engine.createComponent(BodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        //Configures the obstacle to have a static position
        //position.setIsStatic(true);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        CollisionComponent collision = engine.createComponent(CollisionComponent.class);

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

}
