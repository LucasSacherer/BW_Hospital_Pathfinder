package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EdgeManager {

    private List<Edge> edges;

    EdgeManager(){
        edges = new ArrayList<>();
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
