package Pathfinding;

import Database.EdgeManager;
import Entity.Edge;
import Entity.Node;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Queue;
import java.util.List;
import java.util.PriorityQueue;


public class DepthSearch implements PathFinder{
    EdgeManager edgeM;
    ArrayList<String> closedSet = new ArrayList<String>();
    List<Node> neighbors = new ArrayList<Node>();
    List<Node> openList = new ArrayList<>();
    Stack<pathNode> depthS = new Stack<pathNode>();
    pathNode current;

    public DepthSearch(EdgeManager e){
        this.edgeM = e;
    }

    public ArrayList<Node> pathFind(Node start, Node end){
        return depthSearch(start,end);
    }

    private ArrayList<Node> depthSearch(Node start,Node end){
        pathNode sNode = new pathNode(start, null , 0);
        depthS.add(sNode);
        boolean found = false;
        while ( !openList.isEmpty())
        {
            current = depthS.pop();
            neighbors.clear();
            if(current.node.equals(start)){
                return reconstruct_path(current);
            }
            //get all the edges connected to the starting node
            neighbors = edgeM.getNeighbors(current.node);
            for(int i = 0; i < neighbors.size(); i++){
                for (int j = 0; j < closedSet.size(); j++){
                    if (neighbors.get(i).equals(closedSet.get(j))){
                        found = true;
                        break;
                    }
                    else found = true;
                }
                if (!found){
                    //cost is parent cost + cost to neighbor
                    Edge edge= new Edge(current.node,neighbors.get(i));
                    double distToNext = edge.getWeight();
                    double parentCost;
                    if (current.parent == null){
                        parentCost = 0;
                    }
                    else {
                        parentCost = current.parent.cost;
                    }
                    double cost = parentCost + distToNext ;
                    //add to queue
                    pathNode cNode = new pathNode(neighbors.get(i),current,cost);
                    depthS.add(cNode);
                }

            }
        }
        ArrayList<Node> failed = new ArrayList<>();
        return failed;
    }

    private ArrayList<Node> reconstruct_path(pathNode current){
        ArrayList<Node> total_path = new ArrayList<Node>();
        total_path.add(current.node);
        while (current.parent != null) {
            current = current.parent;
            total_path.add(current.node);
        }
        return total_path;
    }
}
