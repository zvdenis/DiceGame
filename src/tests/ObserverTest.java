package tests;

import DiceGame.Observer;
import DiceGame.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ObserverTest {
    static Observer observer;

    @BeforeEach
    void genGame() {
        Player.playerIDGenerator = 1;
        observer = new Observer(null);
    }

    @Test
    void wakeRoundObserver() {
        observer.wakeRoundObserver();
        assertEquals(false, observer.isRoundIsOn());
    }

    @Test
    void wakePlayerObserver() {
        observer.wakePlayerObserver();
        assertEquals(true, observer.isObservePlayer());
    }

    @Test
    void drawCurrentTable() {
        ArrayList<Player> array = new ArrayList<Player>();
        array.add(new Player(null));
        array.add(new Player(null));
        array.add(new Player(null));
        array.get(0).addRoundWon();
        array.get(1).addRoundWon();
        array.get(1).addRoundWon();
        array.get(2).addRoundWon();
        array.get(2).addRoundWon();
        array.get(2).addRoundWon();
        assertEquals("Player1 rounds won:1\n" +
                "Player2 rounds won:2\n" +
                "Player3 rounds won:3\n", observer.drawCurrentTable(array));
        //assertEquals();
    }

    @Test
    void stopObserver() {
        try {
            observer.stopObserver();
        } catch (Exception ex) {

        }
        assertEquals(false, observer.isObserve());
    }
}