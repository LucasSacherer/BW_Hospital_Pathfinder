package Pathfinding;
import Entity.Node;
import java.util.ArrayList;

public interface PathFinder {

    ArrayList<Node> pathFind(Node loc1, Node loc2);
}
