package com.skyteam.model;

import com.skyteam.model.tasks.Task;

import java.util.Arrays;
import java.util.HashSet;

public class GameState {

    public static boolean evaluateAll(Game game) {
        return winCondition(game) || loseCondition(game);
    }

    public static boolean winCondition(Game game) {
        return areAllObligatoryTasksCompleted(game)
                && isDestinationReached(game)
                && isFinalRoundSpeedValid(game)
                && isHorizontalAxisBalanced(game);
    }

    private static boolean areAllObligatoryTasksCompleted(Game game) {
        return game.getBoard().getAvailableTasks(new HashSet<>(Arrays.asList(Role.PILOT, Role.COPILOT)))
                .stream()
                .filter(Task::isObligatory)
                .allMatch(Task::isUsed);
    }


    private static boolean isDestinationReached(Game game) {
        return game.getBoard().getAltitude().getAltitude() == 0
                && game.getBoard().getCurrentPosition() == game.getBoard().getFlightPlan().getFlightPath().size() - 1
                && game.getBoard().getFlightPlan().getFlightPath().get(game.getBoard().getCurrentPosition()) == 0;
    }

    private static boolean isFinalRoundSpeedValid(Game game) {
        return game.getBoard().getAltitude().getAltitude() == 0
                && game.getBoard().getCurrentSpeed() <= game.getBoard().getBrakeStrenght();
    }

    private static boolean isHorizontalAxisBalanced(Game game) {
        return game.getBoard().getCurrentAxis() == 0;
    }

    public static boolean collsion(Game game){
        return game.getBoard().getFlightPlan().getPlanes(game.getBoard().getCurrentPosition())>0;
    }

    public static boolean loseCondition(Game game) {
        return (!areAllObligatoryTasksCompleted(game) && isDestinationReached(game)) || (game.getBoard().getCurrentAxis()<-2 || game.getBoard().getCurrentAxis()>2) ||
                (!isFinalRoundSpeedValid(game)) || (game.getBoard().getCurrentPosition()>=game.getBoard().getFlightPlan().getFlightPath().size()) ||
                (collsion(game)) || (!isDestinationReached(game) && game.getBoard().getAltitude().getAltitude()==0);
    }
}
