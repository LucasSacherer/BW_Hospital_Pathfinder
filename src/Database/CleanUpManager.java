package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.CleanUpRequest;
import Entity.Node;
import Entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CleanUpManager {
    private final List<CleanUpRequest> requests;
    private final DatabaseGargoyle databaseGargoyle;

    private final NodeManager nodeManager;
    private final UserManager userManager;

    public CleanUpManager(NodeManager nodeManager, UserManager userManager){
        this.nodeManager = nodeManager;
        this.userManager = userManager;

        requests = new ArrayList<>();
        databaseGargoyle = new DatabaseGargoyle();
    }

    /**
     * Updates the internal list of uncompleted CleanUpRequests to match the database
     */
    public void updateRequests(){
        String name, type, description;
        LocalDateTime timeCreated, timeCompleted;
        Node node;
        User user;

        requests.clear();
        nodeManager.updateNodes();
        userManager.updateUsers();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM CLEANUPREQUEST",
                databaseGargoyle.getStatement());
        try {
            while (rs.next()) {
                name = rs.getString("NAME");
                type = rs.getString("TYPE");
                description = rs.getString("DESCRIPTION");
                timeCreated = rs.getTimestamp("TIMECREATED").toLocalDateTime();
                timeCompleted = rs.getTimestamp("TIMECOMPLETED").toLocalDateTime();
                node = nodeManager.getNode(rs.getString("NODEID"));
                user = userManager.getUser(rs.getString("USERID"));

                requests.add(new CleanUpRequest(name, timeCreated, timeCompleted, type, description, node, user));
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

    }

    /**
     * Sets the given request as completed in the database (does not delete but stops it from showing up in lists)
     * @param cReq
     */
    public void completeRequest(CleanUpRequest cReq){

    }

    /**
     * Deletes the given request from the database (This request will not be included in request reports)
     * @param cReq
     */
    public void deleteRequest(CleanUpRequest cReq){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM CLEANUPREQUEST WHERE NAME = '" + cReq.getName() +
                "' AND TIMECREATED = '"+Timestamp.valueOf(cReq.getTimeCreated()).toString()+
                "'",databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        updateRequests();
    }

    /**
     * Returns the list of unfinished FoodRequest (Since the last updateRequests() call)
     * @return
     */
    public List<CleanUpRequest> getRequests(){
        return requests;
    }

    /**
     * Adds the given request into the database as an uncompleted request
     * @param cReq
     */
    public void addRequest(CleanUpRequest cReq){
        databaseGargoyle.createConnection();
        String creationTime = "'"+Timestamp.valueOf(cReq.getTimeCreated()).toString()+"'";
        String completionTime;
        if (cReq.getTimeCompleted() == null){
            completionTime = "NULL";
        }else{
            completionTime = "'"+Timestamp.valueOf(cReq.getTimeCompleted()).toString()+"'";
        }
        databaseGargoyle.executeUpdateOnDatabase("INSERT INTO CLEANUPREQUEST VALUES ('"+cReq.getName()+"'," +
                creationTime + "," + completionTime + ",'" + cReq.getType() + "','" + cReq.getDescription() + "','" +
                cReq.getNode().getNodeID() + "','" + cReq.getUser().getUserID() + "')",databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
    }

    /**
     * Returns a list of the completed request directly from the database
     * @return
     */
    public List<CleanUpRequest> getCompleted(){
        return null;
    }
}
