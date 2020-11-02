import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface IServerGame  extends Remote{

    public boolean takeUsername(String username) throws RemoteException;

    public boolean createLobby(IClient client, int roomSize) throws RemoteException;

    public boolean connectToLobby(IClient client, String owner) throws RemoteException;

    public Set<RoomInfo> getLobbies() throws RemoteException;

}
