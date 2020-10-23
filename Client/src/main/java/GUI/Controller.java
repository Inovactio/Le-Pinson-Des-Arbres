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
    private TextField labelmots, labeltours, tempstours,nbimposteurs;
    private int valuemots = 12;



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
        System.out.println(Integer.toString(valuemots));
        labelmots.setText(Integer.toString(valuemots));
        System.out.println(labelmots.getText());
        int value = Integer.parseInt(labelmots.getText())+1;
        System.out.println(value);
        labelmots.setText(Integer.toString(value));
        System.out.println(labelmots.getText());


    }

    @FXML
    private void test1(ActionEvent event) throws Exception {
        labelmots.setText(Integer.toString(12));
        System.out.println(labelmots.getText()==null);

    }

}
