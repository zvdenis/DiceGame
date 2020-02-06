package tests;

import DiceGame.Game;
import DiceGame.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    static Game game;

    @BeforeEach
    void genGame() {
        game = new Game(3, 4, 5);
    }

    @Test
    void getN() {
        Assertions.assertEquals(3, game.getN());
    }

    @Test
    void getK() {
        Assertions.assertEquals(4, game.getK());
    }

    @Test
    void getM() {
        Assertions.assertEquals(5, game.getM());
    }

    @Test
    void constructorGame() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(1, 2, 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game('a', 2, 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(1, 'b', 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(1, 2, 'c');
        });
    }

    @Test
    void testGeneratePlayers() {
        game = new Game(6, 2, 3);
        game.generatePlayers();
        ArrayList<Player> array = game.getPlayers();
        array.get(0).setLastRoundScore(-11);
        for (int i = 1; i < array.size(); i++) {
            assertNotEquals(-11, array.get(i).getLastRoundScore());
            array.get(i).setLastRoundScore(-11);
        }
        assertEquals(6, array.size());
    }

    @Test
    void testIsRoundFinished(){
        assertEquals(false, game.isRoundFinished());
        game.setPlayersParticipated(3);
        assertEquals(true, game.isRoundFinished());
        assertEquals(3, game.getPlayersParticipated());
    }
}
