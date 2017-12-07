package Pathfinding;

import Database.EdgeManager;
import Entity.Node;

import java.util.List;

public abstract class GoodAlgorithms {

    EdgeManager edgeManager;

    abstract List<Node> pathFind(Node startNode, Node endNode);

    List<Node> findPath(Node startNode, Node endNode){
        return pathFind(startNode, endNode);
    }
}
