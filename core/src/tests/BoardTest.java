import com.skyteam.logic.*;
import com.skyteam.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BoardTest {

    private Board board;
    private Player mockPlayer;
    private Task mockTask;

    @BeforeEach
    public void setUp() throws Exception {
        mockPlayer = mock(Player.class);
        mockTask = mock(Task.class);
        board = new Board(Plan.MONTREAL);
    }

    @Test
    public void testInitialBoardState() {
        assertNotNull(board.getFlightPlan());
        assertEquals(0, board.getCoffee());
        assertEquals(0, board.getCurrentPosition());
        assertEquals(0, board.getCurrentAxis());
        assertEquals(0, board.getCurrentSpeed());
    }

    @Test
    public void testSetAndGetCoffee() {
        board.setCoffee(3);
        assertEquals(3, board.getCoffee());
    }

    @Test
    public void testSetAndGetRerollToken() {
        board.addRerollToken();
        assertEquals(1, board.getReroll());
        board.useRerollToken();
        assertEquals(0, board.getReroll());
    }

    @Test
    public void testGetAvailableTasks() {
        ArrayList<Task> availableTasks = board.getAvailableTasks(new HashSet<>(List.of(Role.PILOT)));
        assertNotNull(availableTasks);
        assertFalse(availableTasks.isEmpty());
    }

    @Test
    public void testContributeToInvalidTask() {
        when(mockPlayer.getRole()).thenReturn(Role.PILOT);
        Dice mockDice = mock(Dice.class);
        when(mockPlayer.getDice()).thenReturn(mockDice);
        when(mockTask.isObligatory()).thenReturn(false);
        when(mockTask.isRoleAllowed(anySet())).thenReturn(false);
        when(mockDice.getRolledValues()).thenReturn(new ArrayList<>(List.of(4)));
        boolean result = board.contributeToTask(4, mockTask, mockPlayer);
        assertFalse(result);
    }


    @Test
    public void testResetRoundSpecificTasks() {
        board.resetRoundSpecificTasks();
        board.getAvailableTasks(new HashSet<>(List.of(Role.PILOT))).forEach(task -> assertFalse(task.isUsed()));
    }

    @Test
    public void testSetAndGetSpeedBorders() {
        board.setMaxSpeedBorder(10);
        board.setMinSpeedBorder(3);
        assertEquals(10, board.getMaxSpeedBorder());
        assertEquals(3, board.getMinSpeedBorder());
    }

    @Test
    public void testSetAndGetCurrentAxis() {
        board.setCurrentAxis(5);
        assertEquals(5, board.getCurrentAxis());
    }

    @Test
    public void testSetAndGetCurrentSpeed() {
        board.setCurrentSpeed(7);
        assertEquals(7, board.getCurrentSpeed());
    }

    @Test
    public void testBrakeStrength() {
        board.setBrakeStrenght(2);
        assertEquals(2, board.getBrakeStrenght());
    }
}
