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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
    private Request requestToComplete, requestToDelete;
    private JFXTextField nodeID, requestCleanupName, requestInterpreterName, requestFoodName, foodItem;
    private User user;
    private JFXComboBox languageSelect;
    private JFXListView currentFoodOrder;
    private ObservableList foodOrderList = FXCollections.observableArrayList();
    private ObservableList allStaffRequestsList, requestsIMadeList;

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
        requestsIMadeList = genericRequestController.getAllRequestsByUser(user);
        allStaffRequestsList = genericRequestController.getAllRequestsByDepartment(user.getDepartment());
        super.initializeScene();
        this.user = user;
        languageSelect.setItems(languages);
        refreshLists();
        allStaffRequests.setItems(allStaffRequestsList);
        requestsIMade.setItems(requestsIMadeList);
        allStaffRequests.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue.intValue());
            if (newValue.intValue() != -1) {
                requestToComplete = (Request) allStaffRequests.getItems().get(newValue.intValue());
                currentLoc = requestToComplete.getNode();
                nodeID.setText(requestToComplete.getNode().getNodeID());
                requestInfo.setText(requestToComplete.getRequestReport());
                refreshCanvas();
            }
        });
        requestsIMade.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                requestToDelete = (Request) requestsIMade.getItems().get(newValue.intValue());
                currentLoc = requestToDelete.getNode();
                nodeID.setText(requestToDelete.getNode().getNodeID());
                requestInfo.setText(requestToDelete.getDescription());
                refreshCanvas();
            }
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
        if (requestToComplete != null && requestToComplete.getNode() != null && currentFloor.equals(requestToComplete.getNode().getFloor())) {
            gc.setFill(Color.YELLOW);
            gc.fillOval(requestToComplete.getNode().getXcoord() - 10, requestToComplete.getNode().getYcoord() - 10, 20, 20);
        }
        for (Request r : genericRequestController.getAllRequestsByDepartment(user.getDepartment())) {
            if (r.getNode().getFloor().equals(currentFloor)) {
                gc.setFill(Color.MEDIUMPURPLE);
                gc.fillOval(r.getNode().getXcoord() - 5, r.getNode().getYcoord() - 5, 10, 10);
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
        allStaffRequests.getSelectionModel().clearSelection();
        requestsIMade.getSelectionModel().clearSelection();
        requestsIMadeList.clear();
        allStaffRequestsList.clear();
        requestsIMadeList.addAll(genericRequestController.getAllRequestsByUser(user));
        allStaffRequestsList.addAll(genericRequestController.getAllRequestsByDepartment(user.getDepartment()));

//        ObservableList allRequests = FXCollections.observableArrayList();
//        allRequests.addAll(requestCleanupController.getRequests());
//        allRequests.addAll(requestInterpreterController.getRequests());
//        allRequests.addAll(requestFoodController.getRequests());
//
//        allStaffRequestsList.addAll(allRequests); //todo this needs to check the user's dept.
    }

    public void completeRequest() { //TODO
        if (requestToComplete != null) {
            genericRequestController.completeRequests(requestToComplete);
            refreshCanvas();
            refreshLists();
            requestInfo.clear();
        }
    }

    public void deleteRequest() { //TODO
        if (requestToDelete != null && requestToDelete.getUser().getUserID().equals(user.getUserID())) {
            genericRequestController.deleteRequest(requestToDelete);
            requestToDelete = null;
            refreshCanvas();
            refreshLists();
        }
    }

    public void navigateToRequest() {
        if (origin != null && requestToComplete != null && requestToComplete.getNode() != null) {
            destination = requestToComplete.getNode();
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

    public void editRequest() {
    }
}