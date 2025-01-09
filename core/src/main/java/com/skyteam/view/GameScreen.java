package com.skyteam.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    private Stage stage;

    public GameScreen() {
        stage = new Stage(new FitViewport(1280, 720));
        show();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1280, 720));

        Image boardImage = new Image(new Texture("skins/skyteam-main-board.png"));

        boardImage.setPosition(
            stage.getWidth() / 2f - boardImage.getWidth() / 2f,
            stage.getHeight() / 2f - boardImage.getHeight() / 2f
        );

        stage.addActor(boardImage);
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
