package boundary.sceneControllers;

import Entity.AdminLog;
import Entity.Node;
import MapNavigation.DirectoryController;
import MapNavigation.MapNavigationFacade;
import Pathfinding.TextualDirections;
import boundary.AutoCompleteTextField;
import com.jfoenix.controls.*;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;

public class NavigationDrawerController {
    private JFXDrawer drawer;
    private DirectoryController dc;
    private TextualDirections textualDirections = new TextualDirections();
    private MainSceneController m;
    private MapNavigationFacade mapNavigationFacade;
    private Object currentPath = null;
    private Node originNode, destinationNode;
    private AutoCompleteTextField originTextField, destinationTextField;

    @FXML
    private AnchorPane originPane, destinationPane;

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private JFXTreeTableView<AdminLog> textDirectionsTable = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<AdminLog, String> textDirectionsColumn = new TreeTableColumn<>();

    private TreeItem<AdminLog> root = new TreeItem<>();



    public NavigationDrawerController(JFXDrawer drawer, MapNavigationFacade mapNavigationFacade, DirectoryController dc,
                                      MainSceneController m,JFXTreeTableView<AdminLog> textDirectionsTable,TreeTableColumn<AdminLog, String> textDirectionsColumn) {
        this.drawer = drawer;
        this.mapNavigationFacade = mapNavigationFacade;
        this.dc = dc;
        JFXListView directions = new JFXListView();
//        this.listView = listView;
//        directions.setItems(textualDirections.getTextDirections(currentPath));
        this.textDirectionsTable = textDirectionsTable;
        this.textDirectionsColumn = textDirectionsColumn;

    }

    @FXML
    private void initialize() {
//        if (originNode == null) originNode = mapNavigationFacade.getDefaultNode();

        originTextField = new AutoCompleteTextField(dc, originNode);
        originPane.getChildren().add(originTextField);
        originTextField.setPromptText("Kiosk Location");

        destinationTextField = new AutoCompleteTextField(dc, destinationNode);
        destinationPane.getChildren().add(destinationTextField);
        destinationTextField.setPromptText("Search for a Destination");
        initializeListCells();

    }

    public void initializeListCells() {
        textDirectionsColumn.setCellFactory(col -> {
            TreeTableCell<AdminLog, String> c = new TreeTableCell<>();
            final ImageView imageView = new ImageView("/boundary/images/circle-outline.png");

            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().add(imageView);
//            c.itemProperty().addListener((observable, oldValue, newValue) -> {
//                if (oldValue != null) {
//                    comboBox.valueProperty().unbindBidirectional(oldValue);
//                }
//                if (newValue != null) {
//                    comboBox.valueProperty().bindBidirectional(newValue);
//                }
//            });
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            JFXTextArea textArea = new JFXTextArea("HELLO");
//            textArea.setStyle("-fx-underline: true");
            JFXTextField textField = new JFXTextField("Hey");
            textArea.setPrefWidth(300);
            textArea.setPrefHeight(50);
            vBox.getChildren().add(textArea);
            vBox.getChildren().add(textField);
            hbox.getChildren().add(vBox);
            //c.graphicProperty().bind(Bindings.when(c.emptyProperty()).then((Node) null).otherwise(button));
            c.graphicProperty().bind(Bindings.when(c.emptyProperty()).then((javafx.scene.Node) null).otherwise(hbox));
//            c.setGraphic(hbox);
            return c;
        });
        textDirectionsTable.setRoot(root);
        textDirectionsTable.setShowRoot(true);
    }





    @FXML
    public void closeDrawer() {
        originTextField.clear();
        destinationTextField.clear();
        originNode = mapNavigationFacade.getDefaultNode();
        destinationNode = null;
        drawer.close();
        drawer.toBack();
    }

    @FXML
    public void navigate() throws IOException {
        if (originNode != null && destinationNode != null)
            m.navigate(originNode, destinationNode);
        else
            System.out.println("Origin: " + originNode + " Destination: " + destinationNode);
    }

    @FXML
    public void reversePath() {
       //TODO reverse the path over here
    }

    @FXML
    public void allFloors() {

    }

    @FXML
    public void currentFloor() {

    }

    @FXML
    public void previousFloor() {

    }

    @FXML
    private void nextFloor() {

    }
}
