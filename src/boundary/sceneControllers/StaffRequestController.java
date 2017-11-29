package boundary.sceneControllers;

import Entity.CleanUpRequest;
import Entity.Node;
import Entity.User;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import Request.RequestCleanupController;
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

public class StaffRequestController extends AbstractMapController{
    private JFXTextArea requestCleanupDescription;
    private ObservableList requestTypeList  = FXCollections.observableArrayList("Cleanup", "Interpreter", "Food");
    private RequestCleanupController requestCleanupController;
    private JFXListView allStaffRequests, requestsIMade;
    private CleanUpRequest selectedRequest;
    private JFXTextField nodeID, requestCleanupName, requestInterpreterName, requestFoodName;
    private User user;
    private ChoiceBox requestChoiceBox;

    public StaffRequestController(ImageView requestImageView, Pane requestMapPane, Canvas requestCanvas,
                                  MapNavigationFacade mapNavigationFacade, PathFindingFacade pathFindingFacade,
                                  Label currentFloorNumRequest, RequestCleanupController requestCleanupController,
                                  JFXListView allStaffRequests, JFXListView requestsIMade, JFXTextField requestNodeID,
                                  JFXTextField requestCleanupName, JFXTextField requestInterpreterName,
                                  JFXTextField requestFoodName, JFXTextArea requestCleanupDescription) {
        super(requestImageView, requestMapPane, requestCanvas, mapNavigationFacade, pathFindingFacade, currentFloorNumRequest);
        this.requestCleanupController = requestCleanupController;
        this.allStaffRequests = allStaffRequests;
        this.requestsIMade = requestsIMade;
        this.nodeID = requestNodeID;
        this.requestCleanupName = requestCleanupName;
        this.requestInterpreterName = requestInterpreterName;
        this.requestFoodName = requestFoodName;
        this.requestCleanupDescription = requestCleanupDescription;
    }

    public void initializeScene(User user){
        super.initializeScene();
        allStaffRequests.setItems(requestCleanupController.getRequests());
        allStaffRequests.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRequest = (CleanUpRequest) allStaffRequests.getItems().get(newValue.intValue());
            currentLoc = selectedRequest.getNode();
            nodeID.setText(currentLoc.getNodeID());
            refreshCanvas();
        });
        requestsIMade.setItems(requestCleanupController.getRequests());
        requestsIMade.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRequest = (CleanUpRequest) requestsIMade.getItems().get(newValue.intValue());
            refreshCanvas();
        });
        this.user = user;
        System.out.println(user);
        drawAllRequests();
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
        requestsIMade.setItems(null);
        allStaffRequests.setItems(null);
    }

    public void completeRequest() {
        if (selectedRequest != null) {
            requestCleanupController.completeRequest(selectedRequest);
            refreshCanvas();
        }
    }

    public void editRequest() {
        if (selectedRequest != null && selectedRequest.getUser().getUserID().equals(user.getUserID())) {
            // edit request
            refreshCanvas();
        }
    }

    public void deleteRequest() {
        if (selectedRequest != null && selectedRequest.getUser().getUserID().equals(user.getUserID())) {
            requestCleanupController.deleteRequest(selectedRequest);
            refreshCanvas();
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
}