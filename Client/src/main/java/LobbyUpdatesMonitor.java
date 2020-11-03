import java.util.Set;

import javafx.application.Platform;

import java.util.LinkedList;
import java.util.Queue;
import java.lang.Thread;

public class LobbyUpdatesMonitor {

    private Queue<Set<String>> queue;
    private Controller lobbyController;

    public LobbyUpdatesMonitor(Controller controller) {
        queue = new LinkedList<Set<String>>();
        lobbyController = controller;
        Thread thread = new Thread(() -> { getUpdate(); });
        thread.start();
    }


    public synchronized void getUpdate() {
        if (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("LobbyUpdatesMonitor wait() failed.");
                e.printStackTrace();
            }
        }
        Set<String> currentUpdate = queue.poll();
        Thread thread = new Thread(() -> {lobbyController.refreshLobby(currentUpdate);});
        Platform.runLater(thread);
        getUpdate();
    }

    public synchronized void giveUpdate(Set<String> update) {
        queue.add(update);
        notify();
    }

}