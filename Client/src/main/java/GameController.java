import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.util.Set;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Label;

public class GameController {

    @FXML
    private TextField input;
    @FXML
    private Button send;
    @FXML
    private Label inputInfo, pseudoPlayer1, pseudoPlayer2, pseudoPlayer3, pseudoPlayer4, pseudoPlayer5, pseudoPlayer6, givenWord;

    private Client client;
    private Role role;
    private Set<String> players;
    private Label usernamesGame[];

    public GameController(Client client) {
        this.client = client;
    }

    public synchronized void openInput(String message) {
        inputInfo.setText(message);
        inputInfo.setVisible(true);
        input.setEditable(true);
        send.setDisable(false);
    }

    public synchronized void closeInput() {
        inputInfo.setVisible(false);
        input.setEditable(false);
        send.setDisable(true);
    }

    public void init(String word, Role role, Set<String> players) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        loader.setController(client.getGameController());
        try {
            Parent root = loader.load();
            Stage stage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
            Scene scene = new Scene(root,1080,720);
            stage.setScene(scene);
            givenWord.setText(word);
            initUsernamesGame();
            this.role = role;
            this.players = players;

            int i = 0;
            for (String player : players) {
                usernamesGame[i].setText(player);
                usernamesGame[i].setVisible(true);
                i++;
            }

            for (int j = i; j<=5; j++) {
                usernamesGame[j].setVisible(false);
            }

            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        
    }

    private void initUsernamesGame() {
        usernamesGame = new Label[6];
        usernamesGame[0] = pseudoPlayer1;
        usernamesGame[1] = pseudoPlayer2;
        usernamesGame[2] = pseudoPlayer3;
        usernamesGame[3] = pseudoPlayer4;
        usernamesGame[4] = pseudoPlayer5;
        usernamesGame[5] = pseudoPlayer6;
    }

    @FXML
    private synchronized void sendButtonAction(ActionEvent event) throws Exception {
        String word = input.getText();
        if (word.length()!=0 && !word.equals(givenWord)) {
            client.sendWord(word);
            closeInput();
        } else {
            inputInfo.setText("Please write a valid word : ");
        }
    }
}