package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {

    private static BodyFactory thisInstance;
    private final World world;


    private BodyFactory(World world) {
        this.world = world;
    }

    public static BodyFactory getInstance(World world) {
        if(thisInstance == null) {
            thisInstance = new BodyFactory(world);
        }
        return thisInstance;
    }

    // TODO: add fixtures for different materials

    public Body makeBody(float posX, float posY, float width, float height, BodyDef.BodyType bodyType, boolean fixedRotation) {
        // create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.x = posX;
        bodyDef.position.y = posY;
        bodyDef.fixedRotation = fixedRotation;

        Body body = world.createBody(bodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width, height);
        body.createFixture(poly, 1f);

        // create body with definition
        return body;
    }

}
