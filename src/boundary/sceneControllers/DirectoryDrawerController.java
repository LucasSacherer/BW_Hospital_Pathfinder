package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.DirectoryController;
import MapNavigation.MapNavigationFacade;
import boundary.AutoCompleteTextField;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.IOException;

public class DirectoryDrawerController {
    private MainSceneController mainSceneController;
    private MapNavigationFacade mapNavigationFacade;
    private Region navigateRegion;
    private ObservableList directoryList;
    private JFXDrawer drawer;

    @FXML
    private Label originLabel, destinationLabel;

    @FXML
    private JFXComboBox browser;

    @FXML
    private JFXListView listView;

    public DirectoryDrawerController(JFXDrawer drawer, MapNavigationFacade mapNavigationFacade) {
        this.drawer = drawer;
        this.mapNavigationFacade = mapNavigationFacade;
    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    @FXML
    private void initialize() {
        directoryList = FXCollections.observableArrayList(mapNavigationFacade.getDirectory().keySet());
        browser.setItems(directoryList);
        browser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listView.setItems(mapNavigationFacade.getDirectory().get(browser.getSelectionModel().getSelectedItem()));
            }
        });
        browser.getSelectionModel().select(0);
        listView.setItems(mapNavigationFacade.getDirectory().get(browser.getSelectionModel().getSelectedItem()));
    }

    @FXML
    private void setDirectoryOrigin() throws IOException {
        if (!listView.getSelectionModel().isEmpty()) {
            Node origin = (Node) listView.getSelectionModel().getSelectedItem();
            mainSceneController.setOrigin(origin);
            if (origin.getShortName().length() < 1) originLabel.setText(origin.getNodeID());
            else originLabel.setText(origin.getShortName());
        }
        mainSceneController.hide();
    }

    @FXML
    private void setDirectoryDestination() throws IOException {
        if (!listView.getSelectionModel().isEmpty()) {
            Node destination = (Node) listView.getSelectionModel().getSelectedItem();
            mainSceneController.setDestination(destination);
            if (destination.getShortName().length() < 1) destinationLabel.setText(destination.getNodeID());
            else destinationLabel.setText(destination.getShortName());
        }
        mainSceneController.hide();
    }

    @FXML
    public void closeDrawer() {
        originLabel.setText("Kiosk Location");
        destinationLabel.setText("Select a Destination");
        drawer.close();
        drawer.toBack();
    }

    public void navigate() throws IOException {
        mainSceneController.findPath();
        drawer.setSidePane(navigateRegion);
    }

    @FXML
    public void reversePath() throws IOException {
        mainSceneController.reversePath();
        try {
            originLabel.setText(mainSceneController.getOrigin().getShortName());
            destinationLabel.setText(mainSceneController.getDestination().getShortName());
        } catch (Exception e) {}
        mainSceneController.hide();
    }

    public void setNavigateRegion(Region region) { this.navigateRegion = region; }
}
