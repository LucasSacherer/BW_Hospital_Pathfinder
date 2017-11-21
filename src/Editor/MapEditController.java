package Editor;

import Database.EdgeManager;
import Database.NodeManager;
import Entity.*;
import Iteration1CodeWeMayNotNeed.MapManager;
import entity.Edge;
import entity.Node;

import java.awt.*;
import java.util.List;

public class MapEditController {

    EdgeManager edgeManager;
    NodeManager nodeManager;
    MapManager mapManager;

    public MapEditController(EdgeManager edgeManager, NodeManager nodeManager, MapManager mapManager) {
        this.edgeManager = edgeManager;
        this.nodeManager = nodeManager;
        this.mapManager = mapManager;
    }

    // returns the list of all nodes
    public List<Node> getAllNodes() {
        return nodeManager.getAllNodes();
    }

    // returns the list of all edges
    public List<Edge> getAllEdges() {
        return edgeManager.getAllEdges();
    }

    //
    public void uploadMap(Image string) {
    }

    // adds a new node to the lists of all nodes
    public void addNode(Node node) {
        nodeManager.addNode(node);
    }

    // update an already existing node
    public void editNode(Node node) {
        nodeManager.updateNode(node);
    }

    // deletes an already existing node
    public void deleteNode(Node node) {
        nodeManager.removeNode(node);
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
