package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.FoodRequest;
import Entity.Node;
import Entity.User;
import sun.awt.FontDescriptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FoodManager {
    private final List<FoodRequest> requests;
    private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
    private NodeManager nodeManager = new NodeManager();
    private UserManager userManager = new UserManager();


    public FoodManager(){
        requests = new ArrayList<>();
    }

    /**
     * Updates the internal list of uncompleted FoodRequests to match the database
     */
    public void updateRequests(){
        String name, type, description, nodeID, userID;
        LocalDateTime timeCreated, timeCompleted;
        Node node;
        User user;

        requests.clear();
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM FOODREQUEST", databaseGargoyle.getStatement());
        try {
            while (rs.next()){
                name = rs.getString("NAME");
                timeCreated = rs.getTimestamp("TIMECREATED").toLocalDateTime();
                timeCompleted = rs.getTimestamp("TIMECOMPLETED").toLocalDateTime();
                type = rs.getString("TYPE");
                description = rs.getString("DESCRIPTION");
                nodeID = rs.getString("NODEID");
                userID = rs.getString("USERID");
                node = nodeManager.getNode(nodeID);
                user = userManager.getUser(userID);
                requests.add(new FoodRequest(name, timeCreated, timeCompleted, type, description, node, user, null));
                //TODO: change this so that order is not null
            }
        } catch (SQLException e) {
            System.out.println("Failed to get users from database!");
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Updates the given request in the database (must have matching name and timestamp)
     * @param fReq
     */
    public void updateRequest(FoodRequest fReq){

    }

    /**
     * Sets the given request as completed in the database (does not delete but stops it from showing up in lists)
     * @param fReq
     */
    public void completeRequest(FoodRequest fReq){

    }

    /**
     * Deletes the given request from the database (This request will not be included in request reports)
     * @param fReq
     */
    public void deleteRequest(FoodRequest fReq){

    }

    /**
     * Returns the list of unfinished FoodRequest (Since the last updateRequests() call)
     * @return
     */
    public List<FoodRequest> getRequests(){
        return null;
    }

    /**
     * Adds the given request into the database as an uncompleted request
     * @param fReq
     */
    public void addRequest(FoodRequest fReq){

    }

    /**
     * Returns a list of the completed request directly from the database
     * @return
     */
    public List<FoodRequest> getCompleted(){
        return null;
    }

    /**
     * FOR TESTING ONLY
     * @param name
     * @return
     */
    public FoodRequest getFoodRequest(String name) {
        for (FoodRequest req: requests){
            if (req.getName().equals(name)){
                return req;
            }
        }
        return null;
    }
}
