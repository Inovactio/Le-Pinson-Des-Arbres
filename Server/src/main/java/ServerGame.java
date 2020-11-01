import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class ServerGame extends UnicastRemoteObject implements IServerGame {

    private static final long serialVersionUID = 1L;
    private Set<IRoom> rooms;
    private Set<String> usernames;

    public ServerGame() throws RemoteException {
        this.rooms = new HashSet<IRoom>();
        this.usernames = new HashSet<String>();
    }

    public synchronized boolean takeUsername(String username) throws RemoteException {
        return usernames.add(username);
    }

    @Override
    public boolean createLobby(IClient client, int roomSize) throws RemoteException {

        System.out.println("Création du lobby par le joueur : "+ client.getUsername());
        Room newRoom = new Room(client, roomSize);
        boolean res = rooms.add(newRoom);
        if (res) {
            client.setCurrentRoom(newRoom);
        }
        return res;
    }

    @Override
    public boolean connectToLobby(IClient client, IRoom room) throws RemoteException {
        if(!rooms.contains(room)) return false;
        return room.join(client);
    }
}
