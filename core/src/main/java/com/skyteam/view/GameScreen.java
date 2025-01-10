package com.skyteam.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    private Stage stage;
    private Image boardImage;

    public GameScreen() {
        stage = new Stage(new FitViewport(1280, 720));
        show();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1280, 720));

        boardImage = new Image(new Texture("skins/skyteam-main-board.png"));

        boardImage.setPosition(
            stage.getWidth() / 2f - boardImage.getWidth() / 2f,
            stage.getHeight() / 2f - boardImage.getHeight() / 2f
        );

        stage.addActor(boardImage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Black background
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        boardImage.setPosition(
            stage.getWidth() / 2f - boardImage.getWidth() / 2f,
            stage.getHeight() / 2f - boardImage.getHeight() / 2f
        );
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
