package Pathfinding;

import Database.EdgeManager;
import Entity.Edge;
import Entity.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


public class Dijkstra extends GoodAlgorithims {
   EdgeManager edgeM;
   // The set of nodes already evaluated
   ArrayList<String> closedSet = new ArrayList<String>();
   List<Node> neighbors = new ArrayList<Node>();
   List<Node> connected = new ArrayList<Node>();
   // The set of currently discovered nodes that are not evaluated yet.
   boolean alreadfound = false;

   PriorityQueue<dNode> dPQ = new PriorityQueue<>(2, weightComparator);
   private static Comparator<dNode> weightComparator = new Comparator<dNode>() {
       @Override
       public int compare(dNode d1, dNode d2) {
           return (int) (d1.gCost - d2.gCost);
       }
   };

   public Dijkstra(EdgeManager e){
       this.edgeM = e;
   }

    @Override
    void init(Node loc1, Node loc2) {
        //define starting edge, just to get weight
        tempEdge = new Edge(loc1,loc2);
        //initial dNode to store weight and null parent
        start = new dNode(loc1, null,0);
    }

    @Override
    ArrayList<Node> run(Node loc1, Node loc2) {
        return Dijkstra(loc1,loc2);
    }


    Edge tempEdge;
    dNode start;

    //Dijkstra is A* but without hCost
   private ArrayList<Node> Dijkstra(Node loc1, Node loc2) {

       // Initially, only the start node is known.
       dPQ.add(start);

       //while loop that actually runs through all possible paths.
       while (dPQ.isEmpty() != true) {
           dNode current = dPQ.poll();
           //System.out.println(current.node.getNodeID());
           //Check if the goal has been reached yet
           if (current.node.getNodeID().equals(loc2.getNodeID())) {
               System.out.println("reached dijkstra goal!");
               //if so trace back its path
               closedSet.clear();
               dPQ.clear();
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

                   dNode tempStar = new dNode(neighbors.get(i), current, gCost);
                   dPQ.add(tempStar);
                   //System.out.println(dPQ.size());
               }

           }
       }
       //fail case returns empty list
       ArrayList<Node> failList = new  ArrayList<Node>();
       return failList;
   }
   //Trace back the nodes from goal to start
    private ArrayList<Node>  reconstruct_path(dNode curNode){
        ArrayList<Node> total_path = new ArrayList<Node>();
        total_path.add(curNode.node);
        while (curNode.parent != null) {
            curNode = curNode.parent;
           total_path.add(curNode.node);
        }
        return total_path;
    }

}
