package Database;

import Entity.CleanUpRequest;

import java.util.ArrayList;
import java.util.List;

public class CleanUpManager {
    private final List<CleanUpRequest> requests;

    public CleanUpManager(){
        requests = new ArrayList<>();
    }

    /**
     * Updates the internal list of uncompleted CleanUpRequests to match the database
     */
    public void updateRequests(){

    }

    /**
     * Updates the given request in the database (must have matching name and timestamp)
     * @param cReq
     */
    public void updateRequest(CleanUpRequest cReq){

    }

    /**
     * Sets the given request as completed in the database (does not delete but stops it from showing up in lists)
     * @param cReq
     */
    public void completeRequest(CleanUpRequest cReq){

    }

    /**
     * Deletes the given request from the database (This request will not be included in request reports)
     * @param cReq
     */
    public void deleteRequest(CleanUpRequest cReq){

    }

    /**
     * Returns the list of unfinished FoodRequest (Since the last updateRequests() call)
     * @return
     */
    public List<CleanUpRequest> getRequests(){
        return null;
    }

    /**
     * Adds the given request into the database as an uncompleted request
     * @param cReq
     */
    public void addRequest(CleanUpRequest cReq){

    }

    /**
     * Returns a list of the completed request directly from the database
     * @return
     */
    public List<CleanUpRequest> getCompleted(){
        return null;
    }
}
