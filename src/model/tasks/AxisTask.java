package model.tasks;

import model.Board;
import model.Role;

import java.util.*;

public class AxisTask extends Task {

    public AxisTask(Set<Role> allowedRoles) {
        super(allowedRoles, true, 1, 2, 3, 4, 5, 6);
    }

    @Override
    public void triggerAction(Board board) {
        ArrayList<Task> availableTasks = board.getAvailableTasks(new HashSet<>(Arrays.asList(Role.PILOT, Role.COPILOT)));

        Integer pilotAxisValue = null;
        Integer copilotAxisValue = null;

        for (Task task : availableTasks) {
            if (task instanceof AxisTask) {
                AxisTask axisTask = (AxisTask) task;
                if (axisTask.isUsed()) {
                    if (axisTask.isRoleAllowed(new HashSet<>(List.of(Role.PILOT))) && pilotAxisValue == null) {
                        pilotAxisValue = axisTask.getDiceValue();
                    }
                    if (axisTask.isRoleAllowed(new HashSet<>(List.of(Role.COPILOT))) && copilotAxisValue == null) {
                        copilotAxisValue = axisTask.getDiceValue();
                    }
                }
            }
        }

        if (pilotAxisValue != null && copilotAxisValue != null) {
            int result = pilotAxisValue - copilotAxisValue;

            board.setCurrentAxis(result);
        }
    }
}