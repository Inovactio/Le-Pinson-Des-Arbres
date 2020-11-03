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
                
                break;
            default:
                break;
        }

        bufferIsEmpty = true;
        notify();
        getRequest();
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