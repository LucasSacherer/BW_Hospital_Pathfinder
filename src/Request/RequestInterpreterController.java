package Request;

import Database.InterpreterManager;
import Entity.InterpreterRequest;

import java.util.List;

public class RequestInterpreterController {
    private final InterpreterManager interpreterManager;

    public RequestInterpreterController(InterpreterManager im){
        interpreterManager = im;
    }

    /**
     * Takes a request, checks if it's valid, and then gives it to the manager to be added
     * @param iReq
     */
    public void addRequest(InterpreterRequest iReq){

    }

    /**
     * Takes a request and checks if it's valid (has all the needed information, not null, ...)
     * @param iReq
     * @return
     */
    private boolean validateRequest(InterpreterRequest iReq){
        return false;
    }

    /**
     * returns a list of uncompleted requests of this type (from the manager)
     * @return
     */
    public List<InterpreterRequest> getRequests(){
        return null;
    }

    /**
     * Deletes the given request through the specific request manager
     * @param iReq
     */
    public void deleteRequest(InterpreterRequest iReq){

    }

    /**
     * Updates the given request (only the name and timestamp must match the original request)
     * @param iReq
     */
    public void updateRequest(InterpreterRequest iReq){

    }

    /**
     * Marks the given request as done in the database
     * @param iReq
     */
    public void completeRequest(InterpreterRequest iReq){

    }
}
