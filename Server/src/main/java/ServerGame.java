import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;

public class ServerGame extends UnicastRemoteObject implements IServerGame {

    private static final long serialVersionUID = 1L;
    private Map<String, IRoom> rooms;
    private UsernamesManager usernamesManager;
    

    public ServerGame() throws RemoteException {
        this.rooms = new ConcurrentHashMap<String, IRoom>();
        this.usernamesManager = new UsernamesManager();
    }

    public boolean takeUsername(String username) throws RemoteException {
        return usernamesManager.takeUsername(username);
    }

    public void freeUsername(String username) throws RemoteException {
        usernamesManager.freeUsername(username);
    }

    @Override
    public boolean createLobby(IClient client, int roomSize) throws RemoteException {

        String clientUsername = client.getUsername();
        System.out.println("Création du lobby par le joueur : "+ clientUsername);
        Room newRoom = new Room(client, roomSize, this);
        rooms.putIfAbsent(clientUsername, newRoom);
        if (rooms.containsKey(clientUsername)) {
            client.setCurrentRoom(newRoom);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<String> connectToLobby(IClient client, String owner) throws RemoteException, RoomInexistentException, RoomFullException, GameLaunchedException {
        if (!rooms.containsKey(owner)) throw new RoomInexistentException();
        Set<String> res = rooms.get(owner).join(client);
        client.setCurrentRoom(rooms.get(owner));
        return res;
    }

    @Override
    public Set<RoomInfo> getLobbies() throws RemoteException {
        Set res = new HashSet<RoomInfo>();

        for (Map.Entry<String, IRoom> entry : rooms.entrySet()) {
            res.add(new RoomInfo(entry.getValue()));
        }

        return res;
    }

    public void removeRoom(String owner) {
        rooms.remove(owner);
    }
}
