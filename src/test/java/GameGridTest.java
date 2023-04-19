

import com.minesweeper.exceptions.InvalidInputException;
import com.minesweeper.game.GameGrid;
import com.minesweeper.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameGridTest {
    private GameGrid gameGrid;

    @BeforeEach
    public void setUp() {
        gameGrid = new GameGrid(10, 10);
    }

    @Test
    public void testGetSize() {
        assertEquals(10, gameGrid.getSize());
    }

    @Test
    public void testIsInsideGrid() {
        assertTrue(gameGrid.isInsideGrid(0, 0));
        assertTrue(gameGrid.isInsideGrid(9, 9));
        assertFalse(gameGrid.isInsideGrid(-1, 0));
        assertFalse(gameGrid.isInsideGrid(0, -1));
        assertFalse(gameGrid.isInsideGrid(10, 0));
        assertFalse(gameGrid.isInsideGrid(0, 10));
    }

    @Test
    public void testParseCoordinate() {
        assertThrows(InvalidInputException.class, () -> gameGrid.parseCoordinate(""));
        assertThrows(InvalidInputException.class, () -> gameGrid.parseCoordinate("a"));
        assertThrows(InvalidInputException.class, () -> gameGrid.parseCoordinate("1"));
        assertThrows(InvalidInputException.class, () -> gameGrid.parseCoordinate("A-1"));

        Position position = gameGrid.parseCoordinate("A1");
        assertEquals(0, position.row());
        assertEquals(0, position.col());

        position = gameGrid.parseCoordinate("J10");
        assertEquals(9, position.row());
        assertEquals(9, position.col());
    }

}
