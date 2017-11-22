package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainSceneController extends AbstractMapController{
    private ListView elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical;
    private Label currentFloorNum;
    private Canvas canvas;
    private String currentFloor = "G";
    private GraphicsContext gc;
    private Pane mapPane;
    private List currentPath;
    private MapNavigationFacade mapNavigationFacade;
    private PathFindingFacade pathFindingFacade;
    private ImageView imageView;

    private Node origin, destination, currentLoc;

    public MainSceneController(ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum, ListView elevatorDir, ListView restroomDir, ListView stairsDir, ListView deptDir, ListView labDir, ListView infoDeskDir, ListView conferenceDir, ListView exitDir, ListView shopsDir, ListView nonMedical) {
        super(i, mapPane, canvas, m, p, currentFloorNum);
        // todo set origin:  this.origin = mapNavigationFacade.getDefaultNode(); //todo change the origin when the floor changes
        this.elevatorDir = elevatorDir;
        this.restroomDir = restroomDir;
        this.stairsDir = stairsDir;
        this.labDir = labDir;
        this.deptDir = deptDir;
        this.infoDeskDir = infoDeskDir;
        this.conferenceDir = conferenceDir;
        this.exitDir = exitDir;
        this.shopsDir = shopsDir;
        this.nonMedical = nonMedical;
        initializeDirectoryListeners();
    }

    private void initializeDirectoryListeners(){
        elevatorDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) elevatorDir.getItems().get(newValue.intValue());
            refreshCanvas();
        });
        restroomDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) restroomDir.getItems().get(newValue.intValue());
            refreshCanvas();
        });
        stairsDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) stairsDir.getItems().get(newValue.intValue());
            refreshCanvas();
        });
        labDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) labDir.getItems().get(newValue.intValue());
            refreshCanvas();
        });
        deptDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) deptDir.getItems().get(newValue.intValue());
            refreshCanvas();
        });
        infoDeskDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) infoDeskDir.getItems().get(newValue.intValue());
            refreshCanvas();
        });
        conferenceDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) conferenceDir.getItems().get(newValue.intValue());
            refreshCanvas();
        });
        exitDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) exitDir.getItems().get(newValue.intValue());
            refreshCanvas();
        });
        nonMedical.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) nonMedical.getItems().get(newValue.intValue());
            refreshCanvas();
        });
    }

    public void bathroomClicked() { findNearest(currentLoc, "REST"); }

    public void infoClicked() { findNearest(currentLoc, "INFO"); }

    public void elevatorClicked() { findNearest(currentLoc, "ELEV"); }

    private void findNearest(Node node, String type) {
        destination = mapNavigationFacade.getNearestPOI(node.getXcoord(), node.getYcoord(), type);
        currentPath = pathFindingFacade.getPath(origin, destination);
        refreshCanvas();
    }

    public void navigateToHere() {
        setDestination();
        findPath();
    }

    public void setAsOrigin() { //TODO why doesn't this work?
        currentPath = null;
        origin = currentLoc;
        refreshCanvas();
    }
}