package Pathfinding;

import Entity.Node;

import java.util.ArrayList;
import java.util.List;

public class PathController {
    final private PathFinder pF;

    public PathController(PathFinder pF){
        this.pF = pF;
    }

    /**
     * Gets the path from one node to another, returns null if the path is not found or if the nodes are invalid
     * @param start
     * @param end
     * @return A list of Nodes that is determined by findPath() in Astar class
     */
    public List<Node> findPath(Node start, Node end){
        if (validatePath(start,end)){
            return pF.pathFind(start,end);
        }else{
            return new ArrayList<>();
        }
    }


    /**
     * Takes two nodes and returns true if they are:
     * A. Not the same nodeID
     * B. On the the same floor
     * C. In the same building
     * D. Visitable Nodes
     *
     * @param start
     * @param end
     * @return true if all the above listed factors are satisfied.
     */

    protected  Boolean validatePath(Node start, Node end) {
        if (start == null || end == null) {
            return false;
        }else if (start.getNodeID().equals(end.getNodeID())){
            return false;
        } else if (!start.getFloor().equals(end.getFloor())) {
            return false;
        } else if (!start.getBuilding().equals(end.getBuilding())){
            return false;
        }else {
            return true;
        }
    }

}

