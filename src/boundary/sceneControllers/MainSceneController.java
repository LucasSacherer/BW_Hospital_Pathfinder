package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import MapNavigation.SearchEngine;
import Pathfinding.PathFindingFacade;
import Pathfinding.TextualDirections;
import boundary.GodController;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import Entity.ErrorController;

import java.io.IOException;
import java.util.List;

public class MainSceneController extends AbstractMapController{
    private TextualDirections textualDirections = new TextualDirections();
    private SearchEngine searchEngine;
    private DirectorySceneController directorySceneController;
    private JFXComboBox originField, destinationField;
    private ErrorController errorController = new ErrorController();
    private JFXComboBox searchBar;
    private Stage primaryStage;
    private AnchorPane textPane;
    public MainSceneController(GodController g, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum,
                               JFXComboBox originField, JFXComboBox destinationField, JFXSlider zoomSlider,
                               DirectorySceneController directorySceneController, AnchorPane textPane,
                               ScrollPane scrollPane, SearchEngine searchEngine, Stage primaryStage) {
        super(g, m, p, currentFloorNum, zoomSlider, scrollPane);
        this.primaryStage = primaryStage;
        this.searchEngine = searchEngine;
        this.originField = originField;
        this.destinationField = destinationField;
        this.directorySceneController = directorySceneController;
        this.textPane = textPane;
        initializeSearchBoxes();
    }

    private void initializeSearchBoxes() { } //TODO

    public void setOrigin(Node o) {
        this.origin = o;
        originField.setPromptText(o.getNodeID());
    }

    public void setDestination(Node d) {
        this.destination = d;
        destinationField.setPromptText(d.getNodeID());
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
        setOriginText();
    }


    public void setDestination() {
        super.setDestination();
        setDestinationText();
    }

    private void setOriginText() {
        String prompt;
        if (origin.toString().length() < 1) prompt = origin.getNodeID();
        else prompt = origin.getShortName();
        originField.setPromptText(prompt);
    }

    private void setDestinationText() {
        String prompt;
        if (destination.toString().length() < 1) prompt = destination.getNodeID();
        else prompt = destination.getShortName();
        destinationField.setPromptText(prompt);
    }

    public void setOrigin(MouseEvent m) {
        snapToNode(m);
        currentPath = null;
        origin = currentLoc;
        setOriginText();
        refreshCanvas();
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

    public void openDirectory(AnchorPane dPane) throws IOException {
        JFXRippler rippler = new JFXRippler();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/fxml/directory.fxml"));
        loader.setController(directorySceneController);
        Region region = loader.load();
        dPane.getChildren().add(rippler);
        JFXPopup popup = new JFXPopup(region);
        directorySceneController.setMainSceneController(this);

        popup.show(rippler, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
    }

    public void navigate(Node o, Node d) throws IOException {
        this.origin = o;
        this.destination = d;
        findPath();
    }

    public void findPath() throws IOException {
        if (origin == null || destination == null) return; //TODO throw an error
        goToCorrectFloor();
        //centerMap();
        currentPath = pathFindingFacade.getPath(origin, destination);
        textDirections();
        refreshCanvas();
    }

    private void textDirections() {
        JFXRippler rippler = new JFXRippler();
        textPane.getChildren().add(rippler);
        JFXListView directions = new JFXListView();
        directions.setItems(textualDirections.getTextDirections(currentPath));
        JFXPopup popup = new JFXPopup(directions);

        popup.show(rippler, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
    }

    private void goToCorrectFloor() {
        currentFloor = origin.getFloor();
        imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
        currentFloorNum.setText(currentFloor);
        refreshCanvas();
    }

    public void reversePath() throws IOException {
        Node temp = destination;
        destination = origin;
        origin = temp;
        if (origin == null || destination == null) return; //TODO throw an error
        goToCorrectFloor();
        //centerMap();
        currentPath = pathFindingFacade.getPath(origin, destination);
        textDirections();
        refreshCanvas();
    }

    public Node getDestination() {
        return destination;
    }
}