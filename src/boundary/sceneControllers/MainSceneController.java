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
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.sun.tools.javac.comp.Flow;
import javafx.animation.Transition;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.control.Alert.AlertType;
import Entity.ErrorController;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

import static javafx.scene.input.SwipeEvent.SWIPE_LEFT;

public class MainSceneController extends AbstractMapController{
    private TextualDirections textualDirections = new TextualDirections();
    private DirectoryDrawerController directoryDrawerController;
    private JFXComboBox originField;
    private AnchorPane searchAnchor;
    private ErrorController errorController = new ErrorController();
    private AutoCompleteTextField destinationTextField;
    private AnchorPane textPane;
    private DirectoryController dc;
    private JFXHamburger hamburger;
    private JFXDrawer drawer;
    private Pane mainPane;
    public MainSceneController(GodController g, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum, JFXComboBox originField,
                               AnchorPane searchAnchor, JFXSlider zoomSlider, DirectoryController dc,
                               DirectoryDrawerController directoryDrawerController, AnchorPane textPane,
                               ScrollPane scrollPane, JFXDrawer drawer, JFXHamburger hamburger, Pane mainPane) {
        super(g, m, p, currentFloorNum, zoomSlider, scrollPane);
        this.originField = originField;
        this.searchAnchor = searchAnchor;
        this.directoryDrawerController = directoryDrawerController;
        this.textPane = textPane;
        this.dc = dc;
        this.drawer = drawer;
        this.hamburger = hamburger;
        this.mainPane = mainPane;
    }

    public void initializeScene() throws IOException {
        super.initializeScene();
        destinationTextField = new AutoCompleteTextField(dc, this);
        searchAnchor.getChildren().add(destinationTextField);
        origin = mapNavigationFacade.getDefaultNode();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/fxml/drawerPractice.fxml"));
        loader.setController(directoryDrawerController);
        Region region = loader.load();
        initializeBurger(region);
    }

    private void initializeBurger(Region region) {
//        drawer.setDefaultDrawerSize(textPane.getWidth());
//        drawer.setPrefHeight(700);
        HamburgerBackArrowBasicTransition h = new HamburgerBackArrowBasicTransition(hamburger);
        hamburger.setOnMouseClicked(e -> {
            region.setMaxHeight(mainPane.getHeight()-40);
            region.setPrefHeight(mainPane.getHeight()-40);
            drawer.setSidePane(region);
            h.play();
            System.out.println("hey");
            if (drawer.isHidden() || drawer.isHiding()) {
                drawer.open();
                drawer.toFront();
            } else {
                drawer.close();
            }
        });
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