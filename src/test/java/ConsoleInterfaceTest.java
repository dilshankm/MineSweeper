import com.minesweeper.game.Game;
import com.minesweeper.game.GameState;
import com.minesweeper.messages.Messages;
import com.minesweeper.ui.console.ConsoleInterface;
import com.minesweeper.ui.console.ScannerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Scanner;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleInterfaceTest {

    private ConsoleInterface consoleInterface;

    @Mock
    private ScannerFactory scannerFactory;

    @Mock
    private Game game;

    private ByteArrayOutputStream outContent;


    @Before
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        consoleInterface = new ConsoleInterface(scannerFactory) {
            @Override
            protected void print(String message) {
                System.out.print(message);
            }

            @Override
            protected void println(String message) {
                System.out.println(message);
            }
        };
    }

    @After
    public void tearDown() {
        outContent.reset();
    }

    @Test
    public void testDisplayWelcomeMessage() {
        consoleInterface.displayWelcomeMessage();
        assertEquals(Messages.WELCOME_MESSAGE, outContent.toString().trim());
    }


    @Test
    public void testGetGridSize() {
        String input = "4\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inContent);
        consoleInterface.setScanner(testScanner);
        assertEquals(4, consoleInterface.getGridSize());
    }


    @Test
    public void testGetNumberOfMines() {
        String input = "5\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inContent);
        consoleInterface.setScanner(testScanner);
        assertEquals(5, consoleInterface.getNumberOfMines(4));
    }

    @Test
    public void testDisplayGame() {
        when(game.toString()).thenReturn("");
        when(game.getRevealedCount()).thenReturn(0);
        consoleInterface.displayGame(game);
        assertEquals(Messages.MINEFIELD_MESSAGE.trim(), outContent.toString().trim());
    }

    @Test
    public void testDisplaySquareInfo() {
        consoleInterface.displaySquareInfo(game, 1, 1);
        assertEquals(Messages.SQUARE_INFO_FORMAT, outContent.toString().trim());
    }

    @Test
    public void testDisplayGameState() {
        when(game.isGameWon()).thenReturn(true);
        consoleInterface.displayGameState(game);
        assertEquals(Messages.WON_MESSAGE, outContent.toString().trim());
    }

    @Test
    public void testPlayAgainPrompt() {
        String input = "\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inContent);
        consoleInterface.setScanner(testScanner);
        consoleInterface.playAgainPrompt();
        assertEquals(Messages.PLAY_AGAIN_MESSAGE, outContent.toString().trim());
    }

    private void setInput(String input) {
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inContent);
    }


}
