import java.rmi.RemoteException;
import java.util.List;

public class ClientMock implements IClient {

    @Override
    public void giveLobbyUpdate(List<String> update) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void giveLobbyUpdate(int turnTime, int nbWords, int nbRounds, int nbImpostors) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void giveGameUpdate(String word, int playerIndex) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestVote() throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestWord() throws RemoteException, InterruptedException {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(String word, Boolean isMrWhite, List<String> players) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCurrentRoom(IRoom room) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void kick() throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public String getUsername() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

}

