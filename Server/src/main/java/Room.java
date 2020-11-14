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

    public synchronized void launchGame() throws RemoteException {

        // Throw exception if nb players != 6

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

    @Override
    public void sendVote(String username,String imposteur, String mrWhite, boolean isMrWhite) throws RemoteException {
        gameMonitor.sendVote(username,imposteur,mrWhite, isMrWhite);
    }

    @Override
    public void sendWord(String word) throws RemoteException {
        gameMonitor.sendWord(word);
    }

    @Override
    public void sendGuess(IClient client, String word) throws RemoteException {
        gameMonitor.sendGuess(client, word);
    }

    public void close() {
        try {
            unexportObject(this, true);
        } catch (NoSuchObjectException e) {
            System.out.println("Failed to close room correctly.");
            e.printStackTrace();
        }
    }

    // -----Getters-----

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