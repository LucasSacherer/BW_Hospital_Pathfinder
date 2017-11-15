package controller;

import entity.NodeManager;
import entity.Node;

public class NearestPOIController {
    private NodeManager nm;

    public NearestPOIController(NodeManager nm) {
        this.nm = nm;
    }

    public Node nearestPOI(int x, int y, String floor, String type){
        //calls nearest location from node manager
        Node nearest = nm.nearestLoc(x,y,floor,type);
        return nearest;
    }

}
