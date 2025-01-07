package com.skyteam.tasks;

import com.skyteam.logic.*;

import java.util.HashSet;
import java.util.List;

public class FlapsTask extends Task {
    public FlapsTask(Integer... validDiceValues) {
        super(new HashSet<>(List.of(Role.COPILOT)), false, validDiceValues);
    }

    @Override
    public void triggerAction(Board board) {
        if (board.getMaxSpeedBorder()<12) {
            board.setMaxSpeedBorder(board.getMaxSpeedBorder()+1);
        }
    }
}
