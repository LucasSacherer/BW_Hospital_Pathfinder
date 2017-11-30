package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import javafx.scene.control.Alert.AlertType;
import Entity.ErrorController;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainSceneController extends AbstractMapController{
    private JFXTextField originField, destinationField;
    private ListView elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical;
    private ErrorController errorController = new ErrorController();

    public MainSceneController(ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p,
                               Label currentFloorNum, ListView elevatorDir, ListView restroomDir, ListView stairsDir,
                               ListView deptDir, ListView labDir, ListView infoDeskDir, ListView conferenceDir,
                               ListView exitDir, ListView shopsDir, ListView nonMedical, JFXTextField originField,
                               JFXTextField destinationField) {
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
        this.originField = originField;
        this.destinationField = destinationField;
        initializeDirectory();
        initializeDirectoryListeners();
    }

    private void initializeDirectory() {
        elevatorDir.setItems(mapNavigationFacade.getDirectory().get("Elevators"));
        restroomDir.setItems(mapNavigationFacade.getDirectory().get("Restrooms"));
        stairsDir.setItems(mapNavigationFacade.getDirectory().get("Stairs"));
        labDir.setItems(mapNavigationFacade.getDirectory().get("Departments"));
        deptDir.setItems(mapNavigationFacade.getDirectory().get("Labs"));
        infoDeskDir.setItems(mapNavigationFacade.getDirectory().get("Information Desks"));
        conferenceDir.setItems(mapNavigationFacade.getDirectory().get("Conference Rooms"));
        exitDir.setItems(mapNavigationFacade.getDirectory().get("Exits/Entrances"));
        shopsDir.setItems(mapNavigationFacade.getDirectory().get("Shops, Food, Phones"));
        nonMedical.setItems(mapNavigationFacade.getDirectory().get("Non-Medical Services"));
    }
    private boolean checkNullLocations(){
        boolean success = true;
        try {
            origin.equals("");
            destination.equals("");
        }
        catch(NullPointerException e){
            errorController.showError("Please set a start and end location");
            success = false;
        }
        return success;
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
        boolean success = checkNullLocations();
        if(success) {
            setDestination();
            findPath();
        }
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

    public void displayTextDir(){
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
}