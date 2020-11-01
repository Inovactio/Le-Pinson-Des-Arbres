import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class PlayerInteractionController {

    @FXML
    private TextField input;
    @FXML
    private Button send;
    @FXML
    private Label inputInfo;

    private IClient client;
    private String givenWord;

    public PlayerInteractionController(IClient client) {
        this.client = client;
    }

    public synchronized void openInput() {
        inputInfo.setText("Write a word : ");
        inputInfo.setVisible(true);
        input.setEditable(true);
        send.setDisable(false);
    }

    public synchronized void closeInput() {
        inputInfo.setVisible(false);
        input.setEditable(false);
        send.setDisable(true);
    }

    @FXML
    private synchronized void sendButtonAction(ActionEvent event) throws Exception {
        String word = input.getText();
        if (word.length()!=0 && !word.equals(givenWord)) {
            client.giveWord(word);
            closeInput();
        } else {
            inputInfo.setText("Please write a valid word : ");
        }
    }
}