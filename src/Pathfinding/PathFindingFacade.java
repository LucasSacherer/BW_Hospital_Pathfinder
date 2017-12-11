package Pathfinding;
import Database.PathfindingLogManager;
import Entity.Node;
import Entity.Edge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PathFindingFacade {
    PathFinder pF;
    PathController pathController;
    PathfindingLogManager pathfindingLogManager = new PathfindingLogManager();

    public PathFindingFacade(){};

    public List<Node> getPath(Node start, Node end){
        System.out.println(pF);
        List<Node> path = pF.pathFind(start,end);
        addPathtoLog(path);
        return path;
    }
    public TextualDirections textualDirections = new TextualDirections();

    public List<String> getDirections(List<Node> path){
        return textualDirections.getDir(path);
    }

    public void setPathfinder(PathFinder pathfinder){
        System.out.println(pathfinder.toString());
        this.pF = pathfinder;
    }

    void addPathtoLog (List<Node> path){
        pathfindingLogManager.addPathToLog(path);
    }
}
