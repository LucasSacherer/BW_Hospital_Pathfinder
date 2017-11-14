package controller;

import entity.Node;
import entity.NodeManager;

public class ClickController {

    private final NodeManager nodemanager;

    public ClickController(NodeManager nodeManager){
        nodemanager = nodeManager;
    }

    public Node getNearestNode(int x, int y){
        return nodemanager.nearestNode(x,y);
    }
}
