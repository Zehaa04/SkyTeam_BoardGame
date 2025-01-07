import com.skyteam.logic.*;
import com.skyteam.tasks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;
    private Board board;
    private Dice dice;

    @BeforeEach
    void setUp() throws Exception {
        Plan testPlan = Plan.MONTREAL;
        game = new Game(testPlan);
        board = game.getBoard();
        dice = new Dice();
        assertNotNull(board);
        assertNotNull(board.getAvailableTasks(Set.of(Role.PILOT)));
        assertFalse(board.getAvailableTasks(Set.of(Role.PILOT)).isEmpty());
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game.getBoard());
        assertNotNull(game.getPilot());
        assertNotNull(game.getCopilot());
    }

    /*@Test
    void testPlayRoundAlternatesTurns() {
        int initialAltitude = board.getAltitude().getAltitude();

        game.playRound();
        int altitudeAfterFirstRound = board.getAltitude().getAltitude();
        assertTrue(altitudeAfterFirstRound < initialAltitude);

        game.playRound();
        int altitudeAfterSecondRound = board.getAltitude().getAltitude();
        assertTrue(altitudeAfterSecondRound < altitudeAfterFirstRound);
    }*/

   /* @Test
    void testTakeTurnHandlesNoValidMoves() {
        Player pilot = game.getPilot();
        pilot.getDice().getRolledValues().clear();


        assertFalse(pilot.getDice().getRolledValues().isEmpty());
    }*/

    @Test
    void testBoardInitialization() {
        assertNotNull(board.getFlightPlan());
        assertEquals(0, board.getCoffee());
        assertEquals(0, board.getReroll());
    }

    @Test
    void testContributeToTask() {
        Task speedTask = board.getAvailableTasks(Set.of(Role.PILOT)).stream()
                .filter(task -> task instanceof SpeedTask)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("SpeedTask not found in board's task list."));

        Player pilot = game.getPilot();
        pilot.rollDice(1);
        int validDiceValue = 2;
        assertNull(speedTask.getDiceValue());
        pilot.getDice().setRolledValue(0, validDiceValue);
        boolean contributed = board.contributeToTask(validDiceValue, speedTask, pilot);


        assertTrue(speedTask.isDiceValueValid(2));
        assertTrue(speedTask.isRoleAllowed(Set.of(Role.PILOT)));
        assertTrue(contributed);
        assertTrue(speedTask.isUsed());
    }

    @Test
    void testTaskResetAfterRound() {
        Task speedTask = new SpeedTask(Set.of(Role.PILOT));
        Task axisTask = new AxisTask(Set.of(Role.PILOT));
        Task radioTask = new RadioTask(Set.of(Role.PILOT));
        Task coffeeTask = new CoffeeTask();

        board.getAvailableTasks(Set.of(Role.PILOT)).add(speedTask);
        board.getAvailableTasks(Set.of(Role.PILOT)).add(axisTask);
        board.getAvailableTasks(Set.of(Role.PILOT)).add(radioTask);
        board.getAvailableTasks(Set.of(Role.PILOT)).add(coffeeTask);

        speedTask.setUsed(true);
        axisTask.setUsed(true);
        radioTask.setUsed(true);
        coffeeTask.setUsed(true);

        board.resetRoundSpecificTasks();

        List<Task> availableTasks = board.getAvailableTasks(Set.of(Role.PILOT));
        for (Task task : availableTasks) {
            if (task instanceof SpeedTask || task instanceof AxisTask || task instanceof RadioTask || task instanceof CoffeeTask) {
                assertFalse(task.isUsed(), "Round-specific task should be reset.");
            }
        }
    }

    @Test
    void testDiceRoll() {
        int rollResult = dice.roll();
        assertTrue(rollResult >= 1 && rollResult <= 6);
    }

    @Test
    void testMultipleDiceRoll() {
        List<Integer> rolls = dice.rollMultiple(5);
        assertEquals(5, rolls.size());
        assertTrue(rolls.stream().allMatch(value -> value >= 1 && value <= 6));
    }

    @Test
    void testRerollSpecificDice() {
        dice.rollMultiple(3);
        List<Integer> initialRolls = dice.getRolledValues();
        dice.rerollSpecificDice(List.of(0));
        List<Integer> updatedRolls = dice.getRolledValues();
        assertEquals(dice.getRolledValues().size(), updatedRolls.size());
    }
}

