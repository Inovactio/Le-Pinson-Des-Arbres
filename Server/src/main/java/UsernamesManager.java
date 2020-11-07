import java.util.HashSet;
import java.util.Set;

public class UsernamesManager {

    private Set<String> usernames;

    public UsernamesManager() {
        usernames = new HashSet<String>();
    }

    public synchronized boolean takeUsername(String username) {
        return usernames.add(username);
    }

    public synchronized void freeUsername(String username ){
        usernames.remove(username);
    }
}