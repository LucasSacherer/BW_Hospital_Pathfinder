package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.DirectoryController;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import boundary.AutoCompleteTextField;
import boundary.GodController;
import com.jfoenix.controls.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import Entity.ErrorController;

import java.io.IOException;

public class MainSceneController extends AbstractMapController {
    private DirectoryDrawerController directoryDrawerController;
    private NavigationDrawerController navigationDrawerController;
    private AnchorPane searchAnchor;
    private ErrorController errorController = new ErrorController();
    private AutoCompleteTextField destinationTextField;
    private AnchorPane textPane;
    private DirectoryController dc;
    private JFXHamburger hamburger;
    private JFXDrawer drawer;
    private Pane mainPane;
    private Region navigationRegion, directoryRegion;
    public MainSceneController(GodController g, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum,
                               AnchorPane searchAnchor, JFXSlider zoomSlider, DirectoryController dc,
                               DirectoryDrawerController directoryDrawerController, NavigationDrawerController navigationDrawerController, AnchorPane textPane,
                               ScrollPane scrollPane, JFXDrawer drawer, JFXHamburger hamburger, Pane mainPane) {
        super(g, m, p, currentFloorNum, zoomSlider, scrollPane);
        this.searchAnchor = searchAnchor;
        this.directoryDrawerController = directoryDrawerController;
        this.navigationDrawerController = navigationDrawerController;
        this.textPane = textPane;
        this.dc = dc;
        this.drawer = drawer;
        this.hamburger = hamburger;
        this.mainPane = mainPane;
    }

    public void initializeScene() throws IOException {
        super.initializeScene();
        destinationTextField = new AutoCompleteTextField(dc, this, false);
        destinationTextField.setPromptText("Search Brigham & Women's");
        searchAnchor.getChildren().add(destinationTextField);
        searchAnchor.setPrefHeight(40);
        searchAnchor.setPrefWidth(100);
        origin = mapNavigationFacade.getDefaultNode();

        FXMLLoader directoryLoader = new FXMLLoader(getClass().getResource("/boundary/fxml/directoryDrawer.fxml"));
        directoryLoader.setController(directoryDrawerController);
        directoryRegion = directoryLoader.load();

        FXMLLoader navigationLoader = new FXMLLoader(getClass().getResource("/boundary/fxml/navigationDrawer.fxml"));
        navigationLoader.setController(navigationDrawerController);
        navigationRegion = navigationLoader.load();

        directoryDrawerController.setNavigateRegion(navigationRegion);

        directoryDrawerController.setMainSceneController(this);
        navigationDrawerController.setMainSceneController(this);
        initializeBurger(directoryRegion);
    }

    private void initializeBurger(Region region) {
        hamburger.setOnMouseClicked(e -> {
            region.setMaxHeight(mainPane.getHeight()-40);
            region.setPrefHeight(mainPane.getHeight()-40);
            drawer.setSidePane(region);
            if (drawer.isHidden() || drawer.isHiding()) {
                drawer.open();
                drawer.toFront();
            } else {
                drawer.close();
            }
        });
    }

    private void openNavigationDrawer() {
        drawer.setSidePane(navigationRegion);
        if (drawer.isHidden() || drawer.isHiding()) {
            drawer.open();
            drawer.toFront();
        }
    }

    public void setOrigin(Node o) { this.origin = o; }

    public void setDestination(Node d) {
        this.destination = d;
        if (destination != null) destinationTextField.setText(destination.getNodeID());
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
        origin = mapNavigationFacade.getDefaultNode();
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

    public void setDestination() {
        super.setDestination();
        setDestinationText();
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
        refreshCanvas();
    }

    public void navigate(Node o, Node d) throws IOException {
        this.origin = o;
        this.destination = d;
        findPath();
    }

    public void findPath() throws IOException {
        if (origin == null || destination == null) return;
        openNavigationDrawer();
        navigationDrawerController.setOrigin(origin);
        navigationDrawerController.setDestination(destination);
        navigationDrawerController.setFields();
        goToCorrectFloor();
        //centerMap();
        currentPath = pathFindingFacade.getPath(origin, destination);
        refreshCanvas();
    }

    private void goToCorrectFloor() {
        currentFloor = origin.getFloor();
        imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
//        currentFloorNum.setText(currentFloor);
        refreshCanvas();
    }

    private void centerMap() {
        //TODO
    }

    public void floorL2() {
        currentFloor = "L2";
        imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
        refreshCanvas();
    }

    public void floorL1() {
        currentFloor = "L1";
        imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
        refreshCanvas();
    }

    public void floorG() {
        currentFloor = "G";
        imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
        refreshCanvas();
    }

    public void floor1() {
        currentFloor = "1";
        imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
        refreshCanvas();
    }

    public void floor2() {
        currentFloor = "2";
        imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
        refreshCanvas();
    }

    public void floor3() {
        currentFloor = "3";
        imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
        refreshCanvas();
    }

    public void streetView() {
    }

    public void hide() { destinationTextField.hide(); }
}