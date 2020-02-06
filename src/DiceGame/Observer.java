package DiceGame;

import java.util.ArrayList;

public class Observer {

    private Game observerGame;
    private boolean observe = true;
    private boolean roundIsOn = true;
    private boolean observePlayer = false;

    public Observer(Game observerGame) {
        this.observerGame = observerGame;
    }

    public synchronized void observeRound() {
        while (observe) {
            if (roundIsOn && !observePlayer) {
                try {
                    wait();
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (observePlayer) {
                observePlayer = false;
                System.out.println("Player" + observerGame.getCurrentPlayer().getPlayerID() + " throws " + observerGame.getCurrentPlayer().getLastRoundScore() + "  round leader: Player" + observerGame.getRoundWinner().getPlayerID());
            }
            if (!roundIsOn) {
                roundIsOn = true;
                Player winner = observerGame.getPrevRoundWinner();
                System.out.println("winner - " + winner.toString() + "\n\n");
            }
        }
    }


    public synchronized void wakeRoundObserver() {
        roundIsOn = false;
        this.notifyAll();
    }

    public synchronized void wakePlayerObserver() {
        observePlayer = true;
        this.notifyAll();
    }

    public String drawCurrentTable(ArrayList<Player> array) {
        String ans = "";
        for (int i = 0; i < array.size(); i++) {
            Player player = array.get(i);
            ans += "Player" + player.getPlayerID() +
                    " rounds won:" + player.getRoundsWon() + "\n";
        }
        return ans;
    }

    public void stopObserver() {
        observe = false;
        System.out.println("Leader - Player" + observerGame.getPrevRoundWinner().getPlayerID());
        System.out.println(drawCurrentTable(observerGame.getPlayers()));
    }


    public Game getObserverGame() {
        return observerGame;
    }

    public boolean isObserve() {
        return observe;
    }

    public boolean isRoundIsOn() {
        return roundIsOn;
    }

    public boolean isObservePlayer() {
        return observePlayer;
    }
}
