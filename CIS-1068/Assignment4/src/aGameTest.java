import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class aGameTest {

	@Test
    public void testDetermineWinner() {
        assertEquals("win", aGame.determineWinner("Revskar", "Vittsjo"));
        assertEquals("win", aGame.determineWinner("Vittsjo", "Oxberg"));

        assertEquals("lose", aGame.determineWinner("Revskar", "Oxberg"));
        assertEquals("lose", aGame.determineWinner("Vittsjo", "Revskar"));

        assertEquals("lose", aGame.determineWinner("Revskar", "Revskar"));
    }
}