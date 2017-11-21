package boundary.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    public void goToMainScreen(Pane p) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/main.fxml"));
        AnchorPane mainScene = (AnchorPane) loader.load();
        Scene primaryScene = p.getScene();
        primaryScene.setRoot(mainScene);
    }

    @FXML
    public void goToRequests(Pane p) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/staffRequest.fxml"));
        AnchorPane requests = (AnchorPane) loader.load();
        Scene primaryScene = p.getScene();
        primaryScene.setRoot(requests);
    }

    @FXML
    public void goToAdminHub(Pane p) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/adminHub.fxml"));
        AnchorPane adminHub = (AnchorPane) loader.load();
        Scene primaryScene = p.getScene();
        primaryScene.setRoot(adminHub);
    }
}
