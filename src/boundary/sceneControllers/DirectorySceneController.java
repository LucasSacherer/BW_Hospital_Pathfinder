package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import boundary.GodController;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;

public class DirectorySceneController {
    private MainSceneController m;
    private MapNavigationFacade mapNavigationFacade;
    private ObservableList directoryList;

    @FXML
    private JFXComboBox browser;

    @FXML
    private JFXListView listView;

    public DirectorySceneController(MapNavigationFacade mapNavigationFacade) {
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

}