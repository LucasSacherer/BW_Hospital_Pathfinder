package controller;

import Entity.Node;
import Entity.NodeManager;

public class ClickController {

    private final NodeManager nodemanager;

    public ClickController(NodeManager nodeManager){
        nodemanager = nodeManager;
    }

    public Node getNearestNode(int x, int y, String floor){
        return nodemanager.nearestNode(x,y,floor);
    }
}
