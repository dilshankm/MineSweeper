

import com.minesweeper.game.Cell;
import org.junit.Test;
import static org.junit.Assert.*;

public class CellTest {

    @Test
    public void testCell() {
        Cell cell = new Cell(false, 0);

        assertFalse(cell.isMine());
        assertFalse(cell.isRevealed());
        assertEquals(0, cell.getAdjacentMines());

        cell.setMine(true);
        cell.setRevealed(true);
        cell.setAdjacentMines(3);

        assertTrue(cell.isMine());
        assertTrue(cell.isRevealed());
        assertEquals(3, cell.getAdjacentMines());
    }

    @Test
    public void testToString() {
        Cell cell1 = new Cell(false, 0);
        Cell cell2 = new Cell(true, 0);
        Cell cell3 = new Cell(false, 2);

        assertEquals("_", cell1.toString());
        assertEquals("_", cell2.toString());
        assertEquals("_", cell3.toString());

        cell1.setRevealed(true);
        cell2.setRevealed(true);
        cell3.setRevealed(true);

        assertEquals("0", cell1.toString());
        assertEquals("*", cell2.toString());
        assertEquals("2", cell3.toString());
    }
}
