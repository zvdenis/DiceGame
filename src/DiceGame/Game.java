package DiceGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Game {

    private static Random random = new Random();
    private static final int nMin = 2;
    private static final int nMax = 6;
    private static final int kMin = 2;
    private static final int kMax = 5;
    private static final int mMin = 1;
    private static final int mMax = 100;
    private int n;
    private int k;
    private int m;
    private int roundsPlayed = 0;
    private int playersParticipated;

    private ArrayList<Player> players = new ArrayList<>();
    //private ArrayList<Thread> threads = new ArrayList<>();
    private Player roundWinner;
    private Player prevRoundWinner;
    private Player currentPlayer;
    private Observer observer;
    private boolean diceFree = true;
    private boolean gameFinished;

    public Game(int N, int K, int M) {
        if (N > nMax || N < nMin
                || K > kMax || K < kMin
                || M > mMax || M < mMin) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        this.n = N;
        this.k = K;
        this.m = M;
        generatePlayers();
        roundWinner = players.get(0);
        roundWinner.setLastRoundScore(-11);
    }

    public void generatePlayers() {
        getPlayers().clear();
        for (int i = 0; i < getN(); i++) {
            getPlayers().add(new Player(this));
        }
    }

    public synchronized void throwDice(Player player) {
        int score = 0;
        playersParticipated++;
        for (int i = 0; i < getK(); i++) {
            score += random.nextInt(6) + 1;
        }

        player.setLastRoundScore(score);
        if (score > roundWinner.getLastRoundScore()) {
            roundWinner = player;
        }
        currentPlayer = player.clone();
        observer.wakePlayerObserver();
        //System.out.println(player.getPlayerID() + " " + score);

        if (isRoundFinished()) {
            roundWinner.addRoundWon();
            prevRoundWinner = roundWinner.clone();
            observer.wakeRoundObserver();
            roundsPlayed++;
            playersParticipated = 0;
            roundWinner.setLastRoundScore(-11);
            if(roundWinner.getRoundsWon() >= getM()){
                gameFinished = true;
                finishGame();
            }
        }
    }

    public void startGame() {

        observer = new Observer(this);
        Thread observerThread = new Thread((observer)::observeRound);
        observerThread.setPriority(10);
        observerThread.start();

        for (int i = 0; i < getPlayers().size(); i++) {
            Thread thread = new Thread(getPlayers().get(i)::playGame);
            //threads.add(thread);
            thread.setPriority(1);
            thread.start();
        }
        waitUntilGameIsOver();
    }

    private synchronized void waitUntilGameIsOver() {
        while (!isGameFinished()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void finishGame() {


        Collections.sort(players, Collections.reverseOrder());
        notifyAll();
        observer.stopObserver();

    }

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    public int getM() {
        return m;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public Player getPrevRoundWinner() {
        return prevRoundWinner;
    }


    public boolean isDiceFree() {
        return diceFree;
    }

    public boolean isRoundFinished() {
        return playersParticipated == getN()
                || roundWinner.getLastRoundScore() == getK() * 6;
    }

    public boolean isGameFinished() {

        return gameFinished;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getPlayersParticipated() {
        return playersParticipated;
    }

    public void setPlayersParticipated(int x) {
        playersParticipated = x;
    }

    public Player getRoundWinner() {
        return roundWinner;
    }

}
