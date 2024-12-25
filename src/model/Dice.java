package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dice {
    private Random rand = new Random();
    private List<Integer> rolledValues;

    public Dice() {
        rolledValues = new ArrayList<>();
    }

    public int roll() {
        rolledValues.clear();
        int rollResult = rand.nextInt(6) + 1;
        rolledValues.add(rollResult);
        return rollResult;
    }

    public List<Integer> rollMultiple(int numDice) {
        rolledValues.clear();
        List<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < numDice; i++) {
            rolls.add(rand.nextInt(6) + 1);
        }
        rolledValues.addAll(rolls);
        return rolls;
    }

    public void rerollSpecificDice(List<Integer> indicesToReroll) {
        for (int index : indicesToReroll) {
            if (index >= 0 && index < rolledValues.size()) {
                rolledValues.set(index, rand.nextInt(6) + 1);
            } else {
                throw new IllegalArgumentException("Invalid index for rerolling: " + index);
            }
        }
    }

    public ArrayList<Integer> getRolledValues() {
        return new ArrayList<>(rolledValues);
    }

    public void setRolledValue(int index, int value) {
        if (index >= 0 && index < rolledValues.size()) {
            rolledValues.set(index, value);
        }
    }
}
