public class Player {

    private String name;
    private Role role;
    private String givenWord;
    private String[] writtenWords;

    public Player(String name, Role role, String givenWord) {
        this.name = name;
        this.role = role;
        this.givenWord = givenWord;
        this.writtenWords = new String[2];
    }

    public void write(String word, int turn) {
        writtenWords[turn-1] = word;
    }


}