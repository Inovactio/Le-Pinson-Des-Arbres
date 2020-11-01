import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

        private static String valPseudoJoueur1;
        private static final String SERVER_ADDRESS = "//localhost:8090/undercover";
        private static Client client;

        public static String getValPseudoJoueur1(){
            return valPseudoJoueur1;
        }

        public static void setValPseudoJoueur1(String valeur){
            valPseudoJoueur1=valeur;
        }
        public static void main(String[] args){
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception{
            client = new Client(SERVER_ADDRESS);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Undercover.fxml"));
            loader.setController(client.getController());
            Parent root = loader.load();
            primaryStage.setTitle("Undercover");
            primaryStage.setScene(new Scene(root,1080,720));
            primaryStage.show();

        }

    }



