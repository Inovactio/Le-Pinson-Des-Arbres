import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

import jsonparser.JsonParser;
import jsonparser.Tuple;

public class Room extends UnicastRemoteObject implements IRoom {

    private ServerGame server;
    private Set<IClient> clients;
    private Set<String> usernames;
    private String owner;
    private boolean gameLaunched;
    private int roomSize;

    private int turnTime;
    private int nbWords;
    private int nbRounds;
    private int nbImpostors;

    public Room(int roomSize, ServerGame server) throws RemoteException {
        this.server = server;
        this.clients = new HashSet<IClient>();
        this.usernames = new HashSet<String>();
        this.gameLaunched = false;
        this.turnTime = 20;
        this.nbWords = 3;
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

    public synchronized Set<String> join(IClient client)
            throws RemoteException, GameLaunchedException, RoomFullException {
        if (gameLaunched)
            throw new GameLaunchedException();
        if (clients.size() >= roomSize)
            throw new RoomFullException();
        usernames.add(client.getUsername());
        for (IClient c : clients) {
            c.giveLobbyUpdate(usernames);
        }
        client.giveLobbyUpdate(turnTime, nbWords, nbRounds, nbImpostors);
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
        } else {
            for (IClient c : clients) {
                c.giveLobbyUpdate(usernames);
            }
        }
    }

    public synchronized void launchGame() throws RemoteException {

        // TODO : randomly select roles
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = classLoader.getResource("words.json").getPath();
        JsonParser jsonParser = new JsonParser(path);
        Tuple<String> words = jsonParser.getRandomWords();
        System.out.println("Words : " + words.getFirst() + " / " + words.getSecond());

        for (IClient client : clients) {
            client.init(words.getFirst(), Role.CITIZEN, usernames);
        }
    }

    @Override
    public synchronized void changeSettings(int turnTime, int nbWords, int nbRounds, int nbImpostors) throws RemoteException {
        this.turnTime = turnTime;
        this.nbWords = nbWords;
        this.nbRounds = nbRounds;
        this.nbImpostors = nbImpostors;
        for (IClient client : clients) {
            client.giveLobbyUpdate(turnTime, nbWords, nbRounds, nbImpostors);
        }
    }

    @Override
    public void sendVote(String player) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendWord(String word) throws RemoteException {
        // TODO Auto-generated method stub

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