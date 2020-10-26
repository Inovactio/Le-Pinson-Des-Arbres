import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerGame  extends Remote{

    public void createLobby(BasicPlayer basicPlayer, int roomSize) throws RemoteException;

    public void connectToLobby(BasicPlayer basicPlayer, Room room) throws RemoteException;
}
