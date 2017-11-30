package Editor;

import Database.EdgeManager;
import Database.NodeManager;
import Database.SettingsManager;
import Entity.Node;
import Request.GenericRequestController;

public class NodeEditController {

    //should these be private? Or is package private fine?
    NodeManager nodeManager;
    SettingsManager settingsManager;
    EdgeManager edgeManager;
    GenericRequestController genericRequestController;

    public NodeEditController(NodeManager nodeM, SettingsManager setM, EdgeManager edgeM, GenericRequestController grm){
        this.nodeManager = nodeM;
        this.settingsManager = setM;
        this.edgeManager = edgeM;
        this.genericRequestController = grm;
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
        edgeManager.removeNeighborEdges(node);
        genericRequestController.deleteNodeRequests(node);
        nodeManager.removeNode(node);
    }

    public void setKioskLocation(Node defaultNode){
        settingsManager.setSetting("Default Node", defaultNode.getNodeID());
    }
}
