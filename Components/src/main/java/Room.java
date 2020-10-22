import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class Room extends UnicastRemoteObject implements IRoom {

    private Set<Player> players;
    private boolean gameLaunched;
    private int roomSize;

    public Room(int roomSize) throws RemoteException {
        this.players = new HashSet<Player>();
        this.gameLaunched = false;
        if(roomSize>=6 && roomSize<=10){
            this.roomSize = roomSize;
        }else{
            System.out.println("la taille de la room doit être entre 6 et 10");
            this.roomSize = 6;
        }

    }

    public Room(Player player,int roomSize) throws RemoteException{
        this(roomSize);
        join(player);
    }

    public synchronized boolean join(Player player) throws RemoteException {
        if (gameLaunched) {
            return false;
        } else {
            return players.add(player) && islaunchable();
        }
    }

    private boolean isRoomFull(){
        return (players.size()>= roomSize);
    }

    private boolean islaunchable(){
        if(isRoomFull()) launch();
        return gameLaunched;
    }

    private void launch(){
        gameLaunched = true;
    }
}