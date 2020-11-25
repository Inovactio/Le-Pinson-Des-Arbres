import java.rmi.RemoteException;
import java.util.List;

/**
 * This class is mock to test the game with the method fill() in Room
 */
public class ClientMock implements IClient {

    @Override
    public void giveLobbyUpdate(List<String> update) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void giveLobbyUpdate(int turnTime, int nbRounds, int nbImpostors) throws RemoteException {
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
    public String requestGuess() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
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

    @Override
    public void updateEndOfGameInfo(String imposterNameReveal, String imposterWordReveal, String mrWhiteNameReveal, String citizensWordReveal, String gameResultReveal) throws RemoteException{
        // TODO Auto-generated method stub
    }

    @Override
    public boolean getIsMrWhite() throws RemoteException{
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getFoundImposteur() throws RemoteException{
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setFoundImposteur(boolean foundImposteur) throws RemoteException{
        // TODO Auto-generated method stub
    }

    @Override
    public void switchToVoteScene() throws RemoteException{
        // TODO Auto-generated method stub
    }

}

