package Request;

import Database.CleanUpManager;
import Database.FoodManager;
import Database.InterpreterManager;
import Entity.*;
import com.sun.org.apache.regexp.internal.RE;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    public ObservableList<Request> getAllRequestsByUser(User user){
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
        ObservableList fxResults = FXCollections.observableArrayList();
        fxResults.addAll(results);
        return fxResults;
    }

    /**
     * Takes any of the three request types and deletes if with the correct manager
     * @param request
     */
    public void deleteRequest(Request request){
        if (request instanceof CleanUpRequest){
            cleanUpManager.deleteRequest((CleanUpRequest) request);
        } else if (request instanceof InterpreterRequest){
            interpreterManager.deleteRequest((InterpreterRequest) request);
        } else if (request instanceof FoodRequest){
            foodManager.deleteRequest((FoodRequest) request);
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

    /**
     * Completes the given Request according to what type of request it is
     * @param request
     */
    public void completeRequests(Request request){
        if (request instanceof CleanUpRequest){
            cleanUpManager.completeRequest((CleanUpRequest) request);
        } else if (request instanceof InterpreterRequest){
            interpreterManager.completeRequest((InterpreterRequest) request);
        } else if (request instanceof FoodRequest){
            foodManager.completeRequest((FoodRequest) request);
        }
    }
}
