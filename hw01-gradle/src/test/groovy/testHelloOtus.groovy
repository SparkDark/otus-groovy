
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.assertEquals


class Hw01 {
    @BeforeAll
    static void init() {
      println("Doing Hw01");
    }

    @DisplayName("\uD83D\uDC4D")
    @Test
    void testHWOne() {
        assertEquals(true, true);
    }
}