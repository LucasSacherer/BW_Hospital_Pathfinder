package Pathfinding;
import Database.PathfindingLogManager;
import Entity.Node;
import Entity.Edge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PathFindingFacade {
    private String currentPath;
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

    //changed to return string itself instead of a list of strings
    public String getDirections(List<Node> path){
        return textualDirections.toStringTextDir(textualDirections.getDir(path));
    }

    public void setPathfinder(PathFinder pathfinder){
        System.out.println(pathfinder.toString());
        this.pF = pathfinder;
    }

    void addPathtoLog (List<Node> path){
        pathfindingLogManager.addPathToLog(path);
    }

    public String getPathFinder() { return currentPath; }

    public void setPathFinder(String pathFinder) { currentPath = pathFinder; }
}
