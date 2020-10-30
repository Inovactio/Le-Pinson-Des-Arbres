import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {

    public void getVote() throws RemoteException;
    
    public String getWrite() throws RemoteException;

    public String getGuess() throws RemoteException;

    public void setUsername(String username) throws RemoteException;

    public String getUsername() throws RemoteException;

}