package controller;

import entity.*;

import java.awt.*;
import java.util.ArrayList;
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

    //// getAllNodes method
    // returns the list of all nodes
    public List<Node> getAllNodes() {

        return nodeManager.getAllNodes();
    }

    //// getAllEdges method
    // returns the list of all edges
    public List<Edge> getAllEdges() {
        return edgeManager.getAllEdges();
    }

    //// uploadMap method
    //
    public void uploadMap(Image string) {
    }

    //// addNode method
    // adds a new node to the lists of all nodes
    public void addNode(Node node) {
        nodeManager.addNode(node);
    }

    //// editNode method
    // update an already existing node
    public void editNode(Node node) {
        nodeManager.updateNode(node);
    }

    //// deleteNode method
    // deletes an already existing node
    public void deleteNode(Node node) {
        nodeManager.removeNode(node);
    }

    //// addEdge method
    // adds a new Edge
    public void addEdge(Edge edge) {
        edgeManager.addEdge(edge);
    }

    //// deleteEdge method
    // deletes an already existing edge
    public void deleteEdge(Edge edge) {
        edgeManager.removeEdge(edge);
    }
}
