import database.DataBaseConnect;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.sql.*;

public class Main {



    public static void main(String[] args) {

         final String URL = "jdbc:mysql://localhost/undercover";
         final String USER = "undercoverUser";
         final String PASSWORD = "undercoverPassword";

        DataBaseConnect connect = new DataBaseConnect(URL,USER,PASSWORD);

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
