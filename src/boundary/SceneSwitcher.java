package boundary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class SceneSwitcher {
    public void switchScene(GodController g, Pane from, String to) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(to));

        loader.setController(g);

        AnchorPane mainScene = (AnchorPane) loader.load();
        Scene primaryScene = from.getScene();
        primaryScene.setRoot(mainScene);
    }
}
