package com.mygdx.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.SimpleDirectionGestureDetector;
import com.mygdx.game.entity.components.BodyComponent;
import com.mygdx.game.entity.components.PlayerComponent;

public class PlayerControlSystem extends IteratingSystem {

    ComponentMapper<PlayerComponent> cmPlayer;
    ComponentMapper<BodyComponent> cmBody;

    public PlayerControlSystem() {
        super(Family.all(PlayerComponent.class).get());

        Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
            @Override
            public void onUp() {
                System.out.println("up input");
            }

            @Override
            public void onDown() {
                System.out.println("down input");
            }
        }));

        cmPlayer = ComponentMapper.getFor(PlayerComponent.class);
        cmBody = ComponentMapper.getFor(BodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float dt) {
        BodyComponent body = cmBody.get(entity);
        //body.body.setLinearVelocity(body.body.getLinearVelocity().x + 0.5f, body.body.getLinearVelocity().y);

    }
}
