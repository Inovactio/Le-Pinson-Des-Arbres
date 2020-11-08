import java.util.List;
import java.util.Set;

import javafx.application.Platform;

import java.util.LinkedList;
import java.util.Queue;
import java.lang.Thread;

public class LobbyUpdatesMonitor {

    private Queue<Update> queue;
    private Controller lobbyController;

    public LobbyUpdatesMonitor(Controller controller) {
        queue = new LinkedList<Update>();
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
        Update currentUpdate = queue.poll();
        Thread thread = new Thread(() -> {currentUpdate.handle(lobbyController);});
        Platform.runLater(thread);
        getUpdate();
    }

    public synchronized void giveUpdate(List<String> players) {
        queue.add(new LobbyRefreshUpdate(players));
        notify();
    }

    public synchronized void giveUpdate(int turnTime, int nbWords, int nbRounds, int nbImpostors) {
        queue.add(new SettingsUpdate(turnTime, nbWords, nbRounds, nbImpostors));
        notify();
    }

    public synchronized void kick() {
        queue.add(new KickUpdate());
        notify();
    }


    //-----Updates definition-----

    private interface Update {

        public void handle(Controller controller);
        
    }

    private class KickUpdate implements Update {

        public void handle(Controller controller) {
            controller.kick();
        }

    }
    
    private class LobbyRefreshUpdate implements Update {
        private List<String> players;

        public LobbyRefreshUpdate(List<String> players) {
            this.players = players;
        }

        public void handle(Controller controller) {
            controller.refreshLobby(players);
        }
    } 

    private class SettingsUpdate implements Update {
        private int turnTime;
        private int nbWords;
        private int nbRounds;
        private int nbImpostors;

        public SettingsUpdate(int turnTime, int nbWords, int nbRounds, int nbImpostors) {
            this.turnTime = turnTime;
            this.nbWords = nbWords;
            this.nbRounds = nbRounds;
            this.nbImpostors = nbImpostors;
        }

        public void handle(Controller controller) {
            controller.refreshLobby(turnTime, nbWords, nbRounds, nbImpostors);
        }
    }

}