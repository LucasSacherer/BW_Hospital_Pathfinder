package Pathfinding;
import Entity.Node;
import Entity.Edge;

import java.util.ArrayList;
import java.util.List;

public class PathFindingFacade {
    PathFinder pF;
    public PathFindingFacade(){};

    public List<Node> getPath(Node start, Node end){
        return pF.pathFind(start,end);
    }

    public List<String> getDirections(){
        List<String> na = new ArrayList<>();
        na.add("Not");
        na.add("Available");
        return na;
    }

    public void setPathfinder(PathFinder pathfinder){
        this.pF = pathfinder;
    }

}
