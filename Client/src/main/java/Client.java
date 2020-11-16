import java.rmi.Remote;
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
    private Boolean isMrWhite;
    private Boolean foundImposteur;

    private LobbyUpdatesMonitor lobbyUpdatesMonitor;
    private GameMonitor gameMonitor;

    public Client() throws RemoteException {
        gameController = new GameController(this);
        controller = new Controller(this);
        lobbyUpdatesMonitor = new LobbyUpdatesMonitor(controller);
        gameMonitor = new GameMonitor(gameController);
        foundImposteur=false;
    }

    public void initConnection(String address){
        try {
            server = (IServerGame) Naming.lookup("//"+address+":8090/undercover");
            System.out.println("Connected to server " + address + ".");
        } catch (Exception e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }

    public void giveLobbyUpdate(List<String> update) throws RemoteException {
        lobbyUpdatesMonitor.giveUpdate(update);
    }

    @Override
    public void giveLobbyUpdate(int turnTime, int nbRounds, int nbImpostors) throws RemoteException {
        lobbyUpdatesMonitor.giveUpdate(turnTime, nbRounds, nbImpostors);
    }

    public void changeSettings(int turnTime, int nbRounds, int nbImpostors) throws RemoteException {
        currentRoom.changeSettings(turnTime, nbRounds, nbImpostors);
    }

    public void giveGameUpdate(String word, int playerIndex) throws RemoteException{
        gameMonitor.giveGameUpdate(word,playerIndex);
    }

    public void requestWord() throws RemoteException, InterruptedException {
        gameMonitor.requestWord();
    }

    public void requestVote() throws RemoteException{
        gameMonitor.requestVote();
    }

    public String requestGuess() throws RemoteException{
        gameMonitor.getRequest();
        return null;
    }

    public void init(String word,Boolean isMrWhite, List<String> players) {
        this.isMrWhite = isMrWhite;
        gameMonitor.init(word, isMrWhite, players);
    }

    public Set<RoomInfo> getLobbies() throws RemoteException {
        return server.getLobbies();
    }

    public void sendWord(String word) throws RemoteException {
        currentRoom.sendWord(word);
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

    public void fillLobby() throws RemoteException {
        currentRoom.fill();
    }

    public void sendVoteResult(String imposterVote, String mrWhiteVote) throws RemoteException{
        currentRoom.sendVote(username,imposterVote,mrWhiteVote,isMrWhite);

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

    public void updateEndOfGameInfo(String imposterNameReveal, String imposterWordReveal, String mrWhiteNameReveal, String citizensWordReveal, String gameResultReveal) throws RemoteException{
        gameMonitor.requestUpdateEndOfGame(imposterNameReveal,imposterWordReveal,mrWhiteNameReveal,citizensWordReveal,gameResultReveal);
    }

    public void switchToVoteScene() throws RemoteException{
        gameMonitor.requestSwitchToVoteScene();
    }

    public boolean getIsMrWhite() throws RemoteException{
        return isMrWhite;
    }

    public void setFoundImposteur(boolean foundImposteur) throws RemoteException{
        this.foundImposteur=foundImposteur;
    }

    public boolean getFoundImposteur() throws RemoteException{
        return foundImposteur;
    }

}