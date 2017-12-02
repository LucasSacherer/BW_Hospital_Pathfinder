package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.FoodRequest;
import Entity.Node;
import Entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FoodManager {
    private final List<FoodRequest> requests;
    private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
    private final NodeManager nodeManager;
    private final UserManager userManager;

    public FoodManager(NodeManager nodeManager, UserManager userManager){
        this.nodeManager = nodeManager;
        this.userManager = userManager;

        requests = new ArrayList<>();
        databaseGargoyle = new DatabaseGargoyle();
    }

    /**
     * Updates the internal list of uncompleted FoodRequests to match the database
     */
    public void updateRequests(){
        String name, type, description, nodeID, userID;
        LocalDateTime timeCreated, timeCompleted;
        Node node;
        User user;
        List<String> order;
        nodeManager.updateNodes();
        userManager.updateUsers();
        requests.clear();
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM FOODREQUEST",
                databaseGargoyle.getStatement());
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
                order = getFoodOrders(name, Timestamp.valueOf(timeCreated));

                if(timeCreated.equals(timeCompleted)) {
                    requests.add(new FoodRequest(name, timeCreated, timeCompleted, type, description, node, user, order));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get food requests from database!");
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Helper function for updateRequests()
     * Gets all food items that are in the database for the given request
     * @param requestName
     * @param timeCreated
     * @return
     */
    private ArrayList<String> getFoodOrders(String requestName, Timestamp timeCreated){
        DatabaseGargoyle dbg = new DatabaseGargoyle();
        dbg.createConnection();
        ArrayList<String> result = new ArrayList<>();
        ResultSet orders = dbg.executeQueryOnDatabase("SELECT * FROM FOODORDER " +
                "WHERE REQUESTNAME = '" + requestName + "' AND TIMECREATED = '" +timeCreated+ "'", dbg.getStatement());
        try {
            while (orders.next()){
                result.add(orders.getString("FOODITEM"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbg.destroyConnection();
        return result;
    }

    /**
     * Updates the given request in the database (must have matching name and timestamp)
     * @param fReq
     */
    public void updateRequest(FoodRequest fReq){
        //Update the request in the database
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE FOODREQUEST SET " +
                "TIMECOMPLETED = '" + Timestamp.valueOf(fReq.getTimeCompleted()) + "', " +
                "TYPE = '" + fReq.getType() + "', " +
                "DESCRIPTION = '" + fReq.getDescription() + "', " +
                "NODEID = '" + fReq.getNode().getNodeID() + "', " +
                "USERID = '" + fReq.getUser().getUserID() + "' " +
                "WHERE NAME = '" + fReq.getName() + "' " +
                "AND TIMECREATED = '" + Timestamp.valueOf(fReq.getTimeCreated()) + "'", databaseGargoyle.getStatement());

        //Remove all FOODORERS of this request in the DB and add the new ones
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM FOODORDER " +
                "WHERE REQUESTNAME = '" + fReq.getName() +"' " +
                "AND TIMECREATED = '" + Timestamp.valueOf(fReq.getTimeCreated()) + "'",
                databaseGargoyle.getStatement());
        for (String item: fReq.getOrder()){
            databaseGargoyle.executeUpdateOnDatabase("INSERT INTO FOODORDER VALUES (" +
                    "'" + fReq.getName() + "','"+ Timestamp.valueOf(fReq.getTimeCreated()) + "','" + item + "')",
                    databaseGargoyle.getStatement());
        }
        databaseGargoyle.destroyConnection();
        updateRequests();
    }

    /**
     * Sets the given request as completed in the database (does not delete but stops it from showing up in lists)
     * @param fReq
     */
    public void completeRequest(FoodRequest fReq){
        //Change the completed time of the database request
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE FOODREQUEST SET " +
                "TIMECOMPLETED = '" + Timestamp.valueOf(LocalDateTime.now()) + "' " +
                "WHERE NAME = '" + fReq.getName() + "' " +
                "AND TIMECREATED = '" + Timestamp.valueOf(fReq.getTimeCreated()) + "'", databaseGargoyle.getStatement());

        //Remove all food orders of this request
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM FOODORDER " +
                        "WHERE REQUESTNAME = '" + fReq.getName() +"' " +
                        "AND TIMECREATED = '" + Timestamp.valueOf(fReq.getTimeCreated()) + "'",
                databaseGargoyle.getStatement());
        updateRequests();
    }

    /**
     * Deletes the given request from the database (This request will not be included in request reports)
     * @param fReq
     */
    public void deleteRequest(FoodRequest fReq){
        databaseGargoyle.createConnection();
        //Remove all food orders of this request from the database
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM FOODORDER " +
                        "WHERE REQUESTNAME = '" + fReq.getName() +"' " +
                        "AND TIMECREATED = '" + Timestamp.valueOf(fReq.getTimeCreated()) + "'",
                databaseGargoyle.getStatement());
        //Remove the request from the database
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM FOODREQUEST " +
                        "WHERE NAME = '" + fReq.getName() +"' " +
                        "AND TIMECREATED = '" + Timestamp.valueOf(fReq.getTimeCreated()) + "'",
                databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        updateRequests();
    }

    /**
     * Returns the list of unfinished FoodRequest (Since the last updateRequests() call)
     * @return
     */
    public List<FoodRequest> getRequests(){
        return requests;
    }

    /**
     * Adds the given request into the database as an uncompleted request
     * @param fReq
     */
    public void addRequest(FoodRequest fReq){
        databaseGargoyle.createConnection();
        //Add the request to the FOODREQUEST table
        databaseGargoyle.executeUpdateOnDatabase("INSERT INTO FOODREQUEST VALUES (" +
                "'" + fReq.getName() + "'," +
                "'" + Timestamp.valueOf(fReq.getTimeCreated()) + "'," +
                "'" + Timestamp.valueOf(fReq.getTimeCreated()) + "'," +
                "'" + fReq.getType() + "'," +
                "'" + fReq.getDescription() + "'," +
                "'" + fReq.getNode().getNodeID() + "'," +
                "'" + fReq.getUser().getUserID() + "')", databaseGargoyle.getStatement());
        //Add all food items to the FOODORDER table
        if (fReq.getOrder() != null) {
            for (String item : fReq.getOrder()) {
                databaseGargoyle.executeUpdateOnDatabase("INSERT INTO FOODORDER VALUES (" +
                                "'" + fReq.getName() + "','" + Timestamp.valueOf(fReq.getTimeCreated()) + "','" + item + "')",
                        databaseGargoyle.getStatement());
            }
        }
        databaseGargoyle.destroyConnection();
        updateRequests();
    }

    /**
     * Returns a list of the completed request directly from the database
     * @return
     */
    public ArrayList<FoodRequest> getCompleted(){
        ArrayList<FoodRequest> completed = new ArrayList<>();
        updateRequests();
        String name, type, description, nodeID, userID;
        LocalDateTime timeCreated, timeCompleted;
        Node node;
        User user;
        List<String> order;
        nodeManager.updateNodes();
        userManager.updateUsers();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM FOODREQUEST",
                databaseGargoyle.getStatement());

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
                order = getFoodOrders(name, Timestamp.valueOf(timeCreated));

                if(!(timeCreated.equals(timeCompleted))) {
                    completed.add(new FoodRequest(name, timeCreated, timeCompleted, type, description, node, user, order));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get completed food requests from database!");
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        return completed;
    }

    /**
     * Returns the foodrequest from the given primary keys
     * @param name
     * @return
     */
    public FoodRequest getFoodRequest(String name, LocalDateTime date) {
        for (FoodRequest req: requests){
            if (req.getName().equals(name) && req.getTimeCreated().equals(date)){
                return req;
            }
        }
        return null;
    }

    /**
     * Gets all the requests that were made by the specified user
     * @param user
     * @return
     */
    public List<FoodRequest> getRequestsBy(User user){
        ArrayList<FoodRequest> userRequests = new ArrayList<>();
        updateRequests();

        for (FoodRequest req : requests){
            System.out.println(req.getName());
            if(req.getUser().getUserID().equals(user.getUserID())){
                userRequests.add(req);
            }
        }
        return userRequests;
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

    /**
     * FOR TESTING ONLY: gets the request from the given name
     */
    public FoodRequest getRequestByName(String name){
        for (FoodRequest req: requests){
            if (req.getName().equals(name)){
                return req;
            }
        }
        return null;
    }
}
