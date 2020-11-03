import javafx.fxml.FXML;

import java.util.Set;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class GameController {

    @FXML
    private TextField input;
    @FXML
    private Button send;
    @FXML
    private Label inputInfo;

    private Client client;
    private String givenWord;
    private Role role;

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
        givenWord = word;
        this.role = role;
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