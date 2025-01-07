package com.skyteam.tasks;

import com.skyteam.logic.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BrakesTask extends Task {
    public BrakesTask(Integer... validDiceValues) {
        super(new HashSet<>(List.of(Role.PILOT)), false, validDiceValues);
    }

    @Override
    public void triggerAction(Board board) {
        if (board.getBrakeStrenght()<5) {
            board.setBrakeStrenght(board.getBrakeStrenght()+2);
        }
    }
}
