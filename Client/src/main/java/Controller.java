import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.HashSet;
import java.util.Set;


public class Controller {

    @FXML
    private Button connect, createLobby, joinLobby, playGame;
    @FXML
    private TextField usernameTextField, labelmots, labeltours, tempstours,nbimposteurs;
    @FXML
    private ImageView joueur1, joueur2, joueur3, joueur4, joueur5, joueur6;
    @FXML
    private Label pseudoJoueur1,pseudoJoueur2,pseudoJoueur3,pseudoJoueur4,pseudoJoueur5,pseudoJoueur6, nbJoueurs;
    
    private Client client;

    public Controller() {}

    public Controller(Client client) {
        this.client = client;
    }

    @FXML
    private void takeUsername(ActionEvent event) throws Exception {

        String username = usernameTextField.getText();

        if (username.equals("")) {
            Alert alert = new Alert(AlertType.ERROR, "Please enter a username.");
            alert.show();
            return;
        }

        if (client.getServer().takeUsername(username)) {
            client.setUsername(username);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
            loader.setController(client.getController());
            Parent root = loader.load();
            Stage stage = (Stage) connect.getScene().getWindow();
            Scene scene = new Scene(root,1080,720);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Username already taken.");
            alert.show();
        }

    }

    @FXML
    private void createLobbyButtonAction(ActionEvent event) throws Exception {
     
        if (client.getServer().createLobby(client, 6)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
            loader.setController(client.getController());
            Parent root = loader.load();
            Stage stage = (Stage) createLobby.getScene().getWindow();
            Scene scene = new Scene(root,1080,720);
            stage.setScene(scene);
            refreshLobby(client.getUsername());
            stage.show();
        }
       
    }

    @FXML
    private void joinLobbyButtonAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/join.fxml"));
        Stage stage = (Stage) joinLobby.getScene().getWindow();
        Scene scene = new Scene(root,1080,720);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void playGameButtonAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/game.fxml"));
        Scene scene = new Scene(root,1080,720);
        Stage stage = (Stage) playGame.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void incrementNumberOfWords(ActionEvent event) throws Exception {
        int value = Integer.parseInt(labelmots.getText())+1;
        labelmots.setText(Integer.toString(value));
    }

    @FXML
    private void decrementNumberOfWords(ActionEvent event) throws Exception {
        int value = Integer.parseInt(labelmots.getText())-1;
        labelmots.setText(Integer.toString(value));
    }

    @FXML
    private void incrementNumberOfRounds(ActionEvent event) throws Exception {
        int value = Integer.parseInt(labeltours.getText())+1;
        labeltours.setText(Integer.toString(value));
    }

    @FXML
    private void decrementNumberOfRounds(ActionEvent event) throws Exception {
        int value = Integer.parseInt(labeltours.getText())-1;
        labeltours.setText(Integer.toString(value));
    }

    @FXML
    private void incrementTimeOfRounds(ActionEvent event) throws Exception {
        int value = Integer.parseInt(tempstours.getText())+1;
        tempstours.setText(Integer.toString(value));
    }

    @FXML
    private void decrementTimeOfRounds(ActionEvent event) throws Exception {
        int value = Integer.parseInt(tempstours.getText())-1;
        tempstours.setText(Integer.toString(value));
    }

    @FXML
    private void incrementNumberOfImposters(ActionEvent event) throws Exception {
        int value = Integer.parseInt(nbimposteurs.getText())+1;
        nbimposteurs.setText(Integer.toString(value));
    }

    @FXML
    private void decrementNumberOfImposters(ActionEvent event) throws Exception {
        int value = Integer.parseInt(nbimposteurs.getText())-1;
        nbimposteurs.setText(Integer.toString(value));
    }

    //Creation refresh
    private void refreshLobby(String creator) {
        pseudoJoueur1.setText(creator);
        joueur2.setVisible(false);
        joueur3.setVisible(false);
        joueur4.setVisible(false);
        joueur5.setVisible(false);
        joueur6.setVisible(false);
        nbJoueurs.setText("Joueurs (1/6)");
    }

    //Join refresh
    public void refreshLobby(Set<String> players) {
        pseudoJoueur1.setText(Main.getValPseudoJoueur1());
        joueur2.setVisible(false);
        joueur3.setVisible(false);
        joueur4.setVisible(false);
        joueur5.setVisible(false);
        joueur6.setVisible(false);
        nbJoueurs.setText("Joueurs (" + players.size() + "/6)");
    }

}
