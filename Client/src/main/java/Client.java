import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
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
        } catch (Exception e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        gameController = new GameController(this);
        controller = new Controller(this);
        lobbyUpdatesMonitor = new LobbyUpdatesMonitor(controller);
        gameMonitor = new GameMonitor(gameController);
    }

    public void enablePlayButton() throws RemoteException{
        controller.enablePlayButton();
    }

    public void giveLobbyUpdate(List<String> update) throws RemoteException {
        lobbyUpdatesMonitor.giveUpdate(update);
    }

    @Override
    public void giveLobbyUpdate(int turnTime, int nbWords, int nbRounds, int nbImpostors) throws RemoteException {
        lobbyUpdatesMonitor.giveUpdate(turnTime, nbWords, nbRounds, nbImpostors);
    }

    public void changeSettings(int turnTime, int nbWords, int nbRounds, int nbImpostors) throws RemoteException {
        currentRoom.changeSettings(turnTime, nbWords, nbRounds, nbImpostors);
    }

    public void requestWord() throws RemoteException, InterruptedException {
        gameMonitor.requestWord();
    }

    public void init(String word,Boolean isMrWhite, List<String> players) {
        gameMonitor.init(word, isMrWhite, players);
    }

    public Set<RoomInfo> getLobbies() throws RemoteException {
        return server.getLobbies();
    }

    public void sendWord(String word) throws RemoteException {

    }

    public List<String> connectToLobby(String owner)
            throws RemoteException, RoomInexistentException, RoomFullException, GameLaunchedException {
        return server.connectToLobby(this, owner);
    }

    public void quit() throws RemoteException {
        currentRoom.quit(this);
        currentRoom = null;
    }

    public void kick() throws RemoteException {
        lobbyUpdatesMonitor.kick();
        currentRoom = null;
    }

    public void freeUsername() throws RemoteException {
        server.freeUsername(username);
    }

    public void launchGame() throws RemoteException {
        currentRoom.launchGame();
    }

    // Getters and setters

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public IServerGame getServer() {
        return server;
    }

    public IRoom getCurrentRoom() {
        return currentRoom;
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