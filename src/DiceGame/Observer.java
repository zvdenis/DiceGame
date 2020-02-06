package DiceGame;

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
            if(observePlayer){
                observePlayer = false;
                System.out.println("Player" + observerGame.getCurrentPlayer().getPlayerID() + " throws " + observerGame.getCurrentPlayer().getLastRoundScore() + "  round leader: Player" + observerGame.getRoundWinner().getPlayerID());
            }
            if(!roundIsOn) {
                roundIsOn = true;
                Player winner = observerGame.getPrevRoundWinner();
                System.out.println("winner - " + winner.toString() + "\n\n");
            }
        }
    }

    public void observeTurn() {

    }

    public synchronized void wakeRoundObserver() {
        roundIsOn = false;
        this.notifyAll();
    }

    public synchronized void wakePlayerObserver() {
        observePlayer = true;
        this.notifyAll();
    }

    public void drawCurrentTable(){
        System.out.println("Leader - " + observerGame.getPrevRoundWinner().getPlayerID());

    }

    public void stopObserver(){
        observe = false;
    }
}
