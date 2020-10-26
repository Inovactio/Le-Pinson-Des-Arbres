import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class Room extends UnicastRemoteObject implements IRoom {

    private Set<Client> clients;
    private boolean gameLaunched;
    private int roomSize;

    public Room(int roomSize) throws RemoteException {
        this.clients = new HashSet<Client>();
        this.gameLaunched = false;
        if(roomSize>=6 && roomSize<=10){
            this.roomSize = roomSize;
        } else {
            System.out.println("la taille de la room doit être entre 6 et 10");
            this.roomSize = 6;
        }

    }

    public Room(Client client,int roomSize) throws RemoteException{
        this(roomSize);
        join(client);
    }

    public synchronized boolean join(Client client) throws RemoteException {
        if (gameLaunched) {
            return false;
        } else {
            return clients.add(client);
        }
    }

    public synchronized boolean quit(Client client) {
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