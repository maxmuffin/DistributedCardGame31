package distributedLogic.net.remote;

import distributedLogic.net.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBroadcast extends Remote {
    // TODO
    void forward(Message msg) throws RemoteException;
}
