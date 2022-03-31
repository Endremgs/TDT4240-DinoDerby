package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.mygdx.game.GameSettings;

public class SettingsScreen implements Screen {

    private MyGdxGame parent;
    private Stage stage;

    private Label title;
    private Label musicLabel;
    private Label musicEnabled;
    private Label soundLabel;
    private Label soundEnabled;

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

        //TODO add a skin.
        Skin skin = new Skin(Gdx.files.internal(""));

        TextButton back = new TextButton("Back", skin);

        //music
        final Slider musicSlider = new Slider(0, 1f, 0.1f, false, skin);
        musicSlider.setValue(parent.getPrefrences().getMusicVolume());
        musicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPrefrences().setMusicVolume(musicSlider.getMusicVolume());
                return false;
            }
        });

        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPrefrences().setMusicEnabled(enabled);
                return false;
            }
        });

        //sound
        final Slider soundSlider = new Slider(0, 1f, 0.1f, false, skin);
        musicSlider.setValue(parent.getPrefrences().getSoundVolume());
        musicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPrefrences().setSoundVolume(soundSlider.getSoundVolume());
                return false;
            }
        });

        final CheckBox soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked();
                parent.getPrefrences().setSoundEnabled(enabled);
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
        musicEnabled = new Label(null, skin);
        soundLabel = new Label(null, skin);
        soundEnabled = new Label(null, skin);

        //adding our labels to the table
        table.add(title);
        table.row();
        table.add(musicSlider);
        table.add(musicSlider);
        table.row();
        table.add(musicEnabled);
        table.add(musicCheckbox);
        table.row();
        table.add(soundLabel);
        table.add(soundSlider);
        table.row();
        table.add(soundEnabled);
        table.add(soundCheckbox);
        table.row();
        table.add(back);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
    }

}
