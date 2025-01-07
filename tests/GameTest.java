import model.*;
import model.tasks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;
    private Board board;
    private Dice dice;

    @BeforeEach
    void setUp() throws IOException {
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
                .orElseThrow(() -> new IllegalStateException("SpeedTask not found"));
        Player pilot = game.getPilot();
        pilot.rollDice(1);
        int validDiceValue = 2;
        assertNull(speedTask.getDiceValue());
        pilot.getDice().setRolledValue(0, validDiceValue);
        boolean contributed = board.contributeToTask(validDiceValue, speedTask, pilot);
        assertTrue(speedTask.isDiceValueValid(validDiceValue));
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
        board.getAvailableTasks(Set.of(Role.PILOT)).addAll(List.of(speedTask, axisTask, radioTask, coffeeTask));
        speedTask.setUsed(true);
        axisTask.setUsed(true);
        radioTask.setUsed(true);
        coffeeTask.setUsed(true);
        board.resetRoundSpecificTasks();
        board.getAvailableTasks(Set.of(Role.PILOT)).forEach(task ->
                assertFalse(task.isUsed())
        );
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
        assertEquals(initialRolls.size(), updatedRolls.size());
    }
}
