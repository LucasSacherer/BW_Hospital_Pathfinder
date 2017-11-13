import controller.MapDisplayController;
import entity.DatabaseSetup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Load DB
        DatabaseSetup db = new DatabaseSetup();
        try {
            db.createDatbase();
        }catch (Exception e){
            System.out.println("Database is already created on this machine");
        }

        //Load UI
        Parent root = FXMLLoader.load(getClass().getResource("boundary/main.fxml"));
        primaryStage.setTitle("B&W Path Finding");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}