package Pathfinding;

import Database.EdgeManager;
import Entity.Edge;
import Entity.Node;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;


 public class Astar extends GoodAlgorithims {
    EdgeManager edgeM;
    // The set of nodes already evaluated
    ArrayList<String> closedSet = new ArrayList<String>();
    List<Node> neighbors = new ArrayList<Node>();
    List<Node> connected = new ArrayList<Node>();
    // The set of currently discovered nodes that are not evaluated yet.
    boolean alreadfound = false;

    PriorityQueue<starNode> astarPQ = new PriorityQueue<>(2, weightComparator);
    private static Comparator<starNode> weightComparator = new Comparator<starNode>() {
        @Override
        public int compare(starNode s1, starNode s2) {
            double s1Weight = s1.hCost + s1.gCost;
            double s2Weight = s2.hCost + s2.gCost;
            return (int) (s1Weight - s2Weight);
        }
    };

    public Astar(EdgeManager e){
        this.edgeM = e;
    }

    //public ArrayList<Node> pathFind(Node loc1, Node loc2){
    //    return Astar(loc1, loc2);
    //}

     starNode start;
     double hCost;
     Edge tempEdge;

    private ArrayList<Node> Astar(Node loc1, Node loc2) {
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
            closedSet.add(current.node.getNodeID());

            neighbors.clear();
            //get all the edges connected to the starting node
            neighbors = edgeM.getNeighbors(current.node);
            //add all the nodes from the connected edges to the neighbors list
            Boolean floorChange = false;

            //loop through the nieghbors
            for (int i = 0; i < neighbors.size(); i++) {
                //check if the node is in the closed set
                for (int j = 0; j < closedSet.size(); j++) {
                    if (neighbors.get(i).getNodeID().equals(closedSet.get(j))) {
                        alreadfound = true;
                        break;// Ignore the neighbor which is already evaluated.
                    } else{
                        alreadfound = false;

                    }
                }

                if (alreadfound == true){

                    //if the node is in the closed set ignore it
                }
                else {
                    //if it is not in the closed set add it the priority queue allong with its parent
                    Edge hEdge = new Edge(neighbors.get(i),loc2);
                    double hC = (double)(hEdge.getWeight());
                    Edge gEdge= new Edge(current.node,neighbors.get(i));
                    double distToNext = gEdge.getWeight();
                    double parentCost;
                    if (current.parent == null){
                        parentCost = 0;
                    }
                    else {
                        parentCost = current.parent.gCost;
                    }
                    double gCost = parentCost + distToNext ;
                    if(((loc2.getBuilding().equals("BTM") || loc2.getBuilding().equals("Shapiro")))||((neighbors.get(i).getBuilding().equals("BTM") || neighbors.get(i).getBuilding().equals("Shapiro")))){
                        if (!(loc2.getBuilding().equals(neighbors.get(i)))){
                            //if one is the tower and one is fransis ignore
                            if (!(neighbors.get(i).getFloor().equals("2"))) {
                                gCost += 100;
                            }
                        }
                    }
                    if (!(loc2.getFloor().equals(neighbors.get(i).getFloor()))){
                        //if one is the tower and one is fransis ignore
                            gCost += 200;
                    }

                    starNode tempStar = new starNode(neighbors.get(i), current, hC, gCost);
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
     private ArrayList<Node>  reconstruct_path(starNode curNode){
         ArrayList<Node> total_path = new ArrayList<Node>();
         total_path.add(curNode.node);
         while (curNode.parent != null) {
             curNode = curNode.parent;
            total_path.add(curNode.node);
         }
         return total_path;
     }

     @Override
     void init(Node loc1, Node loc2) {
         //define starting edge, just to get weight
         tempEdge = new Edge(loc1,loc2);
         hCost = (double)(tempEdge.getWeight());
         //initial starNode to store weight and null parent
         start = new starNode(loc1, null, hCost,0);
     }

     @Override
     ArrayList<Node> run(Node loc1, Node loc2) {
         return Astar(loc1,loc2);
     }
 }
