package com.skyteam.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skyteam.controller.GameController;

import java.util.List;

public class GameScreen implements Screen {
    private Stage stage;
    private Image boardImage;
    private Image altitudeTracksImage;
    private Image approachTracksImage;
    private TextButton rollDiceButton;
    private Skin buttonSkin;
    private GameController controller;
    private Table diceResultTable;
    private SpriteBatch spriteBatch;

    private Texture diceTexture;
    private TextureRegion[][] diceRegions;
    private DragAndDrop dragAndDrop;
    private JsonValue config;

    public GameScreen(GameController controller) {
        this.controller = controller;
        loadConfig();
        show();
    }

    private void loadConfig() {
        JsonReader jsonReader = new JsonReader();
        config = jsonReader.parse(Gdx.files.internal("config.json"));
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

        Texture altitudeTracksTexture = new Texture("skins/skyteam-alltitude-tracks.png");
        TextureRegion croppedAltitudeTracks = new TextureRegion(altitudeTracksTexture, 0, 0, 173, altitudeTracksTexture.getHeight());
        altitudeTracksImage = new Image(croppedAltitudeTracks);

        altitudeTracksImage.setPosition(
            boardImage.getX() + boardImage.getWidth() + 10,
            boardImage.getY()
        );

        Texture approachTracksTexture = new Texture("skins/skyteam-approach-tracks.png");
        TextureRegion croppedApproachTracks = new TextureRegion(approachTracksTexture, 0, 0, 173, 718);
        approachTracksImage = new Image(croppedApproachTracks);

        approachTracksImage.setPosition(
            boardImage.getX() - approachTracksImage.getWidth() - 10,
            boardImage.getY()
        );

        Texture tokensTexture = new Texture("skins/skyteam-tokens.png");
        TextureRegion tokenRegion = new TextureRegion(tokensTexture, 200, 0, tokensTexture.getWidth() - 200, tokensTexture.getHeight());

        Image token1 = new Image(tokenRegion);
        Image token2 = new Image(tokenRegion);

        token1.setSize(50, 50);
        token2.setSize(50, 50);

        float tokenY = approachTracksImage.getY() + 718 - 118;
        float tokenX1 = approachTracksImage.getX() + approachTracksImage.getWidth() / 2f - 55;
        float tokenX2 = tokenX1 + 60;

        token1.setPosition(tokenX1, tokenY);
        token2.setPosition(tokenX2, tokenY);

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

        dragAndDrop = new DragAndDrop();
        spriteBatch = new SpriteBatch();

        addDropZonesFromConfig();

        stage.addActor(boardImage);
        stage.addActor(altitudeTracksImage);
        stage.addActor(approachTracksImage);
        stage.addActor(token1);
        stage.addActor(token2);
        stage.addActor(rollDiceButton);
        stage.addActor(diceResultTable);
        stage.setDebugAll(true);

        Gdx.input.setInputProcessor(stage);

        controller.registerRollDiceButton(rollDiceButton);
    }

    private void addDropZonesFromConfig() {
        for (JsonValue location : config.get("leftDiceSpots")) {
            createDropTarget(location.getFloat("x"), location.getFloat("y"));
        }
        for (JsonValue location : config.get("rightDiceSpots")) {
            createDropTarget(location.getFloat("x"), location.getFloat("y"));
        }
        for (JsonValue location : config.get("topCenterDiceSpots")) {
            createDropTarget(location.getFloat("x"), location.getFloat("y"));
        }
        for (JsonValue location : config.get("upperCenterDiceSpots")) {
            createDropTarget(location.getFloat("x"), location.getFloat("y"));
        }
        for (JsonValue location : config.get("lowerCenterDiceSpots")) {
            createDropTarget(location.getFloat("x"), location.getFloat("y"));
        }
        for (JsonValue location : config.get("bottomCenterDiceSpots")) {
            createDropTarget(location.getFloat("x"), location.getFloat("y"));
        }
    }

    private void createDropTarget(float x, float y) {
        float boardOffsetX = boardImage.getX();
        float boardOffsetY = boardImage.getY();

        Image dropTarget = new Image();
        dropTarget.setSize(57, 57); // Updated size based on diceFaceSize
        dropTarget.setPosition(boardOffsetX + x, boardOffsetY + y);

        dragAndDrop.addTarget(new DragAndDrop.Target(dropTarget) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if (payload.getObject() instanceof TextureRegion) {
                    TextureRegion diceFace = (TextureRegion) payload.getObject();
                    Image droppedDice = new Image(diceFace);
                    droppedDice.setSize(57, 57); // Updated size based on diceFaceSize
                    droppedDice.setPosition(boardOffsetX + x, boardOffsetY + y);
                    stage.addActor(droppedDice);
                }
            }
        });

        stage.addActor(dropTarget);
    }

    private void addDragSourceForDice(Image diceImage, TextureRegion diceFace) {
        dragAndDrop.addSource(new DragAndDrop.Source(diceImage) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(diceFace);
                payload.setDragActor(new Image(diceFace));
                return payload;
            }
        });
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
        altitudeTracksImage.setPosition(
            boardImage.getX() + boardImage.getWidth() + 10,
            boardImage.getY()
        );
        approachTracksImage.setPosition(
            boardImage.getX() - approachTracksImage.getWidth() - 10,
            boardImage.getY()
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
        spriteBatch.dispose();
    }

    public void displayRolledDice(List<Integer> rolledDice, boolean isPilotTurn) {
        diceResultTable.clear();

        int row = isPilotTurn ? 0 : 1;

        for (Integer dieValue : rolledDice) {
            TextureRegion diceFace = diceRegions[row][dieValue - 1];
            Image diceImage = new Image(diceFace);

            addDragSourceForDice(diceImage, diceFace);

            diceResultTable.add(diceImage).pad(5);
        }
    }
}
