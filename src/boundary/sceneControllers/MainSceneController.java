package boundary.sceneControllers;

import Entity.GoogleNode;
import Entity.Node;
import GoogleNodes.GoogleNodeController;
import MapNavigation.DirectoryController;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import boundary.AutoCompleteTextField;
import boundary.GodController;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import Entity.ErrorController;

import java.io.IOException;
import java.util.ArrayList;

public class MainSceneController extends AbstractMapController {
    private boolean streetView;
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
    private GoogleNodeController googleNodeController;
    private ArrayList<JFXButton> googleNodes = new ArrayList<JFXButton>();
    public MainSceneController(GodController g, MapNavigationFacade m, PathFindingFacade p,
                               AnchorPane searchAnchor, JFXSlider zoomSlider, DirectoryController dc,
                               DirectoryDrawerController directoryDrawerController, NavigationDrawerController navigationDrawerController,
                               ScrollPane scrollPane, JFXDrawer drawer, JFXHamburger hamburger, Pane mainPane, GoogleNodeController googleNodeController) {
        super(g, m, p, zoomSlider, scrollPane);
        this.searchAnchor = searchAnchor;
        this.directoryDrawerController = directoryDrawerController;
        this.navigationDrawerController = navigationDrawerController;
        this.dc = dc;
        this.drawer = drawer;
        this.hamburger = hamburger;
        this.mainPane = mainPane;
        this.googleNodeController = googleNodeController;
    }

    public void initializeScene() throws IOException {
        super.initializeScene();
        destinationTextField = new AutoCompleteTextField(dc, false);
        destinationTextField.setMainSceneController(this);
        destinationTextField.setPromptText("Search");
        searchAnchor.getChildren().add(destinationTextField);

        FXMLLoader directoryLoader = new FXMLLoader(getClass().getResource("/boundary/fxml/directoryDrawer.fxml"));
        directoryLoader.setController(directoryDrawerController);
        directoryRegion = directoryLoader.load();
        navigationDrawerController.setDirectoryRegion(directoryRegion);
        FXMLLoader navigationLoader = new FXMLLoader(getClass().getResource("/boundary/fxml/navigationDrawer.fxml"));
        navigationLoader.setController(navigationDrawerController);
        navigationRegion = navigationLoader.load();

        directoryDrawerController.setNavigateRegion(navigationRegion);
        initializeBurger(directoryRegion);
    }

    private void resizeDrawer() {
        double height = mainPane.getHeight() - 40;
        drawer.setMaxHeight(height);
        drawer.setPrefHeight(height);
        drawer.setMinHeight(height);
    }

    public void initializeBurger(Region directoryRegion) {
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
        navigationDrawerController.setPath(currentPath);
        drawer.setSidePane(navigationRegion);
        if (drawer.isHidden() || drawer.isHiding()) {
            drawer.open();
            drawer.toFront();
        }
        navigationDrawerController.setFields(origin, destination);
        navigationDrawerController.hide();
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
        currentPath = pathFindingFacade.getPath(origin, destination);
        zoomToPath();
        openNavigationDrawer();
        goToCorrectFloor();
        refreshCanvas();
    }

    private void zoomToPath() {
        double zoomLevel =  ZOOM;

        if (zoomLevel < ZOOM) zoomLevel = ZOOM;
        zoomSlider.setValue(0);
        mapPane.setScaleX(zoomLevel);
        mapPane.setScaleY(zoomLevel);

        Point2D scrollOffset = figureScrollOffset(mapPane, scrollPane);
        repositionScroller(mapPane, scrollPane, 1, scrollOffset);

        double height = 3400.0;
        double y = getPathY();
        double viewHeight = scrollPane.getViewportBounds().getHeight();
        scrollPane.setVvalue(scrollPane.getVmax() * ((y - 0.5 * viewHeight) / (height - viewHeight)));

        double width = 5000.0;
        double x = getPathX();
        double viewWidth = scrollPane.getViewportBounds().getWidth();
        scrollPane.setHvalue(scrollPane.getVmax() * ((x - 0.5 * viewWidth) / (width - viewWidth)) * 0.9);
    }

    private double getPathX() {
        ArrayList<Node> pathList = new ArrayList<>(currentPath);
        int xMax = 0;
        int xMin = 3400;
        for (Node n : pathList) {
            if (n.getXcoord() > xMax) xMax = n.getXcoord();
            if (n.getXcoord() < xMin) xMin = n.getXcoord();
        }
        return (xMax + xMin) / 2;
    }

    private double getPathY() {
        ArrayList<Node> pathList = new ArrayList<>(currentPath);
        int yMax = 0;
        int yMin = 5000;
        for (Node n : pathList) {
            if (n.getYcoord() > yMax) yMax = n.getYcoord();
            if (n.getYcoord() < yMin) yMin = n.getYcoord();
        }
        return (yMax + yMin) / 2;
    }

    public void streetView() {
        if (streetView) {
            googleNodes.clear();
            streetView = false;
            System.out.println("streetView turned off");
        }
        else {
            for (GoogleNode gn : googleNodeController.getGoogleNodeByFloor(currentFloor)) {
                JFXButton jfxButton = new JFXButton();
                jfxButton.setLayoutX(gn.getXcoord());
                jfxButton.setLayoutY(gn.getYcoord());
                jfxButton.setText("HEY");
                googleNodes.add(jfxButton);
                mapPane.getChildren().add(jfxButton);
                jfxButton.toFront();
                jfxButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Google me");
                    }
                });
            }
            streetView = true;
            System.out.println("streetView turned on");
        }
    }

    public void hide() { destinationTextField.hide(); }

    public Node getOrigin() { return origin; }

    public Node getDestination() { return destination; }
}