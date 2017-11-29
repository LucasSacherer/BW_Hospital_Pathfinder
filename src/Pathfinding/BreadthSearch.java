package Pathfinding;

import Database.EdgeManager;
import Entity.Edge;
import Entity.Node;
import java.util.HashMap;
import java.util.*;

public class BreadthSearch implements PathFinder {
    EdgeManager edgeM;
    //ArrayList<String> closedSet = new ArrayList<String>();
    HashMap<String,String> closedSet = new HashMap<String,String>();
    List<Node> neighbors = new ArrayList<Node>();
    List<Node> openList = new ArrayList<>();
    Queue<pathNode> breadthQ = new LinkedList<pathNode>();
    pathNode current;

    public BreadthSearch(EdgeManager e){
        this.edgeM = e;
    }
    public ArrayList<Node> pathFind(Node start, Node end){
       return breadthSearch(start,end);
    }
    private ArrayList<Node> breadthSearch(Node start, Node end){
        pathNode sNode = new pathNode(start, null , 0);
        closedSet.clear();
        breadthQ.clear();
        breadthQ.add(sNode);
        boolean found = false;
        pathNode cNode;
        while ( !breadthQ.isEmpty())
        {
            current = breadthQ.poll();
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
                    //cNode = new pathNode(neighbors.get(i),current,cost);
                    breadthQ.add(new pathNode(neighbors.get(i),current,0));
                    //System.out.println(breadthQ);
                }

            }
            closedSet.put(current.node.getNodeID(),"0");
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
        //System.out.print(total_path);
        return total_path;
    }
}
