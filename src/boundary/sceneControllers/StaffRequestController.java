package boundary.sceneControllers;

import Entity.CleanUpRequest;
import Entity.FoodRequest;
import Entity.Node;
import Entity.User;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import Request.RequestCleanupController;
import Request.RequestFoodController;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class StaffRequestController extends AbstractMapController{
    private RequestCleanupController requestCleanupController;
    private JFXListView allStaffRequests, requestsIMade;
    private CleanUpRequest selectedRequest;
    private JFXTextField selectedRequestNode;


    public StaffRequestController(ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p,
                                  Label currentFloorNum, RequestCleanupController r, JFXListView allStaffRequests,
                                  JFXListView requestsIMade, JFXTextField selectedRequestNode) {
        super(i, mapPane, canvas, m, p, currentFloorNum);
        this.allStaffRequests = allStaffRequests;
        this.requestsIMade = requestsIMade;
        this.requestCleanupController = r;
        this.selectedRequestNode = selectedRequestNode;
    }

    public void initializeScene(){
        super.initializeScene();
        allStaffRequests.setItems(requestCleanupController.getRequests());
        allStaffRequests.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRequest = (CleanUpRequest) allStaffRequests.getItems().get(newValue.intValue());
            refreshCanvas();
        });
    }

    public void addRequest(JFXTextField requestName, JFXTextField description) {
        LocalDateTime l = LocalDateTime.now();
        User user = new User("staff1", "staff1", "staff1", false, "yay");
        requestCleanupController.addRequest(new CleanUpRequest(requestName.getText(), l, l, "Cleanup", description.getText(), origin, user));
    }

    public void completeRequest() {
        if (selectedRequest != null) requestCleanupController.completeRequest(selectedRequest);
    }

    public void editRequest() {
        //TODO
    }

    public void deleteRequest() {
        //TODO
    }

    public void navigateToRequest() {
        if (origin != null && selectedRequest != null && selectedRequest.getNode() != null) {
            destination = selectedRequest.getNode();
            findPath();
        }
    }

    public void snapToNode(MouseEvent m) {
        super.snapToNode(m);
        selectedRequestNode.setText(currentLoc.getNodeID());
    }
}