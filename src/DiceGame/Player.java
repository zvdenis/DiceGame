package DiceGame;

public class Player {
    private static int playerIDGenerator = 1;
    private int lastRoundScore;
    private int roundsWon;
    private int playerID;
    private int lastPlayedRound = -1;
    private Game playerGame;

    Player() {

    }

    Player(Game game) {
        playerGame = game;
        playerID = playerIDGenerator++;
    }


    public void playGame() {
        while (!playerGame.isGameFinished()) {

            synchronized (playerGame) {
                if (playerGame.getRoundsPlayed() > lastPlayedRound) {
                    //System.out.print("");
                    lastPlayedRound++;
                    if (!playerGame.isRoundFinished()) {
                        playerGame.throwDice(this);
                    }
                }
            }
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

    public String toString() {
        return "Player" + playerID + " score: " + getLastRoundScore() + " total rounds won:" + roundsWon;
    }

    public Player clone() {
        Player cloned = new Player();
        cloned.playerID = this.playerID;
        cloned.roundsWon = this.roundsWon;
        cloned.lastRoundScore = this.lastRoundScore;
        return cloned;
    }

}
