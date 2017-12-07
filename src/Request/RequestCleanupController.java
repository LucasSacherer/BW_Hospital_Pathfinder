package Request;

import Database.CleanUpManager;
import Entity.CleanUpRequest;
import Entity.ErrorController;
import Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RequestCleanupController {

    private final CleanUpManager cleanUpManager;
    private ErrorController errorController;

    public RequestCleanupController(CleanUpManager cm){
        cleanUpManager = cm;
    }

    /**
     * Takes a request, checks if it's valid, and then gives it to the manager to be added
     * @param cReq
     */
    public void addRequest(CleanUpRequest cReq) {
        //Check that cReq has a name and timeCompleted that is unique to all cleanUpRequests
        if (validateRequest(cReq)){
                cleanUpManager.addRequest(cReq);
        }else errorController.showError("This request is invalid. Please make sure that the request has a unique name.");
    }

    /**
     * Takes a request and checks if it's valid (has all the needed information, not null, ...)
     * @param cReq
     * @return
     */
    private boolean validateRequest(CleanUpRequest cReq) {
        //Check that cReq has a name and timeCompleted that is unique to all cleanUpRequests
        if (cReq.getName() != null && cReq.getTimeCreated() != null && cReq.getNode() != null) {
            if (cleanUpManager.getCleanUpRequest(cReq.getName(), cReq.getTimeCreated()) == null) {
                return true;
            } else return false;
        } else {
            System.out.println("WHY?");
            return false;
        }
    }

    /**
     * returns a list of uncompleted requests of this type (from the manager)
     * @return
     */
    public ObservableList<CleanUpRequest> getRequests(){
        ObservableList requests =  FXCollections.observableArrayList();
        requests.addAll(cleanUpManager.getRequests());
        return requests;
    }

    /**
     * Deletes the given request through the specific request manager
     * @param cReq
     */
    public void deleteRequest(CleanUpRequest cReq){
        //First make sure the request exists, then delete it
        if (cleanUpManager.getCleanUpRequest(cReq.getName(), cReq.getTimeCreated()) != null){
            cleanUpManager.deleteRequest(cReq);
        } else errorController.showError("The selected request does not exist.");
    }

    /**
     * Updates the given request (only the name and timestamp must match the original request)
     * @param cReq
     */
    public void updateRequest(CleanUpRequest cReq){
        //Confirm that this already exists
        if (cleanUpManager.getCleanUpRequest(cReq.getName(), cReq.getTimeCreated()) != null){
            cleanUpManager.updateRequest(cReq);
        }
        else errorController.showError("This request does not exist in the database.");
    }

    /**
     * Marks the given request as done in the database
     * @param cReq
     */
    public void completeRequest(CleanUpRequest cReq){
        //First confirm that the request exists
        if (cleanUpManager.getCleanUpRequest(cReq.getName(), cReq.getTimeCreated()) != null){
            cleanUpManager.completeRequest(cReq);
        }
        else errorController.showError("This request does not exist in the database.");
    }

    public ObservableList<CleanUpRequest> getRequestsBy(User user){
        return cleanUpManager.getRequestsBy(user);
    }
}
