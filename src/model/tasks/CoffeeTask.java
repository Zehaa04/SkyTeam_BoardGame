package model.tasks;

import model.Board;
import model.Role;

import java.util.Arrays;
import java.util.HashSet;

public class CoffeeTask extends Task {

    public CoffeeTask() {
        super(new HashSet<>(Arrays.asList(Role.PILOT, Role.COPILOT)), false, 1, 2, 3, 4, 5, 6);
    }

    @Override
    public void triggerAction(Board board) {
        if (board.getCoffee()<3) {
            board.setCoffee(board.getCoffee() + 1);
        }
    }
}
