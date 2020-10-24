package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;



public class Controller {

    @FXML
    private Button createLobby,joinLobby;
    @FXML
    private TextField labelmots, labeltours, tempstours,nbimposteurs;


    

    @FXML
    private void createLobbyButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) createLobby.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/lobby.fxml"));
        Scene scene = new Scene(root,1080,720);
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

}
