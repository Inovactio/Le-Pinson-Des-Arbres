import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ServeurJeu extends UnicastRemoteObject implements IServeurJeu {

    public ServeurJeu() throws RemoteException{

    }

    public static void main(String[] args) {
        try{
            LocateRegistry.createRegistry(8090);
            ServeurJeu server = new ServeurJeu();
            Naming.bind("",server);
        }catch (Exception e){
            System.out.println("Erreur serveur Main");
        }
    }


}
