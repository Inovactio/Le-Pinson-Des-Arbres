import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

public interface IClient extends Remote {

    public void giveLobbyUpdate(List<String> update) throws RemoteException;

    public void giveLobbyUpdate(int turnTime, int nbWords, int nbRounds, int nbImpostors) throws RemoteException;

    //public void requestVote() throws RemoteException;
    
    public void requestWord() throws RemoteException, InterruptedException;

    //public String requestGuess() throws RemoteException;

    public void init(String word, Role role, List<String> players) throws RemoteException;

    public void setCurrentRoom(IRoom room) throws RemoteException;

    public void kick() throws RemoteException;

    public String getUsername() throws RemoteException;

}