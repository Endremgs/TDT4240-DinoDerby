package com.mygdx.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.entity.components.PlayerComponent;
import com.mygdx.game.entity.components.PowerupComponent;
import com.mygdx.game.utils.Powerup.Effect;

public class PowerupSystem extends IteratingSystem {
    ComponentMapper<PowerupComponent> cmPowerup;

    public PowerupSystem() {
        super(Family.all(PowerupComponent.class).get());

        this.cmPowerup = ComponentMapper.getFor(PowerupComponent.class);
    }


    public void givePowerUp(Entity player, Effect effect) {

        if (cmPowerup.has(player)) {
            PowerupComponent powerupComponent = player.getComponent(PowerupComponent.class);
            powerupComponent.addPowerup(effect);
        }
    }

    public void removePowerUp(Entity player, Effect effect) {
        if (cmPowerup.has(player)) {
            PowerupComponent powerupComponent = player.getComponent(PowerupComponent.class);
            powerupComponent.removePowerup(effect);
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
