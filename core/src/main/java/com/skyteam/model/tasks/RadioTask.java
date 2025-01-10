package com.skyteam.model.tasks;

import com.skyteam.model.*;

import java.util.Set;

public class RadioTask extends Task {
    public RadioTask(Set<Role> allowedRoles) {
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
