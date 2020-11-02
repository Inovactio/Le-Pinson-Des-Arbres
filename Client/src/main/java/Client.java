import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Set;

public class Client extends UnicastRemoteObject implements IClient {

    private IServerGame server;
    private IRoom currentRoom;
    private String username;
    private PlayerInteractionController playerInteractionController;
    private Controller controller;

    private boolean bufferEmpty;
    private String buffer;

    public Client(String address) throws RemoteException {
        try {
            server = (IServerGame) Naming.lookup(address);
            System.out.println("Connected to server " + address + ".");
        } catch(Exception e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        playerInteractionController = new PlayerInteractionController(this);
        controller = new Controller(this);
        bufferEmpty = true;
    }

    public void refreshLobby(Set<String> players) throws RemoteException {
        controller.refreshLobby(players);
    }

    /*
    public void getVote() throws RemoteException {
    }
    */
    
    
    public synchronized String getWord() throws RemoteException, InterruptedException {
        playerInteractionController.openInput();
        wait(20000);
        bufferEmpty = true;
        return buffer;
    }

   /*
    public synchronized String getGuess() throws RemoteException {
        return "";
    }
    */

    public synchronized void giveWord(String word) {
        buffer = word;
        bufferEmpty = false;
        notify();
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

    public Set<RoomInfo> getLobbies() throws RemoteException {
        return server.getLobbies();
    }

    public Set<String> connectToLobby(String owner) throws RemoteException, RoomInexistentException, RoomFullException, GameLaunchedException {
        return server.connectToLobby(this, owner);
    }
}