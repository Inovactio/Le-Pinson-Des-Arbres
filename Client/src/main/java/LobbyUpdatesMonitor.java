import java.util.Set;

import javafx.application.Platform;

import java.util.LinkedList;
import java.util.Queue;
import java.lang.Thread;

public class LobbyUpdatesMonitor {

    private Queue<Set<String>> queue;
    private boolean kicked;
    private Controller lobbyController;

    public LobbyUpdatesMonitor(Controller controller) {
        queue = new LinkedList<Set<String>>();
        kicked = false;
        lobbyController = controller;
        Thread thread = new Thread(() -> { getUpdate(); });
        thread.start();
    }


    public synchronized void getUpdate() {
        if (queue.isEmpty() && kicked == false) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("LobbyUpdatesMonitor wait() failed.");
                e.printStackTrace();
            }
        }
        if (kicked) {
            kicked = false;
            queue = new LinkedList<Set<String>>();
            Thread thread = new Thread(() -> {lobbyController.kick();});
            Platform.runLater(thread);
        } else {
            Set<String> currentUpdate = queue.poll();
            Thread thread = new Thread(() -> {lobbyController.refreshLobby(currentUpdate);});
            Platform.runLater(thread);
        }
        getUpdate();
    }

    public synchronized void giveUpdate(Set<String> update) {
        queue.add(update);
        notify();
    }

    public synchronized void kick() {
        kicked = true;
        notify();
    }

}