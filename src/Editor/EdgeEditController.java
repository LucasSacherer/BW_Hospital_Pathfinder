package Editor;

import Database.EdgeManager;
import Entity.Edge;

import java.util.List;

public class EdgeEditController {

    EdgeManager edgeManager;

    EdgeEditController(EdgeManager edgem){
        this.edgeManager = edgem;
    }

    //returns all the edges
    public List<Edge> getAllEdges() {
        return edgeManager.getAllEdges();
    }

    // adds a new Edge
    public void addEdge(Edge edge) {
        edgeManager.addEdge(edge);
    }

    // deletes an already existing edge
    public void deleteEdge(Edge edge) {
        edgeManager.removeEdge(edge);
    }
}
