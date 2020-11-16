import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {


        private static Client client;

        public static void main(String[] args){
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception{
            client = new Client();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ipconfig.fxml"));
            loader.setController(client.getController());
            Parent root = loader.load();
            primaryStage.setTitle("Undercover");
            primaryStage.setScene(new Scene(root,1080,720));
            primaryStage.show();
        }

        @Override
        public void stop() throws Exception {
            if (client.getCurrentRoom()!=null) client.quit();
            if (client.getUsername()!=null) client.freeUsername();
        }

    }



