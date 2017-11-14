package controller;

import entity.Astar;
import entity.Node;
import java.util.List;

public class PathController {
    private Astar astar;

    public PathController () {
        astar = new Astar();
    }


    /**
     * This method is simply a extension to Astar.findPath() and returns the same result.
     * @param start
     * @param end
     * @return A list of Nodes that is determined by findPath() in Astar class
     */
    public List<Node> findPath(Node start, Node end){
        return astar.findPath(start,end);
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

    protected static Boolean validatePath(Node start, Node end) {
        if (start.getNodeID().equals(end.getNodeID())){
            return false;
        } else if (!start.getFloor().equals(end.getFloor())) {
            return false;
        } else if (!start.getBuilding().equals(end.getBuilding())){
            return false;
        } else if (!start.isVisitable() || !end.isVisitable()) {
            return false;
        } else {
            return true;
        }

    }
}

