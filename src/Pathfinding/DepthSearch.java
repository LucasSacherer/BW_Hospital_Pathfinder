package Pathfinding;

import Database.EdgeManager;
import Entity.Edge;
import Entity.Node;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Queue;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashMap;


public class DepthSearch implements PathFinder{
    EdgeManager edgeM;
    HashMap<String,String> closedSet = new HashMap<String,String>();
    List<Node> neighbors = new ArrayList<Node>();
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
        closedSet.clear();
        depthS.clear();
        depthS.add(sNode);
        boolean found = false;
        while ( !depthS.isEmpty())
        {
            current = depthS.pop();
            neighbors.clear();
            if(current.node.getNodeID().equals(end.getNodeID())){
                return reconstruct_path(current);
            }
            //get all the edges connected to the starting node
            neighbors = edgeM.getNeighbors(current.node);
            for(int i = 0; i < neighbors.size(); i++){
                found = closedSet.containsKey(neighbors.get(i).getNodeID());
                if (!found){
                    //add to queue
                    pathNode cNode = new pathNode(neighbors.get(i),current,0);
                    depthS.add(cNode);
                }

            }
            closedSet.put(current.node.getNodeID(),"0");
        }
        ArrayList<Node> failed = new ArrayList<>();
        System.out.println("failed");
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
