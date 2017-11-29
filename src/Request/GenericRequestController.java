package Request;

import Database.CleanUpManager;
import Database.FoodManager;
import Database.InterpreterManager;
import Entity.*;
import com.sun.org.apache.regexp.internal.RE;

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

        cleanUpManager.updateRequests();
        foodManager.updateRequests();
        interpreterManager.updateRequests();

        for (CleanUpRequest req: cleanUpManager.getRequestsBy(user)){
            results.add(req);
            System.out.println("Adding " + req.getName());
        }
        for (FoodRequest req: foodManager.getRequestsBy(user)){
            results.add(req);
            System.out.println("Adding " + req.getName());
        }
        for (InterpreterRequest req: interpreterManager.getRequestsBy(user)) {
            results.add(req);
            System.out.println("Adding " + req.getName());
        }
        return results;
    }

    /**
     * Takes any of the three request types and deletes if with the correct manager
     * @param req
     */
    public void deleteRequest(Request req){
        if (req instanceof CleanUpRequest){
            cleanUpManager.deleteRequest((CleanUpRequest) req);
        } else if (req instanceof InterpreterRequest){
            interpreterManager.deleteRequest((InterpreterRequest) req);
        } else if (req instanceof FoodRequest){
            foodManager.deleteRequest((FoodRequest) req);
        }
    }

    /**
     * Gets all requests that line up with the specified department
     * @param department
     * @return
     */
    public List<Request> getAllRequestsByDepartment(String department){
        List<Request> results = new ArrayList<>();
        cleanUpManager.updateRequests();
        foodManager.updateRequests();
        interpreterManager.updateRequests();

        if (department.equalsIgnoreCase("janitorial")){
            for (CleanUpRequest req: cleanUpManager.getRequests()){
                results.add(req);
            }
        } else if (department.equalsIgnoreCase("food")){
            for (FoodRequest req: foodManager.getRequests()){
                results.add(req);
            }
        } else if (department.equalsIgnoreCase("interpreter")){
            for (InterpreterRequest req: interpreterManager.getRequests()){
                results.add(req);
            }
        }
        return results;
    }
}
