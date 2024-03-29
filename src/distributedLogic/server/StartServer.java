package distributedLogic.server;

import distributedLogic.Connection;
import distributedLogic.Player;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class StartServer {
    public static final int PORT = 1099;

    public static void main(String[] args) {
        final int seconds = Integer.parseInt(args[0]);
        final int maxPlayers = 8;
        final String serverAddress;


        try {
            System.out.println("Launching server... for " + seconds + " s");

            final Connection connection = new Connection(maxPlayers);
            LocateRegistry.createRegistry(PORT);
            serverAddress = InetAddress.getLocalHost().getHostAddress();
            final String rmiPath = "rmi://" + serverAddress + ":" + PORT + "/Server";
            Naming.rebind(rmiPath, connection);
            System.out.println("SERVER: Connection established, service is up.");
            System.out.println("SERVER: " + serverAddress);

            //THREAD
            Thread t = new Thread() {
                public void run() {
                    try {
                        sleep(seconds * 1000);
                        connection.endSigning();
                        Naming.unbind(rmiPath);
                        System.out.println("SERVER: Connection ended, service is down.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            };

            t.start();
            Player[] players = connection.getPlayers();

            if (connection.getPlayersNumber() > 0) {
                System.out.println("Players: ");
                for (int i = 0; i < connection.getPlayersNumber(); i++) {
                    Player player = players[i];
                    System.out.println("Player " + (i + 1) + " is " + player.getUsername() + " (" + player.getInetAddress().getHostAddress() + ":" + player.getPort() + ")");
                }
            }

            Thread.sleep(10 * 1000);
            System.out.println("STOP Server");
            System.exit(0);

        } catch (RemoteException e) {
            System.out.println("RemoteException");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
            e.printStackTrace();
        }
    }
}
