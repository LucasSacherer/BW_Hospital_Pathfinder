package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import boundary.GodController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DirectorySceneController {
    private JFXPopup p;
    private MapNavigationFacade mapNavigationFacade;
    private Node destination, origin;

    @FXML
    private JFXComboBox browser;

    @FXML
    private JFXListView listView;

    public DirectorySceneController(MapNavigationFacade mapNavigationFacade) {
        this.mapNavigationFacade = mapNavigationFacade;
    }

    public void setPopup(JFXPopup p) {
        this.p = p;
    }

    @FXML
    private void initialize() {
        browser.setItems(FXCollections.observableArrayList(mapNavigationFacade.getDirectory().keySet()));
        browser.setOnAction(e -> listView.setItems(mapNavigationFacade.getDirectory().get(browser.getSelectionModel().getSelectedItem())));
//        initializeDirectoryListeners();
    }

    @FXML
    private void setDirectoryOrigin() throws IOException {
        //TODO
    }

    @FXML
    private void setDirectoryDestination() throws IOException {
        //TODO
    }

//    @FXML
//    private void setElevatorOrigin() {
//        origin = (Node) elevatorDir.getSelectionModel().getSelectedItem();
//    }
//
//    @FXML
//    private void setRestroomOrigin() {
//        origin = (Node) restroomDir.getSelectionModel().getSelectedItem();
//    }
//
//    @FXML
//    private void setStairsOrigin() {
//        origin = (Node) stairsDir.getSelectionModel().getSelectedItem();
//    }
//
//    @FXML
//    private void setDepartmentsOrigin() {
//        origin = (Node) deptDir.getSelectionModel().getSelectedItem();
//    }
//
//    @FXML
//    private void setInfoDesksOrigin() {
//        origin = (Node) infoDeskDir.getSelectionModel().getSelectedItem();
//    }
//
//    @FXML
//    private void setLabsOrigin() {
//        origin = (Node) labDir.getSelectionModel().getSelectedItem();
//    }
//
//    @FXML
//    private void setExitOrigin() {
//        origin = (Node) exitDir.getSelectionModel().getSelectedItem();
//    }
//
//    @FXML
//    private void setShopsOrigin() {
//        origin = (Node) shopsDir.getSelectionModel().getSelectedItem();
//    }
//
//    @FXML
//    private void setNonMedicalOrigin() {
//        origin = (Node) nonMedical.getSelectionModel().getSelectedItem();
//    }
//
//    @FXML
//    private void setConferenceOrigin() {
//        origin = (Node) conferenceDir.getSelectionModel().getSelectedItem();
//    }

    private void initializeDirectory() {

//        elevatorDir.setItems(mapNavigationFacade.getDirectory().get("Elevators"));
//        restroomDir.setItems(mapNavigationFacade.getDirectory().get("Restrooms"));
//        stairsDir.setItems(mapNavigationFacade.getDirectory().get("Stairs"));
//        labDir.setItems(mapNavigationFacade.getDirectory().get("Departments"));
//        deptDir.setItems(mapNavigationFacade.getDirectory().get("Labs"));
//        infoDeskDir.setItems(mapNavigationFacade.getDirectory().get("Information Desks"));
//        conferenceDir.setItems(mapNavigationFacade.getDirectory().get("Conference Rooms"));
//        exitDir.setItems(mapNavigationFacade.getDirectory().get("Exits/Entrances"));
//        shopsDir.setItems(mapNavigationFacade.getDirectory().get("Shops, Food, Phones"));
//        nonMedical.setItems(mapNavigationFacade.getDirectory().get("Non-Medical Services"));
    }
//
//    private void initializeDirectoryListeners(){
//        elevatorDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            destination = (Node) elevatorDir.getItems().get(newValue.intValue());
//        });
//        restroomDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            destination = (Node) restroomDir.getItems().get(newValue.intValue());
//        });
//        stairsDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            destination = (Node) stairsDir.getItems().get(newValue.intValue());
//        });
//        labDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            destination = (Node) labDir.getItems().get(newValue.intValue());
//        });
//        deptDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            destination = (Node) deptDir.getItems().get(newValue.intValue());
//        });
//        infoDeskDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            destination = (Node) infoDeskDir.getItems().get(newValue.intValue());
//        });
//        conferenceDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            destination = (Node) conferenceDir.getItems().get(newValue.intValue());
//        });
//        exitDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            destination = (Node) exitDir.getItems().get(newValue.intValue());
//        });
//        shopsDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            destination = (Node) shopsDir.getItems().get(newValue.intValue());
//        });
//        nonMedical.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            destination = (Node) nonMedical.getItems().get(newValue.intValue());
//        });
//    }
}
