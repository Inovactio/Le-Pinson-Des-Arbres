public abstract class PlayerWithGivenWord extends Player {

    private String givenWord;

    public PlayerWithGivenWord(String name, Role role, String givenWord) {
        super(name, role);
        this.givenWord=givenWord;
    }

    public String getGivenWord() {
        return givenWord;
    }

    public void setGivenWord(String givenWord) {
        this.givenWord = givenWord;
    }
}
