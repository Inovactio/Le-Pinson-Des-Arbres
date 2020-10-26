import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerGame  extends Remote{

    public boolean createLobby(Client client, int roomSize) throws RemoteException;

    public boolean connectToLobby(Client client, Room room) throws RemoteException;
}
