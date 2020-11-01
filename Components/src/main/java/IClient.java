import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface IClient extends Remote {

    public void refreshLobby(Set<String> players) throws RemoteException;

    //public void getVote() throws RemoteException;
    
    public String getWord() throws RemoteException, InterruptedException;

    //public String getGuess() throws RemoteException;

    public void giveWord(String word) throws RemoteException;

    public void setCurrentRoom(IRoom room) throws RemoteException;

    public String getUsername() throws RemoteException;

}