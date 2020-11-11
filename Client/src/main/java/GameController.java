import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.rmi.RemoteException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

public class GameController {

    @FXML
    private TextField input, guessMrWhiteInput;
    @FXML
    private Button send, voteButton,vote;
    @FXML
    private Label inputInfo, pseudoPlayer1, pseudoPlayer2, pseudoPlayer3, pseudoPlayer4, pseudoPlayer5, pseudoPlayer6, gameGivenWordLabel, whoIsMrWhiteLabel,imposterRevealLabel, imposterWordRevealLabel, mrWhiteRevealLabel, citizensWordRevealLabel, guessMrWhiteLabel, guessImposterLabel;
    @FXML
    private MenuButton imposteurMenu, mrWhiteMenu;
    @FXML
    private ListView<String> listeMotJoueur1,listeMotJoueur2,listeMotJoueur3,listeMotJoueur4,listeMotJoueur5,listeMotJoueur6;

    private Client client;
    private boolean isMrWhite;
    private String givenWord;
    private int myIndex;
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
            givenWord = word;
            this.isMrWhite = isMrwhite;

            int i = 0;
            String myUsername = client.getUsername();
            for (String player : players) {
                if (player.equals(myUsername)) {
                    myIndex = i;
                }
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
        input.clear();
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
            Stage stage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
            Scene scene = new Scene(root,1080,720);
            stage.setScene(scene);
            if(isMrWhite){
                whoIsMrWhiteLabel.setText("Devinez le mot :");
                mrWhiteMenu.setVisible(false);
                mrWhiteMenu.setDisable(true);
                guessMrWhiteLabel.setVisible(false);

            }
            else {
                whoIsMrWhiteLabel.setText("Qui est Mr White ?");
                guessMrWhiteInput.setDisable(true);
                guessMrWhiteInput.setVisible(false);
            }
            for(int i=0;i<6;i++){
                imposteurMenu.getItems().add(new MenuItem(usernamesGame[i].getText()));
                if(!isMrWhite)
                    mrWhiteMenu.getItems().add(new MenuItem(usernamesGame[i].getText()));
            }
            pseudoPlayer1.setText(usernamesGame[0].getText());
            pseudoPlayer2.setText(usernamesGame[1].getText());
            pseudoPlayer3.setText(usernamesGame[2].getText());
            pseudoPlayer4.setText(usernamesGame[3].getText());
            pseudoPlayer5.setText(usernamesGame[4].getText());
            pseudoPlayer6.setText(usernamesGame[5].getText());

            initMenuButtonImposter();
            if(!isMrWhite)
                initMenuButtonMrWhite();

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    private void initMenuButtonImposter() throws Exception{
        imposteurMenu.getItems().get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                guessImposterLabel.setText(imposteurMenu.getItems().get(0).getText());
            }
        });

        imposteurMenu.getItems().get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                guessImposterLabel.setText(imposteurMenu.getItems().get(1).getText());
            }
        });

        imposteurMenu.getItems().get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                guessImposterLabel.setText(imposteurMenu.getItems().get(2).getText());
            }
        });

        imposteurMenu.getItems().get(3).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                guessImposterLabel.setText(imposteurMenu.getItems().get(3).getText());
            }
        });

        imposteurMenu.getItems().get(4).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                guessImposterLabel.setText(imposteurMenu.getItems().get(4).getText());
            }
        });

        imposteurMenu.getItems().get(5).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                guessImposterLabel.setText(imposteurMenu.getItems().get(5).getText());
            }
        });
    }

    private void initMenuButtonMrWhite() throws Exception{
        mrWhiteMenu.getItems().get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                guessMrWhiteLabel.setText(mrWhiteMenu.getItems().get(0).getText());
            }
        });

        mrWhiteMenu.getItems().get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                guessMrWhiteLabel.setText(mrWhiteMenu.getItems().get(1).getText());
            }
        });

        mrWhiteMenu.getItems().get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                guessMrWhiteLabel.setText(mrWhiteMenu.getItems().get(2).getText());
            }
        });

        mrWhiteMenu.getItems().get(3).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                guessMrWhiteLabel.setText(mrWhiteMenu.getItems().get(3).getText());
            }
        });

        mrWhiteMenu.getItems().get(4).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                guessMrWhiteLabel.setText(mrWhiteMenu.getItems().get(4).getText());
            }
        });

        mrWhiteMenu.getItems().get(5).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                guessMrWhiteLabel.setText(mrWhiteMenu.getItems().get(5).getText());
            }
        });

    }

    public void giveGameUpdate(String word, int playerIndex) {
        obsListeMotsJoueurs[playerIndex].add(word);
        if (playerIndex == myIndex) {
            closeInput();
            input.clear();
        }
    }

}