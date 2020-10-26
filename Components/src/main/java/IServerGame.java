import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerGame  extends Remote{

<<<<<<< HEAD
    public void createLobby(BasicPlayer basicPlayer, int roomSize) throws RemoteException;

    public void connectToLobby(BasicPlayer basicPlayer, Room room) throws RemoteException;
=======
    public boolean createLobby(Client client, int roomSize) throws RemoteException;

    public boolean connectToLobby(Client client, Room room) throws RemoteException;
>>>>>>> Use Client instead of Player as Remote object
}
