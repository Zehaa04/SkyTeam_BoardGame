package model.tasks;

import model.Board;
import model.Role;

public class RadioTask extends Task {
    public RadioTask(Role[] allowedRoles) {
        super(allowedRoles, false, 1, 2, 3, 4, 5, 6);
    }

    @Override
    public void triggerAction(Board board) {
        int currentPosition = board.getCurrentPosition();
        int diceValue = getDiceValue();

        int targetIndex = currentPosition + (diceValue - 1);

        if (targetIndex < board.getFlightPlan().getFlightPath().size()) {
            int currentValue = board.getFlightPlan().getPlanes(targetIndex);
            if (currentValue > 0) {
                board.getFlightPlan().getFlightPath().set(targetIndex, currentValue - 1);
            }
        }
    }
}
