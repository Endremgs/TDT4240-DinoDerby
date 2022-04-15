package com.mygdx.game.entity.components;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.utils.Powerup.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PowerupComponent implements Component {

    private List<Effect> powerups;
    public PowerupComponent() {
        this.powerups = new ArrayList<>();
    }

    //Checking not only if the specific powerup object is in list, but class in general
    public void addPowerup(Effect effect) {
        for (Effect powerup: powerups) {
            if (!powerup.getClass().equals(effect.getClass())) {
                powerups.add(effect);
            }
            //Renewing powerup
            else {
                powerups.remove(powerup);
                powerups.add(effect);
            }
        }
    }

    public void removePowerup(Effect effect) {
        for (Effect powerup: powerups) {
            if (powerup.getClass().equals(effect.getClass())) {
                powerups.remove(powerup);
            }
        }
        this.powerups.remove(effect);
    }

    public boolean hasPowerup(final Effect effect) {
        for (Effect powerup: powerups) {
            return (powerup.getClass().equals(effect.getClass()));
        }
        return false;
    }


}
