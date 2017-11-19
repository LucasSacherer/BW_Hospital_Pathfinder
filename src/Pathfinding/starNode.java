package Pathfinding;

import Entity.Node;

public class starNode {
    Node node;
    double weight;
    starNode parent;

    public starNode(Node n, starNode p, double w){
        this.node = n;
        this.weight = w;
        this.parent = p;
    }


}
