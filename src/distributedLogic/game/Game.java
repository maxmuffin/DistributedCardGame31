package distributedLogic.game;

import distributedLogic.Node;
import distributedLogic.Player;
import distributedLogic.Utils;
import distributedLogic.net.messages.GameMessage;

import java.util.ArrayList;

import java.util.List;

public class Game {

    private Deck openDeck;
    private Deck coveredDeck;
    private Hand hand;
    //private List<Boolean> alivePlayers;
    private Player[] players;
    private int currentPlayer = 0;
    private int myId;
    private Move currentMove = null, myMove;
    // TODO other variables
    private boolean saidBusso = false;
    private boolean gameOver;

    public Game(Card uncoveredCard, Deck covered, Hand hand, Player[] players, int myId) {
        this.openDeck = new Deck();
        this.openDeck.putCardOnTop(uncoveredCard);
        this.coveredDeck = covered;
        this.hand = hand;
        this.players = players;
        this.myId = myId;
        this.gameOver = false;

        //this.alivePlayers = Utils.setArraylist(players.length, true);
        // Collections.fill(alivePlayers, Boolean.TRUE);
    }

    public void init() {

    }

    public void update(GameMessage m, int whoMystGoOn) {

    }

    public Player[] getPlayers() {
        return players;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer() {
        this.currentPlayer = nextPlayer(currentPlayer);
    }

    public void setCurrentPlayer(int id) {
        this.currentPlayer = id;
    }


    public boolean isGameOver() {
        return gameOver;
    }

    public Move myTurn() {

        myMove = new Move();

        // Il giocatore pesca e scarta la carta, e puoi bussare
        // TODO logica turno

        setCurrentPlayer();


        if (myMove.isBusso()) {
            players[myId].saidBusso();
        }
        return myMove;
    }

    private int nextPlayer(int currentPlayer) {
        int nextPlayer = -1;
        int current = (currentPlayer + 1) % players.length;
        // TODO
        do {
            nextPlayer = current;
            current = (nextPlayer + 1) % players.length;
        } while (!players[nextPlayer].isActive());
        return nextPlayer;
    }

    public void updateAlivePlayers(List<Boolean> newAlivePlayers) {
        List<Boolean> opponents = ((List) ((ArrayList) newAlivePlayers).clone());
        opponents.set(myId, false);

        if (!opponents.contains(true)) {
            gameOver = true;
            // TODO gui update
        } else {
            // at least one opponent is alive => find changes
            List<Boolean> tmpmap = ((List) ((ArrayList) newAlivePlayers).clone());
            if (!tmpmap.contains(true)) {
                // conti i giocatori che hanno fatto crash
                int numberOfCrash = 0;
                for (int i = 0; i < players.length; i++) {
                    if (tmpmap.get(i)) {
                        numberOfCrash++;
                    }
                }
                System.out.println("Giocatori crash: " + numberOfCrash);
                // TODO update gui (rimuovere i giocatori che hanno fatto crash)
            }
        }
        //alivePlayers = newAlivePlayers;
        System.out.println("GAME: Alive players map is " + newAlivePlayers);
    }

    public void updateCrash(int nodeCrashed) {
        //alivePlayers.set(nodeCrashed, false);
    }

    public void updateAnyCrash(Node[] nodes, int myId) {
        boolean crash = true;
        int i = (myId + 1) % nodes.length;

        while (crash) {
            if (!nodes[i].isActive()) {
                //this.alivePlayers.set(i, false);
                i = (myId + 1) % nodes.length;
            } else {
                crash = false;
            }
        }
    }

}
