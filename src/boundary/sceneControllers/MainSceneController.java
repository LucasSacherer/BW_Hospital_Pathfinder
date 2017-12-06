package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import boundary.AutoCompleteTextField;
import boundary.GodController;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import Entity.ErrorController;

import java.io.IOException;
import java.util.List;

public class MainSceneController extends AbstractMapController{
    private DirectorySceneController directorySceneController;
    private JFXTextField originField, destinationField;
    private ErrorController errorController = new ErrorController();
    private AutoCompleteTextField searchBar;

    public MainSceneController(GodController g, ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p,
                               Label currentFloorNum, JFXTextField originField, JFXTextField destinationField,
                               JFXSlider zoomSlider, DirectorySceneController directorySceneController, AnchorPane searchPane) {
        super(g, i, mapPane, canvas, m, p, currentFloorNum, zoomSlider);
        this.originField = originField;
        this.destinationField = destinationField;
        this.directorySceneController = directorySceneController;
        searchBar = new AutoCompleteTextField();
        searchPane.getChildren().add(searchBar);
        initializeSearchBar();
    }

    private void initializeSearchBar() {
//        this.searchBar.getEntries().addAll();
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

    public void bathroomClicked() { findNearest(currentLoc, "REST"); }

    public void infoClicked() { findNearest(currentLoc, "INFO"); }

    public void elevatorClicked() { findNearest(currentLoc, "ELEV"); }

    private void findNearest(Node node, String type) {
        destination = mapNavigationFacade.getNearestPOI(node.getXcoord(), node.getYcoord(), type);
        currentPath = pathFindingFacade.getPath(origin, destination);
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