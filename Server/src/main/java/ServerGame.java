import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.List;
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

    /**
     * take an usernamer.
     * @param username is the username chose
     * @return true if the username can be used, else false.
     * @throws RemoteException
     */
    public boolean takeUsername(String username) throws RemoteException {
        return usernamesManager.takeUsername(username);
    }

    /**
     * free an username
     * @param username is the username freed.
     * @throws RemoteException
     */
    public void freeUsername(String username) throws RemoteException {
        usernamesManager.freeUsername(username);
    }

    /**
     * create a lobby by a client
     * @param client who created the lobby
     * @param roomSize size of the lobby
     * @return true if the lobby is created, else false
     * @throws RemoteException
     */
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

    /**
     * connect a client to a lobby owned by someone
     * @param client to add in the lobby
     * @param owner of the lobby
     * @return list of clients from the lobby
     * @throws RemoteException
     * @throws RoomInexistentException if the lobby doesn't exists
     * @throws RoomFullException if the lobby is full
     * @throws GameLaunchedException if the game is already launched
     */
    @Override
    public List<String> connectToLobby(IClient client, String owner) throws RemoteException, RoomInexistentException, RoomFullException, GameLaunchedException {
        if (!rooms.containsKey(owner)) throw new RoomInexistentException();
        List<String> res = rooms.get(owner).join(client);
        client.setCurrentRoom(rooms.get(owner));
        return res;
    }

    /**
     * get all lobbies
     * @return a set of lobby.
     * @throws RemoteException
     */
    @Override
    public Set<RoomInfo> getLobbies() throws RemoteException {
        Set res = new HashSet<RoomInfo>();

        for (Map.Entry<String, IRoom> entry : rooms.entrySet()) {
            res.add(new RoomInfo(entry.getValue()));
        }

        return res;
    }

    /**
     * Remove the room of a owner
     * @param owner of the room
     */
    public void removeRoom(String owner) {
        rooms.remove(owner);
    }
}
