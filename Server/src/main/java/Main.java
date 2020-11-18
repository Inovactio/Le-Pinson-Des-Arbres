import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;


public class Main {

    public static void main(String[] args) {

        try {
            /*Création du serveur*/

            final String url = "localhost";
            final int port = 1099;
            LocateRegistry.createRegistry(port);
            IServerGame serverGame = new ServerGame();
            Naming.rebind("//"+url+":"+port+"/undercover", serverGame);
            System.out.println("Server running at //" + url + ":" + port + "/undercover");
        } catch (Exception e) {
            System.out.println("Erreur lors de la création du serveur de jeu.");
            e.printStackTrace();
        }
    }
}
