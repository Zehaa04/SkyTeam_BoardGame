package com.skyteam.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
        Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height-50);
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

        addDropZonesFromConfig();

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Mouse clicked at: x=" + x + ", y=" + y);
                return true;
            }
        });

        stage.addActor(boardImage);
        stage.addActor(altitudeTracksImage);
        stage.addActor(approachTracksImage);
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

        Image dropTarget = new Image(new Texture("skins/test.png"));
        dropTarget.setSize(60, 60);
        dropTarget.setPosition(boardOffsetX + x, boardOffsetY + y);
        dropTarget.setTouchable(Touchable.enabled);

        System.out.println("Creating drop target at: x=" + dropTarget.getX() + ", y=" + dropTarget.getY());

        dragAndDrop.addTarget(new DragAndDrop.Target(dropTarget) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                System.out.println("Dragging over: x=" + dropTarget.getX() + ", y=" + dropTarget.getY() + " | Pointer at: x=" + x + ", y=" + y);
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                System.out.println("Drop detected at: x=" + dropTarget.getX() + ", y=" + dropTarget.getY());
                if (payload.getObject() instanceof TextureRegion) {
                    TextureRegion diceFace = (TextureRegion) payload.getObject();
                    Image droppedDice = new Image(diceFace);
                    droppedDice.setSize(60, 60);
                    droppedDice.setPosition(dropTarget.getX(), dropTarget.getY());
                    stage.addActor(droppedDice);
                    System.out.println("Dice dropped successfully.");
                }
            }
        });

        stage.addActor(dropTarget);
    }

    private void addDragSourceForDice(Image diceImage, TextureRegion diceFace) {
        dragAndDrop.addSource(new DragAndDrop.Source(diceImage) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                System.out.println("Drag started.");
                diceImage.setVisible(false);

                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(diceFace);

                Image dragActor = new Image(diceFace);
                dragActor.setSize(60, 60);
                dragActor.setPosition(x - dragActor.getWidth() / 2, y - dragActor.getHeight() / 2);
                payload.setDragActor(dragActor);

                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                if (target == null) {
                    System.out.println("Drag cancelled: no target.");
                    diceImage.setVisible(true);
                } else {
                    System.out.println("Dice successfully dropped.");
                }
            }
        });
    }

    public void dropDice(int dieValue, float x, float y) {
        float boardOffsetX = boardImage.getX();
        float boardOffsetY = boardImage.getY();

        if (dieValue < 1 || dieValue > diceRegions[0].length) return;

        TextureRegion diceFace = diceRegions[0][dieValue - 1];
        Image droppedDice = new Image(diceFace);
        droppedDice.setSize(60, 60);
        droppedDice.setPosition(boardOffsetX + x, boardOffsetY + y);
        stage.addActor(droppedDice);
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
