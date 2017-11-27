package Request;

import Database.CleanUpManager;
import Database.FoodManager;
import Database.InterpreterManager;
import Entity.Request;
import Entity.CleanUpRequest;
import Entity.FoodRequest;
import Entity.InterpreterRequest;

import java.util.ArrayList;
import java.util.List;

public class GenericRequestController {

    private final CleanUpManager cleanUpManager;
    private final FoodManager foodManager;
    private final InterpreterManager interpreterManager;

    public GenericRequestController(CleanUpManager cl, FoodManager fm, InterpreterManager im){
        cleanUpManager = cl;
        foodManager = fm;
        interpreterManager = im;
    }

    /**
     * Returns a list of all the requests in the system as a list of the Request Interface
     * @return
     */
    public List<Request> getAllRequests(){

        return null;
    }

    /**
     * Takes any of the three request types and deletes if with the correct manager
     * @param req
     */
    public void deleteRequest(Request req){

    }
}
