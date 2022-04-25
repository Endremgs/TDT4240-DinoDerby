package com.mygdx.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entity.components.GhostComponent;
import com.mygdx.game.entity.components.TransformComponent;

import java.util.Map;

public class GhostSystem extends IteratingSystem {

    private MyGdxGame game;

    private ComponentMapper<GhostComponent> cmGhost;
    private ComponentMapper<TransformComponent> cmTransform;

    public GhostSystem(MyGdxGame game) {
        super(Family.all(GhostComponent.class).get());

        cmGhost = ComponentMapper.getFor(GhostComponent.class);
        cmTransform = ComponentMapper.getFor(TransformComponent.class);
        this.game = game;
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        GhostComponent ghost = cmGhost.get(entity);
        TransformComponent transform = cmTransform.get(entity);
        if (game.getPlayers().containsKey(ghost.playerID)) {
            Map<String, Float> playerMap = game.getPlayers().get(ghost.playerID);
            transform.position.x = playerMap.get("xPos");
            transform.position.y = playerMap.get("yPos");
        }

    }
}
