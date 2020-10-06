package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class Controller {

    @FXML
    private Button createLobby,joinLobby;

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

}
