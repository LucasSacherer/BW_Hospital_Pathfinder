package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import MapNavigation.SearchEngine;
import Pathfinding.PathFindingFacade;
import boundary.AutoCompleteTextField;
import boundary.GodController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import Entity.ErrorController;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainSceneController extends AbstractMapController{
    private SearchEngine searchEngine;
    private DirectorySceneController directorySceneController;
    private Label originField, destinationField;
    private ErrorController errorController = new ErrorController();
    private JFXComboBox searchBar;
    public MainSceneController(GodController g, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum,
                               Label originField, Label destinationField, JFXSlider zoomSlider,
                               DirectorySceneController directorySceneController, AnchorPane searchPane,
                               ScrollPane scrollPane, SearchEngine searchEngine) {
        super(g, m, p, currentFloorNum, zoomSlider, scrollPane);
        this.searchEngine = searchEngine;
        this.originField = originField;
        this.destinationField = destinationField;
        this.directorySceneController = directorySceneController;
        searchBar = new JFXComboBox();
        searchPane.getChildren().add(searchBar);
        initializeSearchBar();
    }

    private void initializeSearchBar() {
        searchBar.setPromptText("Search");
        searchBar.setEditable(true);
        searchBar.setPrefWidth(200);
        EventHandler<KeyEvent> k = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    searchBar.hide();
                }

                if (searchBar.getValue() == null) return;
                searchBar.getItems().clear();
                searchBar.setItems(searchEngine.Search((String) searchBar.getValue()));
                searchBar.show();
            }
        };
        searchBar.setOnKeyPressed(k);
    }



    private boolean checkNullLocations(){
        boolean success = true;
        try {
            origin.equals("");
            destination.equals("");
        }
        catch(NullPointerException e){
            errorController.showError("Please select both a start and end location.");
            success = false;
        }
        return success;
    }

    public void bathroomClicked() throws IOException { findNearest(origin, "REST"); }

    public void infoClicked() throws IOException { findNearest(origin, "INFO"); }

    public void elevatorClicked() throws IOException { findNearest(origin, "ELEV"); }

    public void exitClicked() throws IOException { findNearest(origin, "EXIT"); }

    private void findNearest(Node node, String type) throws IOException {
        if (origin == null) origin = mapNavigationFacade.getDefaultNode();
        System.out.println(origin);
        destination = mapNavigationFacade.getNearestPOI(origin.getXcoord(), origin.getYcoord(), type);
        findPath();
        refreshCanvas();
    }

    public void navigateToHere() throws IOException {
//        boolean success = checkNullLocations();
//        if(success) {
            setDestination();
            findPath();
//        }
    }

    public void setOrigin() {
        super.setOrigin();
        originField.setText(origin.getNodeID());
    }

    public void setOrigin(MouseEvent m) {
        snapToNode(m);
        currentPath = null;
        origin = currentLoc;
        originField.setText(origin.getNodeID());
        refreshCanvas();
    }

    public void setDestination() {
        super.setDestination();
        destinationField.setText(destination.getNodeID());
    }

    public void displayTextDir() throws IOException {
        boolean success = checkNullLocations();
        if(success) {
            currentPath = pathFindingFacade.getPath(origin, destination);
            List<String> writtenDir = pathFindingFacade.getDirections(currentPath);
            String dirMessage = "";
            findPath();
            if (writtenDir.isEmpty()) {
                return;
            }
            for (int i = 0; i < writtenDir.size(); i++) {
                dirMessage += writtenDir.get(i);
                dirMessage += "\n";
            }

            Alert directions = new Alert(AlertType.INFORMATION, dirMessage);
            directions.show();
        }
    }

    public void openDirectory(GodController godController) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/fxml/directory.fxml"));
        loader.setController(directorySceneController);
        Parent root = loader.load();
        Stage directoryStage = new Stage();
        directoryStage.setTitle("B&W Directory");
        directoryStage.setScene(new Scene(root, 900, 600));
        directoryStage.show();
    }

    public void directoryNavigate() {
        //TODO
    }

    public void navigate(Node origin, Node destination) throws IOException {
        this.origin = origin;
        this.destination = destination;
        findPath();
    }

    @Override
    public void findPath() throws IOException {
        godController.mainToPathfinding();
    }

    public Node getOrigin() {
        return origin;
    }

    public Node getDestination() {
        return destination;
    }
}