package DiceGame;

public class Player {
    private static int playerIDGenerator = 1;
    private int lastRoundScore;
    private int roundsWon;
    private int playerID;
    private int lastPlayedRound = -1;
    private Game playerGame;


    Player(Game game) {
        playerGame = game;
        playerID = playerIDGenerator++;
    }


    public void playGame() {
        while (!playerGame.isGameFinished()) {
            playerGame.throwDice(this);
//            if (playerGame.getRoundsPlayed() > lastPlayedRound) {
//                lastPlayedRound++;
//                if (!playerGame.isRoundFinished()) {
//
//                }
//            }
        }
    }

    public int getLastRoundScore() {
        return lastRoundScore;
    }

    public void setLastRoundScore(int score) {
        lastRoundScore = score;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void addRoundWon() {
        roundsWon++;
    }
}
