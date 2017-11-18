package controller;

import Entity.RequestManager;
import Entity.Request;
import javafx.collections.ObservableList;

public class RequestController {

    private final RequestManager requestmanager;

    public RequestController(RequestManager requestManager) {
        requestmanager = requestManager;
    }

    /**
     * Checks to see if a node that was input has either the same name as an already present Node
     * or if there already is a node of the same type at same location.
     * @param req the node that you are checking to see if it can be a valid node to add.
     */
    protected boolean validateRequest(Request req) {
        for (int i = 0; i < requestmanager.getRequests().size(); i++) {

            if (req.getName().equals(requestmanager.getRequests().get(i).getName())) {
                return false;
            }
            if (req.getType().equals(requestmanager.getRequests().get(i).getType())
                    && req.getNode().getNodeID().equals(requestmanager.getRequests().get(i).getNode().getNodeID())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks to make sure that the request that should be added is valid
     * If its valid Call the getRequests() method of RequestManager
     * and adds the given Request element to the lists of Requests.
     * @param req (A request that is supposed to be added to the list of requests)
     */
    public boolean addRequest(Request req) throws IllegalArgumentException {
        if (validateRequest(req)) {
            requestmanager.addRequest(req);
            return true;
        } else{
            return false;
        }
    }

    /**
     * Calls the getRequests() method in RequestManager
     * @return The list of current requests in request attribute of RequestManager.
     */
    public ObservableList<Request> getRequests() {
       return requestmanager.getRequests();
    }

    /**
     * Calls the getRequests() method of RequestManager
     * and removes the given Request element from the lists of Requests.
     * It removes the request using the deleterequest() method in RequestManager
     *
     * @param req
     */
    public void deleteRequest(Request req) {
        requestmanager.deleteRequest(req);

    }
}
