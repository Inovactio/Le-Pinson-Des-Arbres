import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Client extends UnicastRemoteObject implements IClient {

    private IServerGame server;
    private IRoom currentRoom;
    private String username;
    private PlayerInteractionController controller;

    private boolean bufferEmpty;
    private String buffer;

    public Client(String address) throws RemoteException {
        try {
            server = (IServerGame) Naming.lookup(address);
            System.out.println("Connected to server " + address + ".");
        } catch(Exception e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        controller = new PlayerInteractionController(this);
        bufferEmpty = true;
    }

    /*
    public void getVote() throws RemoteException {
    }
    */
    
    
    public synchronized String getWord() throws RemoteException, InterruptedException {
        controller.openInput();
        wait(20000);
        bufferEmpty = true;
        return buffer;
    }

   /*
    public synchronized String getGuess() throws RemoteException {
        return "";
    }
    */

    public synchronized void giveWord(String word) {
        buffer = word;
        bufferEmpty = false;
        notify();
    }

    //Getters and setters

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}