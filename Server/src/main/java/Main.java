import jsonparser.JsonParser;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;


public class Main {

    public static void main(String[] args) {

        try {
            /*Création du serveur*/
            final String url = "localhost";
            final int port = 8090;
            LocateRegistry.createRegistry(port);
            ServerGame serverGame = new ServerGame();
            
            Naming.rebind("//"+url+":"+port+"/undercover", serverGame);

            JsonParser jsonParser = new JsonParser("Server/src/main/resources/words.json");

        } catch (Exception e) {
            System.out.println("Erreur lors de la création du serveur de jeu.");
            e.printStackTrace();
        }
    }
}
