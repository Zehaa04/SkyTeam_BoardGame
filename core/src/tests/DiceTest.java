import com.skyteam.logic.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DiceTest {

    private Dice dice;

    @BeforeEach
    public void setUp() {
        dice = new Dice();
    }

    @Test
    public void testRoll() {
        int rollResult = dice.roll();
        assertTrue(rollResult >= 1 && rollResult <= 6);

        List<Integer> rolledValues = dice.getRolledValues();
        assertEquals(1, rolledValues.size());
        assertEquals(rollResult, rolledValues.getFirst());
    }

    @Test
    public void testRollMultiple() {
        int numDice = 5;
        List<Integer> rolls = dice.rollMultiple(numDice);

        assertEquals(numDice, rolls.size());
        for (int roll : rolls) {
            assertTrue(roll >= 1 && roll <= 6);
        }

        List<Integer> rolledValues = dice.getRolledValues();
        assertEquals(numDice, rolledValues.size());
        assertEquals(rolls, rolledValues);
    }

    @Test
    public void testSetRolledValue() {
        dice.rollMultiple(3);
        List<Integer> rolledValues;

        int index = 1;
        int newValue = 4;
        dice.setRolledValue(index, newValue);

        rolledValues = dice.getRolledValues();
        assertEquals(newValue, rolledValues.get(index));
    }

    @Test
    public void testSetRolledValueInvalidIndex() {
        dice.rollMultiple(3);
        List<Integer> rolledValuesBefore = dice.getRolledValues();

        dice.setRolledValue(-1, 4);
        dice.setRolledValue(3, 5);

        List<Integer> rolledValuesAfter = dice.getRolledValues();
        assertEquals(rolledValuesBefore, rolledValuesAfter);
    }

    @Test
    public void testGetRolledValues() {
        dice.rollMultiple(4);
        List<Integer> rolledValues = dice.getRolledValues();

        assertNotNull(rolledValues);
        assertEquals(4, rolledValues.size());

        for (int roll : rolledValues) {
            assertTrue(roll >= 1 && roll <= 6);
        }
    }

    @Test
    public void testRerollSpecificDice() {
        dice.rollMultiple(4);
        List<Integer> initialValues = dice.getRolledValues();

        List<Integer> indicesToReroll = Arrays.asList(0, 2);
        dice.rerollSpecificDice(indicesToReroll);

        List<Integer> updatedValues = dice.getRolledValues();

        assertEquals(4, updatedValues.size());

        assertEquals(initialValues.get(1), updatedValues.get(1));
        assertEquals(initialValues.get(3), updatedValues.get(3));
    }
}
