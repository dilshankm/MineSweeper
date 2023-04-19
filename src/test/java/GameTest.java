

import com.minesweeper.game.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {
    private Game game;

    @Mock
    private GameGrid mockGrid;

    @Before
    public void setUp() {
        game = new Game(9, 10, mockGrid);
    }


    @Test
    public void testInitialGameState() {
        assertEquals(GameState.PLAYING, game.getGameState());
    }

    @Test
    public void testRevealNonMineCell() {
        int row = 0;
        int col = 0;
        when(mockGrid.parseCoordinate("A1")).thenReturn(new Position(row, col));
        when(mockGrid.getCell(row, col)).thenReturn(new Cell(false, 0));
        int initialRevealedCount = game.getRevealedCount();
        game.selectSquare("A1");
        assertEquals(0, game.getRevealedCount());
    }

    @Test
    public void testRevealMineCell() {
        int row = 0;
        int col = 0;
        when(mockGrid.parseCoordinate("A1")).thenReturn(new Position(row, col));
        Cell mineCell = new Cell(false, 0);
        mineCell.setMine(true);
        when(mockGrid.getCell(row, col)).thenReturn(mineCell);
        game.selectSquare("A1");
        assertEquals(GameState.LOST, game.getGameState());
    }

    @Test
    public void testWinGame() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Position position = new Position(row, col);
                String input = Character.toString((char) (65 + row)) + (col + 1);
                when(mockGrid.parseCoordinate(input)).thenReturn(position);
                Cell cell = new Cell(false, 1);
                if (row + col > 0) {
                    cell.setMine(true);
                }
                when(mockGrid.getCell(row, col)).thenReturn(cell);
                game.selectSquare(input);
            }
        }
        assertEquals(GameState.LOST, game.getGameState());
    }

    @Test
    public void testSetGameState() {
        game.setGameState(GameState.WON);
        assertEquals(GameState.WON, game.getGameState());
    }
}
