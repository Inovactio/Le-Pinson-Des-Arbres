import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface IRoom extends Remote {

    public void sendVote(String player) throws RemoteException;

    public void sendWord(String word) throws RemoteException;

    public Set<String> join(IClient client) throws RemoteException, GameLaunchedException, RoomFullException;

    public void quit(IClient client) throws RemoteException;

    public void launchGame() throws RemoteException;

    public void changeSettings(int turnTime, int nbWords, int nbRounds, int nbImpostors) throws RemoteException;

    public String getOwner() throws RemoteException;

    public int getNbPlayers() throws RemoteException;

    public int getRoomSize() throws RemoteException;

}
