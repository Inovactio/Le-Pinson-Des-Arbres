import java.util.List;
import java.util.ArrayList;

public class Player {

    private String name;
    private Role role;
    private String givenWord;
    private List<String> writtenWords;
    private String votedPlayer;

    public Player(String name, Role role, String givenWord) {
        this.name = name;
        this.role = role;
        this.givenWord = givenWord;
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

    public Role getRole() {
        return role;
    }

    public String getGivenWord() {
        return givenWord;
    }

    public List<String> getWrittenWords() {
        return writtenWords;
    }

    public String getVotedPlayer() {
        return votedPlayer;
    }

}