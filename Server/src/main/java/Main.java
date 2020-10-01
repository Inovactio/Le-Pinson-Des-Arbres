

import jsonparser.JsonParser;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;


public class Main {



    public static void main(String[] args) {

        try {
            /*Création du serveur*/
            LocateRegistry.createRegistry(8090);
            ServerGame serverGame = new ServerGame();
            Naming.bind("//localhost:8090", serverGame);




        } catch (Exception e) {
            System.out.println("Erreur lors de la création du serveur de jeu.");
        }
    }
}
