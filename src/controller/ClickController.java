package controller;

import entity.Node;
import entity.NodeManager;

public class ClickController {

    final private NodeManager nodemanager = new NodeManager();

    public Node getNearestNode(int x, int y){
        return nodemanager.nearestNode(x,y);
    }
}
