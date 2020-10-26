import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRoom extends Remote {

    public void vote(String player) throws RemoteException;

<<<<<<< HEAD
    public void guessWord(String word) throws RemoteException;

    public boolean join(BasicPlayer basicPlayer) throws RemoteException;
=======
    public boolean join(Client client) throws RemoteException;
>>>>>>> Use Client instead of Player as Remote object

}
