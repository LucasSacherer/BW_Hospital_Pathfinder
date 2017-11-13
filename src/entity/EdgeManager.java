package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EdgeManager {

    private List<Edge> edges;

    public EdgeManager(){
        edges = new ArrayList<>();
    }

    /**
     * Adds a given edge to the list of edges
     * @param e the edge to add
     */
    public void addEdge(Edge e){
        edges.add(e);
    }

    /**
     * Removes a given edge from the list of edges
     * @param e the edge to remove
     */
    public void removeEdge(Edge e){
        edges.remove(e);
    }

    /**
     * Finds all edges that a given node is connected to
     * @param node the node to find the neighbors of
     * @return List<Edge> the list of edges connected to the given node
     */
    public List<Edge> getNeighbors(Node node){

        return (edges.stream().filter(p -> p.getStartNode() == node || p.getEndNode() == node).collect(Collectors.toList()));
    }

    /**
     * Finds the weight of the edge with the given start and end nodes
     * @param start the node at the start of the edge
     * @param end the node at the end of the edge
     * @return double the weight of the end
     */
    public double edgeWeight(Node start, Node end){

        Edge target;
        target = (edges.stream().filter(p -> p.getStartNode() == start && p.getEndNode() == end).findFirst()).get();
        return (double)target.weight;
    }
}
