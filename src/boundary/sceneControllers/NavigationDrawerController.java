package boundary.sceneControllers;

import Entity.AdminLog;
import Entity.Node;
import MapNavigation.DirectoryController;
import MapNavigation.MapNavigationFacade;
import Pathfinding.TextualDirections;
import boundary.AutoCompleteTextField;
import boundary.NodeReceiver;
import com.jfoenix.controls.*;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NavigationDrawerController implements NodeReceiver{
    private JFXDrawer drawer;
    private DirectoryController dc;
    private TextualDirections textualDirections = new TextualDirections();
    private MapNavigationFacade mapNavigationFacade;
    private Node originNode, destinationNode;
    private AutoCompleteTextField originTextField, destinationTextField;
    private MainSceneController mainSceneController;

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
                                      JFXTreeTableView<AdminLog> textDirectionsTable, TreeTableColumn<AdminLog, String> textDirectionsColumn) {
        this.drawer = drawer;
        this.mapNavigationFacade = mapNavigationFacade;
        this.dc = dc;
        JFXListView directions = new JFXListView();
//        this.listView = listView;
//        directions.setItems(textualDirections.getTextDirections(mainSceneController.getCurrentPath()));
        this.textDirectionsTable = textDirectionsTable;
        this.textDirectionsColumn = textDirectionsColumn;
    }

    @FXML
    private void initialize() {
//        if (originNode == null) originNode = mapNavigationFacade.getDefaultNode();

        originTextField = new AutoCompleteTextField(dc, true);
//        originTextField.setNodeReceiver(this);
        originPane.getChildren().add(originTextField);
        originTextField.setPromptText("Kiosk Location");

        destinationTextField = new AutoCompleteTextField(dc, false);
//        destinationTextField.setNodeReceiver(this);
        destinationPane.getChildren().add(destinationTextField);
        destinationTextField.setPromptText("Search for a Destination");
        initializeListCells();

    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
        originTextField.setNodeReceiver(mainSceneController);
        destinationTextField.setNodeReceiver(mainSceneController);
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
            mainSceneController.findPath();
            mainSceneController.hide();
            originTextField.hide();
            destinationTextField.hide();
    }

    @FXML
    public void reversePath() throws IOException {
        Node temp = originNode;
        originNode = destinationNode;
        destinationNode = temp;
        if (originNode != null) originTextField.setText(originNode.getNodeID());
        if (destinationNode != null) destinationTextField.setText(destinationNode.getNodeID());
        mainSceneController.navigate(originNode, destinationNode);
        mainSceneController.hide();
        originTextField.hide();
        destinationTextField.hide();
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

    public void setOrigin(Node origin) {
        System.out.println("setting origin");
        this.originNode = origin;
        if (originNode != null) originTextField.setText(origin.getNodeID());
        originTextField.hide();
    }

    public void setDestination(Node destination) {
        System.out.println("Setting destination");
        this.destinationNode = destination;
        if (destinationNode != null) destinationTextField.setText(destination.getNodeID());
        destinationTextField.hide();
    }

    public void setFields() {
        if (destinationNode != null) destinationTextField.setText(destinationNode.getNodeID());
        if (originNode != null) originTextField.setText(originNode.getNodeID());
        destinationTextField.hide();
        originTextField.hide();
    }
}
