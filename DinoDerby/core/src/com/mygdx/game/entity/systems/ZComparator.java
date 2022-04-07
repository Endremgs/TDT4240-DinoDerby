package com.mygdx.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.mygdx.game.entity.components.TransformComponent;

import java.util.Comparator;


public class ZComparator implements Comparator<Entity> {

    private final ComponentMapper<TransformComponent> cmTransform;

    public ZComparator() {
        cmTransform = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public int compare(Entity entity1, Entity entity2) {
        float entity1Z = cmTransform.get(entity1).position.z;
        float entity2Z = cmTransform.get(entity2).position.z;
        int result = 0;
        if(entity1Z > entity2Z) {
            result = 1;
        } else if(entity1Z < entity2Z) {
            result = -1;
        }
        return result;
    }
}
