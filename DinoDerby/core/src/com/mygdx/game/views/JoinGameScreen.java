package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Toast;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JoinGameScreen implements Screen {

    private final MyGdxGame parent;
    private Stage stage;
    Skin skin = new Skin(Gdx.files.internal("skin/buttonskin.json"));
    private final List<Toast> toasts = new LinkedList<Toast>();
    private Toast.ToastFactory toastFactory;

    public JoinGameScreen(MyGdxGame dinoDerby){
        parent = dinoDerby;
        stage = new Stage(new ScreenViewport());

        BitmapFont font = skin.getFont("DoHyeon");

        // create factory
        toastFactory = new Toast.ToastFactory.Builder()
                .font(font)
                .build();
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        final TextField lobbyIdField = new TextField("", skin);
        TextButton joinGame = new TextButton("Join game", skin);
        TextButton backBtn = new TextButton("Back", skin);
        table.add(lobbyIdField).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(joinGame).fillX().uniformX();
        table.row();
        table.add(backBtn).fillX().uniformX();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        joinGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    System.out.println("GameID: " + lobbyIdField.getText());
                    System.out.println("Game started i joingamescreen");
                    if (!parent.getFirebaseInstance().checkGameStarted(lobbyIdField.getText())) {
                        parent.getFirebaseInstance().joinGame(lobbyIdField.getText(),parent.getPlayerID());
                        parent.changeScreen(MyGdxGame.LOBBY);
                        System.out.println("--------");
                        System.out.println("game is not started");
                    }
                    else {
                        System.out.println("--------");
                        System.out.println("game is started");
                        toasts.add(toastFactory.create("Game is already started", Toast.Length.LONG));
                    }
                }catch (IllegalArgumentException i) {
                    System.err.println(i);
                    toasts.add(toastFactory.create(i.getMessage(), Toast.Length.LONG));
                }
            }
        });
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(MyGdxGame.MENU);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        Iterator<Toast> it = toasts.iterator();
        while(it.hasNext()) {
            Toast t = it.next();
            if (!t.render(Gdx.graphics.getDeltaTime())) {
                it.remove(); // toast finished -> remove
            } else {
                break; // first toast still active, break the loop
            }
        }
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
        stage.clear();
    }

    @Override
    public void dispose() {

    }
}
