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

    public void vote(String player) throws RemoteException {
        // TODO Auto-generated method stub

    }

    public void write(String word) throws RemoteException {
        // TODO Auto-generated method stub

    }

    public void guessWord(String word) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void createLobby(Player creator,int roomSize) throws RemoteException {
        System.out.println("Création du lobby par le joueur : "+creator.getName());
        Room newRoom = new Room(creator,roomSize);
        rooms.add(newRoom);
    }

    @Override
    public void connectToLobby(Player player, Room room) throws RemoteException {
        if(!rooms.contains(room)) return;
        room.join(player);
        player.setCurrentRoom(room);
    }
}
