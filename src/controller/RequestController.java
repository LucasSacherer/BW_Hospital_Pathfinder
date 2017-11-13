package controller;

import entity.RequestManager;
import entity.Request;
import java.util.List;

public class RequestController {
    final private RequestManager requestManager = new RequestManager();


    /**
     * Calls the getRequests() method of RequestManager
     * and adds the given Request element to the lists of Requests.
     * @param req (A request that is supposed to be added to the list of requests)
     */
    public void addRequest(Request req){
        RequestManager.getRequests().add(req);
    }

    /**
     *
     * @param req
     */
    //Should this be Boolean?
    protected void validateRequest(Request req){
        for (int i = 0; i <= RequestManager.getRequests().size(); i++){
            if (req.getName().equals(RequestManager.getRequests().get(i).getName())){
                //Give some error if same name
            }
            if (req.getType().equals(RequestManager.getRequests().get(i).getType()) && req.getNode().equals(RequestManager.getRequests().get(i)).getNode()){
                //Give some error if same type & node
            }
        }
    }

    /**
     * Calls the getRequests() method in RequestManager
     * @return The list of current requests in request attribute of RequestManager.
     */
    public List<Request> getRequests()
    {
        return RequestManager.getRequests();
    }

    /**
     * Calls the getRequests() method of RequestManager
     * and removes the given Request element from the lists of Requests.
     * It removes the request using the deleterequest() method in RequestManager
     * @param req
     */
    public void deleteRequest(Request req){
        RequestManager.deleteRequest(req);

    }

    public class sameNamedRequestException extends Exception{

    }
}
