package boundary.sceneControllers;

import Database.CleanUpManager;
import Database.FoodManager;
import Database.InterpreterManager;
import Entity.*;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

public class AdminRequestController {
    private InterpreterManager interpreterManager;
    private FoodManager foodManager;
    private CleanUpManager cleanUpManager;

    private JFXTreeTableView<InterpreterRequest> requestsIntTable;
    private TreeTableColumn<InterpreterRequest, String> requestIntNameColumn;
    private TreeTableColumn<InterpreterRequest, String> timeCreatedIntColumn;
    private TreeTableColumn<InterpreterRequest, String> timeCompleteIntColumn;
    private TreeTableColumn<InterpreterRequest, String> requestTypeIntColumn;
    private TreeTableColumn<InterpreterRequest, String> requestDescriptionIntColumn;
    private TreeTableColumn<InterpreterRequest, String> requestLocationIntColumn;
    private TreeTableColumn<InterpreterRequest, String> requestUserIntColumn;

    private JFXTreeTableView<CleanUpRequest> requestsTableSpills;
    private TreeTableColumn<CleanUpRequest, String> requestNameSpillsColumn;
    private TreeTableColumn<CleanUpRequest, String> timeCreatedSpillsColumn;
    private TreeTableColumn<CleanUpRequest, String> timeCompletedSpillsColumn;
    private TreeTableColumn<CleanUpRequest, String> requestTypeSpillsColumn;
    private TreeTableColumn<CleanUpRequest, String> requestDescriptionSpillsColumn;
    private TreeTableColumn<CleanUpRequest, String> requestLocationSpillsColumn;
    private TreeTableColumn<CleanUpRequest, String> requestUserSpillsColumn;

    private JFXTreeTableView<FoodRequest> requestsTableFood;
    private TreeTableColumn<FoodRequest, String> requestNameFoodColumn;
    private TreeTableColumn<FoodRequest, String> timeCreatedFoodColumn;
    private TreeTableColumn<FoodRequest, String> timeCompletedFoodColumn;
    private TreeTableColumn<FoodRequest, String> requestTypeFoodColumn;
    private TreeTableColumn<FoodRequest, String> requestDescriptionFoodColumn;
    private TreeTableColumn<FoodRequest, String> requestLocationFoodColumn;
    private TreeTableColumn<FoodRequest, String> requestUserFoodColumn;

    private TreeItem<InterpreterRequest> intRoot = new TreeItem<>();
    private TreeItem<CleanUpRequest> cleanRoot = new TreeItem<>();
    private TreeItem<FoodRequest> foodRoot = new TreeItem<>();

    public AdminRequestController(InterpreterManager interpreterManager, FoodManager foodManager,
                                  CleanUpManager cleanUpManager, JFXTreeTableView<InterpreterRequest> requestsIntTable,
                                  TreeTableColumn<InterpreterRequest, String> requestIntNameColumn, TreeTableColumn<InterpreterRequest, String> timeCreatedIntColumn,
                                  TreeTableColumn<InterpreterRequest, String> timeCompleteIntColumn, TreeTableColumn<InterpreterRequest, String> requestTypeIntColumn,
                                  TreeTableColumn<InterpreterRequest, String> requestDescriptionIntColumn, TreeTableColumn<InterpreterRequest, String> requestLocationIntColumn,
                                  TreeTableColumn<InterpreterRequest, String> requestUserIntColumn, JFXTreeTableView<CleanUpRequest> requestsTableSpills,
                                  TreeTableColumn<CleanUpRequest, String> requestNameSpillsColumn, TreeTableColumn<CleanUpRequest, String> timeCreatedSpillsColumn,
                                  TreeTableColumn<CleanUpRequest, String> timeCompletedSpillsColumn, TreeTableColumn<CleanUpRequest, String> requestTypeSpillsColumn,
                                  TreeTableColumn<CleanUpRequest, String> requestDescriptionSpillsColumn, TreeTableColumn<CleanUpRequest, String> requestLocationSpillsColumn,
                                  TreeTableColumn<CleanUpRequest, String> requestUserSpillsColumn, JFXTreeTableView<FoodRequest> requestsTableFood,
                                  TreeTableColumn<FoodRequest, String> requestNameFoodColumn, TreeTableColumn<FoodRequest, String> timeCreatedFoodColumn,
                                  TreeTableColumn<FoodRequest, String> timeCompletedFoodColumn, TreeTableColumn<FoodRequest, String> requestTypeFoodColumn,
                                  TreeTableColumn<FoodRequest, String> requestDescriptionFoodColumn, TreeTableColumn<FoodRequest, String> requestLocationFoodColumn,
                                  TreeTableColumn<FoodRequest, String> requestUserFoodColumn) {
        this.interpreterManager = interpreterManager;
        this.foodManager = foodManager;
        this.cleanUpManager = cleanUpManager;
        this.requestsIntTable = requestsIntTable;
        this.requestIntNameColumn = requestIntNameColumn;
        this.timeCreatedIntColumn = timeCreatedIntColumn;
        this.timeCompleteIntColumn = timeCompleteIntColumn;
        this.requestTypeIntColumn = requestTypeIntColumn;
        this.requestDescriptionIntColumn = requestDescriptionIntColumn;
        this.requestLocationIntColumn = requestLocationIntColumn;
        this.requestUserIntColumn = requestUserIntColumn;
        this.requestsTableSpills = requestsTableSpills;
        this.requestNameSpillsColumn = requestNameSpillsColumn;
        this.timeCreatedSpillsColumn = timeCreatedSpillsColumn;
        this.timeCompletedSpillsColumn = timeCompletedSpillsColumn;
        this.requestTypeSpillsColumn = requestTypeSpillsColumn;
        this.requestDescriptionSpillsColumn = requestDescriptionSpillsColumn;
        this.requestLocationSpillsColumn = requestLocationSpillsColumn;
        this.requestUserSpillsColumn = requestUserSpillsColumn;
        this.requestsTableFood = requestsTableFood;
        this.requestNameFoodColumn = requestNameFoodColumn;
        this.timeCreatedFoodColumn = timeCreatedFoodColumn;
        this.timeCompletedFoodColumn = timeCompletedFoodColumn;
        this.requestTypeFoodColumn = requestTypeFoodColumn;
        this.requestDescriptionFoodColumn = requestDescriptionFoodColumn;
        this.requestLocationFoodColumn = requestLocationFoodColumn;
        this.requestUserFoodColumn = requestUserFoodColumn;
    }

    public void initializeScene() {
        initializeSpillsTable();
        initializeFoodTable();
        initializeInterpreterTable();

    }

    private void initializeSpillsTable() {
        for (CleanUpRequest cleanUpRequest : cleanUpManager.getRequests()) {
            cleanRoot.getChildren().add(new TreeItem<>(cleanUpRequest));

        }
        requestNameSpillsColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CleanUpRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getName()));
        timeCreatedSpillsColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CleanUpRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTimeCreated().toString()));
        timeCompletedSpillsColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CleanUpRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTimeCreated().toString()));
        requestTypeSpillsColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CleanUpRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getType()));
        requestDescriptionSpillsColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CleanUpRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getDescription()));
        requestLocationSpillsColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CleanUpRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getNode().getLongName()));
        requestUserSpillsColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CleanUpRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getUser().getUserID()));


        requestsTableSpills.setRoot(cleanRoot);
        requestsTableSpills.setShowRoot(false);

    }

    private void initializeFoodTable() {
        for (FoodRequest foodRequest : foodManager.getRequests()) {
            foodRoot.getChildren().add(new TreeItem<>(foodRequest));

        }
        requestNameFoodColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getName()));
        timeCreatedFoodColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTimeCreated().toString()));
        timeCompletedFoodColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTimeCreated().toString()));
        requestTypeFoodColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getType()));
        requestDescriptionFoodColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getDescription()));
        requestLocationFoodColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getNode().getLongName()));
        requestUserFoodColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<FoodRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getUser().getUserID()));


        requestsTableFood.setRoot(foodRoot);
        requestsTableFood.setShowRoot(false);

    }

    private void initializeInterpreterTable() {
        for (InterpreterRequest interpreterRequest : interpreterManager.getRequests()) {
            intRoot.getChildren().add(new TreeItem<>(interpreterRequest));

        }
        requestIntNameColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<InterpreterRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getName()));
        timeCreatedIntColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<InterpreterRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTimeCreated().toString()));
        timeCompleteIntColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<InterpreterRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTimeCreated().toString()));
        requestTypeIntColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<InterpreterRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getType()));
        requestDescriptionIntColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<InterpreterRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getDescription()));
        requestLocationIntColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<InterpreterRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getNode().getLongName()));
        requestUserIntColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<InterpreterRequest, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getUser().getUserID()));


        requestsIntTable.setRoot(intRoot);
        requestsIntTable.setShowRoot(false);

    }

    public void deleteSpill() {
        if (requestsTableSpills.getSelectionModel().getSelectedItem() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "No selected Item");
            error.show();
        } else {
            TreeItem<CleanUpRequest> selectedMenuItem = requestsTableSpills.getSelectionModel().getSelectedItem();
            cleanUpManager.deleteRequest(selectedMenuItem.getValue());
            cleanRoot.getChildren().remove(selectedMenuItem);
        }
    }

    public void deleteAllSpills() {
        if (requestsTableSpills.getSelectionModel().getSelectedItem() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "No selected Item");
            error.show();
        } else {
            cleanUpManager.getRequests().clear();
            cleanRoot.getChildren().clear();
            initializeSpillsTable();
        }
    }


    public void deleteFood() {
        if (requestsTableFood.getSelectionModel().getSelectedItem() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "No selected Item");
            error.show();
        } else {
            TreeItem<FoodRequest> selectedMenuItem = requestsTableFood.getSelectionModel().getSelectedItem();
            foodManager.deleteRequest(selectedMenuItem.getValue());
            foodRoot.getChildren().remove(selectedMenuItem);
        }
    }

    public void deleteAllFoods() {
        foodManager.getRequests().clear();
        foodRoot.getChildren().clear();
        initializeFoodTable();
    }


    public void deleteInterpeter() {
        if (requestsIntTable.getSelectionModel().getSelectedItem() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "No selected Item");
            error.show();
        } else {
            TreeItem<InterpreterRequest> selectedMenuItem = requestsIntTable.getSelectionModel().getSelectedItem();
            interpreterManager.deleteRequest(selectedMenuItem.getValue());
            intRoot.getChildren().remove(selectedMenuItem);
        }
    }

    public void deleteAllInterpeter() {
        interpreterManager.getRequests().clear();
        intRoot.getChildren().clear();
        initializeInterpreterTable();
    }
}




