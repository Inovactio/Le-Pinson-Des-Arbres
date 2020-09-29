import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class ServerGame extends UnicastRemoteObject implements IServerGame {

    private static final long serialVersionUID = 1L;
    private Set<Room> rooms;

    public ServerGame() throws RemoteException {
        this.rooms = new HashSet<Room>();
    }

    public void vote(String player) {
        // TODO Auto-generated method stub

    }

    public void write(String word) {
        // TODO Auto-generated method stub

    }

    public void guessWord(String word) {
        // TODO Auto-generated method stub

    }

}
