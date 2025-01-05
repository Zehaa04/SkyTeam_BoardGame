import model.Board;
import model.Plan;
import model.Player;
import model.Role;
import model.tasks.SpeedTask;
import model.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;
    private Board board;

    @BeforeEach
    public void setUp() throws IOException {
        board = new Board(Plan.MONTREAL);
        player = board.getPilot();
    }

    @Test
    public void testRollDice() {
        List<Integer> rolls = player.rollDice(3);
        assertEquals(3, rolls.size());
        for (int roll : rolls) {
            assertTrue(roll >= 1 && roll <= 6);
        }
    }

    @Test
    public void testRollDiceInvalidNumber() {
        assertThrows(IllegalArgumentException.class, () -> player.rollDice(0));
    }

    @Test
    public void testUseCoffee() {
        player.rollDice(1);
        while (player.getDice().getRolledValues().getFirst() != 6) {
            board.setCoffee(1);
            int firstRoll = player.getDice().getRolledValues().getFirst();
            player.useCoffee(0, 1);
            int secondRoll = player.getDice().getRolledValues().getFirst();

            assertTrue(firstRoll < secondRoll);
            assertEquals(0, board.getCoffee());
        }
    }


    @Test
    public void testContributeToTask() {
        Task speedTask = board.getAvailableTasks(Set.of(Role.PILOT)).stream()
                .filter(task -> task instanceof SpeedTask)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("SpeedTask not found in board's task list."));

        player.rollDice(1);
        player.getDice().setRolledValue(0, 2);

        assertNull(speedTask.getDiceValue());
        assertTrue(speedTask.isDiceValueValid(2));
        assertTrue(speedTask.isRoleAllowed(Set.of(Role.PILOT)));

        boolean result = player.contributeToTask(2, speedTask);
        assertTrue(result);
    }

    @Test
    public void testUseRerollToken() {
        board.addRerollToken();
        player.rollDice(4);

        List<Integer> initialValues = player.getDice().getRolledValues();
        Integer[] valuesToReroll = {initialValues.get(0), initialValues.get(1)};

        List<Integer> updatedValues = player.useRerollToken(valuesToReroll);

        assertEquals(4, player.getDice().getRolledValues().size());
        assertTrue(player.getDice().getRolledValues().containsAll(updatedValues));
    }

    @Test
    public void testUseRerollTokenNoTokens() {
        assertThrows(IllegalStateException.class, () -> player.useRerollToken(1, 2));
    }

    @Test
    public void testUseRerollTokenInvalidNumber() {
        board.addRerollToken();
        player.rollDice(4);

        assertThrows(IllegalArgumentException.class, () -> player.useRerollToken(10, 5));

        List<Integer> initialValues = player.getDice().getRolledValues();
        assertThrows(IllegalArgumentException.class, () -> player.useRerollToken(initialValues.getFirst(), initialValues.get(1), initialValues.get(2), initialValues.get(3), 6));
    }

}
