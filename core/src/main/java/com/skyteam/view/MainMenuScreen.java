package com.skyteam.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private TextButton startButton;
    private TextButton quitButton;

    public MainMenuScreen() {
        stage = new Stage(new FitViewport(1280, 720));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        startButton = new TextButton("Start Game", skin);
        quitButton = new TextButton("Quit", skin);


        table.add(startButton).pad(10);
        table.row();
        table.add(quitButton).pad(10);
    }

    public TextButton getStartButton() {
        return startButton;
    }

    public TextButton getQuitButton() {
        return quitButton;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
