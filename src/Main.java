import DatabaseSetup.DatabaseGargoyle;
import boundary.GodController;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();
        databaseGargoyle.createTables();
        databaseGargoyle.destroyConnection();

        //Load UI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("boundary/fxml/main.fxml"));
        GodController godController = new GodController(primaryStage);
        godController.initializeObservers();
        loader.setController(godController);
        Parent root = loader.load();
        primaryStage.setTitle("B&W Path Finding");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMaximized(true);
        primaryStage.show();

        //everything bellow is for testing only
        Stage stage = new Stage();
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        browser.loadHTML("<iframe src=\"https://www.google.com/maps/embed?pb=!1m0!4v1512948843494!6m8!1m7!1sCAoSLEFGMVFpcE8wMDFHcHZoNS1YOXgwWXRNNUdCWXNsQWN0T1F2SjI2OVdRTXBl!2m2!1d42.33705814724588!2d-71.10651595337299!3f306.1673401875984!4f0!5f0.7820865974627469\" width=\"600\" height=\"450\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>");
        //browser.loadURL("https://www.google.com/maps/@42.3370581,-71.106516,3a,75y,306.17h,90t/data=!3m8!1e1!3m6!1sAF1QipO001Gpvh5-X9x0YtM5GBYslActOQvJ269WQMpe!2e10!3e11!6shttps:%2F%2Flh5.googleusercontent.com%2Fp%2FAF1QipO001Gpvh5-X9x0YtM5GBYslActOQvJ269WQMpe%3Dw203-h100-k-no-pi0-ya314.68118-ro-0-fo100!7i12000!8i6000");
        /*
        WebView wv = new WebView();
        //wv.getEngine().load("https://maps.google.com");
        wv.getEngine().loadContent("<iframe src=\"https://www.google.com/maps/embed?pb=!1m0!4v1512948843494!6m8!1m7!1sCAoSLEFGMVFpcE8wMDFHcHZoNS1YOXgwWXRNNUdCWXNsQWN0T1F2SjI2OVdRTXBl!2m2!1d42.33705814724588!2d-71.10651595337299!3f306.1673401875984!4f0!5f0.7820865974627469\" width=\"600\" height=\"450\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>");
        Scene scene = new Scene(wv);*/
        Scene scene = new Scene(browserView);
        stage.setTitle("Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}