package entity;

import java.util.ArrayList;
import java.util.List;

public class NodeManager {

    private List<Node> nodes;

    public NodeManager(){
        nodes = new ArrayList<>();
    }

    /**
     * FOR TESTING ONLY
     * @param start - starting point of the list
     */
    public  NodeManager(List<Node> start){
        nodes = start!=null ? start : new ArrayList<>();
    }

    /**
     * Gets a node out of the database from the nodeID, return null if the node is not found.
     * @param nodeID ID of the desired node
     * @return node with the desired ID or null if the node is not found
     */
    public Node getNode(String nodeID){
        return null;
    }
}
