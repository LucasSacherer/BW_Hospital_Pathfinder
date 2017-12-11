package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.DirectoryController;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import boundary.AutoCompleteTextField;
import boundary.GodController;
import com.jfoenix.controls.*;
import javafx.fxml.FXMLLoader;
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
    private DirectoryController dc;
    private JFXHamburger hamburger;
    private JFXDrawer drawer;
    private Pane mainPane;
    private Region navigationRegion, directoryRegion;
    public MainSceneController(GodController g, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum,
                               AnchorPane searchAnchor, JFXSlider zoomSlider, DirectoryController dc,
                               DirectoryDrawerController directoryDrawerController, NavigationDrawerController navigationDrawerController,
                               ScrollPane scrollPane, JFXDrawer drawer, JFXHamburger hamburger, Pane mainPane) {
        super(g, m, p, currentFloorNum, zoomSlider, scrollPane);
        this.searchAnchor = searchAnchor;
        this.directoryDrawerController = directoryDrawerController;
        this.navigationDrawerController = navigationDrawerController;
        this.dc = dc;
        this.drawer = drawer;
        this.hamburger = hamburger;
        this.mainPane = mainPane;
    }

    public void initializeScene() throws IOException {
        super.initializeScene();
        destinationTextField = new AutoCompleteTextField(dc, false);
        destinationTextField.setMainSceneController(this);
        destinationTextField.setPromptText("Search Brigham & Women's");
        searchAnchor.getChildren().add(destinationTextField);
//        searchAnchor.setPrefHeight(40);
//        searchAnchor.setPrefWidth(100);
        origin = mapNavigationFacade.getDefaultNode();

        FXMLLoader directoryLoader = new FXMLLoader(getClass().getResource("/boundary/fxml/directoryDrawer.fxml"));
        directoryLoader.setController(directoryDrawerController);
        directoryRegion = directoryLoader.load();
        navigationDrawerController.setDirectoryRegion(directoryRegion);
        FXMLLoader navigationLoader = new FXMLLoader(getClass().getResource("/boundary/fxml/navigationDrawer.fxml"));
        navigationLoader.setController(navigationDrawerController);
        navigationRegion = navigationLoader.load();

        directoryDrawerController.setNavigateRegion(navigationRegion);
//        resizeDrawer();
        initializeBurger(directoryRegion);
    }

    private void resizeDrawer() {
        double height = mainPane.getHeight() - 40;
        drawer.setMaxHeight(height);
        drawer.setPrefHeight(height);
        drawer.setMinHeight(height);
    }

    private void initializeBurger(Region directoryRegion) {
        hamburger.setOnMouseClicked(e -> {
            drawer.setSidePane(directoryRegion);
            directoryDrawerController.setMainSceneController(this);
            if (drawer.isHidden() || drawer.isHiding()) {
                drawer.open();
                drawer.toFront();
            } else {
                drawer.close();
            }
        });
    }

    private void openNavigationDrawer() {
        navigationDrawerController.setMainSceneController(this);
        drawer.setSidePane(navigationRegion);
        if (drawer.isHidden() || drawer.isHiding()) {
            drawer.open();
            drawer.toFront();
        }
    }

    public void setOrigin(Node o) { this.origin = o; }

    public void setDestination(Node d) {
        this.destination = d;
        setDestinationText();
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
        setDestination();
        findPath();
        hide();
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

    public void reversePath() throws IOException {
        navigate(destination, origin);

    }

    public void findPath() throws IOException {
        if (origin == null || destination == null) return;
        openNavigationDrawer();
        navigationDrawerController.setFields(origin, destination);
        goToCorrectFloor();
        //centerMap();
        currentPath = pathFindingFacade.getPath(origin, destination);
        refreshCanvas();
        navigationDrawerController.hide();
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

    public Node getOrigin() { return origin; }

    public Node getDestination() { return destination; }
}