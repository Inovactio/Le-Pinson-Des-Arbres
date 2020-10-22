import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerGame  extends Remote{

    public void write(String word) throws RemoteException;

    public void vote(String player) throws RemoteException;

    public void guessWord(String word) throws RemoteException;

    public void createLobby(Player player, int roomSize) throws RemoteException;

    public void connectToLobby(Player player, Room room) throws RemoteException;
}
