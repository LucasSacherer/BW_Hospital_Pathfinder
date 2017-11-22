package boundary.sceneControllers;

import Entity.CleanUpRequest;
import Entity.FoodRequest;
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
import javafx.scene.layout.Pane;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class StaffRequestController extends AbstractMapController{
    private RequestCleanupController requestCleanupController;
    private JFXListView allStaffRequests;

    public StaffRequestController(ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p,
                                  Label currentFloorNum, RequestCleanupController r, JFXListView allStaffRequests) {
        super(i, mapPane, canvas, m, p, currentFloorNum);
        this.allStaffRequests = allStaffRequests;
        this.requestCleanupController = r;
    }

    public void initializeScene(){
        super.initializeScene();
        allStaffRequests.setItems(requestCleanupController.getRequests());
    }

    public void addRequest(JFXTextField name, JFXTextField description) {
        LocalDateTime l = LocalDateTime.now();
        System.out.println(origin);
        User user = new User("staff1", "staff1", "staff1", false, "yay");
        requestCleanupController.addRequest(new CleanUpRequest(name.getText(), l, l, "Cleanup", description.getText(), origin, user));
    }

    public void completeRequest() {

    }

    public void editRequest() {

    }

    public void deleteRequest() {

    }
}