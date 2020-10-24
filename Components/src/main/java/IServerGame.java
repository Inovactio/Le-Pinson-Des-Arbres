import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerGame  extends Remote{

    public void createLobby(Player player, int roomSize) throws RemoteException;

    public void connectToLobby(Player player, Room room) throws RemoteException;
}
