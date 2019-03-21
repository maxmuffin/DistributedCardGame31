package project.server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.client.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ChatServer extends UnicastRemoteObject implements ChatInterface {

    private static final long serialVersionUID = 1L;
    private Map<String, Client> users;

    private ObservableList<String> usersList = FXCollections.<String>observableArrayList();

    public ChatServer() throws RemoteException {
        users = new HashMap<String, Client>();
    }
//
//    @Override
//    public boolean loginPossible(String username) throws RemoteException {
//        // restituisce False se l'username è già presente
//        return !this.users.containsKey(username);
//
//    }
//
//    @Override
//    public synchronized void addUser(String username, StartClient client) throws RemoteException {
//        this.users.put(username, client);
//        // Comunico a tutti gli utenti un nuovo ingresso
//        Collection<StartClient> clients = users.values();
//        for (StartClient c : clients) {
//            if (c != client) {
//                c.receive('\"' + username + '\"' + " has joined the channel");
//            }
//        }
//        registryListner(username);
//    }
//
//    @Override
//    public void logOut(String username) throws RemoteException {
//        this.users.remove(username);
//
//    }
//
//    @Override
//    public synchronized void broadcastMessage(String username, String message) {
//        String msg = "[" + new Date().toString() + "] " + username + ": " + message;
//
//        boolean infoClient = false;
//
//        for (Iterator<Map.Entry<String, StartClient>> it = this.users.entrySet().iterator(); it.hasNext(); ) {
//            try {
//                it.next().getValue().receive(message);
//            } catch (RemoteException e) {
//                System.out.println("Remote Exception: " + e.getMessage());
//                it.remove();
//                infoClient = true;
//            }
//        }
//
//    }

    public void registryListner(String details) throws RemoteException {
        System.out.println( new Date()+" -> "+ details+" unito alla sessione");
        usersList.add(details);

    }

    public ObservableList<String> printUsers(){
        return usersList;
    }

    public ObservableList<String> getUsersList() {
        return usersList;
    }

    @Override
    public boolean login(String username) throws RemoteException {
        return false;
    }

    @Override
    public void logout(String username) throws RemoteException {

    }

    @Override
    public void sendMessage(Message message) throws RemoteException {

    }

    @Override
    public List<Message> getAllMesage() throws RemoteException {
        return null;
    }

    @Override
    public List<String> getAllUsers() throws RemoteException {
        return null;
    }
}
