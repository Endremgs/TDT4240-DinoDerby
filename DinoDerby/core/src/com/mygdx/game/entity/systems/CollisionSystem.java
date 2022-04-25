package com.mygdx.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entity.components.CollisionComponent;
import com.mygdx.game.entity.components.PlayerComponent;
import com.mygdx.game.entity.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {

    private ComponentMapper<CollisionComponent> cmCollision;
    private ComponentMapper<PlayerComponent> cmPlayer;

    private MyGdxGame parent;


    public CollisionSystem(MyGdxGame parent) {
        super(Family.all(CollisionComponent.class, PlayerComponent.class).get());

        cmCollision = ComponentMapper.getFor(CollisionComponent.class);
        cmPlayer = ComponentMapper.getFor(PlayerComponent.class);
        this.parent = parent;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent cc = cmCollision.get(entity);

        Entity collidedEntity = cc.collisionEntity;
        if(collidedEntity != null){
            TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
            System.out.println(type.type);
            if(type != null){
                switch(type.type){
                    case TypeComponent.OBSTACLE:
                        System.out.println("player hit obstacle");
                        parent.changeScreen(MyGdxGame.GAMEOVER);
                        break;
                    case TypeComponent.GHOST:
                        System.out.println("player hit ghost");
                        break;
                    case TypeComponent.POWERUP:
                        System.out.println("player hit powerup");
                        break;
                    case TypeComponent.OTHER:
                        System.out.println("player hit other");
                        parent.changeScreen(MyGdxGame.GAMEWIN);
                        break;
                }
                cc.collisionEntity = null; // collision handled reset component
            }
        }
    }
}
