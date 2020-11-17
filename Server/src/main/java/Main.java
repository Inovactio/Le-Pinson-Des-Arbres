import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Main {

    public static void main(String[] args) {

        try {
            /*Création du serveur*/
            final String url = "localhost";
            final int port = 8090;
            Registry registry = LocateRegistry.createRegistry(port);
            ServerGame serverGame = new ServerGame();
            IServerGame stub = (IServerGame) UnicastRemoteObject.exportObject(serverGame, 0);
            registry.bind("undercover", stub);

        } catch (Exception e) {
            System.out.println("Erreur lors de la création du serveur de jeu.");
            e.printStackTrace();
        }
    }
}
