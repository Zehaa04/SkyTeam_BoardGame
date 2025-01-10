package com.skyteam;

import com.skyteam.controller.GameController;
import com.skyteam.model.Game;
import com.skyteam.model.Plan;

import java.io.IOException;

public class SkyTeamGame extends com.badlogic.gdx.Game {
    @Override
    public void create() {
        Game gameLogic;
        try {
            gameLogic =  new Game(choosePlan());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GameController gameController = new GameController(this, gameLogic);
        setScreen(gameController.getMainMenuScreen());
    }

    private Plan choosePlan() { //wird implementiert
        return Plan.MONTREAL;
    }
}
