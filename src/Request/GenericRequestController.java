package Request;

import Database.CleanUpManager;
import Database.FoodManager;
import Database.InterpreterManager;
import Entity.*;

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
    public List<Request> getAllRequestsByUser(User user){
        List<Request> results = new ArrayList<>();
        results.addAll(cleanUpManager.getRequestsBy(user));
        results.addAll(foodManager.getRequestsBy(user));
        results.addAll(interpreterManager.getRequestsBy(user));
        return results;
    }

    /**
     * Takes any of the three request types and deletes if with the correct manager
     * @param req
     */
    public void deleteRequest(Request req){

    }
}
