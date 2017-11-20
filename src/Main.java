import DatabaseSetup.DatabaseGargoyle;
import boundary.GodController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();
        databaseGargoyle.createTables();
        databaseGargoyle.destroyConnection();
    }
    /*
        //Load UI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("boundary/fxml/main.fxml"));
        GodController godController = new GodController();
        loader.setController(godController);
        Parent root = loader.load();
        primaryStage.setTitle("B&W Path Finding");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }*/
}