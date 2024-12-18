package model.tasks;

import model.Board;
import model.Role;

import java.util.*;

public class SpeedTask extends Task {

    public SpeedTask(Set<Role> allowedRoles) {
        super(allowedRoles, true,  1, 2, 3, 4, 5, 6);
    }


    @Override
    public void triggerAction(Board board) {
        ArrayList<Task> availableTasks = board.getAvailableTasks(new HashSet<>(Arrays.asList(Role.PILOT, Role.COPILOT)));

        boolean pilotSpeedTaskUsed = false;
        boolean copilotSpeedTaskUsed = false;
        int totalSpeed = 0;

        for (Task task : availableTasks) {
            if (task instanceof SpeedTask) {
                SpeedTask speedTask = (SpeedTask) task;
                if (speedTask.isUsed()) {
                    totalSpeed += speedTask.getDiceValue();
                    if (speedTask.isRoleAllowed(new HashSet<>(List.of(Role.PILOT)))) {
                        pilotSpeedTaskUsed = true;
                    }
                    if (speedTask.isRoleAllowed(new HashSet<>(List.of(Role.COPILOT)))) {
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
