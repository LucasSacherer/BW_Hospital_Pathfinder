package Request;

import Database.InterpreterManager;
import Entity.ErrorController;
import Entity.InterpreterRequest;
import Entity.User;

import java.util.List;

public class RequestInterpreterController {
    private final InterpreterManager interpreterManager;
    private ErrorController errorController;
    public RequestInterpreterController(InterpreterManager im){
        interpreterManager = im;
    }

    /**
     * Takes a request, checks if it's valid, and then gives it to the manager to be added
     * @param iReq
     */
    public void addRequest(InterpreterRequest iReq){
        //Check that cReq has a name and timeCompleted that is unique to all cleanUpRequests
        if (validateRequest(iReq)){
            interpreterManager.addRequest(iReq);
        }else errorController.showError("This request is invalid. Please make sure that the request has a unique name.");
    }

    /**
     * Takes a request and checks if it's valid (has all the needed information, not null, ...)
     * @param iReq
     * @return
     */
    private boolean validateRequest(InterpreterRequest iReq){
        //Check that cReq has a name and timeCompleted that is unique to all cleanUpRequests
        if (iReq.getName() != null && iReq.getTimeCreated() != null && iReq.getNode()!=null && iReq.getLanguage()!=null){
            if (interpreterManager.getInterpreterRequest(iReq.getName(), iReq.getTimeCreated()) == null){
                return true;
            } else return false;
        } else return false;
    }

    /**
     * returns a list of uncompleted requests of this type (from the manager)
     * @return
     */
    public List<InterpreterRequest> getRequests(){
        return interpreterManager.getRequests();
    }

    /**
     * Deletes the given request through the specific request manager
     * @param iReq
     */
    public void deleteRequest(InterpreterRequest iReq){
        //First make sure the request exists, then delete it
        if (interpreterManager.getInterpreterRequest(iReq.getName(), iReq.getTimeCreated()) != null){
            interpreterManager.deleteRequest(iReq);
        } else errorController.showError("The selected request does not exist.");
    }

    /**
     * Updates the given request (only the name and timestamp must match the original request)
     * @param iReq
     */
    public void updateRequest(InterpreterRequest iReq){
        //Confirm that this already exists
        if (interpreterManager.getInterpreterRequest(iReq.getName(), iReq.getTimeCreated()) != null){
            interpreterManager.updateRequest(iReq);
        }
        else errorController.showError("This request does not exist in the database.");
    }

    /**
     * Marks the given request as done in the database
     * @param iReq
     */
    public void completeRequest(InterpreterRequest iReq){
        //First confirm that the request exists
        if (interpreterManager.getInterpreterRequest(iReq.getName(), iReq.getTimeCreated()) != null){
            interpreterManager.completeRequest(iReq);
        }
        else errorController.showError("This request does not exist in the database.");
    }

    public List<InterpreterRequest> getRequestsBy(User user){
        return interpreterManager.getRequestsBy(user);
    }
}
