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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;


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

    private TreeTableCell<AdminLog, String> cell;

    private Label inputLabel = new Label("Hey");
    private Label inputLabel1 = new Label("Hello");
    private Image image = new Image("/boundary/images/circle-outline.png");



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
        setNew();
        TreeTableCell<AdminLog,String> secondCell = new TreeTableCell<>();

//        textDirectionsTable.getTreeItem(2).set;
    }




    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
        originTextField.setMainSceneController(mainSceneController);
        destinationTextField.setMainSceneController(mainSceneController);
    }

    public void initializeListCells() {
        textDirectionsColumn.setCellFactory(param -> {
             cell = new TreeTableCell<AdminLog, String>() {
                private HBox hBox = new HBox();
                private VBox vBox = new VBox();
                private Label labelDist = new Label();
                private Label labelDire = new Label();
                private ImageView imageView = new ImageView();
                @Override
                protected void updateItem(String item, boolean empty) {
                    imageView.setImage(image);
                    imageView.setFitHeight(40);
                    imageView.setFitWidth(40);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    vBox.setAlignment(Pos.CENTER);
                    labelDist.setText(inputLabel.getText());
                    labelDire.setText(inputLabel1.getText());
                    labelDist.setPrefWidth(300);
                    labelDist.setPrefHeight(20);
                    vBox.getChildren().setAll(labelDist);
                    vBox.getChildren().setAll(labelDist,labelDire);
                    hBox.getChildren().setAll(imageView,vBox);
                    cell.setGraphic(hBox);
//                        cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((javafx.scene.Node) null).otherwise(hBox));
                }

            };
            return cell;
        });
        root.getChildren().add(new TreeItem<>());
        textDirectionsTable.setRoot(root);
        textDirectionsTable.setShowRoot(false);
    }

    @FXML
    public void backToDirectory() { drawer.setSidePane(directoryRegion); }

    private void setNew(){
        inputLabel.setText("Hello again");
        inputLabel1.setText("Hellloooooooo");
    }


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
