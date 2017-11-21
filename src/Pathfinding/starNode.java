package Pathfinding;

import Entity.Node;

public class starNode {
    Node node;
    double hCost;
    double gCost;
    starNode parent;

    public starNode(Node n, starNode p, double hC, double gC){
        this.node = n;
        this.hCost = hC;
        this.parent = p;
        this.gCost = gC;
    }


}
