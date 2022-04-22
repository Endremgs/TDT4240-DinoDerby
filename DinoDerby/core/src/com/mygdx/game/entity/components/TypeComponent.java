package com.mygdx.game.entity.components;

import com.badlogic.ashley.core.Component;
public class TypeComponent implements Component {

    public static final int PLAYER = 0;
    public static final int OBSTACLE = 1;
    public static final int POWERUP = 2;
    public static final int GHOST = 3;
    public static final int OTHER = 4;

    public int type = OTHER;

}