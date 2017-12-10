package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DirectoryDrawerController {
    private MainSceneController m;
    private MapNavigationFacade mapNavigationFacade;
    private ObservableList directoryList;

    @FXML
    private AnchorPane originPane, destinationPane;

    @FXML
    private JFXComboBox browser;

    @FXML
    private JFXListView listView;

    public DirectoryDrawerController(MapNavigationFacade mapNavigationFacade) {
        this.mapNavigationFacade = mapNavigationFacade;
    }

    public void setMainSceneController(MainSceneController m) {
        this.m = m;
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
        if (!listView.getSelectionModel().isEmpty())
            m.setOrigin((Node) listView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void setDirectoryDestination() throws IOException {
        if (!listView.getSelectionModel().isEmpty())
            m.setDestination((Node) listView.getSelectionModel().getSelectedItem());
    }

    public void close() {

    }

    public void setOrigin() {

    }

    public void setDestination() {

    }

    public void navigate() {

    }

    public void reversePath() {

    }
}
