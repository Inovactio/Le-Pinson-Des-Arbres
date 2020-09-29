import java.rmi.Remote;

public interface IServerGame  extends Remote{

    public void write(String word);

    public void vote(String player);

    public void guessWord(String word);
}
