package model.tasks;

import model.Board;
import model.Role;

public class BrakesTask extends Task {
    public BrakesTask(Integer... validDiceValues) {
        super(new Role[]{Role.PILOT}, false, validDiceValues);
    }

    @Override
    public void triggerAction(Board board) {
        if (board.getBrakeStrenght()<5) {
            board.setBrakeStrenght(board.getBrakeStrenght()+2);
        }
    }
}
