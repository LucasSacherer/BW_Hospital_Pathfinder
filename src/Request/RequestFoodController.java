package Request;

import Database.FoodManager;
import entity.FoodRequest;

import java.util.List;

public class RequestFoodController {
    private final FoodManager foodManager;

    public RequestFoodController(FoodManager fm){
        foodManager = fm;
    }

    /**
     * Takes a request, checks if it's valid, and then gives it to the manager to be added
     * @param fReq
     */
    public void addRequest(FoodRequest fReq){

    }

    /**
     * Takes a request and checks if it's valid (has all the needed information, not null, ...)
     * @param fReq
     * @return
     */
    private boolean validateRequest(FoodRequest fReq){
        return false;
    }

    /**
     * returns a list of uncompleted requests of this type (from the manager)
     * @return
     */
    public List<FoodRequest> getRequests(){
        return null;
    }

    /**
     * Deletes the given request through the specific request manager
     * @param fReq
     */
    public void deleteRequest(FoodRequest fReq){

    }

    /**
     * Updates the given request (only the name and timestamp must match the original request)
     * @param fReq
     */
    public void updateRequest(FoodRequest fReq){

    }

    /**
     * Marks the given request as done in the database
     * @param fReq
     */
    public void completeRequest(FoodRequest fReq){

    }
}
