package entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NodeManager {

    final String DBURL = "jdbc:derby://localhost:1527/bw_pathfinder_db;create=true;user=granite_gargoyle;password=wong";

    private List<Node> nodes;

    public NodeManager(){
        nodes = new ArrayList<>();
    }

    /**
     * FOR TESTING ONLY, sets the value of the nodes in node manager instead of using the updateNodes method
     * @param start - starting point of the list
     */
    public  NodeManager(List<Node> start){
        nodes = start!=null ? start : new ArrayList<>();
    }

    /**
     * Updates the nodes in the node manager to match the nodes in the database
     */
    public void updateNodes(){
        nodes.clear();

        try {
            Connection conn = DriverManager.getConnection(DBURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM NODE");
            while(rs.next()){
                String nodeID = rs.getString("NODEID");
                int xcoord = rs.getInt("XCOORD");
                int ycoord = rs.getInt("YCOORD");
                String floor = rs.getString("FLOOR");
                String building = rs.getString("BUILDING");
                String nodetype = rs.getString("NODETYPE");
                String longName = rs.getString("LONGNAME");
                String shortName = rs.getString("SHORTNAME");
                String visitableS = rs.getString("VISITABLE");
                boolean visitable = visitableS.equals("true");

                nodes.add(new Node(nodeID,xcoord,ycoord,floor,building,nodetype,longName,shortName,visitable));
            }
        }catch (SQLException ex){
            System.out.println("Failed to update the node manager!");
            ex.printStackTrace();
        }

        //Un-comment for testing this function
        //printOutContent();
    }

    /**
     * Returns the node with the given nodeID, returns null if the node could not be found
     * @param nodeID
     * @return
     */
    public Node getNode(String nodeID){
        if(nodeID == null)
            return null;

        for(Node node : nodes){
            if (node.getNodeID().equals(nodeID)){
                return node;
            }
        }

        return null;
    }

    /**
     * For testing updateNodes only, should never really be used
     */
    private void printOutContent(){
        for (Node node: nodes){
            System.out.println(node.getNodeID() + "," + node.getXcoord() + "," + node.getYcoord() + "," +
                    node.getFloor() + "," + node.getBuilding() + "," + node.getNodeType() + "," + node.getLongName() +
                    "," + node.getShortName() + "," + node.isVisitable());
        }
    }
}
