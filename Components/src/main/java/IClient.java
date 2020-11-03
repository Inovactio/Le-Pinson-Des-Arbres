import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface IClient extends Remote {

    public void giveLobbyUpdate(Set<String> update) throws RemoteException;

    //public void requestVote() throws RemoteException;
    
    public void requestWord() throws RemoteException, InterruptedException;

    //public String requestGuess() throws RemoteException;

    public void init(String word, Role role, Set<String> players) throws RemoteException;

    public void setCurrentRoom(IRoom room) throws RemoteException;

    public String getUsername() throws RemoteException;

}