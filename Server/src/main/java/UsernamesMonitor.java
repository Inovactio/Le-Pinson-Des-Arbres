import java.util.HashSet;
import java.util.Set;

public class UsernamesMonitor {

    private Set<String> usernames;

    public UsernamesMonitor() {
        usernames = new HashSet<String>();
    }

    public synchronized boolean takeUsername(String username) {
        return usernames.add(username);
    }
}