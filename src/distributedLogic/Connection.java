package distributedLogic;

import distributedLogic.remote.IPartecipant;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Connection extends UnicastRemoteObject implements IConnection {

    private Player[] players;
    private IPartecipant[] partecipants;
    private int playersMaxNo;
    private int playersNumber = 0;
    private boolean acceptPartecipants = true;


    public Connection(int playersMaxNumber) throws RemoteException {
        this.playersMaxNo = playersMaxNumber;
        this.players = new Player[playersMaxNumber];
        this.partecipants = new IPartecipant[playersMaxNumber];
    }

    public synchronized boolean subscribe(IPartecipant partecipant, Player player) {
        if (playersNumber < playersMaxNo && acceptPartecipants) {
//            if (isDuplicated(player, players)) {
//                System.out.println("CONNECTION: " + "duplicated player " + player);
//                return false;
//            }
            System.out.println("CONNECTION: " + "new player " + player);
            partecipants[playersNumber] = partecipant;
            players[playersNumber] = player;
            playersNumber++;

            if (playersNumber == playersMaxNo) {
                acceptPartecipants = false;
                sendJoin();
                notify();
            }
            return true;
        }
        return false;
    }

    public synchronized void endSigning() {
        if (acceptPartecipants) {
            acceptPartecipants = false;
            sendJoin();
            notify();
        }
    }


    private boolean isDuplicated(Player target, Player[] players) {
        for (int i = 0; i < players.length; i++)
            if (target.compareTo(players[i]) == 0)
                return true;
        return false;
    }

    public synchronized Player[] getPlayers() {
        if (acceptPartecipants)
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        return players;
    }

    public synchronized int getPlayersNumber() {
        if (acceptPartecipants)
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        return playersNumber;
    }

    private void sendJoin() {
        final Player[] readyPlayers = new Player[playersNumber];
        System.arraycopy(players, 0, readyPlayers, 0, playersNumber);
        players = readyPlayers;

        for (int i = 0; i < playersNumber; i++) {
            final IPartecipant p = partecipants[i];
            final int j = i;
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println("ANNOUNCE: " + "Configuring partecipant " + j + ": " + readyPlayers[j] + "... ");
                        p.configure(players);
                        System.out.println("ANNOUNCE: " + "Configuring partecipant " + j + ": " + readyPlayers[j] + "... done.");
                    } catch (RemoteException e) {
                        System.out.println("REMOTE EXCEPTION: " + e.getMessage());
                    }
                }
            };
            t.start();
        }
    }
}