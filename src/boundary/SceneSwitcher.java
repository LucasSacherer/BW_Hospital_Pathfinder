package boundary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class SceneSwitcher {

    private final String mainLoc = "/boundary/fxml/main.fxml";
    private final String loginLoc = "/boundary/fxml/loginScene.fxml";
    private final String adminHubLoc = "/boundary/fxml/adminHub.fxml";
    private final String requestLoc = "/boundary/fxml/staffRequest.fxml";
    private final String adminLogLoc = "/boundary/fxml/adminLog.fxml";
    private final String adminRequestLoc = "/boundary/fxml/adminRequest.fxml";
    private final String adminEmployeeLoc = "/boundary/fxml/adminEmployee.fxml";
    private final String mapEditLoc = "/boundary/fxml/adminMap.fxml";
    private final String pathfindingLoc = "/boundary/fxml/pathfinding.fxml";

    public void switchScene(GodController g, Pane from, String to) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(to));
        loader.setController(g);
        AnchorPane mainScene = (AnchorPane) loader.load();
        Scene primaryScene = from.getScene();
        primaryScene.setRoot(mainScene);
    }

    public void toMain(GodController g, Pane from) throws IOException { switchScene(g, from, mainLoc); }

    public void toLogin(GodController g, Pane from) throws IOException { switchScene(g, from, loginLoc); }

    public void toStaffRequests(GodController g, Pane from) throws IOException { switchScene(g, from, requestLoc); }

    public void toAdminHub(GodController g, Pane from) throws IOException { switchScene(g, from, adminHubLoc); }

    public void toAdminLog(GodController g, Pane from) throws IOException { switchScene(g, from, adminLogLoc); }

    public void toAdminRequests(GodController g, Pane from) throws IOException { switchScene(g, from, adminRequestLoc); }

    public void toAdminMap(GodController g, Pane from) throws IOException { switchScene(g, from, mapEditLoc); }

    public void toAdminEmployee(GodController g, Pane from) throws IOException { switchScene(g, from, adminEmployeeLoc); }

    public void toPathfinding(GodController g, Pane from) throws IOException { switchScene(g, from, pathfindingLoc); }
}