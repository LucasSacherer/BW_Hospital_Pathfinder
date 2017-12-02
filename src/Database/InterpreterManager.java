package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.InterpreterRequest;
import Entity.Node;
import Entity.User;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InterpreterManager {

    private final List<InterpreterRequest> requests;
    private final DatabaseGargoyle databaseGargoyle;

    private final NodeManager nodeManager;
    private final UserManager userManager;

    public InterpreterManager(NodeManager nodeManager, UserManager userManager){
        this.nodeManager = nodeManager;
        this.userManager = userManager;

        requests = new ArrayList<>();
        databaseGargoyle = new DatabaseGargoyle();
    }

    /**
     * Updates the internal list of uncompleted InterpreterRequests to match the database
     */
    public void updateRequests(){
        String name, type, description;
        LocalDateTime timeCreated, timeCompleted;
        Node node;
        User user;
        String language;

        requests.clear();
        nodeManager.updateNodes();
        userManager.updateUsers();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM INTERPRETERREQUEST",
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
                language = rs.getString("LANGUAGE");

                if(timeCreated.equals(timeCompleted)) {
                    requests.add(new InterpreterRequest(name, timeCreated, timeCompleted, type, description, node, user, language));
                }
            }
        }catch (SQLException ex){
            System.out.println("Failed to update the list of interpreter requests!");
            ex.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Updates the given request in the database (must have matching name and timestamp)
     * @param iReq
     */
    public void updateRequest(InterpreterRequest iReq){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE INTERPRETERREQUEST SET " +
                "TYPE = '" + iReq.getType() + "', " +
                "DESCRIPTION = '" + iReq.getDescription() + "', " +
                "LANGUAGE = '" + iReq.getLanguage() + "', " +
                "NODEID = '" + iReq.getNode().getNodeID() + "', " +
                "USERID = '" + iReq.getUser().getUserID() + "' " +
                "WHERE NAME = '" + iReq.getName() + "' " +
                "AND TIMECREATED = '" + Timestamp.valueOf(iReq.getTimeCreated()) + "'", databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        updateRequests();
    }

    /**
     * Sets the given request as completed in the database (does not delete but stops it from showing up in lists)
     * @param iReq
     */
    public void completeRequest(InterpreterRequest iReq){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE INTERPRETERREQUEST SET " +
                "TIMECOMPLETED = '" + Timestamp.valueOf(LocalDateTime.now()) + "' " +
                "WHERE NAME = '" + iReq.getName() + "' " +
                "AND TIMECREATED = '" + Timestamp.valueOf(iReq.getTimeCreated()) + "'", databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        updateRequests();
    }

    /**
     * Deletes the given request from the database (This request will not be included in request reports)
     * @param iReq
     */
    public void deleteRequest(InterpreterRequest iReq){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM INTERPRETERREQUEST WHERE NAME = '" + iReq.getName() +
                "' AND TIMECREATED = '" + Timestamp.valueOf(iReq.getTimeCreated()) +
                "'", databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        updateRequests();
    }

    /**
     * Returns the list of unfinished InterpreterRequest (Since the last updateRequests() call)
     * @return
     */
    public List<InterpreterRequest> getRequests(){
        return requests;
    }

    /**
     * Adds the given request into the database as an uncompleted request
     * @param iReq
     */
    public void addRequest(InterpreterRequest iReq){
        databaseGargoyle.createConnection();
        String creationTime = "'"+Timestamp.valueOf(iReq.getTimeCreated()).toString()+"'";
        databaseGargoyle.executeUpdateOnDatabase("INSERT INTO INTERPRETERREQUEST VALUES ('"+iReq.getName()+"'," +
                creationTime + "," + creationTime + ",'" + iReq.getType() + "','" + iReq.getDescription() + "','" +
                iReq.getLanguage() + "','" + iReq.getNode().getNodeID() + "','" + iReq.getUser().getUserID() + "')",databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        updateRequests();
    }

    /**
     * Returns a list of the completed request directly from the database
     * @return
     */
    public List<InterpreterRequest> getCompleted(){
        ArrayList<InterpreterRequest> completed = new ArrayList<>();
        updateRequests();

        String name, type, description;
        LocalDateTime timeCreated, timeCompleted;
        Node node;
        User user;
        String language;

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM INTERPRETERREQUEST",
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
                language = rs.getString("LANGUAGE");

                if(!(timeCreated.equals(timeCompleted))) {
                    completed.add(new InterpreterRequest(name, timeCreated, timeCompleted, type, description, node, user, language));
                }
            }
        }catch (SQLException ex){
            System.out.println("Failed to get completed interpreter requests!");
            ex.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        return completed;
    }

    /**
     * Returns a list of requests that were made by the specified user
     * @param user
     * @return
     */
    public List<InterpreterRequest> getRequestsBy(User user){
        ArrayList<InterpreterRequest> userRequests = new ArrayList<>();
        updateRequests();

        for (InterpreterRequest req : requests){
            System.out.println(req.getName());
            if(req.getUser().getUserID().equals(user.getUserID())){
                userRequests.add(req);
            }
        }
        return userRequests;
    }

    /**
     * Returns the request with the given name and date
     * @param name
     * @param date
     * @return
     */
    public InterpreterRequest getInterpreterRequest(String name, LocalDateTime date){
        for (InterpreterRequest req: requests){
            if (req.getName().equals(name) && req.getTimeCreated().equals(date)){
                return req;
            }
        }
        return null;
    }

    /**
     * FOR TESTING ONLY: gets the request from the given name
     */
    public InterpreterRequest getRequestByName(String name){
        for (InterpreterRequest req: requests){
            if (req.getName().equals(name)){
                return req;
            }
        }
        return null;
    }
}
