package com.skyteam.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skyteam.controller.GameController;

import java.util.List;

public class GameScreen implements Screen {
    private Stage stage;
    private Image boardImage;
    private TextButton rollDiceButton;
    private Skin buttonSkin;
    private GameController controller;
    private Table diceResultTable;

    private Texture diceTexture;
    private TextureRegion[][] diceRegions;

    public GameScreen(GameController controller) {
        this.controller = controller;
        show();
    }

    @Override
    public void show() {
        Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height - 50);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        boardImage = new Image(new Texture("skins/skyteam-main-board.png"));
        boardImage.setPosition(
            stage.getWidth() / 2f - boardImage.getWidth() / 2f,
            stage.getHeight() / 2f - boardImage.getHeight() / 2f
        );

        buttonSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        rollDiceButton = new TextButton("Roll Dice", buttonSkin);
        rollDiceButton.setSize(200, 50);
        rollDiceButton.setPosition(
            stage.getWidth() / 2f - rollDiceButton.getWidth() / 2f,
            50
        );

        diceResultTable = new Table();
        diceResultTable.setFillParent(true);
        diceResultTable.top().pad(20);
        diceTexture = new Texture("skins/skyteam-dice.png");
        diceRegions = TextureRegion.split(diceTexture, 85, 85);

        stage.addActor(boardImage);
        stage.addActor(rollDiceButton);
        stage.addActor(diceResultTable);

        Gdx.input.setInputProcessor(stage);
        controller.registerRollDiceButton(rollDiceButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
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
        rollDiceButton.setPosition(
            stage.getWidth() / 2f - rollDiceButton.getWidth() / 2f,
            50
        );
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        buttonSkin.dispose();
        diceTexture.dispose();
    }

    public void displayRolledDice(List<Integer> rolledDice, boolean isPilotTurn) {
        diceResultTable.clear();

        int row = isPilotTurn ? 0 : 1;

        for (Integer dieValue : rolledDice) {
            TextureRegion diceFace = diceRegions[row][dieValue - 1];
            Image diceImage = new Image(diceFace);
            diceResultTable.add(diceImage).pad(5);
        }
    }
}
