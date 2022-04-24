package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

public class TutorialScreen implements Screen {

    private final MyGdxGame parent;
    private Stage stage;
    private final SpriteBatch sb;
    private Texture texture;

    public TutorialScreen(MyGdxGame dinoDerby){
        parent = dinoDerby;
        this.texture = new Texture("TutorialPart1.png");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        sb = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/buttonskin.json"));
        TextButton back = new TextButton("Back", skin);
        table.add(back).fillX().uniformX();
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(MyGdxGame.MENU);
            }
        });


    }

    @Override
    public void render(float delta) {
        sb.begin();
        stage.draw();
        sb.draw(texture, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.end();
    }

    @Override
    public void resize(int width, int height) {

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
        //texture.dispose();
        //parent.dispose();
        //sb.dispose();
    }
}
