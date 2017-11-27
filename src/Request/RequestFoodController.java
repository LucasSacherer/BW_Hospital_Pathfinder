package Request;

import Database.FoodManager;
import Entity.ErrorScreen;
import Entity.FoodRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class RequestFoodController {
    private final FoodManager foodManager;
    private ErrorScreen errorscreen;

    public RequestFoodController(FoodManager fm){
        foodManager = fm;
    }

    /**
     * Takes a request, checks if it's valid, and then gives it to the manager to be added
     * @param fReq
     */
    public void addRequest(FoodRequest fReq){
        //Check that cReq has a name and timeCompleted that is unique to all cleanUpRequests
        foodManager.updateRequests();
        if (validateRequest(fReq)){
            foodManager.addRequest(fReq);
        }else errorscreen.displayError("The request is invalid, make sure there it has a UNIQUE name and time created pair");
    }

    /**
     * Takes a request and checks if it's valid (has all the needed information, not null, ...)
     * @param fReq
     * @return
     */
    private boolean validateRequest(FoodRequest fReq){
        //Check that cReq has a name and timeCompleted that is unique to all cleanUpRequests
        foodManager.updateRequests();
        if (fReq.getName() != null && fReq.getTimeCreated() != null && fReq.getNode()!=null){
            if (foodManager.getFoodRequest(fReq.getName(), fReq.getTimeCreated()) == null){
                return true;
            } else return false;
        } else return false;
    }

    /**
     * returns a list of uncompleted requests of this type (from the manager)
     * @return
     */
    public ObservableList<FoodRequest> getRequests(){
        ObservableList requests =  FXCollections.observableArrayList();
        requests.addAll(foodManager.getRequests());
        return requests;
    }

    /**
     * Deletes the given request through the specific request manager
     * @param fReq
     */
    public void deleteRequest(FoodRequest fReq){
        foodManager.updateRequests();
        //Check to make sure the request exists
        if (foodManager.getFoodRequest(fReq.getName(), fReq.getTimeCreated()) != null){
            foodManager.deleteRequest(fReq);
        } else errorscreen.displayError("The request you want to delete does not exist");
    }

    /**
     * Updates the given request (only the name and timestamp must match the original request)
     * @param fReq
     */
    public void updateRequest(FoodRequest fReq){
        //Confirm that this already exists
        foodManager.updateRequests();
        if (foodManager.getFoodRequest(fReq.getName(), fReq.getTimeCreated()) != null){
            foodManager.updateRequest(fReq);
        }
        else errorscreen.displayError("This request does not already exist in the database");
    }

    /**
     * Marks the given request as done in the database
     * @param fReq
     */
    public void completeRequest(FoodRequest fReq){
        foodManager.updateRequests();
        //First confiurm that the request exists
        if (foodManager.getFoodRequest(fReq.getName(), fReq.getTimeCreated()) != null){
            foodManager.completeRequest(fReq);
        }
        else errorscreen.displayError("This request does not already exist in the database");
    }
}
