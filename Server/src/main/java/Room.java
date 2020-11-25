import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Room extends UnicastRemoteObject implements IRoom {

    private ServerGame server;
    private List<IClient> clients;
    private List<String> usernames;
    private String owner;
    private boolean gameLaunched;
    private int roomSize;

    private int turnTime;
    private int nbRounds;
    private int nbImpostors;

    private GameMonitor gameMonitor;

    public Room(int roomSize, ServerGame server) throws RemoteException {
        this.server = server;
        this.clients = new ArrayList<IClient>();
        this.usernames = new ArrayList<String>();
        this.gameLaunched = false;
        this.turnTime = 20;
        this.nbRounds = 3;
        this.nbImpostors = 1;
        if (roomSize >= 6 && roomSize <= 10) {
            this.roomSize = roomSize;
        } else {
            System.out.println("la taille de la room doit être entre 6 et 10");
            this.roomSize = 6;
        }

    }

    public Room(IClient client, int roomSize, ServerGame server) throws RemoteException {
        this(roomSize, server);
        clients.add(client);
        usernames.add(client.getUsername());
        owner = client.getUsername();
    }

    /**
     * add a client to this room
     * @param client is the client added
     * @return the list of all client in the room
     * @throws RemoteException
     * @throws GameLaunchedException throw if the game is already lauched
     * @throws RoomFullException throw if the lobby is full
     */
    public synchronized List<String> join(IClient client)
            throws RemoteException, GameLaunchedException, RoomFullException {
        if (gameLaunched)
            throw new GameLaunchedException();
        if (clients.size() >= roomSize)
            throw new RoomFullException();
        usernames.add(client.getUsername());
        for (IClient c : clients) {
            c.giveLobbyUpdate(usernames);
        }
        client.giveLobbyUpdate(turnTime, nbRounds, nbImpostors);
        clients.add(client);
        return usernames;
    }

    /**
     * remove a client from this room
     * @param client is the client removed
     * @throws RemoteException
     */
    public synchronized void quit(IClient client) throws RemoteException {
        clients.remove(client);
        usernames.remove(client.getUsername());
        if (client.getUsername().equals(owner)) {
            for (IClient c : clients) {
                c.kick();
            }
            server.removeRoom(owner);
            close();
        } else {
            for (IClient c : clients) {
                c.giveLobbyUpdate(usernames);
            }
        }
    }

    /**
     * Start the game
     * @throws RemoteException
     */
    public synchronized void launchGame() throws RemoteException {
        gameMonitor = new GameMonitor(this, clients, usernames, nbRounds, nbImpostors, turnTime);
        Thread monitorThread = new Thread(() -> {
            try{
                gameMonitor.launchGame();
            }
            catch(RemoteException e) {
                System.out.println("game launching failed.");
                e.printStackTrace();
            }

        });
        server.removeRoom(owner);
        monitorThread.start();
    }

    /**
     * Change settings of the room
     * @param turnTime is time of a turn
     * @param nbRounds is the number of round
     * @param nbImpostors is the number of impostors
     * @throws RemoteException
     */
    @Override
    public synchronized void changeSettings(int turnTime, int nbRounds, int nbImpostors)
            throws RemoteException {
        this.turnTime = turnTime;
        this.nbRounds = nbRounds;
        this.nbImpostors = nbImpostors;
        for (IClient client : clients) {
            client.giveLobbyUpdate(turnTime, nbRounds, nbImpostors);
        }
    }

    /**
     * fill the room with mock ( used to test )
     * @throws RemoteException
     */
    @Override
    public synchronized void fill() throws RemoteException {
        if (clients.size()==1) {
            for (int i = 0; i<5; i++) {
                clients.add(new ClientMock());
                String s = "bot" + i;
                usernames.add(s);
            }
            for (IClient c : clients) {
                c.giveLobbyUpdate(usernames);
            }
        }
    }

    /**
     * Send a word
     * @param word is the word send
     * @throws RemoteException
     */
    @Override
    public void sendWord(String word) throws RemoteException {
        gameMonitor.sendWord(word);
    }

    /**
     * Send vote for who is an impostor and who is MrWhite
     * @param username is the username of the client who vote
     * @param imposteur is username of the vote for impostor
     * @param mrWhite is the username of the vote for mrWhite
     * @param isMrWhite true if the client is MrWhite, else false
     * @throws RemoteException
     */
    @Override
    public void sendVote(String username,String imposteur, String mrWhite, boolean isMrWhite) throws RemoteException {
        gameMonitor.sendVote(username,imposteur,mrWhite, isMrWhite);
    }

    /**
     * Send the guess of MrWhite at the end of the game
     * @param client is the client who guess
     * @param word is the word guessed
     * @throws RemoteException
     */
    @Override
    public void sendGuess(IClient client, String word) throws RemoteException {
        gameMonitor.sendGuess(client, word);
    }

    /**
     * Close the lobby
     */
    public void close() {
        try {
            unexportObject(this, true);
        } catch (NoSuchObjectException e) {
            System.out.println("Failed to close room correctly.");
            e.printStackTrace();
        }
    }

    public String getOwner() throws RemoteException {
        return owner;
    }

    public int getNbPlayers() throws RemoteException {
        return clients.size();
    }

    public int getRoomSize() throws RemoteException {
        return roomSize;
    }

}