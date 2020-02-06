package tests;

import DiceGame.Game;
import DiceGame.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {


    static Player player;

    @BeforeEach
    void genGame() {
        Player.playerIDGenerator = 1;
        player = new Player(null);
    }


    @Test
    void getLastRoundScore() {
        assertEquals(0, player.getLastRoundScore());
        player.setLastRoundScore(-10);
        assertEquals(-10, player.getLastRoundScore());
    }

    @Test
    void setLastRoundScore() {
        assertEquals(0, player.getLastRoundScore());
        player.setLastRoundScore(-23);
        assertEquals(-23, player.getLastRoundScore());
    }

    @Test
    void getRoundsWon() {
        assertEquals(0, player.getRoundsWon());
        player.addRoundWon();
        assertEquals(1, player.getRoundsWon());
    }

    @Test
    void getPlayerID() {
        assertEquals(1, player.getPlayerID());
        player = new Player(null);
        assertEquals(2, player.getPlayerID());
        player = new Player(null);
        assertEquals(3, player.getPlayerID());
    }

    @Test
    void addRoundWon() {
        assertEquals(0, player.getRoundsWon());
        player.addRoundWon();
        assertEquals(1, player.getRoundsWon());
    }

    @Test
    void toString1() {
        assertEquals("Player1 score: 0 total rounds won:0", player.toString());
    }

    @Test
    void clone1() {
        Player player2 = player.clone();
        player.addRoundWon();
        assertNotEquals(player2.getRoundsWon(), player.getRoundsWon());
    }

    @Test
    void compareTo() {
        Player player2 = player.clone();
        player2.addRoundWon();
        assertEquals(1, player2.compareTo(player));
    }
}