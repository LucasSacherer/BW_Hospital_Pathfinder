package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.DirectoryController;
import MapNavigation.MapNavigationFacade;
import Pathfinding.TextualDirections;
import boundary.AutoCompleteTextField;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NavigationDrawerController {
    private JFXDrawer drawer;
    private DirectoryController dc;
    private TextualDirections textualDirections = new TextualDirections();
    private MainSceneController m;
    private MapNavigationFacade mapNavigationFacade;
    private Object currentPath = null;
    private Node originNode, destinationNode;
    private AutoCompleteTextField originTextField, destinationTextField;
    private MainSceneController mainSceneController;

    @FXML
    private AnchorPane originPane, destinationPane;

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private JFXListView listView;


    public NavigationDrawerController(JFXDrawer drawer, MapNavigationFacade mapNavigationFacade, DirectoryController dc) {
        this.drawer = drawer;
        this.mapNavigationFacade = mapNavigationFacade;
        this.dc = dc;
        JFXListView directions = new JFXListView();
//        directions.setItems(textualDirections.getTextDirections(currentPath));
    }

    @FXML
    private void initialize() {
//        if (originNode == null) originNode = mapNavigationFacade.getDefaultNode();

        originTextField = new AutoCompleteTextField(dc, m, true);
        originPane.getChildren().add(originTextField);
        originTextField.setPromptText("Kiosk Location");

        destinationTextField = new AutoCompleteTextField(dc, m, false);
        destinationPane.getChildren().add(destinationTextField);
        destinationTextField.setPromptText("Search for a Destination");
    }

    @FXML
    public void closeDrawer() {
        originTextField.clear();
        destinationTextField.clear();
        originNode = mapNavigationFacade.getDefaultNode();
        destinationNode = null;
        drawer.close();
        drawer.toBack();
    }

    @FXML
    public void navigate() throws IOException {
        if (originNode != null && destinationNode != null)
            m.navigate(originNode, destinationNode);
        else
            System.out.println("Origin: " + originNode + " Destination: " + destinationNode);
    }

    @FXML
    public void reversePath() {
       //TODO reverse the path over here
    }

    @FXML
    public void allFloors() {

    }

    @FXML
    public void currentFloor() {

    }

    @FXML
    public void previousFloor() {

    }

    @FXML
    private void nextFloor() {

    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
}
