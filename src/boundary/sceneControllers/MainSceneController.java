package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.DirectoryController;
import MapNavigation.MapNavigationFacade;
import MapNavigation.SearchEngine;
import Pathfinding.PathFindingFacade;
import Pathfinding.TextualDirections;
import boundary.AutoCompleteTextField;
import boundary.GodController;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.control.Alert.AlertType;
import Entity.ErrorController;

import java.io.IOException;
import java.util.List;

public class MainSceneController extends AbstractMapController{
    private TextualDirections textualDirections = new TextualDirections();
    private DirectorySceneController directorySceneController;
    private JFXComboBox originField;
    private AnchorPane searchAnchor;
    private ErrorController errorController = new ErrorController();
    private AutoCompleteTextField destinationTextField;
    private AnchorPane textPane;
    private DirectoryController dc;
    public MainSceneController(GodController g, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum, JFXComboBox originField,
                               AnchorPane searchAnchor, JFXSlider zoomSlider, DirectoryController dc,
                               DirectorySceneController directorySceneController, AnchorPane textPane,
                               ScrollPane scrollPane) {
        super(g, m, p, currentFloorNum, zoomSlider, scrollPane);
        this.originField = originField;
        this.searchAnchor = searchAnchor;
        this.directorySceneController = directorySceneController;
        this.textPane = textPane;
        this.dc = dc;
    }

    public void initializeScene() {
        super.initializeScene();
        destinationTextField = new AutoCompleteTextField(dc, this);
        searchAnchor.getChildren().add(destinationTextField);
        origin = mapNavigationFacade.getDefaultNode();
    }

    public void setOrigin(Node o) {
        this.origin = o;
        originField.setPromptText(o.getNodeID());
    }

    public void setDestination(Node d) {
        this.destination = d;
        destinationTextField.setText(d.getNodeID());
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

    private void setOriginText() { //TODO sometimes the short names are too long for the JFXComboBox
        String prompt;
        if (origin.toString().length() < 1) prompt = origin.getNodeID();
        else prompt = origin.getShortName();
        originField.setPromptText(prompt);
    }

    private void setDestinationText() {
        String prompt;
        if (destination.toString().length() < 1) prompt = destination.getNodeID();
        else prompt = destination.getShortName();
        destinationTextField.setText(prompt);
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
        if (origin == null || destination == null) return; //TODO throw an error
        Node temp = destination;
        destination = origin;
        origin = temp;
        setOriginText();
        setDestinationText();
        goToCorrectFloor();
        centerMap();
        currentPath = pathFindingFacade.getPath(origin, destination);
        textDirections();
        refreshCanvas();
    }

    private void centerMap() {
        //TODO
    }

}