package entity;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;
import java.util.List;

public class Astar {
    EdgeManager edgeM;
    // The set of nodes already evaluated
    ArrayList<Node> closedSet = new ArrayList<Node>();
    ArrayList<Node> neighbors = new ArrayList<Node>();
    List<Node> connected = new ArrayList<Node>();
    // The set of currently discovered nodes that are not evaluated yet.
    boolean alreadfound = false;
    PriorityQueue<starNode> astarPQ = new PriorityQueue<>(2, weightComparator);
    private static Comparator<starNode> weightComparator = new Comparator<starNode>() {
        @Override
        public int compare(starNode s1, starNode s2) {
            return (int) (s1.weight - s2.weight);
        }
    };

    public Astar(EdgeManager e){
        this.edgeM = e;
    }

    public ArrayList<Node> Astar(Node loc1, Node loc2) {
        //define starting edge, just to get weight
        Edge startingEdge = new Edge(loc1,loc2);
        //initial starNode to store weight and null parent
        starNode start = new starNode(loc1, null, startingEdge.getWeight());
        // Initially, only the start node is known.
        astarPQ.add(start);

        //while loop that actually runs through all possible paths.
        while (astarPQ.isEmpty() != true) {
            starNode current = astarPQ.poll();
            //System.out.println(current.node.getNodeID());
            //Check if the goal has been reached yet
            if (current.node.getNodeID().equals(loc2.getNodeID())) {
                System.out.println("reached goal!");
                //if so trace back its path
                closedSet.clear();
                astarPQ.clear();
                return reconstruct_path(current);
            }
            //add the current path to the closedSet(Explored)
            closedSet.add(current.node);
            //get all the edges connected to the starting node
            connected = edgeM.getNeighbors(current.node);
            //add all the nodes from the connected edges to the neighbors list
            neighbors.clear();
            for (int i = 0; i< connected.size(); i ++) {
                neighbors.add((connected.get(i)));
                //System.out.println(connected.get(i).getNodeID());
            }
            //loop through the nieghbors
            for (int i = 0; i < neighbors.size(); i++) {
                //check if the node is in the closed set
                for (int j = 0; j < closedSet.size(); j++) {
                    if (neighbors.get(i).getNodeID().equals(closedSet.get(j).getNodeID())) {
                        alreadfound = true;
                        break;// Ignore the neighbor which is already evaluated.
                    } else {
                        alreadfound = false;

                    }
                }

                if (alreadfound == true){
                    //if the node is in the closed set ignore it



                }
                else {
                    //if it is not in the closed set add it the priority queue allong with its parent
                    Edge tempEdge = new Edge(current.node, neighbors.get(i));
                    starNode tempStar = new starNode(neighbors.get(i), current, tempEdge.getWeight());
                    astarPQ.add(tempStar);
                    //System.out.println(astarPQ.size());
                }

            }
        }
        //fail case returns empty list
        ArrayList<Node> failList = new  ArrayList<Node>();
        return failList;
    }
    //Trace back the nodes from goal to start
     private ArrayList<Node>  reconstruct_path(starNode current){
         ArrayList<Node> total_path = new ArrayList<Node>();
         total_path.add(current.node);
         while (current.parent != null) {
            current = current.parent;
            total_path.add(current.node);
         }
         return total_path;
     }
}
