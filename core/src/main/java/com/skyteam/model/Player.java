package com.skyteam.model;

import com.skyteam.model.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class Player {

    protected Role role;
    protected Board board;
    protected Dice dice;

    public Player(Role role, Board board) {
        this.role = role;
        this.dice = new Dice();
        this.board = board;
    }

    public List<Integer> rollDice(int numDice) {
        if (numDice < 1) {
            throw new IllegalArgumentException("Number of dice must be greater than 0");
        }
        return dice.rollMultiple(numDice);
    }

    public void useCoffee(int indexOfDice, int positiveOrNegative) {
        if (board.getCoffee() > 0) {
            board.setCoffee(board.getCoffee() - 1);
            List<Integer> rolledValues = dice.getRolledValues();
            if (indexOfDice >= 0 && indexOfDice < rolledValues.size()) {
                int dieToChange = rolledValues.get(indexOfDice);
                if (positiveOrNegative > 0 && dieToChange < 6) {
                    dice.setRolledValue(indexOfDice, dieToChange + 1);
                } else if (positiveOrNegative < 0 && dieToChange > 1) {
                    dice.setRolledValue(indexOfDice, dieToChange - 1);
                }
            } else {
                throw new IllegalArgumentException("Invalid dice index.");
            }
        } else {
            throw new IllegalStateException("No coffee left to use!");
        }
    }

    public Role getRole() {
        return role;
    }

    public boolean contributeToTask(int diceValue, Task task) {
        return board.contributeToTask(diceValue, task, this);
    }

    public Dice getDice() {
        return dice;
    }

    public Board getBoard() {
        return board;
    }

    public List<Integer> useRerollToken(Integer... diceToReroll) {
        if (getBoard().getReroll() <= 0) {
            throw new IllegalStateException("No reroll tokens available on the board!!");
        }

        List<Integer> currentValues = getDice().getRolledValues();
        List<Integer> indicesToReroll = new ArrayList<>();
        for (Integer value : diceToReroll) {
            int index = currentValues.indexOf(value);
            if (index == -1) {
                throw new IllegalArgumentException("One or more specified dice values are not in the current rolled values!!");
            }
            indicesToReroll.add(index);
        }

        if (diceToReroll.length > getDice().getRolledValues().size()) {
            throw new IllegalArgumentException("you cant reroll more dices than you have!!");
        }

        getBoard().useRerollToken();
        getDice().rerollSpecificDice(indicesToReroll);

        return getDice().getRolledValues();
    }


}
