package boundary.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainSceneController {
    @FXML
    public void goToLogin(Pane p) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/loginScene.fxml"));
        AnchorPane login = (AnchorPane) loader.load();
        Scene primaryScene = p.getScene();
        primaryScene.setRoot(login);
    }
}