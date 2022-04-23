package com.mygdx.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.SimpleDirectionGestureDetector;
import com.mygdx.game.entity.components.BodyComponent;
import com.mygdx.game.entity.components.PlayerComponent;

public class PlayerControlSystem extends IteratingSystem {

    ComponentMapper<PlayerComponent> cmPlayer;
    ComponentMapper<BodyComponent> cmBody;
    OrthographicCamera camera;
    float velocity = 5;
    int jump = 0;
    int position = 200;

    public PlayerControlSystem(OrthographicCamera camera) {
        super(Family.all(PlayerComponent.class).get());

        cmPlayer = ComponentMapper.getFor(PlayerComponent.class);
        cmBody = ComponentMapper.getFor(BodyComponent.class);
        this.camera = camera;


        Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
            @Override
            public void onUp() {
                System.out.println("up input");
                jump = 50;
            }

            @Override
            public void onDown() {
                System.out.println("down input");
            }
        }));

    }

    @Override
    protected void processEntity(Entity entity, float dt) {
        BodyComponent body = cmBody.get(entity);

        // moves the player towards the right
        body.body.applyForceToCenter(100000, 0, true);
        if(jump > 0) {
            body.body.applyForceToCenter(10000, 100000,false);
            jump--;
        } else if (body.body.getPosition().y >= 10) {
            body.body.applyForceToCenter(10000, -100000, false);
        }
        // camera follows player
        if (body.body.getPosition().x >= camera.viewportWidth/2 - position)
        camera.position.x = body.body.getPosition().x + position;

        //System.out.printf("test: %s %s\n", body.body.getLinearVelocity(), body.body.getPosition());

    }
}
