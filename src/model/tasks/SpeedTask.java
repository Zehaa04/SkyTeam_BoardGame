package model.tasks;

import model.Board;
import model.Role;

public class SpeedTask extends Task {

    public SpeedTask(Role[] allowedRoles) {
        super(allowedRoles, true,  1, 2, 3, 4, 5, 6);
    }


    @Override
    public void triggerAction(Board board) {
        Task[] availableTasks = board.getAvailableTasks(Role.PILOT, Role.COPILOT);

        boolean pilotSpeedTaskUsed = false;
        boolean copilotSpeedTaskUsed = false;
        int totalSpeed = 0;

        for (Task task : availableTasks) {
            if (task instanceof SpeedTask) {
                SpeedTask speedTask = (SpeedTask) task;
                if (speedTask.isUsed()) {
                    totalSpeed += speedTask.getDiceValue();
                    if (speedTask.isRoleAllowed(new Role[]{Role.PILOT})) {
                        pilotSpeedTaskUsed = true;
                    }
                    if (speedTask.isRoleAllowed(new Role[]{Role.COPILOT})) {
                        copilotSpeedTaskUsed = true;
                    }
                }
            }
        }

        if (pilotSpeedTaskUsed && copilotSpeedTaskUsed) {
            int minSpeed = board.getMinSpeedBorder();
            int maxSpeed = board.getMaxSpeedBorder();

            if (totalSpeed > minSpeed && totalSpeed < maxSpeed) {
                board.setCurrentPosition(board.getCurrentPosition() + 1);
            } else if (totalSpeed >= maxSpeed) {
                board.setCurrentPosition(board.getCurrentPosition() + 2);
            }
        }
        board.setCurrentSpeed(totalSpeed);
    }
}
