import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRoom extends Remote {

    public void sendVote(String imposter, String mrWhite) throws RemoteException;

    public void sendWord(String word) throws RemoteException;

    public void sendGuess(IClient client, String word) throws RemoteException;

    public List<String> join(IClient client) throws RemoteException, GameLaunchedException, RoomFullException;

    public void quit(IClient client) throws RemoteException;

    public void launchGame() throws RemoteException;

    public void changeSettings(int turnTime, int nbWords, int nbRounds, int nbImpostors) throws RemoteException;

    public void fill() throws RemoteException;

    public String getOwner() throws RemoteException;

    public int getNbPlayers() throws RemoteException;

    public int getRoomSize() throws RemoteException;

}
