package controller;

import entity.RequestManager;
import entity.Request;
import java.util.List;

public class RequestController {
    final private RequestManager requestManager = new RequestManager();

    //Check if this needs to be
    public void addRequest(Request req){
        RequestManager.getRequests().add(req);
    }

    protected void validateRequest(Request req){

    }

    /**
     * Calls the getRequests() method in RequestManager
     * @return The list of current requests in request attribute of RequestManager.
     */
    public List<Request> getRequests()
    {
        return RequestManager.getRequests();
    }

    public void deleteRequest(Request req){
        RequestManager.deleteRequest(req);

    }
}
