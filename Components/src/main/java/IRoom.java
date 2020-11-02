import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRoom extends Remote {

    public void vote(String player) throws RemoteException;

    public boolean join(IClient client) throws RemoteException;

    public boolean quit(IClient client) throws RemoteException;

    public String getOwner() throws RemoteException;

    public int getNbPlayers() throws RemoteException;

    public int getRoomSize() throws RemoteException;

}
