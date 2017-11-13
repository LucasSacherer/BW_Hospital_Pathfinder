package entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EdgeManager {

    private NodeManager nodeManager;
    private List<Edge> edges;
    final String DBURL = "jdbc:derby://localhost:1527/bw_pathfinder_db;create=true;user=granite_gargoyle;password=wong";

    EdgeManager(NodeManager nodeManager){
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

    public void addEdge(Edge e){
        edges.add(e);
    }

    public void removeEdge(Edge e){
        edges.remove(e);
    }

    public List<Edge> getNeighbors(Node node){

        //filter to find any edge that starts or end with this node
        return (edges.stream().filter(p -> p.getStartNode() == node || p.getEndNode() == node).collect(Collectors.toList()));
    }

    public double edgeWeight(Node start, Node end){

        //find the edge in the list with the given start & end node, return the weight
        Edge target;
        target = (edges.stream().filter(p -> p.getStartNode() == start && p.getEndNode() == end).findFirst()).get();
        return (double)target.weight;
    }
}
