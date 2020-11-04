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

import java.io.IOException;
import java.util.Set;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class Controller {

    @FXML
    private Button connect, createLobby, showLobbies, playGame, refreshButton, joinToMainMenuButton,
            lobbyToMainMenuButton;
    @FXML
    private TextField usernameTextField, labelmots, labeltours, tempstours, nbimposteurs;
    @FXML
    private ImageView joueur1, joueur2, joueur3, joueur4, joueur5, joueur6;
    @FXML
    private Label pseudoJoueur1, pseudoJoueur2, pseudoJoueur3, pseudoJoueur4, pseudoJoueur5, pseudoJoueur6, nbJoueurs;
    @FXML
    private TableView lobbiesList;

    private Client client;
    private Label usernamesLobby[];
    private ImageView imagesLobby[];

    public Controller() {
    }

    public Controller(Client client) {
        this.client = client;
    }

    // -----Interactions-----

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
            Scene scene = new Scene(root, 1080, 720);
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
            Scene scene = new Scene(root, 1080, 720);
            stage.setScene(scene);
            initUsernamesLobby();
            initImagesLobby();
            refreshLobby(client.getUsername());
            stage.show();
        }

    }

    @FXML
    private void showLobbies(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/join.fxml"));
        loader.setController(client.getController());
        Parent root = loader.load();
        Stage stage = (Stage) showLobbies.getScene().getWindow();
        Scene scene = new Scene(root, 1080, 720);
        stage.setScene(scene);
        refreshLobbiesList(client.getLobbies());
        stage.show();
    }

    @FXML
    private void playGameButtonAction(ActionEvent event) throws Exception {
        client.launchGame();
    }

    @FXML
    public void refreshLobbiesList() throws Exception {
        refreshLobbiesList(client.getLobbies());
    }

    // -----Back buttons-----

    @FXML
    public void joinToMainMenu(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
        loader.setController(client.getController());
        Parent root = loader.load();
        Stage stage = (Stage) lobbiesList.getScene().getWindow();
        Scene scene = new Scene(root, 1080, 720);
        stage.setScene(scene);
        stage.show();
    }

    public void lobbyToMainMenu(ActionEvent event) throws Exception {
        client.quit();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
        loader.setController(client.getController());
        Parent root = loader.load();
        Stage stage = (Stage) lobbyToMainMenuButton.getScene().getWindow();
        Scene scene = new Scene(root, 1080, 720);
        stage.setScene(scene);
        stage.show();
    }

    // -----Lobby tuning-----

    @FXML
    private void incrementNumberOfWords(ActionEvent event) throws Exception {
        int value = Integer.parseInt(labelmots.getText()) + 1;
        labelmots.setText(Integer.toString(value));
    }

    @FXML
    private void decrementNumberOfWords(ActionEvent event) throws Exception {
        int value = Integer.parseInt(labelmots.getText()) - 1;
        labelmots.setText(Integer.toString(value));
    }

    @FXML
    private void incrementNumberOfRounds(ActionEvent event) throws Exception {
        int value = Integer.parseInt(labeltours.getText()) + 1;
        labeltours.setText(Integer.toString(value));
    }

    @FXML
    private void decrementNumberOfRounds(ActionEvent event) throws Exception {
        int value = Integer.parseInt(labeltours.getText()) - 1;
        labeltours.setText(Integer.toString(value));
    }

    @FXML
    private void incrementTimeOfRounds(ActionEvent event) throws Exception {
        int value = Integer.parseInt(tempstours.getText()) + 1;
        tempstours.setText(Integer.toString(value));
    }

    @FXML
    private void decrementTimeOfRounds(ActionEvent event) throws Exception {
        int value = Integer.parseInt(tempstours.getText()) - 1;
        tempstours.setText(Integer.toString(value));
    }

    @FXML
    private void incrementNumberOfImposters(ActionEvent event) throws Exception {
        int value = Integer.parseInt(nbimposteurs.getText()) + 1;
        nbimposteurs.setText(Integer.toString(value));
    }

    @FXML
    private void decrementNumberOfImposters(ActionEvent event) throws Exception {
        int value = Integer.parseInt(nbimposteurs.getText()) - 1;
        nbimposteurs.setText(Integer.toString(value));
    }

    // -----Intermediate methods-----

    private void initUsernamesLobby() {
        usernamesLobby = new Label[6];
        usernamesLobby[0] = pseudoJoueur1;
        usernamesLobby[1] = pseudoJoueur2;
        usernamesLobby[2] = pseudoJoueur3;
        usernamesLobby[3] = pseudoJoueur4;
        usernamesLobby[4] = pseudoJoueur5;
        usernamesLobby[5] = pseudoJoueur6;
    }

    private void initImagesLobby() {
        imagesLobby = new ImageView[6];
        imagesLobby[0] = joueur1;
        imagesLobby[1] = joueur2;
        imagesLobby[2] = joueur3;
        imagesLobby[3] = joueur4;
        imagesLobby[4] = joueur5;
        imagesLobby[5] = joueur6;
    }

    // Creation refresh
    private void refreshLobby(String creator) {
        pseudoJoueur1.setText(creator);
        joueur2.setVisible(false);
        joueur3.setVisible(false);
        joueur4.setVisible(false);
        joueur5.setVisible(false);
        joueur6.setVisible(false);
        nbJoueurs.setText("Joueurs (1/6)");
    }

    // Join refresh
    public void refreshLobby(Set<String> players) {
        int i = 0;
        for (String player : players) {
            usernamesLobby[i].setText(player);
            usernamesLobby[i].setVisible(true);
            imagesLobby[i].setVisible(true);
            i++;
        }

        for (int j = i; j <= 5; j++) {
            usernamesLobby[j].setVisible(false);
            imagesLobby[j].setVisible(false);
        }

        nbJoueurs.setText("Joueurs (" + players.size() + "/6)");
    }

    private void refreshLobbiesList(Set<RoomInfo> rooms) {

        ObservableList<RoomInfo> roomsList = FXCollections.observableArrayList(rooms);

        Callback<TableColumn<RoomInfo, Void>, TableCell<RoomInfo, Void>> cellFactory = new Callback<TableColumn<RoomInfo, Void>, TableCell<RoomInfo, Void>>() {
            @Override
            public TableCell<RoomInfo, Void> call(final TableColumn<RoomInfo, Void> param) {
                final TableCell<RoomInfo, Void> cell = new TableCell<RoomInfo, Void>() {

                    private final Button btn = new Button("Join");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            RoomInfo room = getTableView().getItems().get(getIndex());
                            joinLobby(room.getOwner());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        lobbiesList.getVisibleLeafColumn(0).setCellValueFactory(new PropertyValueFactory<>("owner"));
        lobbiesList.getVisibleLeafColumn(1).setCellValueFactory(new PropertyValueFactory<>("capacity"));
        lobbiesList.getVisibleLeafColumn(2).setCellFactory(cellFactory);
        lobbiesList.setItems(roomsList);
    }

    private void joinLobby(String owner) {
        System.out.println("Trying to join room owned by " + owner);
        try {
            Set<String> players = client.connectToLobby(owner);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
            loader.setController(client.getController());
            Parent root = loader.load();
            Stage stage = (Stage) lobbiesList.getScene().getWindow();
            Scene scene = new Scene(root, 1080, 720);
            stage.setScene(scene);
            initUsernamesLobby();
            initImagesLobby();
            refreshLobby(players);
            playGame.setVisible(false);
            stage.show();
        } catch (RoomFullException e) {
            Alert alert = new Alert(AlertType.ERROR, "This room is full.");
            alert.show();
        } catch (GameLaunchedException e) {
            Alert alert = new Alert(AlertType.ERROR, "The game is already launched.");
            alert.show();
        } catch (RoomInexistentException e) {
            Alert alert = new Alert(AlertType.ERROR, "This room doesn't exist anymore.");
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void kick() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
        loader.setController(client.getController());
        try {
            Parent root = loader.load();
            Stage stage = (Stage) lobbyToMainMenuButton.getScene().getWindow();
            Scene scene = new Scene(root,1080,720);
            stage.setScene(scene);
            stage.show();
            Alert alert = new Alert(AlertType.WARNING, "The owner left the lobby.");
            alert.show();
        } catch (IOException e) {
            System.out.println("Kick failed.");
            e.printStackTrace();
        }
    }

}
