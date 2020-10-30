import java.applet.Applet;
import java.util.List;
import java.util.ArrayList;

public class BasicPlayer {

    private String name;
    private List<String> writtenWords;
    private String votedPlayer;

    public BasicPlayer(String name) {
        this.name = name;
        this.writtenWords = new ArrayList<String>();
    }

    public void write(String word) {
        writtenWords.add(word);
    }

    public void vote(String player) {
        this.votedPlayer = player;
    }

    
    //Getters

    public String getName() {
        return name;
    }

    public List<String> getWrittenWords() {
        return writtenWords;
    }

    public String getVotedPlayer() {
        return votedPlayer;
    }

}