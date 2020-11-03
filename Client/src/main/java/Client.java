import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Set;

public class Client extends UnicastRemoteObject implements IClient {

    private IServerGame server;
    private IRoom currentRoom;
    private String username;
    private GameController gameController;
    private Controller controller;

    private LobbyUpdatesMonitor lobbyUpdatesMonitor;
    private GameMonitor gameMonitor;


    public Client(String address) throws RemoteException {
        try {
            server = (IServerGame) Naming.lookup(address);
            System.out.println("Connected to server " + address + ".");
        } catch(Exception e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        gameController = new GameController(this);
        controller = new Controller(this);
        lobbyUpdatesMonitor = new LobbyUpdatesMonitor(controller);
        gameMonitor = new GameMonitor(gameController);
    }

    public void giveLobbyUpdate(Set<String> update) throws RemoteException {
        lobbyUpdatesMonitor.giveUpdate(update);
    }

    /*
    public void getVote() throws RemoteException {
    }
    */
    
    
    public void requestWord() throws RemoteException, InterruptedException {
        gameMonitor.requestWord();
    }

   /*
    public synchronized String getGuess() throws RemoteException {
        return "";
    }
    */

    public void init(String word, Role role, Set<String> players) {
        gameMonitor.init(word, role, players);
    }

    public Set<RoomInfo> getLobbies() throws RemoteException {
        return server.getLobbies();
    }

    public void sendWord(String word) throws RemoteException {

    } 

    public Set<String> connectToLobby(String owner) throws RemoteException, RoomInexistentException, RoomFullException, GameLaunchedException {
        return server.connectToLobby(this, owner);
    }

    public void launchGame() throws RemoteException {
        currentRoom.launchGame();
    }

    //Getters and setters

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public IServerGame getServer() {
        return server;
    }

    public void setCurrentRoom(IRoom room) {
        currentRoom = room;
    }

    public Controller getController() {
        return controller;
    }

    public GameController getGameController() {
        return gameController;
    }

}