package com.skyteam.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private Table table;
    private Texture backgroundTexture;
    private SpriteBatch spriteBatch;

    public MainMenuScreen() {
        stage = new Stage(new FitViewport(1280, 720));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        spriteBatch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("skins/pic7398904.png"));

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        startButton = new TextButton("Start Game", skin);
        quitButton = new TextButton("Quit", skin);

        startButton.getLabel().setFontScale(2.0f);
        quitButton.getLabel().setFontScale(2.0f);

        table.add(startButton).pad(20).width(300).height(100);
        table.row();
        table.add(quitButton).pad(20).width(300).height(100);
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
        spriteBatch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.invalidateHierarchy();
        table.layout();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
        spriteBatch.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
        spriteBatch.dispose();
    }
}
