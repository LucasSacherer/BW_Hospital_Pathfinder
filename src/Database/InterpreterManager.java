package Database;

import Entity.InterpreterRequest;

import java.util.ArrayList;
import java.util.List;

public class InterpreterManager {

    private final List<InterpreterRequest> requests;

    public InterpreterManager(){
        requests = new ArrayList<>();
    }

    /**
     * Updates the internal list of uncompleted InterpreterRequests to match the database
     */
    public void updateRequests(){

    }

    /**
     * Updates the given request in the database (must have matching name and timestamp)
     * @param iReq
     */
    public void updateRequest(InterpreterRequest iReq){

    }

    /**
     * Sets the given request as completed in the database (does not delete but stops it from showing up in lists)
     * @param iReq
     */
    public void completeRequest(InterpreterRequest iReq){

    }

    /**
     * Deletes the given request from the database (This request will not be included in request reports)
     * @param iReq
     */
    public void deleteRequest(InterpreterRequest iReq){

    }

    /**
     * Returns the list of unfinished InterpreterRequest (Since the last updateRequests() call)
     * @return
     */
    public List<InterpreterRequest> getRequests(){
        return null;
    }

    /**
     * Adds the given request into the database as an uncompleted request
     * @param iReq
     */
    public void addRequest(InterpreterRequest iReq){

    }

    /**
     * Returns a list of the completed request directly from the database
     * @return
     */
    public List<InterpreterRequest> getCompleted(){
        return null;
    }
}
