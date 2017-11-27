package boundary.sceneControllers;

import Entity.CleanUpRequest;
import Entity.User;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import Request.RequestCleanupController;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;

public class StaffRequestController extends AbstractMapController{
    private RequestCleanupController requestCleanupController;
    private JFXListView allStaffRequests, requestsIMade;
    private CleanUpRequest selectedRequest;
    private JFXTextField selectedRequestTextField;
    private User user;


    public StaffRequestController(ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p,
                                  Label currentFloorNum, RequestCleanupController r, JFXListView allStaffRequests,
                                  JFXListView requestsIMade, JFXTextField selectedRequestTextField) {
        super(i, mapPane, canvas, m, p, currentFloorNum);
        this.allStaffRequests = allStaffRequests;
        this.requestsIMade = requestsIMade;
        this.requestCleanupController = r;
        this.selectedRequestTextField = selectedRequestTextField;
    }

    public void initializeScene(User user){
        super.initializeScene();
        allStaffRequests.setItems(requestCleanupController.getRequests());
        allStaffRequests.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRequest = (CleanUpRequest) allStaffRequests.getItems().get(newValue.intValue());
            currentLoc = selectedRequest.getNode();
            selectedRequestTextField.setText(currentLoc.getNodeID());
            refreshCanvas();
        });
        requestsIMade.setItems(requestCleanupController.getRequests());
        requestsIMade.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRequest = (CleanUpRequest) requestsIMade.getItems().get(newValue.intValue());
            refreshCanvas();
        });
        this.user = user;
        drawAllRequests();
    }

    public void clickOnMap(MouseEvent m) {
        super.clickOnMap(m);
        selectedRequestTextField.setText(currentLoc.getNodeID());
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

    public void addRequest(JFXTextField requestName, JFXTextField description) {
        LocalDateTime l = LocalDateTime.now();
        requestCleanupController.addRequest(new CleanUpRequest(requestName.getText(), l, l, "Cleanup",
                description.getText(), currentLoc, user));
        allStaffRequests.setItems(requestCleanupController.getRequests());
        refreshCanvas();
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
        selectedRequestTextField.setText(currentLoc.getNodeID());
    }
}