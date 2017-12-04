package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import boundary.GodController;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class DirectorySceneController {
    private MainSceneController mainSceneController;
    private MapNavigationFacade mapNavigationFacade;
    private Node destination, origin;

    @FXML
    private JFXTextField directoryOrigin, directoryDestination;

    @FXML
    private ListView elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical;

    public DirectorySceneController(MapNavigationFacade mapNavigationFacade) {
        this.mapNavigationFacade = mapNavigationFacade;
    }

    public void setMainSceneController(MainSceneController m) {
        this.mainSceneController = m;
    }

    @FXML
    private void initialize() {
        initializeDirectory();
        initializeDirectoryListeners();
        origin = mapNavigationFacade.getDefaultNode();
    }

    @FXML
    private void directoryNavigate() throws IOException {
        mainSceneController.navigate(origin, destination);
        Stage stage = (Stage) directoryOrigin.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void resetOrigin() {
        origin = mapNavigationFacade.getDefaultNode();
        directoryOrigin.clear();
    }

    @FXML
    private void setElevatorOrigin() {
        origin = (Node) elevatorDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setRestroomOrigin() {
        origin = (Node) restroomDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setStairsOrigin() {
        origin = (Node) stairsDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setDepartmentsOrigin() {
        origin = (Node) deptDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setInfoDesksOrigin() {
        origin = (Node) infoDeskDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setLabsOrigin() {
        origin = (Node) labDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setExitOrigin() {
        origin = (Node) exitDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setShopsOrigin() {
        origin = (Node) shopsDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setNonMedicalOrigin() {
        origin = (Node) nonMedical.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
    }

    @FXML
    private void setConferenceOrigin() {
        origin = (Node) conferenceDir.getSelectionModel().getSelectedItem();
        directoryOrigin.setText(origin.toString());
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

    private void initializeDirectoryListeners(){
        elevatorDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) elevatorDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        restroomDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) restroomDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        stairsDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) stairsDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        labDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) labDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        deptDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) deptDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        infoDeskDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) infoDeskDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        conferenceDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) conferenceDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        exitDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) exitDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        shopsDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) shopsDir.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
        nonMedical.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            destination = (Node) nonMedical.getItems().get(newValue.intValue());
            directoryDestination.setText(destination.toString());
        });
    }
}
