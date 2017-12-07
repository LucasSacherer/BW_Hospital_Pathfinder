package Pathfinding;

import Entity.Node;

import java.util.ArrayList;

public abstract class GoodAlgorithims implements PathFinder{

    @Override
    public ArrayList<Node> pathFind(Node loc1, Node loc2) {
        init(loc1,loc2);
        return run(loc1,loc2);
    }

    abstract void init(Node loc1, Node loc2);
    abstract ArrayList<Node> run(Node loc1, Node loc2);
}
