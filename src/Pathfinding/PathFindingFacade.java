package Pathfinding;
import Entity.Node;
import Entity.Edge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PathFindingFacade {
    PathController pathController;
    PathFinder pF;
    public PathFindingFacade(){};

    public List<Node> getPath(Node start, Node end){
        return pathController.findPath(start,end);
    }
    public TextualDirections textualDirections = new TextualDirections();

    public List<String> getDirections(List<Node> path){
        return textualDirections.getDir(path);
    }

    public void setPathfinder(PathFinder pathfinder){
        this.pF = pathfinder;
    }

}
