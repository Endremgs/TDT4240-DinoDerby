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
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

public class SettingsScreen implements Screen {

    private MyGdxGame parent;
    private Stage stage;

    private Label title;
    private Label musicEnabled;
    private Color bgColor = Color.ORANGE;

    public static final String PREFERENCES_NAME = "gameSett";


    public SettingsScreen(MyGdxGame gdxGame){
        parent = gdxGame;
        stage = new Stage(new ScreenViewport());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/buttonskin.json"));

        final TextButton back = new TextButton("Back", skin);
        final CheckBox musicCheckbox = new CheckBox("", skin);
        musicCheckbox.getImage().setScaling(Scaling.fill);
        musicCheckbox.getImageCell().size(75);

        if (parent.musicPlaying) {
            musicCheckbox.setChecked(true);
        } else {
            musicCheckbox.setChecked(false);
        }

        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if(musicCheckbox.isChecked()){
                    parent.music.play();
                    parent.musicPlaying = true;
                } else{
                    parent.music.pause();
                    parent.musicPlaying = false;
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
        title.setFontScale(1.75F);
        musicEnabled = new Label("Enable Music", skin);
        musicEnabled.setFontScale(1.25F);
        //adding our labels to the table
        table.add(title).fillX().uniformX();
        table.row();
        table.add(musicEnabled);
        table.add(musicCheckbox).width(128).height(128);

        table.row();
        table.add(back).fillX().uniformX();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

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
    }

}
