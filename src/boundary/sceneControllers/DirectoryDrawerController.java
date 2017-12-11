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
    private NavigationDrawerController navigationDrawerController;
    private Region navigateRegion;
    private DirectoryController dc;
    private ObservableList directoryList;
    private JFXDrawer drawer;
    private Node originNode, destinationNode;

    @FXML
    private Label originLabel, destinationLabel;

    @FXML
    private JFXComboBox browser;

    @FXML
    private JFXListView listView;

    public DirectoryDrawerController(JFXDrawer drawer, MapNavigationFacade mapNavigationFacade, DirectoryController dc, NavigationDrawerController navigationDrawerController) {
        this.drawer = drawer;
        this.navigationDrawerController = navigationDrawerController;
        this.mapNavigationFacade = mapNavigationFacade;
        this.dc = dc;
    }

    @FXML
    private void initialize() {
        originNode = mapNavigationFacade.getDefaultNode();
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
           originNode = (Node) listView.getSelectionModel().getSelectedItem();
            originLabel.setText(originNode.getNodeID());
        }
    }

    @FXML
    private void setDirectoryDestination() throws IOException {
        if (!listView.getSelectionModel().isEmpty()) {
            destinationNode = (Node) listView.getSelectionModel().getSelectedItem();
            destinationLabel.setText(destinationNode.getNodeID());
        }
    }

    @FXML
    public void closeDrawer() {
        originLabel.setText("Kiosk Location");
        destinationLabel.setText("Select a Destination");
        originNode = mapNavigationFacade.getDefaultNode();
        destinationNode = null;
        drawer.close();
        drawer.toBack();
    }

    public void navigate() throws IOException {
        mainSceneController.navigate(originNode, destinationNode);
        navigationDrawerController.navigate();
        drawer.setSidePane(navigateRegion);

    }

    @FXML
    public void reversePath() {
        Node temp = originNode;
        originNode = destinationNode;
        destinationNode = temp;
        if (originNode != null) originLabel.setText(originNode.getNodeID());
        if (destinationNode != null) destinationLabel.setText(destinationNode.getNodeID());
    }

    public void setNavigateRegion(Region region) {
        this.navigateRegion = region;
    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
}
