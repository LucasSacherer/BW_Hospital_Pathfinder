package boundary.sceneControllers;

import Entity.AdminLog;
import Entity.Node;
import MapNavigation.DirectoryController;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import Pathfinding.TextualDirections;
import Pathfinding.textDirEntry;
import boundary.AutoCompleteTextField;
import boundary.GodController;
import com.jfoenix.controls.*;
import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;


import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

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
    private JFXTreeTableView<textDirEntry> textDirectionsTable = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<textDirEntry, ImageView> imageDirectionColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<textDirEntry, String> textDirectionsColumn = new TreeTableColumn<>();

    private TreeItem<textDirEntry> root = new TreeItem<>();
    protected List<Node> path = new ArrayList<>();
    protected List<List<textDirEntry>> textDirs = new ArrayList<>();


    public NavigationDrawerController(JFXDrawer drawer, DirectoryController dc,
                                      JFXTreeTableView<textDirEntry> textDirectionsTable, TreeTableColumn<textDirEntry, String> textDirectionsColumn,
                                      TreeTableColumn<textDirEntry, ImageView> imageDirectionColumn) {
        this.drawer = drawer;
        this.dc = dc;
        JFXListView directions = new JFXListView();
//        directions.setItems(textualDirections.getTextDirections(mainSceneController.getCurrentPath()));
        this.textDirectionsTable = textDirectionsTable;
        this.textDirectionsColumn = textDirectionsColumn;
        this.imageDirectionColumn = imageDirectionColumn;


    }

    @FXML
    private void initialize() {
        originTextField = new AutoCompleteTextField(dc, true);
        originPane.getChildren().add(originTextField);
        originTextField.setPromptText("Kiosk Location");
        destinationTextField = new AutoCompleteTextField(dc, false);
        destinationPane.getChildren().add(destinationTextField);
        destinationTextField.setPromptText("Search for a Destination");

    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
        originTextField.setMainSceneController(mainSceneController);
        destinationTextField.setMainSceneController(mainSceneController);
    }

    public void initializeTable() {
        root.getChildren().clear();

        for (List<textDirEntry> lists : textDirs) {
            for (textDirEntry dirEntry : lists) {
                root.getChildren().add(new TreeItem<>(dirEntry));
            }
//            root.getChildren().add(new TreeItem<>(null));
        }
        imageDirectionColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<textDirEntry, ImageView> param) -> new SimpleObjectProperty<ImageView>(param.getValue().getValue().getSymbol()));
        textDirectionsColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<textDirEntry, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getInstruction()));

        textDirectionsColumn.setCellFactory(new Callback<TreeTableColumn<textDirEntry, String>, TreeTableCell<textDirEntry, String>>() {

            @Override
            public TreeTableCell<textDirEntry, String> call(
                    TreeTableColumn<textDirEntry, String> param) {
                TreeTableCell<textDirEntry, String> cell = new TreeTableCell();
                Text text = new Text();
                cell.setGraphic(text);
                cell.wrapTextProperty();
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(cell.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            }

        });
        textDirectionsTable.setRoot(root);
        textDirectionsTable.setShowRoot(false);
        textDirectionsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0) {
                centerNode();
            }
        });
    }


    private void centerNode() {
        if (textDirectionsTable.getSelectionModel().getSelectedItem() != null) {
            TreeItem<textDirEntry> selectedTextDirEntry = textDirectionsTable.getSelectionModel().getSelectedItem();
            mainSceneController.centerMap(selectedTextDirEntry.getValue().getCurrNode());
            mainSceneController.goToCorrectFloorOfNode(selectedTextDirEntry.getValue().getCurrNode());
        }
    }


    @FXML
    public void backToDirectory() {
        drawer.setSidePane(directoryRegion);
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

    public void setPath(List path) {
        this.path = path;
        textDirs = textualDirections.makeTextDir(path);
        initializeTable();


    }
}
