package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSettings {


    private static final String MUSIC_VOLUME = "volume";
    private static final String MUSIC_ENABLED = "music.enabled";
    private static final String SOUND_VOLUME = "sound";
    private static final String SOUND_ENABLED = "sound.enabled";
    private static final String PREFERENCES_NAME = "gameSett";

    public Preferences getPrefs(){
        return Gdx.app.getPreferences(PREFERENCES_NAME);
    }
/*
    public void setMusicEnabled(boolean musicEnabled){
        getPrefs().putBoolean(MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }*/

    public boolean isMusicEnabled(){
        return getPrefs().getBoolean(MUSIC_ENABLED, true);
    }

    public float getMusicVolume(){
        return getPrefs().getFloat(MUSIC_VOLUME, 1f);
    }

    public void setMusicVolume(float musicVolume){
        getPrefs().putFloat(MUSIC_VOLUME,musicVolume);
        getPrefs().flush();
    }

    public void setSoundEnabled(boolean soundEnabled){
        getPrefs().putBoolean(SOUND_ENABLED, soundEnabled);
        getPrefs().flush();
    }

    public boolean isSoundEnabled(){
        return getPrefs().getBoolean(SOUND_ENABLED, true);
    }

    public void setSoundVolume(float soundVolume){
        getPrefs().putFloat(SOUND_VOLUME, soundVolume);
        getPrefs().flush();
    }

    public float getSoundVolume(){
        return getPrefs().getFloat(SOUND_VOLUME, 1f);
    }

}
