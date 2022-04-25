package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

public class SettingsScreen implements Screen {

    private MyGdxGame parent;
    private Stage stage;

    private Label title;
    private Label musicLabel;
    private Label musicEnabled;
    private Label soundLabel;
    private Label soundEnabled;

    private Color bgColor = Color.CYAN;

    private static final String MUSIC_VOLUME = "volume";
    private static final String MUSIC_ENABLED = "music.enabled";
    public static final String PREFERENCES_NAME = "gameSett";

    private Music music;

    public Preferences getPrefs(){
        return Gdx.app.getPreferences(PREFERENCES_NAME);
    }

    public SettingsScreen(MyGdxGame gdxGame){
        parent = gdxGame;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/buttonskin.json"));

        final TextButton back = new TextButton("Back", skin);
        final CheckBox musicCheckbox = new CheckBox("null", skin);
        musicCheckbox.setChecked(parent.musicEnabled);
        music = Gdx.audio.newMusic(Gdx.files.internal("kahoot.wav"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();

        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if(musicCheckbox.isChecked()){
                    music.play();
                } else{
                    music.pause();
                }
                return false;
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(MyGdxGame.MENU);
            }
        });

        //labels
        title = new Label("Settings", skin);
        musicLabel = new Label(null, skin);
        musicEnabled = new Label("Enable Music", skin);
        soundLabel = new Label(null, skin);
        soundEnabled = new Label(null, skin);

        //adding our labels to the table
        table.add(title).fillX().uniformX();
        table.row();
        table.add(musicEnabled);
        table.add(musicCheckbox);

        table.row();
        table.add(back).fillX().uniformX();
    }

    public boolean setMusicEnabled(boolean enabled) {
        return getPrefs().getBoolean(MUSIC_ENABLED, true);
        //return false;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        music.dispose();

    }

}
