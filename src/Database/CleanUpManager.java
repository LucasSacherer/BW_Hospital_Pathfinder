package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.CleanUpRequest;
import Entity.Node;
import Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CleanUpManager implements EntityManager {
    private final List<CleanUpRequest> requests;
    private final DatabaseGargoyle databaseGargoyle;
    private final NodeManager nodeManager;
    private final UserManager userManager;

    public CleanUpManager(DatabaseGargoyle dbG, NodeManager nodeManager, UserManager userManager){
        this.nodeManager = nodeManager;
        this.userManager = userManager;

        requests = new ArrayList<>();
        databaseGargoyle = dbG;
    }

    /**
     * Updates the internal list of uncompleted CleanUpRequests to match the database
     */
    public void update(){
        String name, type, description;
        LocalDateTime timeCreated, timeCompleted;
        Node node;
        User user;

        requests.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM CLEANUPREQUEST");
        try {
            while (rs.next()) {
                name = rs.getString("NAME");
                type = rs.getString("TYPE");
                description = rs.getString("DESCRIPTION");
                timeCreated = rs.getTimestamp("TIMECREATED").toLocalDateTime();
                timeCompleted = rs.getTimestamp("TIMECOMPLETED").toLocalDateTime();
                node = nodeManager.getNode(rs.getString("NODEID"));
                user = userManager.getUser(rs.getString("USERID"));

                if(timeCreated.equals(timeCompleted)) {
                    requests.add(new CleanUpRequest(name, timeCreated, timeCompleted, type, description, node, user));
                }
            }
        }catch (SQLException ex){
            System.out.println("Failed to update the list of clean-up requests!");
            ex.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Updates the given request in the database (must have matching name and timestamp)
     * @param cReq
     */
    public void updateRequest(CleanUpRequest cReq){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE CLEANUPREQUEST SET " +
                "TYPE = '" + cReq.getType() + "', " +
                "DESCRIPTION = '" + cReq.getDescription() + "', " +
                "NODEID = '" + cReq.getNode().getNodeID() + "', " +
                "USERID = '" + cReq.getUser().getUserID() + "' " +
                "WHERE NAME = '" + cReq.getName() + "' " +
                "AND TIMECREATED = '" + Timestamp.valueOf(cReq.getTimeCreated()) + "'");
        databaseGargoyle.destroyConnection();
    }

    /**
     * Sets the given request as completed in the database (does not delete but stops it from showing up in lists)
     * @param cReq
     */
    public void completeRequest(CleanUpRequest cReq){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE CLEANUPREQUEST SET " +
                "TIMECOMPLETED = '" + Timestamp.valueOf(LocalDateTime.now()) + "' " +
                "WHERE NAME = '" + cReq.getName() + "' " +
                "AND TIMECREATED = '" + Timestamp.valueOf(cReq.getTimeCreated()) + "'");
        databaseGargoyle.destroyConnection();
    }

    /**
     * Deletes the given request from the database (This request will not be included in request reports)
     * @param cReq
     */
    public void deleteRequest(CleanUpRequest cReq){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM CLEANUPREQUEST WHERE NAME = '" + cReq.getName() +
                "' AND TIMECREATED = '"+Timestamp.valueOf(cReq.getTimeCreated()).toString()+ "'");
        databaseGargoyle.destroyConnection();
    }

    /**
     * Returns the list of unfinished FoodRequest (Since the last update() call)
     * @return
     */
    public List<CleanUpRequest> getRequests(){
        ArrayList<CleanUpRequest> results = new ArrayList<>();
        for (CleanUpRequest req: requests){
            results.add(req);
        }
        return results;
    }

    /**
     * Adds the given request into the database as an uncompleted request
     * @param cReq
     */
    public void addRequest(CleanUpRequest cReq){
        databaseGargoyle.createConnection();
        String creationTime = "'"+Timestamp.valueOf(cReq.getTimeCreated()).toString()+"'";
        databaseGargoyle.executeUpdateOnDatabase("INSERT INTO CLEANUPREQUEST VALUES ('"+cReq.getName()+"'," +
                creationTime + "," + creationTime + ",'" + cReq.getType() + "','" + cReq.getDescription() + "','" +
                cReq.getNode().getNodeID() + "','" + cReq.getUser().getUserID() + "')");
        databaseGargoyle.destroyConnection();
    }

    /**
     * Returns a list of the completed request directly from the database
     * @return
     */
    public ArrayList<CleanUpRequest> getCompleted(){
        ArrayList<CleanUpRequest> completed = new ArrayList<>();
        String name, type, description;
        LocalDateTime timeCreated, timeCompleted;
        Node node;
        User user;

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM CLEANUPREQUEST");
        try {
            while (rs.next()) {
                name = rs.getString("NAME");
                type = rs.getString("TYPE");
                description = rs.getString("DESCRIPTION");
                timeCreated = rs.getTimestamp("TIMECREATED").toLocalDateTime();
                timeCompleted = rs.getTimestamp("TIMECOMPLETED").toLocalDateTime();
                node = nodeManager.getNode(rs.getString("NODEID"));
                user = userManager.getUser(rs.getString("USERID"));

                if(!(timeCreated.equals(timeCompleted))) {
                    completed.add(new CleanUpRequest(name, timeCreated, timeCompleted, type, description, node, user));
                }
            }
        }catch (SQLException ex){
            System.out.println("Failed to get the list of completed clean-up requests!");
            ex.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        return completed;
    }

    /**
     * Returns the request with the given name and date
     * @param name
     * @param date
     * @return
     */
    public CleanUpRequest getCleanUpRequest(String name, LocalDateTime date){
        for (CleanUpRequest req: requests){
            if (req.getName().equals(name) && req.getTimeCreated().equals(date)){
                return req;
            }
        }
        return null;
    }

    /**
     * Gets all requests that were made by a specified user
     * @param user
     * @return
     */
    public ObservableList<CleanUpRequest> getRequestsBy(User user){
        ArrayList<CleanUpRequest> userRequests = new ArrayList<>();

        for (CleanUpRequest req : requests){
            if(req.getUser().getUserID().equals(user.getUserID())){
                userRequests.add(req);
            }
        }
        ObservableList fxUserRequests = FXCollections.observableArrayList();
        fxUserRequests.addAll(userRequests);
        return fxUserRequests;
    }

    /**
     * FOR TESTING ONLY: gets the request from the given name
     */
    public CleanUpRequest getRequestByName(String name){
        for (CleanUpRequest req: requests){
            if (req.getName().equals(name)){
                return req;
            }
        }
        return null;
    }

    public void printRequests(){
        for (CleanUpRequest req: requests){
            System.out.println(req.getName());
        }
        System.out.println("");
    }
}
