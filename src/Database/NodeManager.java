package Database;

import DatabaseSetup.DatabaseGargoyle;
import entity.Node;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NodeManager {
    private List<Node> nodes;
    private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

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
        int xcoord, ycoord;
        String floor, building, nodetype, longName, shortName;
        nodes.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM NODE", databaseGargoyle.getStatement());
        try {
            while(rs.next()){
                String nodeID = rs.getString("NODEID");
                xcoord = rs.getInt("XCOORD");
                ycoord = rs.getInt("YCOORD");
                floor = rs.getString("FLOOR");
                building = rs.getString("BUILDING");
                nodetype = rs.getString("NODETYPE");
                longName = rs.getString("LONGNAME");
                shortName = rs.getString("SHORTNAME");
                nodes.add(new Node(nodeID,xcoord,ycoord,floor,building,nodetype,longName,shortName));
            }
        } catch (SQLException e) {
            System.out.println("Failed to update the node manager!");
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        //Un-comment for testing this function
        //printOutContent();
    }

    /**
     * Returns the node with the given nodeID, returns null if the node could not be found
     * @param nodeID the ID of the node you want to get
     * @return the corresponding node
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

    // returns the list of all nodes
    public List<Node> getAllNodes(){
        return nodes;
    }

    /**
     * Returns a list of all nodes marked with the visitable flag
     * @return the list of visitable nodes
     */
    public List<Node> getVisitableNodes(){
        //filters to nodes that are only visitable
        return nodes.stream().filter(Node::isVisitable).collect(Collectors.toList());
    }

    /**
     * Adds the given node to the database and updates the node manager
     * @param node - node to be added to the database
     */
    public void addNode(Node node){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("INSERT INTO NODE VALUES ('"+node.getNodeID()+"',"+node.getXcoord()+","+
                node.getYcoord()+",'"+node.getFloor()+"','"+node.getBuilding()+"','"+node.getNodeType()+"','"+
                node.getLongName()+"','"+node.getShortName()+"','Team G')", databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();

        updateNodes();
    }

    /**
     * Deletes the given node (with matching nodeID) from the database and updates the node manager
     * @param node - node with the nodeID of the node to remove
     */
    public void removeNode(Node node){
        String nodeToRemove = node.getNodeID();
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM NODE WHERE NODEID = '"+nodeToRemove+"'", databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        updateNodes();
    }

    /**
     * Updates the node in the database with the matching nodeID to the given node object and updates the manager
     * @param node - updated node (must use an existing nodeID)
     */
    public void updateNode(Node node){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE NODE SET XCOORD = " + node.getXcoord() + "," +
                "YCOORD = " + node.getYcoord() + "," +
                "FLOOR = '" + node.getFloor() + "'," +
                "BUILDING = '" + node.getBuilding() + "'," +
                "NODETYPE = '" + node.getNodeType() + "'," +
                "LONGNAME = '" + node.getLongName() + "'," +
                "SHORTNAME = '" + node.getShortName() + "' WHERE NODEID = '" + node.getNodeID() + "'", databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        updateNodes();
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

    /**
     * Finds the node nearest to the given coordinates
     * @param x the x coordinate to search from
     * @param y the y coordinate to search from
     * @return result the closest node
     */
    public Node nearestNode(int x, int y, String floor){
        double newDistance;
        double nodeDistance = 10000000000.0;
        Node result = null;

        for (Node node: nodes){
            if (!node.getFloor().equals(floor)){
                continue;
            }
            newDistance = Math.sqrt(Math.abs((x - node.getXcoord()) * (x - node.getXcoord()) +
                    (y - node.getYcoord()) *  (y - node.getYcoord())));
            if (newDistance < nodeDistance){
                nodeDistance = newDistance;
                result = node;
            }
        }
        return result;
    }

    /**
     * Finds the node of a given type nearest to the given coordinate
     * @param x the x coordinate to search from
     * @param y the y coordinate to search from
     * @param type the type of node to search for
     * @return result the closest node of the specified type
     */
    public Node nearestLoc(int x, int y, String floor, String type){
        double newDistance;
        double nodeDistance = 10000000000.0;
        Node result = null;

        for (Node node: nodes){
            if(node.getNodeType().equals(type) && node.getFloor().equals(floor)) {
                newDistance = Math.sqrt(Math.abs((x - node.getXcoord()) * (x - node.getXcoord()) +
                        (y - node.getYcoord()) * (y - node.getYcoord())));
                if (newDistance < nodeDistance) {
                    nodeDistance = newDistance;
                    result = node;
                }
            }
        }
        return result;
    }
}
