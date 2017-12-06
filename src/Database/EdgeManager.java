package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.AdminLog;
import Entity.Edge;
import Entity.Node;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EdgeManager implements EntityManager{
    final private NodeManager nodeManager;
    private List<Edge> edges;
    private DatabaseGargoyle databaseGargoyle;
    private AdminLogManager adminLogManager;

    public EdgeManager(DatabaseGargoyle dbG, NodeManager nodeManager, AdminLogManager adminLogManager){
        databaseGargoyle = dbG;
        this.nodeManager = nodeManager;
        this.adminLogManager = adminLogManager;
        edges = new ArrayList<>();
    }

    /**
     * Updates list of edges to match what is currently in the database
     */
    public void update(){
        String startNodeID, endNodeID;
        edges.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM EDGE");
        try {
            while (rs.next()){
                startNodeID = rs.getString("STARTNODE");
                endNodeID = rs.getString("ENDNODE");
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
        if (!edges.contains(getEdge(e.getStartNode(),e.getEndNode()))){
            databaseGargoyle.createConnection();
            databaseGargoyle.executeUpdateOnDatabase("INSERT INTO EDGE VALUES ('"+
                    e.getStartNode().getNodeID()+"_"+e.getEndNode().getNodeID()+"','"+
                    e.getStartNode().getNodeID()+"','"+e.getEndNode().getNodeID()+"')");
            databaseGargoyle.destroyConnection();
            adminLogManager.addAdminLog(new AdminLog(databaseGargoyle.getCurrentUser().getUserID(),"Added Edge: " + e.getStartNode().getNodeID() + "_" + e.getEndNode().getNodeID(), LocalDateTime.now()));
        }
    }

    /**
     * Removes a given edge from the list of edges
     * @param e the edge to remove
     */
    public void removeEdge(Edge e) {
        if (e == null) return;
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM EDGE WHERE EDGEID = '" +
                e.getStartNode().getNodeID() + "_" + e.getEndNode().getNodeID() + "'");
        databaseGargoyle.destroyConnection();
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM EDGE WHERE EDGEID = '" +
                e.getEndNode().getNodeID() + "_" + e.getStartNode().getNodeID() + "'");
        databaseGargoyle.destroyConnection();
        adminLogManager.addAdminLog(new AdminLog(databaseGargoyle.getCurrentUser().getUserID(),"Removed Edge: " + e.getStartNode().getNodeID() + "_" + e.getEndNode().getNodeID(), LocalDateTime.now()));
    }

    /**
     *
     * @param start The starting node of the edge you want to remove
     * @param end The end node of the edge you want to remove
     * @return The cooresponding Edge that matches the starting and ending node given.
     */
    public Edge getEdge(Node start, Node end){
        for (Edge edge : edges){
            if (edge.getStartNode().getNodeID().equals(start.getNodeID()) && edge.getEndNode().getNodeID().equals(end.getNodeID())){
                return edge;
            }
            else if(edge.getEndNode().getNodeID().equals(start.getNodeID()) && edge.getStartNode().getNodeID().equals(end.getNodeID())){
                return edge;
            }
        }
        return null;
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

    /**
     * Uses a similar structure for getNeighbors() to get a list of all connecting edges from a given
     * input node and deletes them from the database.
     * @param node the node that will be deleted in future
     */
    public void removeNeighborEdges(Node node){
        List<Edge> connectedEdges = (edges.stream().filter(p -> p.getStartNode().getNodeID().equals(node.getNodeID()) ||
                p.getEndNode().getNodeID().equals(node.getNodeID())).collect(Collectors.toList()));

        for (int i = 0; connectedEdges.size() > i; i++){
            removeEdge(connectedEdges.get(i));
        }
    }

}
