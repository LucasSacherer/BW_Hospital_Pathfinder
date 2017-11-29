package boundary.sceneControllers;

import Entity.*;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import Request.GenericRequestController;
import Request.RequestCleanupController;
import Request.RequestFoodController;
import Request.RequestInterpreterController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class StaffRequestController extends AbstractMapController{
    private ObservableList languages = FXCollections.observableArrayList("Spanish", "French", "Mandarin", "Finnish");
    private JFXTextArea requestCleanupDescription, requestInterpreterDescription, requestFoodDescription, requestInfo;
    private ObservableList requestTypeList  = FXCollections.observableArrayList("Cleanup", "Interpreter", "Food");
    private GenericRequestController genericRequestController;
    private RequestCleanupController requestCleanupController;
    private RequestInterpreterController requestInterpreterController;
    private RequestFoodController requestFoodController;
    private JFXListView allStaffRequests, requestsIMade;
    private Request selectedRequest;
    private JFXTextField nodeID, requestCleanupName, requestInterpreterName, requestFoodName, foodItem;
    private User user;
    private ChoiceBox requestChoiceBox;
    private JFXComboBox languageSelect;
    private JFXListView currentFoodOrder;
    private ObservableList foodOrderList = FXCollections.observableArrayList();

    public StaffRequestController(ImageView requestImageView, Pane requestMapPane, Canvas requestCanvas,
                                  MapNavigationFacade mapNavigationFacade, PathFindingFacade pathFindingFacade,
                                  Label currentFloorNumRequest, GenericRequestController genericRequestController,
                                  RequestCleanupController requestCleanupController,
                                  RequestInterpreterController requestInterpreterController,
                                  RequestFoodController requestFoodController,
                                  JFXListView allStaffRequests, JFXListView requestsIMade, JFXTextField requestNodeID,
                                  JFXTextField requestCleanupName, JFXTextField requestInterpreterName,
                                  JFXTextField requestFoodName, JFXTextArea cleanupDescription,
                                  JFXComboBox languageSelect, JFXTextArea requestInterpreterDescription,
                                  JFXTextArea requestFoodDescription, JFXTextArea requestInfo,
                                  JFXListView currentFoodOrder, JFXTextField foodItem) {
        super(requestImageView, requestMapPane, requestCanvas, mapNavigationFacade, pathFindingFacade, currentFloorNumRequest);
        this.requestCleanupController = requestCleanupController;
        this.allStaffRequests = allStaffRequests;
        this.requestsIMade = requestsIMade;
        this.nodeID = requestNodeID;
        this.requestCleanupName = requestCleanupName;
        this.requestInterpreterName = requestInterpreterName;
        this.requestFoodName = requestFoodName;
        this.requestCleanupDescription = cleanupDescription;
        this.requestInterpreterDescription = requestInterpreterDescription;
        this.languageSelect = languageSelect;
        this.requestInterpreterController = requestInterpreterController;
        this.requestFoodController = requestFoodController;
        this.genericRequestController = genericRequestController;
        this.requestFoodDescription = requestFoodDescription;
        this.requestInfo = requestInfo;
        this.currentFoodOrder = currentFoodOrder;
        this.foodItem = foodItem;
    }

    public void initializeScene(User user){
        super.initializeScene();
        this.user = user;
        languageSelect.setItems(languages);
        refreshLists();
        allStaffRequests.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRequest = (Request) allStaffRequests.getItems().get(newValue.intValue());
            currentLoc = selectedRequest.getNode();
            nodeID.setText(currentLoc.getNodeID());
            requestInfo.setText(selectedRequest.getRequestReport());
            refreshCanvas();
        });
        requestsIMade.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRequest = (Request) requestsIMade.getItems().get(newValue.intValue());
            currentLoc = selectedRequest.getNode();
            nodeID.setText(currentLoc.getNodeID());
            requestInfo.setText(selectedRequest.getDescription());
            refreshCanvas();
        });
        refreshCanvas();
        // requestChoiceBox.setItems(requestTypeList);
    }

    public void clickOnMap(MouseEvent m) {
        super.clickOnMap(m);
        nodeID.setText(currentLoc.getNodeID());
    }

    public void refreshCanvas() {
        super.refreshCanvas();
        drawAllRequests();
    }

    public void drawAllRequests() {
        if (selectedRequest != null && selectedRequest.getNode() != null && currentFloor.equals(selectedRequest.getNode().getFloor())) {
            gc.setFill(Color.YELLOW);
            gc.fillOval(selectedRequest.getNode().getXcoord() - 10, selectedRequest.getNode().getYcoord() - 10, 20, 20);
        }
        for (CleanUpRequest c : requestCleanupController.getRequests()) {
            if (c.getNode().getFloor().equals(currentFloor)) {
                gc.setFill(Color.MEDIUMPURPLE);
                gc.fillOval(c.getNode().getXcoord() - 5, c.getNode().getYcoord() - 5, 10, 10);
            }
        }
    }

    public void addCleanup() {
        System.out.println(currentLoc + " " + user + requestCleanupDescription.getText() + requestCleanupName.getText());
        LocalDateTime l = LocalDateTime.now();
        requestCleanupController.addRequest(new CleanUpRequest(requestCleanupName.getText(), l, l, "Cleanup",
                requestCleanupDescription.getText(), currentLoc, user));
        refreshLists();
        refreshCanvas();
    }

    private void refreshLists() {

        requestsIMade.setItems(genericRequestController.getAllRequestsByUser(user));
        ObservableList allRequests = FXCollections.observableArrayList();

        /* for testing */
//        allRequests.addAll(requestCleanupController.getRequests());
//        allRequests.addAll(requestInterpreterController.getRequests());
//        allRequests.addAll(requestFoodController.getRequests());
//
//        allStaffRequests.setItems(allRequests); //todo this needs to check the user's dept.

        allStaffRequests.setItems(genericRequestController.getAllRequestsByDepartment(user.getDepartment()));
    }

    public void completeRequest() { //TODO
        if (selectedRequest != null) {
            genericRequestController.completeRequests(selectedRequest);
            refreshCanvas();
            refreshLists();
        }
    }

    public void deleteRequest() { //TODO
        if (selectedRequest != null && selectedRequest.getUser().getUserID().equals(user.getUserID())) {
            genericRequestController.deleteRequest(selectedRequest);
            selectedRequest = null;
            refreshCanvas();
            refreshLists();
        }
    }

    public void navigateToRequest() {
        if (origin != null && selectedRequest != null && selectedRequest.getNode() != null) {
            destination = selectedRequest.getNode();
            findPath();
        }
    }

    public void snapToNode(MouseEvent m) {
        super.snapToNode(m);
        nodeID.setText(currentLoc.getNodeID());
    }

    public void resetCleanup() {
        requestCleanupName.clear();
        requestCleanupDescription.clear();
    }

    public void resetInterpreter() {
        requestInterpreterName.clear();
        requestInterpreterDescription.clear();
        languageSelect.setItems(languages);
    }

    public void addInterpreter() {
        if (currentLoc == null) return;
        LocalDateTime l = LocalDateTime.now();
        InterpreterRequest iReq = new InterpreterRequest(requestInterpreterName.getText(), l, l, "interpreter",
                requestInterpreterDescription.getText(), currentLoc, user,
                languageSelect.getSelectionModel().getSelectedItem().toString());
        requestInterpreterController.addRequest(iReq);
        refreshLists();
        refreshCanvas();
    }

    public void submitFoodRequest() {
        if (currentLoc == null) return;
        LocalDateTime l = LocalDateTime.now();
        ArrayList<String> order = new ArrayList<>();
        order.addAll(foodOrderList);
        FoodRequest fReq = new FoodRequest(requestFoodName.getText(), l, l, "food",
                requestFoodDescription.getText(), currentLoc, user, order);
        requestFoodController.addRequest(fReq);
        resetFoodRequest();
        refreshLists();
    }

    public void resetCurrentOrder() {
        foodOrderList.clear();
        currentFoodOrder.setItems(foodOrderList);
    }

    public void addFoodItem() {
        System.out.println("made it");
        foodOrderList.add(foodItem.getText());
        currentFoodOrder.setItems(foodOrderList);
        foodItem.clear();
    }

    public void resetFoodRequest() {
        foodOrderList.clear();
        currentFoodOrder.setItems(foodOrderList);
        requestFoodDescription.clear();
        requestFoodName.clear();
        foodItem.clear();
    }

}