import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

public interface IServerGame  extends Remote{

    public boolean takeUsername(String username) throws RemoteException;

    public void freeUsername(String username) throws RemoteException;

    public boolean createLobby(IClient client, int roomSize) throws RemoteException;

    public List<String> connectToLobby(IClient client, String owner) throws RemoteException, RoomInexistentException, RoomFullException, GameLaunchedException;

    public Set<RoomInfo> getLobbies() throws RemoteException;

}
