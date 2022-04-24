package com.mygdx.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.entity.components.CollisionComponent;
import com.mygdx.game.entity.components.PlayerComponent;
import com.mygdx.game.entity.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {

    ComponentMapper<CollisionComponent> cmCollision;
    ComponentMapper<PlayerComponent> cmPlayer;

    public CollisionSystem() {
        super(Family.all(CollisionComponent.class,PlayerComponent.class).get());

        cmCollision = ComponentMapper.getFor(CollisionComponent.class);
        cmPlayer = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get player collision component
        CollisionComponent cc = cmCollision.get(entity);

        Entity collidedEntity = cc.collisionEntity;
        if(collidedEntity != null){
            TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
            if(type != null){
                switch(type.type){
                    case TypeComponent.PLAYER:
                        System.out.println("player hit player");
                        break;
                    case TypeComponent.OBSTACLE:
                        System.out.println("player hit obstacle");
                        break;
                    case TypeComponent.POWERUP:
                        System.out.println("player hit powerup");
                        break;
                    case TypeComponent.OTHER:
                        System.out.println("player hit other");
                        break;
                }
                cc.collisionEntity = null; // collision handled reset component
            }
        }
    }
}
