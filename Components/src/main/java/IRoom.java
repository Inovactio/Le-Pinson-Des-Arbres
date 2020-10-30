import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRoom extends Remote {

    public void vote(String player) throws RemoteException;

    public boolean join(Client client) throws RemoteException;

    public boolean quit(Client client) throws RemoteException;

}
