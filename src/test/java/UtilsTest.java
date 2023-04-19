import com.minesweeper.utils.Utils;
import org.junit.Test;
import static org.junit.Assert.*;

public class UtilsTest {
    @Test
    public void testIsNumeric() {
        assertTrue(Utils.isNumeric("12345"));
        assertFalse(Utils.isNumeric("12a34"));
        assertFalse(Utils.isNumeric("abc"));
    }

}
