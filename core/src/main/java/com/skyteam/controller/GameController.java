package com.skyteam.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.skyteam.logic.Game;
import com.skyteam.view.GameScreen;
import com.skyteam.view.MainMenuScreen;

public class GameController {
    private final com.badlogic.gdx.Game gdxGame;
    private final Game gameLogic;
    private final MainMenuScreen mainMenuScreen;
    private final GameScreen gameScreen;

    public GameController(com.badlogic.gdx.Game gdxGame, Game gameLogic) {
        this.gdxGame = gdxGame;
        this.gameLogic = gameLogic;
        this.mainMenuScreen = new MainMenuScreen();
        this.gameScreen = new GameScreen();

        setupListeners();
    }

    private void setupListeners() {
        mainMenuScreen.getStartButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startGame();
            }
        });

        mainMenuScreen.getQuitButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exitGame();
            }
        });
    }

    private void startGame() {
        gameLogic.startGame();
        gdxGame.setScreen(gameScreen);
    }

    private void exitGame() {
        gdxGame.dispose();
    }

    public MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }
}
