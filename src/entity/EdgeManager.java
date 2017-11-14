package entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EdgeManager {

    final private NodeManager nodeManager;
    private List<Edge> edges;
    final String DBURL = "jdbc:derby://localhost:1527/bw_pathfinder_db;create=true;user=granite_gargoyle;password=wong";

    public EdgeManager(NodeManager nodeManager){
        this.nodeManager = nodeManager;
        edges = new ArrayList<>();
    }

    public void updateEdges(){
        edges.clear();

        try{
            Connection conn = DriverManager.getConnection(DBURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EDGE");
            while (rs.next()){
                String startNodeID = rs.getString("STARTNODE");
                String endNodeID = rs.getString("ENDNODE");
                edges.add(new Edge(nodeManager.getNode(startNodeID), nodeManager.getNode(endNodeID)));
            }
        }catch (SQLException ex){
            System.out.println("Failed to get edges from database!");
            ex.printStackTrace();
        }
    }

    /**
     * Adds a given edge to the list of edges
     * @param e the edge to add
     */
    public void addEdge(Edge e){
        try{
            Connection conn = DriverManager.getConnection(DBURL);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EDGE VALUES ('"+
                    e.getStartNode().getNodeID()+"-"+e.getEndNode().getNodeID()+"','"+
                    e.getStartNode().getNodeID()+"','"+e.getEndNode().getNodeID()+"')");
            stmt.close();
            conn.close();
        }catch (SQLException ex){
            System.out.println("Failed to add an edge to the database!");
            ex.printStackTrace();
            return;
        }

        updateEdges();
    }

    /**
     * Removes a given edge from the list of edges
     * @param e the edge to remove
     */
    public void removeEdge(Edge e) {
        try {
            Connection conn = DriverManager.getConnection(DBURL);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM EDGE WHERE EDGEID = '" +
                    e.getStartNode().getNodeID() + "-" + e.getEndNode().getNodeID() + "'");
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Failed to remove Edge from the database!");
            ex.printStackTrace();
            return;
        }

        updateEdges();
    }

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
        return (double)target.weight;
    }
}
