package Pathfinding;

import Database.PathfindingLogManager;
import Entity.Node;

import java.util.ArrayList;
import java.util.List;

public class PathController {
    final private PathFinder pF;
    PathfindingLogManager pathfindingLogManager = new PathfindingLogManager();


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
            List<Node> path = pF.pathFind(start,end);
            return path;
        }else{
            return new ArrayList<>();
        }

    }


    /**
     * Takes two nodes and returns true if they are:
     * A. Not the same nodeID
     * B. Has two nodes given
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
        } else {
            return true;
        }
    }



}

