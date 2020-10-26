import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class Room extends UnicastRemoteObject implements IRoom {

<<<<<<< HEAD
    private Set<BasicPlayer> basicPlayers;
=======
    private Set<Client> clients;
>>>>>>> Use Client instead of Player as Remote object
    private boolean gameLaunched;
    private int roomSize;

    public Room(int roomSize) throws RemoteException {
<<<<<<< HEAD
        this.basicPlayers = new HashSet<BasicPlayer>();
=======
        this.clients = new HashSet<Client>();
>>>>>>> Use Client instead of Player as Remote object
        this.gameLaunched = false;
        if(roomSize>=6 && roomSize<=10){
            this.roomSize = roomSize;
        } else {
            System.out.println("la taille de la room doit être entre 6 et 10");
            this.roomSize = 6;
        }

    }

<<<<<<< HEAD
    public Room(BasicPlayer basicPlayer, int roomSize) throws RemoteException{
        this(roomSize);
        join(basicPlayer);
    }

    public synchronized boolean join(BasicPlayer basicPlayer) throws RemoteException {
        if (gameLaunched) {
            return false;
        } else {
            return basicPlayers.add(basicPlayer) && islaunchable();
=======
    public Room(Client client,int roomSize) throws RemoteException{
        this(roomSize);
        join(client);
    }

    public synchronized boolean join(Client client) throws RemoteException {
        if (gameLaunched) {
            return false;
        } else {
            return clients.add(client);
>>>>>>> Use Client instead of Player as Remote object
        }
    }

    public synchronized boolean quit(Client client) {
        return clients.remove(client);
    }

    public void vote(String player) throws RemoteException {
        // TODO Auto-generated method stub

    }

    private boolean isRoomFull(){
<<<<<<< HEAD
        return (basicPlayers.size()>= roomSize);
    }

    private boolean islaunchable(){
        if(isRoomFull()) launch();
        return gameLaunched;
=======
        return (clients.size()>= roomSize);
>>>>>>> Use Client instead of Player as Remote object
    }

    private void launch(){
        gameLaunched = true;
    }
}