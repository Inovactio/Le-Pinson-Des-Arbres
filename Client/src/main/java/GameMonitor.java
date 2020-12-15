import java.util.List;

import javafx.application.Platform;

/**
 * Monitor of a Client
 */
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

    /**
     * Get the current Request
     */
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

    /**
     * Put a InitRequest in buffer
     * @param word attribute to this client
     * @param isMrWhite true if the server choose you as MrWhite, else false
     * @param players list of player's name
     */
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

    /**
     * Put a word request in the buffer
     */
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

    /**
     * Put a updateEndOfGameRequest in buffer
     * @param imposterNameReveal name(s) of the imposter(s)
     * @param imposterWordReveal word of the imposter(s)
     * @param mrWhiteNameReveal name of the MrWhite
     * @param citizensWordReveal word of the citizens
     * @param gameResultReveal result of the game
     */
    public synchronized void requestUpdateEndOfGame(String imposterNameReveal, String imposterWordReveal, String mrWhiteNameReveal, String citizensWordReveal, String gameResultReveal){
        if (!bufferIsEmpty) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("GameMonitor wait() failed.");
                e.printStackTrace();
            }
        }
        buffer = new UpdateEndOfGameRequest(imposterNameReveal, imposterWordReveal, mrWhiteNameReveal, citizensWordReveal, gameResultReveal);
        bufferIsEmpty = false;
        notify();
    }

    /**
     * Put a SwitchToVoteSceneRequest in the buffer
     */
    public synchronized void requestSwitchToVoteScene(){
        if (!bufferIsEmpty) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("GameMonitor wait() failed.");
                e.printStackTrace();
            }
        }
        buffer = new SwitchToVoteSceneRequest();
        bufferIsEmpty = false;
        notify();
    }

    /**
     * Put a VoteRequest in the buffer
     */
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

    /**
     * Put an UpdateRequest in the buffer
     * @param word is the word write by the player
     * @param playerIndex is the inder of the player
     */
    public synchronized void giveGameUpdate(String word, int playerIndex) {
        if (!bufferIsEmpty) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("GameMonitor wait() failed.");
                e.printStackTrace();
            }
        }
        buffer = new UpdateRequest(word, playerIndex);
        bufferIsEmpty = false;
        notify();
    }

    //-----Requests definition-----

    /**
     * Interface for Request
     */
    private interface Request {

        public void handle(GameController controller);

    }

    /**
     * Request for initialization of the game containing, your word, if you're MrWhite or not, and the name of other players
     */
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

    /**
     * Request for a client to send a word
     */
    private class WordRequest implements Request {
        public void handle(GameController controller) {
            controller.openInput("Write a word : ");
        }
    }

    /**
     * Requet to switch to the vote Scene
     */
    private class SwitchToVoteSceneRequest implements Request {
        public void handle(GameController controller){
            controller.switchToVoteScene();
        }
    }

    /**
     * Request for a vote a the end of the game.
     */
    private class VoteRequest implements Request {
        public void handle(GameController controller) {
            controller.openInput("Vote : ");
        }
    }

    /**
     * Request for word guess by a player.
     */
    private class UpdateRequest implements Request {
        private String word;
        private int playerIndex;

        public UpdateRequest(String word, int playerIndex) {
            this.word = word;
            this.playerIndex = playerIndex;
        }

        public void handle(GameController controller) {
            controller.giveGameUpdate(word, playerIndex);
        }
    }


    /**
     * Request for the end of the game with the reveal of imposters, their words, who is MrWhite , citizen's word and the result of the game.
     */
    private class UpdateEndOfGameRequest implements Request {
        private String imposterNameReveal;
        private String imposterWordReveal;
        private String mrWhiteNameReveal;
        private String citizensWordReveal;
        private String gameResultReveal;

        public UpdateEndOfGameRequest(String imposterNameReveal, String imposterWordReveal, String mrWhiteNameReveal, String citizensWordReveal, String gameResultReveal){
            this.imposterNameReveal=imposterNameReveal;
            this.imposterWordReveal=imposterWordReveal;
            this.mrWhiteNameReveal=mrWhiteNameReveal;
            this.citizensWordReveal=citizensWordReveal;
            this.gameResultReveal=gameResultReveal;
        }
        public void handle(GameController controller){controller.updateEndOfGameInfo(imposterNameReveal,imposterWordReveal,mrWhiteNameReveal,citizensWordReveal,gameResultReveal);}
    }




    
}