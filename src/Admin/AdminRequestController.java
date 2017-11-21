package Admin;

import Database.CleanUpManager;
import Database.FoodManager;
import Database.InterpreterManager;
import entity.Request;

import java.util.List;

public class AdminRequestController {

    private final CleanUpManager cleanUpManager;
    private final FoodManager foodManager;
    private final InterpreterManager interpreterManager;

    public AdminRequestController (CleanUpManager cl, FoodManager fm, InterpreterManager im){
        cleanUpManager = cl;
        foodManager = fm;
        interpreterManager = im;
    }

    /**
     * Deletes all the request of a specific type ("InterpreterRequest",...), a type of null deletes everything
     * @param type
     */
    public void clearAllRequests(String type){

    }

    /**
     * Returns a list of all the requests in the system as a list of the Request Interface
     * @return
     */
    public List<Request> getAllRequests(){
        return null;
    }

    /**
     * Takes any of the three request types and updates it with the correct manager
     * @param req
     */
    public void editRequest(Request req){

    }

    /**
     * Takes any of the three request types and deletes if with the correct manager
     * @param req
     */
    public void deleteRequest(Request req){

    }
}
