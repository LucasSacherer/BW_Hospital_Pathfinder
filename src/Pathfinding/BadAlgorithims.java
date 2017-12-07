package Pathfinding;

import Entity.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class BadAlgorithims implements PathFinder{

    @Override
    public ArrayList<Node> pathFind(Node loc1, Node loc2) {
        setWeight();
        return findPath(loc1, loc2);
    }

    abstract void setWeight();
    abstract ArrayList<Node> findPath(Node loc1, Node loc2);


}
