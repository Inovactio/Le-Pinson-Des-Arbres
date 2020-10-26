public abstract class Player extends BasicPlayer {

    private Role role;

    public Player(String name,Role role) {
        super(name);
        this.role=role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public abstract String getGivenWord();

}
