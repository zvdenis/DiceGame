package DiceGame;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private static Random random = new Random();
    public static final int nMin = 2;
    public static final int nMax = 6;
    public static final int kMin = 2;
    public static final int kMax = 5;
    public static final int mMin = 1;
    public static final int mMax = 100;
    private int n;
    private int k;
    private int m;
    private int roundsPlayed = 0;
    private int playersParticipated;

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Thread> threads = new ArrayList<>();
    private Player roundWinner;
    private boolean diceFree = true;
    //private boolean gameFinished;

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
    }

    public void generatePlayers() {
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
        System.out.println(player.getPlayerID() + " " + score);

        if(isRoundFinished()){
            roundsPlayed++;
            playersParticipated = 0;
            roundWinner.addRoundWon();
        }
    }

    public void startGame() {
        for (int i = 0; i < getPlayers().size(); i++) {
            Thread thread = new Thread(getPlayers().get(i)::playGame);
            threads.add(thread);
            thread.start();
        }
        while (!isGameFinished()) {

        }
    }

//    private void playRound() {
//        while (!isRoundFinished()) {
//            System.out.print("");
//        }
//        roundsPlayed++;
//        playersParticipated = 0;
//        roundWinner.addRoundWon();
//    }

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

    public boolean isDiceFree() {
        return diceFree;
    }

    public boolean isRoundFinished() {
        return playersParticipated == getN()
                || roundWinner.getLastRoundScore() == getK() * 6;
    }

    public boolean isGameFinished() {
        return roundsPlayed >= getN();
    }
}
