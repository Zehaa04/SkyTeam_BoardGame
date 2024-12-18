package model;

import model.tasks.Task;

import java.util.List;

public abstract class Player {

    protected Role role;
    protected Board board;
    protected Dice dice;


    public Player(Role role) {
        this.role = role;
        this.dice = new Dice();
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
}

