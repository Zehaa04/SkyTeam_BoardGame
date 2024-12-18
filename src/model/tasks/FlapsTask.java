package model.tasks;

import model.Board;
import model.Role;

public class FlapsTask extends Task {
    public FlapsTask(Integer... validDiceValues) {
        super(new Role[]{Role.COPILOT}, false, validDiceValues);
    }

    @Override
    public void triggerAction(Board board) {
        if (board.getMaxSpeedBorder()<12) {
            board.setMaxSpeedBorder(board.getMaxSpeedBorder()+1);
        }
    }
}
