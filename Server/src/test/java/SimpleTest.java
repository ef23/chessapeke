import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleTest {
    @Test
    public void multiplyByZeroShouldBeZero() {
        assertEquals("1 * 0 should be 0", 0, 1 * 0);
        assertEquals("0 * 0 should be 0", 0, 0 * 0);
    }
}
