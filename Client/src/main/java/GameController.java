import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private Label inputInfo, pseudoPlayer1, pseudoPlayer2, pseudoPlayer3, pseudoPlayer4, pseudoPlayer5, pseudoPlayer6, gameGivenWordLabel ;
    @FXML
    private MenuButton imposteurMenu, mrWhiteMenu;
    @FXML
    private ListView<String> listeMotJoueur1,listeMotJoueur2,listeMotJoueur3,listeMotJoueur4,listeMotJoueur5,listeMotJoueur6;

    private Client client;
    private boolean isMrWhite;
    private List<String> players;
    private Label usernamesGame[];
    private ObservableList<String> obsListeMotsJoueurs[];
    private ListView<String> listeMotsJoueurs[];

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
            if(isMrwhite){
                gameGivenWordLabel.setText("you are Mister White");
            }
            else{
                gameGivenWordLabel.setText(" your word is : " + word);
            }

            initUsernamesGame();
            initListeMots();
            this.isMrWhite = isMrwhite;
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
        listeMotsJoueurs= new ListView[6];
        listeMotsJoueurs[0]= listeMotJoueur1;
        listeMotsJoueurs[1]= listeMotJoueur2;
        listeMotsJoueurs[2]= listeMotJoueur3;
        listeMotsJoueurs[3]= listeMotJoueur4;
        listeMotsJoueurs[4]= listeMotJoueur5;
        listeMotsJoueurs[5]= listeMotJoueur6;
        obsListeMotsJoueurs = new ObservableList[6];
        for (int i=0;i<6;i++){
            obsListeMotsJoueurs[i]=FXCollections.observableArrayList();
        }
        listeMotsJoueurs[0].setItems(obsListeMotsJoueurs[0]);
        listeMotsJoueurs[1].setItems(obsListeMotsJoueurs[1]);
        listeMotsJoueurs[2].setItems(obsListeMotsJoueurs[2]);
        listeMotsJoueurs[3].setItems(obsListeMotsJoueurs[3]);
        listeMotsJoueurs[4].setItems(obsListeMotsJoueurs[4]);
        listeMotsJoueurs[5].setItems(obsListeMotsJoueurs[5]);


    }



    @FXML
    private synchronized void sendButtonAction(ActionEvent event) throws Exception {
        String word = input.getText();
        if (word.length()!=0 && !word.equals(gameGivenWordLabel.getText())) {
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
            Stage stage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
            Scene scene = new Scene(root,1080,720);
            stage.setScene(scene);
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
        obsListeMotsJoueurs[playerIndex].add(word);

    }
}