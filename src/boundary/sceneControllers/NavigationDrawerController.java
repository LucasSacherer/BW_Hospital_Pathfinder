package boundary.sceneControllers;

import Entity.AdminLog;
import Entity.Node;
import MapNavigation.DirectoryController;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NavigationDrawerController {
    private JFXDrawer drawer;
    private DirectoryController dc;
    private TextualDirections textualDirections = new TextualDirections();
    private AutoCompleteTextField originTextField, destinationTextField;
    private MainSceneController mainSceneController;
    private Region directoryRegion;

    @FXML
    private AnchorPane originPane, destinationPane;

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private JFXTreeTableView<AdminLog> textDirectionsTable = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<AdminLog, String> textDirectionsColumn = new TreeTableColumn<>();

    private TreeItem<AdminLog> root = new TreeItem<>();

    public NavigationDrawerController(JFXDrawer drawer, DirectoryController dc,
                                      JFXTreeTableView<AdminLog> textDirectionsTable, TreeTableColumn<AdminLog, String> textDirectionsColumn) {
        this.drawer = drawer;
        this.dc = dc;
        JFXListView directions = new JFXListView();
//        directions.setItems(textualDirections.getTextDirections(mainSceneController.getCurrentPath()));
        this.textDirectionsTable = textDirectionsTable;
        this.textDirectionsColumn = textDirectionsColumn;
    }

    @FXML
    private void initialize() {
        originTextField = new AutoCompleteTextField(dc, true);
        originPane.getChildren().add(originTextField);
        originTextField.setPromptText("Kiosk Location");

        destinationTextField = new AutoCompleteTextField(dc, false);
        destinationPane.getChildren().add(destinationTextField);
        destinationTextField.setPromptText("Search for a Destination");

        initializeListCells();
    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
        originTextField.setMainSceneController(mainSceneController);
        destinationTextField.setMainSceneController(mainSceneController);
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
    public void backToDirectory() { drawer.setSidePane(directoryRegion); }

    @FXML
    public void closeDrawer() {
        originTextField.clear();
        destinationTextField.clear();
        drawer.close();
        drawer.toBack();
    }

    @FXML
    public void navigate() throws IOException {
            mainSceneController.findPath();
            mainSceneController.hide();
            hide();
    }

    @FXML
    public void reversePath() throws IOException {
        originTextField.setText(destinationTextField.getText());
        destinationTextField.setText(originTextField.getText());
        mainSceneController.reversePath();
        mainSceneController.hide();
        hide();
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

    public void setFields(Node origin, Node destination) {
        destinationTextField.setText(destination.getShortName());
        originTextField.setText(origin.getShortName());
        hide();
    }

    public void hide() {
        originTextField.hide();
        destinationTextField.hide();
    }

    public void setDirectoryRegion(Region directoryRegion) {
        this.directoryRegion = directoryRegion;
    }
}
