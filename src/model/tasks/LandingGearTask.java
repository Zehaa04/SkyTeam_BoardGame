package model.tasks;

import model.Board;
import model.Role;

import java.util.HashSet;
import java.util.List;

public class LandingGearTask extends Task {

    public LandingGearTask(Integer... validDiceValues) {
        super(new HashSet<>(List.of(Role.PILOT)), false, validDiceValues);
    }


    @Override
    public void triggerAction(Board board) {
        if (board.getMinSpeedBorder()<8) {
            board.setMinSpeedBorder(board.getMinSpeedBorder()+1);
        }
    }
}
