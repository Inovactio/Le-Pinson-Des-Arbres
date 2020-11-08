import java.util.List;
import java.util.Set;

import javafx.application.Platform;

public class GameMonitor {

    private GameController gameController;
    private Request buffer;
    private boolean bufferIsEmpty;

    public GameMonitor(GameController gameController) {
        this.gameController = gameController;
        bufferIsEmpty = true;
        Thread thread = new Thread(() -> { getRequest(); });
        thread.start();
    }

    public synchronized void getRequest() {
        if (bufferIsEmpty) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("GameMonitor wait() failed.");
                e.printStackTrace();
            }
        }

        Thread thread = new Thread(() -> {buffer.handle(gameController);});
        Platform.runLater(thread);
        bufferIsEmpty = true;
        notify();
        getRequest();
    }

    public synchronized void init(String word, Boolean isMrWhite, List<String> players) {
        if (!bufferIsEmpty) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("GameMonitor wait() failed.");
                e.printStackTrace();
            }
        }

        buffer = new InitRequest(word,isMrWhite, players);
        bufferIsEmpty = false;
        notify();
    }

    public synchronized void requestWord() {
        if (!bufferIsEmpty) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("GameMonitor wait() failed.");
                e.printStackTrace();
            }
        }
        buffer = new WordRequest();
        bufferIsEmpty = false;
        notify();
    }

    public synchronized void requestVote() {
        if (!bufferIsEmpty) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("GameMonitor wait() failed.");
                e.printStackTrace();
            }
        }
        buffer = new VoteRequest();
        bufferIsEmpty = false;
        notify();
    }

    public synchronized void requestGuess() {
        if (!bufferIsEmpty) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("GameMonitor wait() failed.");
                e.printStackTrace();
            }
        }
        buffer = new GuessRequest();
        bufferIsEmpty = false;
        notify();
    }


    //-----Requests definition-----

    private interface Request {

        public void handle(GameController controller);

    }

    private class InitRequest implements Request {
        private String word;
        private Boolean isMrwhite;
        private List<String> players;

        public InitRequest(String word, Boolean isMrwhite, List<String> players) {
            this.word = word;
            this.isMrwhite=isMrwhite;
            this.players = players;
        }

        public void handle(GameController controller) {
            controller.init(word, isMrwhite, players);
        }
    }

    private class WordRequest implements Request {
        public void handle(GameController controller) {
            controller.openInput("Write a word : ");
        }
    }

    private class VoteRequest implements Request {
        public void handle(GameController controller) {
            controller.openInput("Vote : ");
        }
    }

    private class GuessRequest implements Request {
        public void handle(GameController controller) {
            controller.openInput("Guess the word : ");
        }
    }
}