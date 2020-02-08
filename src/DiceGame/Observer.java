package DiceGame;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;


enum MessageType {
    PLAYERINFO,
    ROUNDINFO
}

class Message {

    MessageType messageType;
    Player player;
    Player roundWinner;

    Message(Player player, Player roundWinner, MessageType messageType) {
        this.messageType = messageType;
        this.player = player;
        this.roundWinner = roundWinner;
    }
}


public class Observer {


    private Game observerGame;
    private boolean observe = true;
    private boolean observePlayer = false;
    ArrayDeque<Message> queue = new ArrayDeque<>();

    public Observer(Game observerGame) {
        this.observerGame = observerGame;
    }

    public synchronized void observeRound() {
        while (observe) {
            if (queue.size() < 1) {
                try {
                    wait();
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (queue.size() != 0) {
                Message message = queue.poll();
                if (message.messageType == MessageType.PLAYERINFO) {
                    printPlayerInfo(message);
                }
                if (message.messageType == MessageType.ROUNDINFO) {
                    printRoundInfo(message);
                }
            }
        }
    }

    private void printPlayerInfo(Message message) {
        System.out.println("Player" + message.player.getPlayerID()
                + " throws " + message.player.getLastRoundScore()
                + "  round leader: Player" + message.roundWinner.getPlayerID());
    }

    private void printRoundInfo(Message message) {
        System.out.println("winner - " + message.player.toString() + "\n\n");
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

    public void addMessage(Player player, Player roundWinner, MessageType messageType) {
        Message message = new Message(player, roundWinner, messageType);
        queue.add(message);
        wakePlayerObserver();
    }

    public Game getObserverGame() {
        return observerGame;
    }

    public boolean isObserve() {
        return observe;
    }

    public boolean isObservePlayer() {
        return observePlayer;
    }
}
