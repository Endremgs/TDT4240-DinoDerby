package com.mygdx.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.entity.components.CollisionComponent;
import com.mygdx.game.entity.components.PlayerComponent;

public class CollisionSystem extends IteratingSystem {

    ComponentMapper<CollisionComponent> cmCollision;
    ComponentMapper<PlayerComponent> cmPlayer;

    public CollisionSystem(Family family) {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
