import com.sun.security.ntlm.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerGame extends UnicastRemoteObject implements IServerGame{

    public ServerGame() throws RemoteException{

    }

}
