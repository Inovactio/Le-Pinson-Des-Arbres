import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRoom extends Remote {

    public void write(String word) throws RemoteException;

    public void vote(String player) throws RemoteException;

    public void guessWord(String word) throws RemoteException;

    public boolean join(BasicPlayer basicPlayer) throws RemoteException;

}
