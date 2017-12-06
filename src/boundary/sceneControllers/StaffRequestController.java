package boundary.sceneControllers;

import Entity.*;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import Request.GenericRequestController;
import Request.RequestCleanupController;
import Request.RequestFoodController;
import Request.RequestInterpreterController;
import boundary.GodController;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;
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
    private Node requestNodeToComplete, requestNodeToDelete;
    private JFXTextField nodeID, requestCleanupName, requestInterpreterName, requestFoodName, foodItem;
    private User user;
    private JFXComboBox languageSelect;
    private JFXListView currentFoodOrder;
    private ObservableList foodOrderList = FXCollections.observableArrayList();
    private ObservableList allStaffRequestsList, requestsIMadeList;

    public StaffRequestController(GodController g, ImageView requestImageView, Pane requestMapPane, Canvas requestCanvas,
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
                                  JFXListView currentFoodOrder, JFXTextField foodItem, JFXSlider zoomSlider) {
        super(g, requestImageView, requestMapPane, requestCanvas, mapNavigationFacade, pathFindingFacade, currentFloorNumRequest, zoomSlider);
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
                requestNodeToComplete = requestToComplete.getNode();
                nodeID.setText(requestToComplete.getNode().getNodeID());
                requestInfo.setText(requestToComplete.getRequestReport());
                refreshRequestCanvas();
            }
        });
        requestsIMade.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                requestToDelete = (Request) requestsIMade.getItems().get(newValue.intValue());
                requestNodeToDelete = requestToDelete.getNode();
                nodeID.setText(requestToDelete.getNode().getNodeID());
                requestInfo.setText(requestToDelete.getDescription());
                refreshRequestCanvas();
            }
        });
        refreshRequestCanvas();
        // requestChoiceBox.setItems(requestTypeList);
    }

    public void zoomInMap() {
        super.zoomInMap();
        refreshRequestCanvas();
    }

    public void zoomOutMap() {
        super.zoomOutMap();
        refreshRequestCanvas();
    }

    public void floorDown() throws IOException, SQLException {
        super.floorDown();
        refreshRequestCanvas();
    }

    public void floorUp() throws IOException, SQLException {
        super.floorUp();
        refreshRequestCanvas();
    }
    public void clickOnMap(MouseEvent m) {
        int x = (int) m.getX();
        int y = (int) m.getY();
        currentLoc = mapNavigationFacade.getNearestNode(x,y,currentFloor);
        nodeID.setText(currentLoc.getNodeID());
        refreshRequestCanvas();
    }

    public void refreshRequestCanvas() {
//        super.refreshCanvas();
        clearCanvas();
        drawAllRequests();
    }

    public void drawAllRequests() {
        if(currentLoc != null && currentLoc.getFloor().equals(currentFloor)) {
            gc.setFill(Color.BLUE);
            gc.fillOval(currentLoc.getXcoord() - 5, currentLoc.getYcoord() - 5, 10, 10);
        }
        if (requestNodeToComplete != null && currentFloor.equals(requestNodeToComplete.getFloor())) {
            gc.setFill(Color.BLACK);
            gc.fillOval(requestNodeToComplete.getXcoord() - 11, requestNodeToComplete.getYcoord() - 11, 22, 22);
            gc.setFill(Color.YELLOW);
            gc.fillOval(requestNodeToComplete.getXcoord() - 10, requestNodeToComplete.getYcoord() - 10, 20, 20);
        }
        if (requestNodeToDelete != null && currentFloor.equals(requestNodeToDelete.getFloor())) {
            gc.setFill(Color.BLACK);
            gc.fillOval(requestNodeToDelete.getXcoord() - 11, requestNodeToDelete.getYcoord() - 11, 22, 22);
            gc.setFill(Color.ORANGE);
            gc.fillOval(requestNodeToDelete.getXcoord() - 10, requestNodeToDelete.getYcoord() - 10, 20, 20);
        }
        ArrayList<Request> requestsToShow = new ArrayList<>();
        requestsToShow.addAll(genericRequestController.getAllRequestsByDepartment(user.getDepartment()));
        for (Request r : requestsToShow) {
            if (r.getNode().getFloor().equals(currentFloor)) {
                gc.setFill(Color.BLACK);
                gc.fillOval(r.getNode().getXcoord() - 6, r.getNode().getYcoord() - 6, 12, 12);
                gc.setFill(Color.MEDIUMPURPLE);
                gc.fillOval(r.getNode().getXcoord() - 5, r.getNode().getYcoord() - 5, 10, 10);
            }
        }
    }


    public void addCleanup() {
        LocalDateTime l = LocalDateTime.now();
        boolean success = true;
        try {
            currentLoc.equals("");
            user.equals("");
            if(requestCleanupName.getText().equals("")||requestCleanupDescription.getText().equals("")){
                throw new NullPointerException();
            }
        }
        catch(NullPointerException e){
            errorController.showError("Please complete all request information fields.");
            success = false;
        }
        if(success) {
            requestCleanupController.addRequest(new CleanUpRequest(requestCleanupName.getText(), l, l, "Cleanup",
                    requestCleanupDescription.getText(), currentLoc, user));
            refreshLists();
            resetCleanup();
        }
    }

    private void refreshLists() {
        allStaffRequests.getSelectionModel().clearSelection();
        requestsIMade.getSelectionModel().clearSelection();
        requestsIMadeList.clear();
        allStaffRequestsList.clear();
        requestsIMadeList.addAll(genericRequestController.getAllRequestsByUser(user));
        allStaffRequestsList.addAll(genericRequestController.getAllRequestsByDepartment(user.getDepartment()));
        refreshRequestCanvas();

        /* start test */
//        ObservableList allRequests = FXCollections.observableArrayList();
//        allRequests.addAll(requestCleanupController.getRequests());
//        allRequests.addAll(requestInterpreterController.getRequests());
//        allRequests.addAll(requestFoodController.getRequests());

//        ArrayList<Request> requestsToShow = new ArrayList<>();
//        requestsToShow.addAll(allRequests);
//        for (Request r : requestsToShow) {
//            if (r.getNode().getFloor().equals(currentFloor)) {
//                gc.setFill(Color.BLACK);
//                gc.fillOval(r.getNode().getXcoord() - 6, r.getNode().getYcoord() - 6, 12, 12);
//                gc.setFill(Color.MEDIUMPURPLE);
//                gc.fillOval(r.getNode().getXcoord() - 5, r.getNode().getYcoord() - 5, 10, 10);
//            }
//        }
//
//        allStaffRequestsList.addAll(allRequests);
        /* end test */
    }

    public void completeRequest() {
        if (requestToComplete != null) {
            genericRequestController.completeRequests(requestToComplete);
            if (requestNodeToComplete == requestNodeToDelete) requestNodeToDelete = null;
            requestToComplete = null;
            requestNodeToComplete = null;
            refreshLists();
            requestInfo.clear();
        }
    }

    public void deleteRequest() {
        if (requestToDelete != null && requestToDelete.getUser().getUserID().equals(user.getUserID())) {
            genericRequestController.deleteRequest(requestToDelete);
            if (requestNodeToDelete == requestNodeToComplete) requestNodeToComplete = null;
            requestNodeToDelete = null;
            requestToDelete = null;
            refreshLists();
        }
    }

    public void navigateToRequest() throws IOException {
        if (origin != null && requestNodeToComplete != null) {
            destination = requestNodeToComplete;
            findPath();
        }
        refreshRequestCanvas();
    }

//    public void snapToNode(MouseEvent m) {
//        int x = (int) m.getX();
//        int y = (int) m.getY();
//        currentLoc = mapNavigationFacade.getNearestNode(x,y,currentFloor);
//        nodeID.setText(currentLoc.getNodeID());
//        refreshRequestCanvas();
//    }

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
        boolean success = true;
        try {
            currentLoc.equals("");
            user.equals("");
            if(requestInterpreterName.getText().equals("")||requestInterpreterDescription.getText().equals("")){
                throw new NullPointerException();
            }
        }
        catch(NullPointerException e){
            errorController.showError("Please complete all request information fields.");
            success = false;
        }
        if(success) {
            if (currentLoc == null) return;
            LocalDateTime l = LocalDateTime.now();
            InterpreterRequest iReq = new InterpreterRequest(requestInterpreterName.getText(), l, l, "interpreter",
                    requestInterpreterDescription.getText(), currentLoc, user,
                    languageSelect.getSelectionModel().getSelectedItem().toString());
            requestInterpreterController.addRequest(iReq);
            refreshLists();
            resetInterpreter();
        }
    }

    public void submitFoodRequest() {
        boolean success = true;
        try {
            currentLoc.equals("");
            user.equals("");
            if(requestFoodName.getText().equals("")||requestFoodDescription.getText().equals("")){
                throw new NullPointerException();
            }
        }
        catch(NullPointerException e){
            errorController.showError("Please complete all request information fields.");
            success = false;
        }
        if(success) {
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
    }

    public void resetCurrentOrder() {
        foodOrderList.clear();
        currentFoodOrder.setItems(foodOrderList);
    }

    public void addFoodItem() {
        boolean success = true;
        try {

            if(foodItem.getText().equals("")){
                throw new NullPointerException();
            }
        }
        catch(NullPointerException e){
            errorController.showError("Please select a food item.");
            success = false;
        }
        if(success) {
            //System.out.println("made it");
            foodOrderList.add(foodItem.getText());
            currentFoodOrder.setItems(foodOrderList);
            foodItem.clear();
        }
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