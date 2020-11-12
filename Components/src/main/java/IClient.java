import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IClient extends Remote {

    public void giveLobbyUpdate(List<String> update) throws RemoteException;

    public void giveLobbyUpdate(int turnTime, int nbWords, int nbRounds, int nbImpostors) throws RemoteException;

    public void giveGameUpdate(String word, int playerIndex)throws RemoteException;

    public void requestVote() throws RemoteException;
    
    public void requestWord() throws RemoteException, InterruptedException;

    public String requestGuess() throws RemoteException;

    public void init(String word, Boolean isMrWhite, List<String> players) throws RemoteException;

    public void setCurrentRoom(IRoom room) throws RemoteException;

    public void kick() throws RemoteException;

    public String getUsername() throws RemoteException;

    public void updateEndOfGameInfo(String imposterNameReveal, String imposterWordReveal, String mrWhiteNameReveal, String citizensWordReveal, String gameResultReveal) throws RemoteException;

    public void switchToVoteScene() throws RemoteException;

    public boolean getIsMrWhite() throws RemoteException;

    public boolean getFoundImposteur() throws RemoteException;

    public void setFoundImposteur(boolean foundImposteur) throws RemoteException;
}
