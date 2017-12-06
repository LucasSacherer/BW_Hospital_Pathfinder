package Pathfinding;

import Entity.Node;

public class dNode {
    Node node;
    double gCost;
    dNode parent;

    public dNode(Node n, dNode p, double gC){
        this.node = n;
        this.parent = p;
        this.gCost = gC;
    }


}
