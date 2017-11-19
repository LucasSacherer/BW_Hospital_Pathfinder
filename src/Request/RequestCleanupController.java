package Request;

import Database.CleanUpManager;
import Entity.CleanUpRequest;

import java.util.List;

public class RequestCleanupController {

    private final CleanUpManager cleanUpManager;

    public RequestCleanupController(CleanUpManager cm){
        cleanUpManager = cm;
    }

    /**
     * Takes a request, checks if it's valid, and then gives it to the manager to be added
     * @param cReq
     */
    public void addRequest(CleanUpRequest cReq){

    }

    /**
     * Takes a request and checks if it's valid (has all the needed information, not null, ...)
     * @param cReq
     * @return
     */
    private boolean validateRequest(CleanUpRequest cReq){
       return false;
    }

    /**
     * returns a list of uncompleted requests of this type (from the manager)
     * @return
     */
    public List<CleanUpRequest> getRequests(){
        return null;
    }

    /**
     * Deletes the given request through the specific request manager
     * @param cReq
     */
    public void deleteRequest(CleanUpRequest cReq){

    }

    /**
     * Updates the given request (only the name and timestamp must match the original request)
     * @param cReq
     */
    public void updateRequest(CleanUpRequest cReq){

    }

    /**
     * Marks the given request as done in the database
     * @param cReq
     */
    public void completeRequest(CleanUpRequest cReq){

    }
}
