import java.util.Set;

import javafx.application.Platform;

public class GameMonitor {

    private GameController gameController;
    private Request buffer;
    private boolean bufferIsEmpty;
    private String givenWord;
    private Role role;
    private Set<String> players;

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

        Thread thread;
        switch (buffer) {
            case GUESS:
                thread = new Thread(() -> {gameController.openInput("Guess the word :");});
                Platform.runLater(thread);
                break;
            case VOTE:
                thread = new Thread(() -> {gameController.openInput("Vote for a player :");});
                Platform.runLater(thread);
                break;
            case WORD:
                thread = new Thread(() -> {gameController.openInput("Write a word :");});
                Platform.runLater(thread);
                break;
            case INIT:
                thread = new Thread(() -> {gameController.init(givenWord, role, players);});
                Platform.runLater(thread);
                break;
            default:
                break;
        }

        bufferIsEmpty = true;
        notify();
        getRequest();
    }

    public synchronized void init(String word, Role role, Set<String> players) {
        if (!bufferIsEmpty) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("GameMonitor wait() failed.");
                e.printStackTrace();
            }
        }

        givenWord = word;
        this.role = role;
        this.players = players;

        buffer = Request.INIT;
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
        buffer = Request.WORD;
        bufferIsEmpty = false;
        notify();
    }

    private enum Request {
        WORD, VOTE, GUESS, INIT
    }
}