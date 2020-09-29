import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ServerGame extends UnicastRemoteObject implements IServerGame {

    public ServerGame() throws RemoteException {

    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(8090);
            ServerGame serverGame = new ServerGame();
            Naming.bind("localhost", serverGame);
        } catch (Exception e) {
            System.out.println("Erreur Server Main");
        }
    }

    public void vote(String player) {
        // TODO Auto-generated method stub

    }

    public void write(String word) {
        // TODO Auto-generated method stub

    }

    

}
