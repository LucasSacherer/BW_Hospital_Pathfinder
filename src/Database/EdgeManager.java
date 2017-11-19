package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.Edge;
import Entity.Node;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EdgeManager {
    final private NodeManager nodeManager;
    private List<Edge> edges;
    private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

    public EdgeManager(NodeManager nodeManager){
        this.nodeManager = nodeManager;
        edges = new ArrayList<>();
    }

    /**
     * Updates list of edges to match what is currently in the database
     */
    public void updateEdges(){
        edges.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM EDGE");
        try {
            while (rs.next()){
                String startNodeID = rs.getString("STARTNODE");
                String endNodeID = rs.getString("ENDNODE");
                edges.add(new Edge(nodeManager.getNode(startNodeID), nodeManager.getNode(endNodeID)));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get edges from database!");
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Adds a given edge to the list of edges
     * @param e the edge to add
     */
    public void addEdge(Edge e){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("INSERT INTO EDGE VALUES ('"+
                e.getStartNode().getNodeID()+"_"+e.getEndNode().getNodeID()+"','"+
                e.getStartNode().getNodeID()+"','"+e.getEndNode().getNodeID()+"')");
        databaseGargoyle.destroyConnection();
        updateEdges();
    }

    /**
     * Removes a given edge from the list of edges
     * @param e the edge to remove
     */
    public void removeEdge(Edge e) {
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM EDGE WHERE EDGEID = '" +
                e.getStartNode().getNodeID() + "_" + e.getEndNode().getNodeID() + "'");
        databaseGargoyle.destroyConnection();
        updateEdges();
    }

    /**
     * Returns a list of all edges currently in the Java Edge Object (Not in database)
     * @return
     */
    public List<Edge> getAllEdges(){
        return edges;
    }

    /**
     * Finds all edges that a given node is connected to
     * @param node the node to find the neighbors of
     * @return List<Edge> the list of edges connected to the given node
     */
    public List<Node> getNeighbors(Node node){

        List<Edge> connectedEdges = (edges.stream().filter(p -> p.getStartNode().getNodeID().equals(node.getNodeID()) ||
                p.getEndNode().getNodeID().equals(node.getNodeID())).collect(Collectors.toList()));

        List<Node> neighbors = new ArrayList<>();
        for (Edge edge: connectedEdges){
            if (edge.getStartNode().getNodeID().equals(node.getNodeID())){
                neighbors.add(edge.getEndNode());
            }else{
                neighbors.add(edge.getStartNode());
            }
        }
        return neighbors;
    }

    /**
     * Finds the weight of the edge with the given start and end nodes
     * @param start the node at the start of the edge
     * @param end the node at the end of the edge
     * @return double the weight of the end
     */
    public double edgeWeight(Node start, Node end){
        Edge target;
        target = (edges.stream().filter(p -> (p.getStartNode().getNodeID().equals(start.getNodeID()) &&
                p.getEndNode().getNodeID().equals(end.getNodeID())) ||
                (p.getStartNode().getNodeID().equals(end.getNodeID()) &&
                        p.getEndNode().getNodeID().equals(start.getNodeID()))).findFirst()).get();
        return (double)target.getWeight();
    }
}
