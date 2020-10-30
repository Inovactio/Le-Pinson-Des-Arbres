import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Client extends UnicastRemoteObject {

    IServerGame server;
    IRoom currentRoom;
    String username;
    

    public Client(String address) throws RemoteException {
        try {
            server = (IServerGame) Naming.lookup(address);
            System.out.println("Connected to server " + address + ".");
        } catch(Exception e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }

    /*
    public void getVote() throws RemoteException {

    }
    
    
    public String getWrite() throws RemoteException {
        wait();
    }

   
    public String getGuess() throws RemoteException {

    }
    */

    //Getters and setters

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}