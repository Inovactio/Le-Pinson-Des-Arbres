import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

public class GameController {

    @FXML
    private TextField input, guessMrWhiteInput;
    @FXML
    private Button send, voteButton,vote;
    @FXML
    private Label inputInfo, pseudoPlayer1, pseudoPlayer2, pseudoPlayer3, pseudoPlayer4, pseudoPlayer5, pseudoPlayer6, givenWord ;
    @FXML
    private MenuButton imposteurMenu, mrWhiteMenu;
    @FXML
    private ListView<String> listeMotJoueur1,listeMotJoueur2,listeMotJoueur3,listeMotJoueur4,listeMotJoueur5,listeMotJoueur6;

    private Client client;
    private Role role;
    private List<String> players;
    private Label usernamesGame[];
    private ListView<String> listeMotList[];

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

    public void init(String word, Boolean isMrwhite, List<String> players) {
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

    private void initListeMots(){
        listeMotList = new ListView[6];
        listeMotList[0] = listeMotJoueur1;
        listeMotList[1] = listeMotJoueur2;
        listeMotList[2] = listeMotJoueur3;
        listeMotList[3] = listeMotJoueur4;
        listeMotList[4] = listeMotJoueur5;
        listeMotList[5] = listeMotJoueur6;

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

    @FXML
    public void switchToVoteScene(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vote.fxml"));
        loader.setController(client.getGameController());
        try {
            Parent root = loader.load();
            Stage stage = (Stage) voteButton.getScene().getWindow();
            Scene scene = new Scene(root,1080,720);
            stage.setScene(scene);
            initListeMots();
            for(int i=0;i<6;i++){
                imposteurMenu.getItems().add(new MenuItem(usernamesGame[i].getText()));
                mrWhiteMenu.getItems().add(new MenuItem(usernamesGame[i].getText()));
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void giveGameUpdate(String word, int playerIndex)throws RemoteException {
        listeMotList[playerIndex].getItems().add(word);
    }
}