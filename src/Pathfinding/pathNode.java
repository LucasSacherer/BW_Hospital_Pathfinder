package Pathfinding;

import Entity.Node;

public class pathNode {
    Node node;
    double cost;
    pathNode parent;

    public pathNode(Node n, pathNode p, double cost){
        this.node = n;
        this.cost = cost;
        this.parent = p;

    }
}
