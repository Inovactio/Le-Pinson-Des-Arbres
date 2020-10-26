import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class Room extends UnicastRemoteObject implements IRoom {

    private Set<BasicPlayer> basicPlayers;
    private boolean gameLaunched;
    private int roomSize;

    public Room(int roomSize) throws RemoteException {
        this.basicPlayers = new HashSet<BasicPlayer>();
        this.gameLaunched = false;
        if(roomSize>=6 && roomSize<=10){
            this.roomSize = roomSize;
        }else{
            System.out.println("la taille de la room doit être entre 6 et 10");
            this.roomSize = 6;
        }

    }

    public Room(BasicPlayer basicPlayer, int roomSize) throws RemoteException{
        this(roomSize);
        join(basicPlayer);
    }

    public synchronized boolean join(BasicPlayer basicPlayer) throws RemoteException {
        if (gameLaunched) {
            return false;
        } else {
            return basicPlayers.add(basicPlayer) && islaunchable();
        }
    }

    public void vote(String player) throws RemoteException {
        // TODO Auto-generated method stub

    }

    public void write(String word) throws RemoteException {
        // TODO Auto-generated method stub

    }

    public void guessWord(String word) throws RemoteException {
        // TODO Auto-generated method stub

    }

    private boolean isRoomFull(){
        return (basicPlayers.size()>= roomSize);
    }

    private boolean islaunchable(){
        if(isRoomFull()) launch();
        return gameLaunched;
    }

    private void launch(){
        gameLaunched = true;
    }
}