package com.skyteam.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.skyteam.model.Game;
import com.skyteam.model.Player;
import com.skyteam.view.GameScreen;
import com.skyteam.view.MainMenuScreen;

import java.util.List;

public class GameController {
    private final com.badlogic.gdx.Game gdxGame;
    private final Game gameLogic;
    private final MainMenuScreen mainMenuScreen;
    private final GameScreen gameScreen;

    public GameController(com.badlogic.gdx.Game gdxGame, Game gameLogic) {
        this.gdxGame = gdxGame;
        this.gameLogic = gameLogic;
        this.mainMenuScreen = new MainMenuScreen();
        this.gameScreen = new GameScreen(this);
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

    public void registerRollDiceButton(TextButton rollDiceButton) {
        rollDiceButton.addListener(new ChangeListener() {
            boolean isPilotTurn = true;

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = isPilotTurn ? getGameLogic().getPilot() : getGameLogic().getCopilot();
                List<Integer> rolledDice = currentPlayer.rollDice(4);
                gameScreen.displayRolledDice(rolledDice, isPilotTurn);
                isPilotTurn = !isPilotTurn;
            }
        });
    }

    private void startGame() {
        gameLogic.startGame();
        gdxGame.setScreen(gameScreen);
    }

    private void exitGame() {
        com.badlogic.gdx.Gdx.app.exit();
    }

    public MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public Game getGameLogic() {
        return gameLogic;
    }
}
