import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRoom extends Remote {

    public boolean join(Player player) throws RemoteException;


}
