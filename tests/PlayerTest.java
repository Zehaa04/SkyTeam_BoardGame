import model.Board;
import model.Player;
import model.Role;
import model.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerTest {

    private Player player;
    private Task mockTask;
    private Board mockBoard;

    @BeforeEach
    public void setUp() throws Exception {
        mockBoard = mock(Board.class);
        player = new Player(Role.PILOT);

        Field boardField = Player.class.getDeclaredField("board");
        boardField.setAccessible(true);
        boardField.set(player, mockBoard);

        mockTask = mock(Task.class);
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
        when(mockBoard.getCoffee()).thenReturn(1);

        player.rollDice(1);
        player.useCoffee(0, 1);

        verify(mockBoard, times(1)).setCoffee(0);
    }

    @Test
    public void testUseCoffeeNoCoffeeLeft() {
        when(mockBoard.getCoffee()).thenReturn(0);
        assertThrows(IllegalStateException.class, () -> player.useCoffee(0, 1));
    }

    @Test
    public void testContributeToTask() {
        when(mockBoard.contributeToTask(anyInt(), eq(mockTask), eq(player))).thenReturn(true);
        boolean result = player.contributeToTask(4, mockTask);
        assertTrue(result);
        verify(mockBoard, times(1)).contributeToTask(4, mockTask, player);
    }

    @Test
    public void testUseRerollToken() {
        when(mockBoard.getReroll()).thenReturn(1);
        player.rollDice(4);

        List<Integer> initialValues = player.getDice().getRolledValues();
        Integer[] valuesToReroll = {initialValues.get(0), initialValues.get(1)};

        List<Integer> updatedValues = player.useRerollToken(valuesToReroll);

        assertEquals(4, player.getDice().getRolledValues().size());
        assertTrue(player.getDice().getRolledValues().containsAll(updatedValues));
        verify(mockBoard, times(1)).useRerollToken();
    }

    @Test
    public void testUseRerollTokenNoTokens() {
        when(mockBoard.getReroll()).thenReturn(0);
        assertThrows(IllegalStateException.class, () -> player.useRerollToken(1, 2));
    }

    @Test
    public void testUseRerollTokenInvalidNumber() {
        when(mockBoard.getReroll()).thenReturn(1);
        player.rollDice(4);

        assertThrows(IllegalArgumentException.class, () -> player.useRerollToken(10, 5));

        List<Integer> initialValues = player.getDice().getRolledValues();
        assertThrows(IllegalArgumentException.class, () -> player.useRerollToken(initialValues.getFirst(), initialValues.get(1), initialValues.get(2), initialValues.get(3), 6));
    }

}
