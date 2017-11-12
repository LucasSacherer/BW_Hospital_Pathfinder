package controller;

import entity.Node;
import java.util.List;

public class PathController {


    //public static List<Node> findPath(Node start, Node end){


    //}

    /**
     * Takes two nodes and returns true if they are:
     * A. Not the same node
     * B. On the the same floor
     * C. In the same building
     * D. not Null
     * @param start
     * @param end
     * @return true if all the above listed factors are satisfied.
     */

    //MAKE THIS PRIVATE AFTER TESTING
    //Add Null checking
    public static Boolean validatePath(Node start, Node end){
        if (start.getNodeID().equals(end.getNodeID()) || start.getFloor().equals(end.getFloor())|| start.getBuilding().equals(end.getBuilding())){
            return false;
        }
        else
            return true;
    }

}


