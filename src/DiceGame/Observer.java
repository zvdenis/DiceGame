package DiceGame;

public class Observer {

    private Game observerGame;
    private boolean sleeping = true;

    public Observer(Game observerGame){
        this.observerGame = observerGame;
    }

    public synchronized void observeRound() {
        while (!observerGame.isGameFinished()) {
            if (sleeping) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                sleeping = true;
                Player winner = observerGame.getPrevRoundWinner();
                System.out.println(winner.toString());
            }
        }
    }

    public void observeTurn() {

    }

    public synchronized void wakeObserver(){
        sleeping = false;
        this.notifyAll();
    }

}
