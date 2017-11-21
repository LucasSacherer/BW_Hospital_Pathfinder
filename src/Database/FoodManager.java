package Database;



import Entity.FoodRequest;

import java.util.ArrayList;
import java.util.List;

public class FoodManager {

    private final List<FoodRequest> requests;

    public FoodManager(){
        requests = new ArrayList<>();
    }

    /**
     * Updates the internal list of uncompleted FoodRequests to match the database
     */
    public void updateRequests(){

    }

    /**
     * Updates the given request in the database (must have matching name and timestamp)
     * @param fReq
     */
    public void updateRequest(FoodRequest fReq){

    }

    /**
     * Sets the given request as completed in the database (does not delete but stops it from showing up in lists)
     * @param fReq
     */
    public void completeRequest(FoodRequest fReq){

    }

    /**
     * Deletes the given request from the database (This request will not be included in request reports)
     * @param fReq
     */
    public void deleteRequest(FoodRequest fReq){

    }

    /**
     * Returns the list of unfinished FoodRequest (Since the last updateRequests() call)
     * @return
     */
    public List<FoodRequest> getRequests(){
        return null;
    }

    /**
     * Adds the given request into the database as an uncompleted request
     * @param fReq
     */
    public void addRequest(FoodRequest fReq){

    }

    /**
     * Returns a list of the completed request directly from the database
     * @return
     */
    public List<FoodRequest> getCompleted(){
        return null;
    }
}
