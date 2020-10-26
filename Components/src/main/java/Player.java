public class Player extends BasicPlayer {

    private Role role;
    private String givenWord;

    public Player(String name,Role role, String givenWord) {
        super(name);
        this.role=role;
        this.givenWord=givenWord;
    }

    public Role getRole() {
        return role;
    }

    public String getGivenWord() {
        return givenWord;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setGivenWord(String givenWord) {
        this.givenWord = givenWord;
    }
}
