import java.util.HashSet;
import java.util.Set;

public class Room {

    private Set<Player> players;
    private boolean gameLaunched;

    public Room() {
        this.players = new HashSet<Player>();
        this.gameLaunched = false;
    }

    public synchronized boolean join(Player player) {
        if (gameLaunched) {
            return false;
        } else {
            return players.add(player);
        }
    }

    public void launch() {
        gameLaunched = true;
    }
}