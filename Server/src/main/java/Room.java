import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class Room extends UnicastRemoteObject implements IRoom {

    private Set<IClient> clients;
    private String owner;
    private boolean gameLaunched;
    private int roomSize;

    public Room(int roomSize) throws RemoteException {
        this.clients = new HashSet<IClient>();
        this.gameLaunched = false;
        if(roomSize>=6 && roomSize<=10){
            this.roomSize = roomSize;
        } else {
            System.out.println("la taille de la room doit être entre 6 et 10");
            this.roomSize = 6;
        }

    }

    public Room(IClient client, int roomSize) throws RemoteException{
        this(roomSize);
        join(client);
        owner = client.getUsername();
    }

    public synchronized boolean join(IClient client) throws RemoteException {
        if (gameLaunched) {
            return false;
        } else {
            return clients.add(client);
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

    public synchronized boolean quit(IClient client) {
        return clients.remove(client);
    }

    public void vote(String player) throws RemoteException {
        // TODO Auto-generated method stub

    }

    private boolean isRoomFull(){
        return (clients.size()>= roomSize);
    }

    private void launch(){
        gameLaunched = true;
    }

}