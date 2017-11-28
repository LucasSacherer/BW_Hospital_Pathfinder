package MapNavigation;

import Database.NodeManager;
import Entity.Node;

public class NearestPOIController {
    private NodeManager nm;

    public NearestPOIController(NodeManager nm) {
        this.nm = nm;
    }

    Node nearestPOI(int x, int y, String type){
        //calls nearest location from node manager
        Node nearest = nm.nearestLoc(x,y,type);
        return nearest;
    }
}