import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerGame  extends Remote{

    public boolean takeUsername(String username) throws RemoteException;

    public boolean createLobby(IClient client, int roomSize) throws RemoteException;

    public boolean connectToLobby(IClient client, IRoom room) throws RemoteException;

}
