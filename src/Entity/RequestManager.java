package Entity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class RequestManager {

    final private NodeManager nodeManager;
    private ObservableList<Request> requests;
    final String DBURL = "jdbc:derby://localhost:1527/bw_pathfinder_db;create=true;user=granite_gargoyle;password=wong";

    public RequestManager (NodeManager nodeManager){
        this.nodeManager = nodeManager;
        requests = FXCollections.observableArrayList();
    }

    //updates the requests list to match what is currently on the database
    public void updateRequests(){
        requests.clear();

        try{
            Connection conn = DriverManager.getConnection(DBURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM REQUEST");
            while (rs.next()){
                String name = rs.getString("NAME");
                LocalDateTime timestamp = rs.getTimestamp("TIME").toLocalDateTime();
                String type = rs.getString("TYPE");
                String description = rs.getString("DESCRIPTION");
                String nodeID = rs.getString("nodeID");
                Node node = nodeManager.getNode(nodeID);
                requests.add(new Request(type,name,description,node,timestamp));
            }
            stmt.close();
            conn.close();
        }catch (SQLException ex){
            System.out.println("Failed to update requests from the database!");
            ex.printStackTrace();
        }
    }

    //adds a request to the database
    public void addRequest(Request req){
        try{
            Connection conn = DriverManager.getConnection(DBURL);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO REQUEST VALUES ('"+req.getName()+"'," +
                    "'"+Timestamp.valueOf(req.getTimeStamp()).toString()+"'," +
                    "'"+req.getType()+"'," +
                    "'"+req.getDescription()+"'," +
                    "'"+req.getNode().getNodeID()+"')");
            stmt.close();
            conn.close();
        }catch (SQLException ex){
            System.out.println("Failed to add new request!");
            ex.printStackTrace();
            return;
        }

        updateRequests();
    }

    //deletes a request from the database
    public void deleteRequest(Request req){
        try {
            Connection conn = DriverManager.getConnection(DBURL);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM REQUEST WHERE NAME = '"+req.getName()+"'AND TIME = '"+
                    Timestamp.valueOf(req.getTimeStamp()).toString()+"'");
            stmt.close(); 
            conn.close();
        }catch (SQLException ex){
            System.out.println("Failed to remove request from the database!");
            ex.printStackTrace();
            return;
        }

        updateRequests();
    }

    public ObservableList<Request> getRequests() {
        return requests;
    }
}
