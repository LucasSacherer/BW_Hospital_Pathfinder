package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.TextualDirections;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NavigationDrawerController {

    private TextualDirections textualDirections = new TextualDirections();
    private MainSceneController m;
    private MapNavigationFacade mapNavigationFacade;
    private Object currentPath = null;

    @FXML
    private AnchorPane originPane, destinationPane;

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private JFXListView listView;


    public NavigationDrawerController(MapNavigationFacade mapNavigationFacade) {
        this.mapNavigationFacade = mapNavigationFacade;
        JFXListView directions = new JFXListView();
//        directions.setItems(textualDirections.getTextDirections(currentPath));
    }

    public void setMainSceneController(MainSceneController m) {
        this.m = m;
    }

    @FXML
    private void initialize() {

    }

    @FXML
    public void closeDrawer() {

    }

    @FXML
    public void navigate() {

    }

    @FXML
    public void reversePath() {

    }

    @FXML
    public void allFloors() {

    }

    @FXML
    public void previousFloor() {

    }

    @FXML
    private void nextFloor() {

    }
}
