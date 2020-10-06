package GUI;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /* private Stage primaryStage;
    private AnchorPane mainLayout;
        @Override
        public void start(Stage primaryStage) throws Exception{
                this.primaryStage=primaryStage;
                this.primaryStage.setTitle("Undercover App");
                showMainView();
                primaryStage.show();

        }

        private void showMainView() throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("MainView.fxml"));
            mainLayout = loader.load();
        }


        public static void main(String[] args){
            Application.launch(Main.class, args);
        }
        */

        public static void main(String[] args){
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("/Undercover.fxml"));
            primaryStage.setTitle("Undercover");
            primaryStage.setScene(new Scene(root,1080,720));
            primaryStage.show();

        }

    }



