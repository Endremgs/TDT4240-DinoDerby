package com.mygdx.game.entity.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.SimpleDirectionGestureDetector;
import com.mygdx.game.entity.components.BodyComponent;
import com.mygdx.game.entity.components.PlayerComponent;
import com.mygdx.game.entity.components.TransformComponent;

public class PlayerControlSystem extends IteratingSystem {

    ComponentMapper<PlayerComponent> cmPlayer;
    ComponentMapper<BodyComponent> cmBody;
    ComponentMapper<TransformComponent> cmTransform;

    public PlayerControlSystem() {
        super(Family.all(PlayerComponent.class).get());

        cmPlayer = ComponentMapper.getFor(PlayerComponent.class);
        cmBody = ComponentMapper.getFor(BodyComponent.class);
        cmTransform = ComponentMapper.getFor(TransformComponent.class);

    }

    @Override
    protected void processEntity(Entity entity, float dt) {
        final TransformComponent transform = cmTransform.get(entity);
        final BodyComponent b2body = cmBody.get(entity);
        Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
            @Override
            public void onUp() {
                System.out.println("up input");
                System.out.println(transform.position);
                transform.position.add(0, 30, 0);
                b2body.body.setLinearVelocity(b2body.body.getLinearVelocity().
                        x + 0.5f, b2body.body.getLinearVelocity().y);
                System.out.println(transform.position);
            }

            @Override
            public void onDown() {
                System.out.println("down input");
            }
        }));
        //body.body.setLinearVelocity(body.body.getLinearVelocity().x + 0.5f, body.body.getLinearVelocity().y);

    }
}
